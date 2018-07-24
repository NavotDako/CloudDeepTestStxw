package cloudHealthMonitors;

import Framework.IdentificationMethods;
import Framework.QuadMap;
import MyMain.Main;
import Utils.Utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Monitors cloud - Ping + nslookup to verify no network issues are present
 * Repsonsible for updating CloudServer in case a device was added or removed from the cloud
 */
public class CloudMonitor extends Thread{
    String serverURL = Main.cs.getServer();

    static String nsLookUpLastRes = "";
    static String pingLastIP = "";
    public void run(){
        long lastSample = System.currentTimeMillis();
        QuadMap<String, String, String, String> devicesFirstSample = null;
        Utilities.writeToHealthLog("Starting to monitor connected devices");
        try {
            devicesFirstSample = Main.cs.getAllDevices();
            printDeviceProperties(devicesFirstSample);
        } catch (Exception e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                if ((System.currentTimeMillis() - lastSample) < 1000 * 60 * 5) {
                    Thread.sleep(1000 * 30);
                    continue;
                }
                lastSample = System.currentTimeMillis();
                QuadMap<String, String, String, String> currDevices = Main.cs.getAllDevices();
                printDeviceProperties(currDevices);
                compareDevicesList(currDevices, devicesFirstSample);
//                devicesFirstSample = Main.cs.getAllDevices();
                pingServer();
                nsLookUP();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private void nsLookUP(){
        String res = runCMD("nslookup", serverURL);
        if(res.equals(nsLookUpLastRes)){
            return;
        }
        else{
            nsLookUpLastRes = res;
            Utilities.writeToHealthLog("nslookup returned a different address !!!!");
        }
        Utilities.writeToHealthLog(nsLookUpLastRes);
    }
    public void printDeviceProperties(QuadMap<String, String, String, String> currDevices) {
        for (String device : (List<String>) currDevices.listKeys()) {
            Utilities.writeToHealthLog("Device - " + device + " of os " +
                    currDevices.get(device, IdentificationMethods.devicesMapTranslator.OS) + " with version " +
                    currDevices.get(device, IdentificationMethods.devicesMapTranslator.VERSION) + " with status " +
                    currDevices.get(device, IdentificationMethods.devicesMapTranslator.STATUS));
        }
    }
    public void compareDevicesList(QuadMap currDeviceMap, QuadMap previousDevicesMap) {
        List previousDevices = previousDevicesMap.listKeys();
        List<?> currDevices = currDeviceMap.listKeys();
        for (Object device : previousDevices) {
            if (!currDevices.contains(device)) {
                Utilities.writeToHealthLog("FAIL !!!!! - Missing a device: " + device);
                Main.cs.removeDeviceFromMap((String) device,
                        (String) previousDevicesMap.get(device, IdentificationMethods.devicesMapTranslator.OS));
            }
        }
        for (Object device : currDevices) {
//            if (!previousDevices.contains(device)) {
            if(((String)currDeviceMap.get(device, IdentificationMethods.devicesMapTranslator.STATUS)).equalsIgnoreCase("available")) {
                Utilities.writeToHealthLog("Adding a device: " + device);
                Main.cs.addDevice((String) device,
                        (String) currDeviceMap.get(device, IdentificationMethods.devicesMapTranslator.OS),
                        (String) currDeviceMap.get(device, IdentificationMethods.devicesMapTranslator.STATUS));
            }
//            }
//            else if(((String) previousDevicesMap.get(device, IdentificationMethods.devicesMapTranslator.STATUS)).equals("In Use")){
//                Utilities.writeToHealthLog("This device" + device +" seems to be released, adding to cloud server devices: ");
//            }
        }
    }

    private String runCMD(String... params){
        ProcessBuilder builder = new ProcessBuilder(params);
        String result = "";
        builder.redirectErrorStream();
        Process process = null;
        try {
            process = builder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try{
            InputStream is = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line = "";
            while ((line = reader.readLine()) != null) {
                result += line + "\n";
            }
        }catch(Exception e) {
            process.destroy();
        }
        return result;
    }

    private void pingServer(){
        String res = runCMD("ping", serverURL);
        try {
            String ip = res.substring(res.indexOf("[" + 1), res.indexOf("]"));
            if (!ip.equals(pingLastIP)) {
                pingLastIP = ip;
                Utilities.writeToHealthLog("ping returned a different address !!!!");
                Utilities.writeToHealthLog(res);
            } else {
                for (String line : res.split("\n")) {
                    if (line.trim().startsWith("Packets")) {
                        String sentPackages = line.substring(line.indexOf("Sent = ") + "Sent = ".length() , line.indexOf(", Received = ")).trim();
                        String recPackages = line.substring(line.indexOf("Received = ") + "Received = ".length(), line.indexOf(", Lost")).trim();
                        if (!recPackages.equals(sentPackages)) {
                            Utilities.writeToHealthLog("not all sent packages were received !!!!");
                            Utilities.writeToHealthLog(res);
                        }
                    }
                    else if(line.trim().startsWith("Minimum")){
//                        System.out.println(line);
                        String maximum = line.substring(line.indexOf("Maximum = ") + "Maximum = ".length() , line.indexOf(", Average")).trim();
                        //Check if units are not in milli seconds, or greater than 200
                        if(!maximum.contains("ms")){
                            Utilities.writeToHealthLog("Units for ping are too high !!!!");
                            Utilities.writeToHealthLog(res);
                        }
                        else if(new Integer(maximum.split("ms")[0]) > 200){
                            Utilities.writeToHealthLog("ping took more than 200 ms !!!!");
                            Utilities.writeToHealthLog(res);
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            Utilities.writeToHealthLog("Got exception for some reason, printing the result anyway " + res);
        }
    }
}
