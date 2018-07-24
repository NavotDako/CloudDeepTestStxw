package MyMain;

import Framework.QuadMap;
import Utils.Utilities;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * This class holds all connected devices information
 * Next device to test can be gotten by getDevice(os), and released by end of test by release device(udid,os)
 * Logic :
 * At start all devices are added as available, to two maps
 * ios devices map --> key : udid, value: status
 * android devices map --> key : udid, value: status
 * devicesLastUse --> key: udid, value: last used
 * Cloud Health thread - monitors cloud devices, interacts with this class by:
 * If device is offline - it calls remove device method
 * If device is available - it calls add device --> if device is not in last used map, or was not used for 20 mins --> then devices is added to its respective map
 */
public class CloudServer {
    public static String webPage;
    public static String authStringEnc;
    public static String DEVICES_URL = "/api/v1/devices";
    public static String URL_ADDRESS = null;
    public static String HOST;
    public static int PORT;
    public static String USER;
    public static String PASSWORD;
    public static boolean SECURED = false;
    public static String GRID_URL;
    public static CloudServerName cloudName;
    public static String authString;
    public static String result;
    public static String ACCESSKEY;
    public static Map<String, String> androidDevicesMap;
    public static Map<String, String> iosDevicesMap;
    public static Map<String, Long> devicesLastUse;//This map holds device's last use, a device will be released if used for more than 5 minutes
    ReentrantLock androidDeviceMapLock = new ReentrantLock(true);
    ReentrantLock iosDeviceMapLock = new ReentrantLock(true);
    ReentrantLock devicesLastUseLock = new ReentrantLock(true);
    public static QuadMap<String, String, String, String> devicesMap = null;
    public static Map<String, String> devicesUDIDToIdMap = null;
    public static Map<Class, List> devicesToAvoid = new HashMap<>();
    public CloudServer(CloudServerName cloudName) {
        this.cloudName = cloudName;
        updateCloudDetails();
        if (this.SECURED) {
            URL_ADDRESS = "https://" + HOST + ":" + PORT;
        } else {
            URL_ADDRESS = "http://" + HOST + ":" + PORT;
        }
        GRID_URL = URL_ADDRESS + "/wd/hub/";

        authString = this.USER + ":" + this.PASSWORD;
        if (this.SECURED) {
            webPage = "https://" + this.HOST + ":" + this.PORT + "/api/v1";
        } else {
            webPage = "http://" + this.HOST + ":" + this.PORT + "/api/v1";
        }

        byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
        authStringEnc = new String(authEncBytes);
        try {
            devicesMap = getAllDevices();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getUSER() {
        return USER;
    }

    public int getPort() {
        return PORT;
    }

    public String getUser() {
        return USER;
    }

    public String getPass() {
        return PASSWORD;
    }

    public boolean getIsSecured() {
        return SECURED;
    }

    public String getServerHostName() {
        return HOST;
    }

    public String getHost() {
        return HOST;
    }

    public String getPASS() {
        return PASSWORD;
    }

    public enum CloudServerName {
        MY, QA, MIRRON, KHALED, MASTER, KHALED_SECURED, RELEASE, ATT, DEEP_TESTING, DEEP_TESTING_SECURED;
    }

    public void updateCloudDetails() {
        switch (cloudName) {
            case DEEP_TESTING_SECURED:
                HOST = "qa-win2016.experitest.com";
                PORT = 443;
                USER = "cloudUser";
                PASSWORD = "Experitest2012";
                SECURED = true;
                ACCESSKEY = "eyJ4cC51Ijo3LCJ4cC5wIjoyLCJ4cC5tIjoiTVRVeE5UWTRNVEk1TXpjd01nIiwiYWxnIjoiSFMyNTYifQ.eyJleHAiOjE4MzEzOTE3MjEsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.COWPT41PGRV2zYlwWgqOUvYzkKxP1moe8KJB1p1jMSA";
                break;
            case MY:
                HOST = "192.168.2.13";
                PORT = 80;
                USER = "admin";
                PASSWORD = "Experitest2012";
                break;
            case DEEP_TESTING:
                HOST = "qa-win2016.experitest.com";
                PORT = 443;
                USER = "admin";
                PASSWORD = "Experitest2012";
                break;
            case KHALED_SECURED:
                HOST = "khaleds-mac-mini.local";
                PORT = 8090;
                USER = "khaleda";
                PASSWORD = "Experitest2012";
                SECURED = true;
                break;
            case QA:
                HOST = "192.168.2.135";
                PORT = 80;
                USER = "khaleda";
                PASSWORD = "Experitest2012";
                break;
            case MIRRON:
                HOST = "192.168.2.71";
                PORT = 8080;
                USER = "user1";
                PASSWORD = "Welc0me!";
                break;
            case KHALED:
                HOST = "192.168.2.156";
                PORT = 80;
                USER = "khaleda";
                PASSWORD = "Experitest2012";
                break;
            case MASTER:
                HOST = "mastercloud";
                PORT = 80;
                USER = "khaleda";
                PASSWORD = "Experitest2012";
                break;
            case RELEASE:
                HOST = "releasecloud";
                PORT = 80;
                USER = "khaleda";
                PASSWORD = "Experitest2012";
                break;
            default:
                HOST = "192.168.2.13";
                PORT = 80;
                USER = "admin";
                PASSWORD = "Experitest2012";
                break;
        }
    }

    public String doGet(String objectsToGet) throws IOException {

        String authString = USER + ":" + PASSWORD;
        authStringEnc = java.util.Base64.getEncoder().encodeToString(authString.getBytes());
        URL url = new URL(URL_ADDRESS + DEVICES_URL);
        URLConnection urlConnection = url.openConnection();
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
            return null;
        }
    }

    public QuadMap<String, String, String, String> getAllDevices() throws Exception {
        QuadMap<String, String, String, String> devicesMap = getDevicesMap(doGet(DEVICES_URL));
        return devicesMap;
    }

    private QuadMap<String, String, String, String> getDevicesMap(String result) throws Exception {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Map<String, Object> obj = new Gson().fromJson(
                jsonObject.toString(), new TypeToken<HashMap<String, Object>>() {
                }.getType()
        );
        List<Object> data = (List<Object>) obj.get("data");
        Object[] devicesArray = GetFilteredDevices(data);
        QuadMap<String, String, String, String> tempDevicesMap = new QuadMap<>();
        for (int i = 0; i < devicesArray.length; i++) {
            String[] devicePropertiesArray = devicesArray[i].toString().replace("{", "").replace("]", "").split(",");
            int j = 0;

            boolean udidFlag = false;
            boolean versionFlag = false;
            boolean osFlag = false;
            boolean statusFlag = false;
            String udid = null;
            String version = null;
            String os = null;
            String status = null;
            while (j < devicePropertiesArray.length && !((udidFlag && versionFlag && statusFlag && osFlag))) {
                if (devicePropertiesArray[j].contains("udid")) {
                    udid = devicePropertiesArray[j].replace("udid=", "").trim();
                    udidFlag = true;
                }
                if (devicePropertiesArray[j].contains("osVersion")) {
                    version = devicePropertiesArray[j].replace("osVersion=", "").trim();
                    versionFlag = true;
                    System.out.println("get os version");
                }
                if (devicePropertiesArray[j].contains("deviceOs=")) {
                    os = devicePropertiesArray[j].replace("deviceOs=", "").trim();
                    osFlag = true;
                    System.out.println("got device os");
                }
                if (devicePropertiesArray[j].contains("displayStatus=")) {
                    status = devicePropertiesArray[j].replace("displayStatus=", "").trim();
                    statusFlag = true;
                    System.out.println("got device status");
                }
                j++;
            }
            tempDevicesMap.put(udid, os, version, status);
        }

        return tempDevicesMap;
    }

    /**
     * @param predicate - this is used to get a device with a specific property if needed - in most places it is set as true,
     *                  In the test FindByUIAutomator - it is set to get a device with version > 4
     * @return - an android device
     */
    private String getAndroidDeviceAndUpdateMap(Predicate predicate) {
        androidDeviceMapLock.lock();
        String result = null;
        // While set is unordered, using keySet without unordered, seems to return a set that is almost always identical in its order, so added the
        // stream unordered method to provide some randomization
        // Added the device Not Used, to get more randomness in choosing the devices
        try {
            for (String key : androidDevicesMap.keySet().stream().unordered().collect(Collectors.toSet())) {
                if (androidDevicesMap.get(key).equalsIgnoreCase("Available") && predicate.test(key) && deviceWasNotUsedInLastFiveMinutes(key)) {
                    androidDevicesMap.replace(key, "Available", "In Use");
                    Utilities.log(Thread.currentThread().getName() + " reserving android device " + key);
                    result = key;
                    break;
                } else {
                    Utilities.log("Device " + key + " is under use");
                }
            }
        } catch (Exception e) {
            Utilities.log("Got exception while trying to reserve device " + e);
        } finally {
            androidDeviceMapLock.unlock();
        }
        if (result.equals(null)) {
            Assert.fail("No available android devices");
        }
        reserveDeviceInDevicesLastUsed(result);
        return result;
    }

    private void reserveDeviceInDevicesLastUsed(String udid) {
        try {
            devicesLastUseLock.lock();
            devicesLastUse.put(udid, System.currentTimeMillis());
        } catch (Exception e) {

        } finally {
            devicesLastUseLock.unlock();
        }
    }

    /**
     * @param predicate - this is used to get a device with a specific property if needed - in most places it is set as true,
     *                  In the test FindByUIAutomator - it is set to get a device with version > 4
     * @return - an ios device
     */
    private String getIosDeviceAndUpdateMap(Predicate predicate) {
        iosDeviceMapLock.lock();
        String result = null;
        // While set is unordered, using keySet without unordered, seems to return a set that is almost always identical in its order, so added the
        // stream unordered method to provide some randomization
        // Added the device Not Used, to get more randomness in choosing the devices
        try {
            for (String key : iosDevicesMap.keySet().stream().unordered().collect(Collectors.toSet())) {
                if (iosDevicesMap.get(key).equalsIgnoreCase("Available") && predicate.test(key) && deviceWasNotUsedInLastFiveMinutes(key)) {
                    iosDevicesMap.replace(key, "Available", "In Use");
                    Utilities.log("reserving ios device " + key);
                    result = key;
                    break;
                }
            }
        } catch (Exception e) {
            Utilities.log("Got exception while trying to reserve device " + e);
        } finally {
            iosDeviceMapLock.unlock();
        }
        if (result.equals(null)) {
            Assert.fail("No available ios devices");
        }
        reserveDeviceInDevicesLastUsed(result);
        return result;
    }
    private boolean deviceWasNotUsedInLastFiveMinutes(String device){
        if(devicesLastUse.get(device) != null) {
            return (devicesLastUse.get(device) + 2 * 60 * 1000 < System.currentTimeMillis());
        }
        return true;
    }

    public String getDevice(String os, Predicate predicate) {
        Utilities.log(Thread.currentThread() + "getting a new device " + os);
        if (os.equalsIgnoreCase("android")) {
            return getAndroidDeviceAndUpdateMap(predicate);
        }
        return getIosDeviceAndUpdateMap(predicate);
    }

    public void releaseDevice(String udid, String os) {
        Main.executorService.submit(() -> releaseDevice(udid));//This insures device is released using rest API
        Utilities.log(Thread.currentThread().getName() + " removing the device:removing the device: " + udid + "from the " + os + " map");
        if (os.equalsIgnoreCase("android")) {
            releaseAndroidDevice(udid);
            Assert.assertEquals(androidDevicesMap.get(udid), "Available");
        }
        else {
            releaseIOSDevice(udid);
            Assert.assertEquals(iosDevicesMap.get(udid), "Available");
        }
        Utilities.log(Thread.currentThread().getName() + " removed the device " + udid + "from the " + os + " map");
    }

    private void releaseAndroidDevice(String udid) {
        try {
            androidDeviceMapLock.lock();
            androidDevicesMap.replace(udid, "In Use", "Available");
        } catch (Exception e) {

        } finally {
            androidDeviceMapLock.unlock();
        }

    }

    private void releaseIOSDevice(String udid) {
        try {
            iosDeviceMapLock.lock();
            iosDevicesMap.replace(udid, "In Use", "Available");
        } catch (Exception e) {

        } finally {
            iosDeviceMapLock.unlock();
        }
    }

    public void updateAvailableDevices(String restResponse) {
        devicesUDIDToIdMap = new HashMap<>();
        androidDevicesMap = new HashMap<>();
        devicesLastUse = new HashMap<>();
        iosDevicesMap = new HashMap<>();
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(restResponse);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Map<String, Object> obj = new Gson().fromJson(
                jsonObject.toString(), new TypeToken<HashMap<String, Object>>() {
                }.getType()
        );
        List<Object> data = (List<Object>) obj.get("data");
        Object[] devicesArray = GetFilteredDevices(data);
        for (int i = 0; i < devicesArray.length; i++) {
            String[] devicePropertiesArray = devicesArray[i].toString().replace("{", "").replace("]", "").split(",");
            int j = 0;

            boolean udidFlag = false;
            boolean osFlag = false;
            boolean statusFlag = false;
            boolean idFlag = false;
            String udid = null;
            String os = null;
            String status = null;
            String id = null;
            while (j < devicePropertiesArray.length && !((udidFlag && statusFlag && osFlag && idFlag))) {
                if (devicePropertiesArray[j].contains("udid")) {
                    udid = devicePropertiesArray[j].replace("udid=", "").trim();
                    udidFlag = true;
                }
                if (devicePropertiesArray[j].contains("deviceOs=")) {
                    os = devicePropertiesArray[j].replace("deviceOs=", "").trim();
                    osFlag = true;
                    System.out.println("got device os");
                }
                if (devicePropertiesArray[j].contains("displayStatus=")) {
                    status = devicePropertiesArray[j].replace("displayStatus=", "").trim();
                    statusFlag = true;
                    System.out.println("got device status");
                }
                if (devicePropertiesArray[j].contains("id=") && !devicePropertiesArray[j].contains("udid")) {
                    id = devicePropertiesArray[j].replace("id=", "").trim();
                    idFlag = true;
                    System.out.println("got device status");
                }
                j++;
            }
            if (os.equalsIgnoreCase("android")) {
                androidDevicesMap.put(udid, status);
                Utilities.log("Added android device " + udid + " with status: " + status);
            } else {
                iosDevicesMap.put(udid, status);
                Utilities.log("Added ios device " + udid + " with status: " + status);
            }
            devicesUDIDToIdMap.put(udid, id);
        }
        Utilities.log("added android + ios devices");
    }

    private Object[] GetFilteredDevices(List<Object> data) {
        Object[] devicesArray = data
                .stream()
                .filter(device -> (((Map) device).get("displayStatus").equals("Available")
                        || ((Map) device).get("displayStatus").equals("In Use")))
                .toArray();
        return devicesArray;
    }

    //Used when device became offline - removed if not under use (reboot test)
    public void removeDeviceFromMap(String udid, String os) {
        if (devicesLastUse.containsKey(udid)) {
            if (devicesLastUse.get(udid) + 20 * 60 * 1000 > (System.currentTimeMillis())) {
                Utilities.log("not removing the device: " + udid + " from the map: " + os);
                return;
            }
        }
        Utilities.log("removing the device: " + udid + " from the map: " + os);
        if (os.equalsIgnoreCase("android")) {
            try {
                androidDeviceMapLock.lock();
                androidDevicesMap.remove(udid);
            } catch (Exception e) {

            } finally {
                androidDeviceMapLock.unlock();
            }
        } else {
            try {
                iosDeviceMapLock.lock();
                iosDevicesMap.remove(udid);
            } catch (Exception e) {
            } finally {
                iosDeviceMapLock.unlock();
            }
        }
    }

    //Used when seeing a new device, or device completed reboot
    public void addDevice(String udid, String os, String status) {
        if (devicesLastUse.containsKey(udid)) {
            if (devicesLastUse.get(udid) + 20 * 60 * 1000 > (System.currentTimeMillis())) {
                return;
            }
        }
        if (androidDevicesMap.containsKey(udid)) {
            return;
        }
        Utilities.log("adding device " + udid + " to available devices map");
        if (os.equalsIgnoreCase("android")) {
            addAndroidDevice(udid, status);
        } else {
            addIOSDevice(udid, status);
        }
    }

    //Checks the current available devices
    //If any os has less than 2 available devices, it returns the os which has more available devices,
    // Otherwise, returns a random os
    public String getNextOS() {
        String osToRun = "";
        long androidAvailableDevices = androidDevicesMap.keySet().stream().filter(k -> androidDevicesMap.get(k).equalsIgnoreCase("Available")).count();
        long iosAvailableDevices = iosDevicesMap.keySet().stream().filter(k -> iosDevicesMap.get(k).equalsIgnoreCase("Available")).count();

        if (androidAvailableDevices < 2 || iosAvailableDevices < 2) {
            osToRun = androidAvailableDevices < iosAvailableDevices ? "ios" : "android";
        } else {
            if (Math.random() > Math.random()) {
                osToRun = "android";
            } else {
                osToRun = "ios";
            }
        }
        return osToRun;
    }

    public String getServer() {
        return HOST;
    }

    private void addAndroidDevice(String udid, String status) {
        try {
            androidDeviceMapLock.lock();
            androidDevicesMap.put(udid, status);
        } catch (Exception e) {

        } finally {
            androidDeviceMapLock.unlock();
        }
    }

    private void addIOSDevice(String udid, String status) {
        try {
            iosDeviceMapLock.lock();
            iosDevicesMap.put(udid, status);
        } catch (Exception e) {

        } finally {
            iosDeviceMapLock.unlock();
        }
    }

    private void releaseDevice(String udid) {
        HttpResponse<String> response = null;
        Utilities.log("releasing the device " + udid);
        try {
            String prefix = this.SECURED ? "https://" : "http://";
            response = Unirest.post( prefix + HOST + ":" + PORT + "/api/v1/devices/" + devicesUDIDToIdMap.get(udid) + "/release")
                    .basicAuth("admin", "Experitest2012")
                    .header("content-type", "*/*").asString();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        Utilities.log("releasing the device " + udid + " got response: " + response.getBody());
    }

}
