package Framework;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.map.HashedMap;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Purpose - run this class to handle insufficient storage issue
 * This class should be run as a standalone (without seetest/cloud) - will uninstall any application with a UID higher than the userIDCuttoff
 * Followed by rebooting the device
 */
public class UninstallAppsWithHighUID {

    //private inner class to hold cloud server info

    private static class CloudServerClass{
        String HOST = "qa-win2016.experitest.com";
        String PORT = "443";
        String USER = "khaleda";
        String PASS = "Experitest2012";
        boolean SECURED = true;
        boolean SINGLE_PORT = true;


        public boolean isSinglePort() {
            return SINGLE_PORT;
        }
        public String getHost() {
            return HOST;
        }

        public String getPort() {
            return PORT;
        }

        public String getUSER() {
            return USER;
        }

        public String getPASS() {
            return PASS;
        }

        public boolean isSecured() {
            return SECURED;
        }



    }
    //devices to Clean
    static List<String> devicesList = new ArrayList<>();
    static boolean specificDevices = false;
    static boolean runOverLocalDevices = true;
    static boolean runOverRemoteDevices = false;
    static int userIDCuttoff = 11000;
    //    static boolean isCloudSinglePort = true;
    //    static boolean isCloudSecured = true;
    //    static String cloudServerPort = "443";
    //    static String cloudServerHost = "qa-win2016.experitest.com";
    public static CloudServerClass cloudServer;
    public static Map<String,String> agentHostToAgentMapping = new HashMap<>();
    public static Map<String,String> deviceToAgentMapping = new HashMap<>();
    public static Map<String,String> deviceUDIDToID = new HashMap<>();

    public enum returnType{
        LIST, STRING, NONE
    }

    public static void updateDevicesToClean(){
        devicesList.add("0123456789ABCDEF");
    }

    public static void main(String[] args) throws Exception {
        if(specificDevices || runOverRemoteDevices){
            cloudServer = new CloudServerClass();
            if(specificDevices) {
                updateDevicesToClean();
                runOverSpecificDevices();
            }else{
                runOverRemoteDevices();
            }
        }
        if(runOverLocalDevices) {
            handleLocalDevices();
        }

    }

    private static void runOverSpecificDevices() throws Exception {
        updateAgentsMapping();
        //this is repsonsible for updating device udid to id map
         getDevicesMap();


        Map<String,String> deviceToAgentPath = new HashMap<>();
        for(String device: devicesList){
            if(!deviceUDIDToID.containsKey(device)){
                throw new Exception("Device " + device + " exists in device map, but not in udid to id map, was it reserved while running the program?");
            }
            deviceToAgentPath.put(device, agentHostToAgentMapping.get(deviceToAgentPath.get(device)));
        }
        reserveAndCleanDevice(deviceToAgentPath);
    }

    // iterate over remote devices - can't run in parallelm not supported in RMDB
    private static void runOverRemoteDevices() throws IOException, InterruptedException {
        updateAgentsMapping();
        Map<String, String> devices = getDevicesMap();
        reserveAndCleanDevice(devices);
    }

    private static void reserveAndCleanDevice(Map<String, String> devices) throws IOException, InterruptedException {
        for(String device: devices.keySet()){
            doPost("/devices/" + deviceUDIDToID.get(device) +"/reservations/new", buildReserveQuery(),"/api/v1");
            new Thread(() -> {
                runCMD(returnType.NONE, true, "C:\\Program Files (x86)\\Experitest\\SeeTestRemoteDebugging\\rdb.exe", "connect", "-udid", device,
                        "-ip", cloudServer.getHost(),"-port", cloudServer.getPort(),cloudServer.isSinglePort() ? "-agentPath" : "", cloudServer.isSinglePort() ? devices.get(device) : "",
                        cloudServer.isSecured()? "-secure" : "");
            }).start();
            while(!runCMD(returnType.STRING, false, "adb", "devices").get(0).contains(device)){
                Thread.sleep(5000);
                System.out.println("waiting for rmdb to start");
            }
            List<String> apps = getApplicationsList(device);
            uninstallUnneededApps(apps, device);
            rebootDevice(device);
            doPost("/devices/" + deviceUDIDToID.get(device) +"/release", "","/api/v1");
        }
    }

    private static void handleLocalDevices() {
        List<String> devices = getDevicesList();
        for(String device : devices)
        new Thread(() -> {
            List<String> apps = getApplicationsList(device);
            uninstallUnneededApps(apps, device);
            //This should be the last deleted app before reboot - so that the cloud won't reinstall the app before device is rebooted
            uninstallApp("com.experitest.uiautomator.test", device);
//            rebootDevice(device);
        }).start();
    }

    /**
     * Iterates over all apps installed on the device
     * If an app has a userID higher than userIDCuttoff - the app will be deleted
     */
    public static void uninstallUnneededApps(List<String> apps, String device){
        for(String app:apps){
            try {
                if(Integer.parseInt(getAppUID(app, device)) > userIDCuttoff){
                    System.out.println("app user id " + getAppUID(app,device));
                    System.out.println("user id cutoff " + userIDCuttoff);
                    uninstallApp(app, device);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public static void rebootDevice(String device){
        System.out.println("rebooting the device " + device);
        runCMD(returnType.NONE, false, "adb", "-s" , device , "shell", "reboot");
    }

    private static void uninstallApp(String app, String device){
        System.out.println("uninstalling the app " + app);
        runCMD(returnType.NONE, false , "adb", "-s" , device , "uninstall", app);
    }

    /**
     * runs a command line
     * Returns:
     * List - each line will be added to a list
     * String - The result will be in one String, located in the first element in the list
     * None - no the result of the command line will be ignored
     */
    private static List<String> runCMD(returnType returnType, boolean infiniteTimeout, String... params) {
        long startTime = System.currentTimeMillis();
        for (String param:
             params) {
            System.out.println(param);

        }
        ProcessBuilder builder = new ProcessBuilder(params);
        String result = "";
        List<String> resList = new ArrayList<>();
        builder.redirectErrorStream();
        Process process = null;
        try {
            process = builder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            InputStream is = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line = "";
            while (((line = reader.readLine()) != null) || infiniteTimeout) {
                if(infiniteTimeout) {
                    System.out.println("still alive + " + String.join(" ", params));
                }else{
                    //timeout is 60 seconds
                    if(((System.currentTimeMillis() - startTime) / 1000) > 60){
                        process.destroy();
                        return resList;
                    }
                }
                if (line.length() == 0){
                    continue;
                }
                if(returnType.equals(returnType.LIST)){
                    resList.add(line);
                }
                else if(returnType.equals(returnType.STRING)){
                    result += line + "\n";
                }
            }
        } catch (Exception e) {
            process.destroy();
        }
        if(returnType.equals(returnType.STRING)) {
            resList.add(result);
        }
        return resList;
    }

    //returns a list of all connected devices
    private static List<String> getDevicesList() {
        List<String> devices = new ArrayList<String>();
        List<String> lines = runCMD(returnType.LIST,false, "adb", "devices");
        for (int i = 1; i < lines.size(); i++) {
            String newLine = (lines.get(i).split("device")[0].trim());
            if (newLine.length() > 2) {
                devices.add(newLine);
            }
        }
        System.out.println("list of devices:");
        for (String device : devices){
            System.out.println("current device is " + device);
        }
        return devices;
    }

    // returns the device's installed applications
    private static List<String> getApplicationsList(String device){
        List<String> apps = new ArrayList<String>();
        List<String> lines = runCMD(returnType.LIST, false, "adb", "-s" , device, "shell", "pm", "list", "packages");
        for (int i = 0; i < lines.size(); i++) {
            System.out.println("curr line is "+ lines.get(i));
            String newLine = (lines.get(i).split(":")[1].trim());
            if (newLine.length() > 2) {
                apps.add(newLine);
            }
        }
        System.out.println("list of apps in device " + device + ":");
        for (String app : apps){
            System.out.println("current app is " + app);
        }
        return apps;
    }

    //returns the current app userID
    private static String getAppUID(String app, String device){
        String userID = "";
        List<String> lines = runCMD(returnType.LIST, false, "adb", "-s" , device, "shell", "dumpsys", "package", app);
        for(String line:lines){
            if(line.trim().startsWith("userId")){
                userID = line.trim().split("userId=")[1].trim().split(" ")[0];
                break;
            }
        }
        System.out.println("Got the userID " + userID + " for the app " + app);
        return userID;
    }

    private static Map<String,String> getDevicesMap() throws IOException {
        Map<String,String> devicesToHostNameMap = getAvailableDevicesMap();


        return devicesToHostNameMap;
    }


    private static Map<String,String> getAvailableDevicesMap() throws IOException {
        String result = getFromCloudAPI("/api/v1", "/devices");
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Map<String, Object> obj = new Gson().fromJson(
                jsonObject.toString(), new TypeToken<HashMap<String, Object>>() {}.getType()
        );
        List<Object> data = (List<Object>) obj.get("data");
        Object[] devicesArray = GetFilteredDevices(data);
        Map tempDevicesMap = new HashedMap();
        for (int i = 0; i < devicesArray.length; i++) {
            String[] devicePropertiesArray = devicesArray[i].toString().replace("{", "").replace("]", "").split(",");
            int j = 0;
            boolean udidFlag = false;
            String udid = null;
            String agent = null;
            boolean agentFlag = false;
            String id = null;
            boolean idFlag = false;
            while (j < devicePropertiesArray.length && !((udidFlag && agentFlag && idFlag))) {
                if (devicePropertiesArray[j].contains("udid")) {
                    udid = devicePropertiesArray[j].replace("udid=", "").trim();
                    udidFlag = true;
                }
                else if (devicePropertiesArray[j].contains("agentName")) {
                    agent = devicePropertiesArray[j].replace("agentName=", "").trim();
                    agentFlag = true;
                }
                else if (devicePropertiesArray[j].trim().startsWith("id")) {
                    idFlag = true;
                    id = devicePropertiesArray[j].trim().replace("id=", "").trim();
                }
                j++;
            }
            System.out.println("udid " + udid + " id " + id);
            deviceUDIDToID.put(udid,id);
            deviceToAgentMapping.put(udid, agent);
            System.out.println("device udid: " + udid + " agent: " + agentHostToAgentMapping.get(agent));
            tempDevicesMap.put(udid, agentHostToAgentMapping.get(agent));
        }

        return tempDevicesMap;
    }

    private static Object[] GetFilteredDevices(List<Object> data) {
        return data
                .stream()
                .filter(device -> ((Map) device).get("displayStatus").equals("Available") && ((Map) device).get("deviceOs").equals("Android"))
                .toArray();
    }

    //gets devices json using V1/V2 protocols
    private static String getFromCloudAPI(String webPage, String entity) throws IOException {
        String apiURL = (cloudServer.SECURED ? "https://" : "http://") + cloudServer.getHost() + ":" + cloudServer.getPort() + webPage;
//        System.out.println(apiURL+entity);
        URL url = new URL(apiURL + entity);
        URLConnection urlConnection = url.openConnection();

        //Authentication info
        byte[] authEncBytes = Base64.encodeBase64((cloudServer.getUSER() + ":" + cloudServer.getPASS()).getBytes());
        String authStringEnc = new String(authEncBytes);

        urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);
        InputStream is = urlConnection.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        int numCharsRead;
        char[] charArray = new char[1024];
        StringBuffer sb = new StringBuffer();
        while ((numCharsRead = isr.read(charArray)) > 0) {
            sb.append(charArray, 0, numCharsRead);
        }
        String result = sb.toString();
        if (((HttpURLConnection) urlConnection).getResponseCode() < 300) {
            return result;
        } else {
            throw new RuntimeException(result);
        }
    }


    /**
     *  uses cloud api to get the agent mapping in reverse proxy - useful for remote debugging the device
     * @throws IOException
     */
    private static void updateAgentsMapping() throws IOException {
        String result = getFromCloudAPI("/api/v2", "/agents");
        //Added these so that json object will be able to handle the result
        result = "{\"status\":\"SUCCESS\",\"data\":" + result + ",\"code\":\"OK\"}";
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Map<String, Object> obj = new Gson().fromJson(
                jsonObject.toString(), new TypeToken<HashMap<String, Object>>() {}.getType()
        );
        List<Object> data = (List<Object>) obj.get("data");
        for (int i = 0; i < data.size(); i++) {
            String[] agentProps = data.get(i).toString().replace("{", "").replace("]", "").split(",");
            int j = 0;

            boolean agentNameFound = false;

            String agentName = null;
            String agentID = null;
            boolean agentIDFlag = false;

            while (j < agentProps.length && !((agentNameFound && agentIDFlag))) {
                if (agentProps[j].contains("name")) {
                    agentName = agentProps[j].replace("name=", "").trim();
                    agentNameFound = true;
                }
                if (agentProps[j].contains("id=")) {
                    agentID = agentProps[j].replace("id=", "").trim();
                    agentIDFlag = true;
                }
                j++;
            }
            agentID = agentID.contains(".") ? agentID.replace(".0", "") : agentID;
            System.out.println("agent name " + agentName + ", agent id: " + agentID);
            agentHostToAgentMapping.put(agentName, "agent-" + agentID);
        }

    }


    private static String buildReserveQuery() throws UnsupportedEncodingException {
        String timeStamp  = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(Calendar.getInstance().getTime());
        String endTime    = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(System.currentTimeMillis() + 1000 * 360);
        String charset = StandardCharsets.UTF_8.name();

        String query = String.format("clientCurrentTimestamp=%s&start=%s&end=%s",
                URLEncoder.encode(timeStamp, charset),
                URLEncoder.encode(timeStamp, charset),
                URLEncoder.encode(endTime, charset));
        return query;
    }

    /**
     * @param entity can be "/users" / "/projects" / "/devices" etc
     * String query = String.format("param1=%s&param2=%s", URLEncoder.encode(param1, charset), URLEncoder.encode(param2, charset));
     */
    protected static String doPost(String entity , String query, String webPage) throws IOException {

        //Auth info
        byte[] authEncBytes = Base64.encodeBase64((cloudServer.getUSER() + ":" + cloudServer.getPASS()).getBytes());
        String authStringEnc = new String(authEncBytes);

        String apiURL = (cloudServer.SECURED ? "https://" : "http://") + cloudServer.getHost() + ":" + cloudServer.getPort() + webPage;
        URL url = new URL(apiURL + entity);
        URLConnection urlConnection = url.openConnection();
        urlConnection.setDoOutput(true);
        urlConnection.setRequestProperty("Accept", "application/json");
        urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + StandardCharsets.UTF_8.name());
        urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);

        OutputStream output = urlConnection.getOutputStream();
        output.write(query.getBytes(StandardCharsets.UTF_8.name()));

        HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;

        InputStream stream = null;

        if (httpURLConnection.getResponseCode() >= 400) {
            stream = httpURLConnection.getErrorStream();

        } else {
            stream = httpURLConnection.getInputStream();
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(stream));
        String inputLine;
        StringBuffer responseBuffer = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            responseBuffer.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(responseBuffer.toString());
        boolean isResponseValid = httpURLConnection.getResponseCode() < 300;
        Assert.assertTrue("Did not get valid response", isResponseValid);
        return responseBuffer.toString();
    }
}
