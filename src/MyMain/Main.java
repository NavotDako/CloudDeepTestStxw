package MyMain;

import AdministrationSuite.AdminRunner;
import STASuite.STARunner;
import STGridSuite.STGridRunner;
import STXWSuite.STXWRunner;
import TestPlanSuite.TestPlanRunner;
import Utils.Utilities;
import cloudHealthMonitors.CloudMonitor;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Main {
    public static Enums enums = new Enums();
    public static File logsFolder;
    public static File reporterAttachmentsFolder;
    public static Map<String, Integer> suites = new HashMap<>();
    public static PrintWriter overallWriter;
    public static PrintWriter summaryWriter;
    public static PrintWriter exceptionWriter = null;
    public static PrintWriter cloudHealth = null;
    public static CloudServer cs;
    public static String CloudDevicesInfo;
    public static ExecutorService executorService = Executors.newFixedThreadPool(3);
//    private static int numOfThreads = 5;
    public static String deviceOs = "ios";

    public static void main(String[] args) throws IOException, InterruptedException {

        suites.put("STXWRunner", 5);
        suites.put("AdminRunner", 5);
        suites.put("STARunner", 0);
        suites.put("TestPlanRunner", 0);
        suites.put("STGridRunner", 6);

        cs = new CloudServer(CloudServer.CloudServerName.DEEP_TESTING_SECURED);

        logsFolder = Utilities.createLogsFolderForRun();
        reporterAttachmentsFolder = Utilities.createAttachmentsFolder();
        overallWriter = Utilities.createReportFile(logsFolder, "", "OverallReport");
        summaryWriter = Utilities.createReportFile(logsFolder, "", "Summary");
        exceptionWriter = Utilities.createReportFile(logsFolder, "", "ExceptionWriter");
        cloudHealth = Utilities.createReportFile(logsFolder, "", "CloudMonitor");

        new CloudMonitor().start();

        CloudDevicesInfo = cs.doGet("devices");
        Utilities.log(CloudDevicesInfo);
        cs.updateAvailableDevices(CloudDevicesInfo);
            if (suites.get("TestPlanRunner") > 0) {
                TestPlanRunner[] threadArray = new TestPlanRunner[suites.get("TestPlanRunner")];
                PrintWriter testPlanRunnerOverallWriter = Utilities.createReportFile(logsFolder, "TestPlanRunner", "OverallReport");
                PrintWriter testPlanRunnerSummaryWriter = Utilities.createReportFile(logsFolder, "TestPlanRunner", "Summary");

                for (int i = 0; i < suites.get("TestPlanRunner"); i++) {
                    threadArray[i] = new TestPlanRunner(i, testPlanRunnerSummaryWriter, testPlanRunnerOverallWriter);
                    threadArray[i].start();
                    threadArray[i].setName("TestPlanRunner-" + i);
                    Thread.sleep(10000);
                }
            }
            if (suites.get("STXWRunner") > 0) {
                STXWRunner[] threadArray = new STXWRunner[suites.get("STXWRunner")];
                PrintWriter STXWRunnerOverallWriter = Utilities.createReportFile(logsFolder, "STXWRunner", "OverallReport");
                PrintWriter STXWRunnerSummaryWriter = Utilities.createReportFile(logsFolder, "STXWRunner", "Summary");

                for (int i = 0; i < suites.get("STXWRunner"); i++) {
                    threadArray[i] = new STXWRunner(i, STXWRunnerSummaryWriter, STXWRunnerOverallWriter);
                    threadArray[i].start();
                    threadArray[i].setName("STXWRunner-" + i);
                    Thread.sleep(10000);
                }
            }

            if (suites.get("AdminRunner") > 0) {
                AdminRunner[] adminThreadArray = new AdminRunner[suites.get("AdminRunner")];
                PrintWriter AdminRunnerOverallWriter = Utilities.createReportFile(logsFolder, "AdminRunner", "OverallReport");
                PrintWriter AdminRunnerSummaryWriter = Utilities.createReportFile(logsFolder, "AdminRunner", "Summary");

                for (int i = 0; i < suites.get("AdminRunner"); i++) {
                    adminThreadArray[i] = new AdminRunner(i, AdminRunnerSummaryWriter, AdminRunnerOverallWriter);
                    adminThreadArray[i].start();
                    adminThreadArray[i].setName("AdminRunner-" + i);
                    Thread.sleep(10000);
                }
            }

            if (suites.get("STARunner") > 0) {
                STARunner[] STAThreadArray = new STARunner[suites.get("STARunner")];
                PrintWriter STARunnerOverallWriter = Utilities.createReportFile(logsFolder, "STARunner", "OverallReport");
                PrintWriter STARunnerSummaryWriter = Utilities.createReportFile(logsFolder, "STARunner", "Summary");

                for (int i = 0; i < suites.get("STARunner"); i++) {
                    STAThreadArray[i] = new STARunner(i, STARunnerSummaryWriter, STARunnerOverallWriter);
                    STAThreadArray[i].start();
                    STAThreadArray[i].setName("STARunner-" + i);
                    Thread.sleep(10000);
                }
            }

            if (suites.get("STGridRunner") > 0) {
                STGridRunner[] STGridRunnerArray = new STGridRunner[suites.get("STGridRunner")];
                PrintWriter STGridRunnerOverallWriter = Utilities.createReportFile(logsFolder, "STGridRunner", "OverallReport");
                PrintWriter STGridRunnerSummaryWriter = Utilities.createReportFile(logsFolder, "STGridRunner", "Summary");

                for (int i = 0; i < suites.get("STGridRunner"); i++) {
                    STGridRunnerArray[i] = new STGridRunner(i, STGridRunnerSummaryWriter, STGridRunnerOverallWriter);
                    STGridRunnerArray[i].start();
                    STGridRunnerArray[i].setName("STGridRunner-" + i);
                    Thread.sleep(5000);
                }
            }
//        overallWriter.close();

    }



    public static boolean checkIfDeviceIsReservedForDifferentUser(String device, String user){
        JSONObject obj;
        Utilities.log("Looking if device " + device + "is reserved to a different user" );
        try {
            obj = new JSONObject(cs.doGet("devices"));
            JSONArray arr = obj.getJSONArray("data");
            System.out.println(arr);
            for (int i = 0; i < arr.length(); i++) {
                String deviceName = arr.getJSONObject(i).getString("deviceName");
                String userName = arr.getJSONObject(i).getString("currentUser");
                Utilities.log(obj.toString());
                if(userName.equalsIgnoreCase("None") && device.equalsIgnoreCase(deviceName)){
                    return false;
                }
                if (deviceName.equals(device)) {
                    Utilities.log("got device " + device + "with username " + userName);
                    return !user.equals(userName);
                }
            }
            Utilities.log("devices arr " + arr);

        } catch (Exception e) {
                e.printStackTrace();
            }
        return false;
    }
}
