package MyMain;

import AdministrationSuite.AdminRunner;
import STASuite.STARunner;
import STGridSuite.STGridRunner;
import STXWSuite.STXWRunner;
import TestPlanSuite.TestPlanRunner;
import Utils.Utilities;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;


public class Main {
    public static CloudServer cloudServer = new CloudServer(CloudServer.CloudServerName.DEEP_TESTING_SECURED);
    public static Enums enums = new Enums();
    public static File logsFolder;
    public static Map<String, Boolean> suites = new HashMap<>();
    public static PrintWriter overallWriter;
    public static PrintWriter summaryWriter;
    public static CloudServer cs;
    public static String CloudDevicesInfo;

    private static int numOfThreads = 4;

    public static void main(String[] args) throws IOException, InterruptedException {

        suites.put("STXWRunner", true);
        suites.put("AdminRunner", false);
        suites.put("STARunner", false);
        suites.put("TestPlanRunner", false);
        suites.put("STGridRunner", false);

        cs = new CloudServer(CloudServer.CloudServerName.DEEP_TESTING_SECURED);

        logsFolder = Utilities.CreateLogsFolderForRun();
        overallWriter = Utilities.createReportFile(logsFolder, "", "OverallReport");
        summaryWriter = Utilities.createReportFile(logsFolder, "", "Summary");

        CloudDevicesInfo = cs.doGet("devices");
        Utilities.log(CloudDevicesInfo);
        System.setProperty("webdriver.chrome.driver", "lib/chromedriver.exe");

        if (suites.get("TestPlanRunner")) {
            TestPlanRunner[] threadArray = new TestPlanRunner[numOfThreads];
            PrintWriter testPlanRunnerOverallWriter = Utilities.createReportFile(logsFolder, "TestPlanRunner", "OverallReport");
            PrintWriter testPlanRunnerSummaryWriter = Utilities.createReportFile(logsFolder, "TestPlanRunner", "Summary");

            for (int i = 0; i < numOfThreads; i++) {
                threadArray[i] = new TestPlanRunner(i, testPlanRunnerSummaryWriter, testPlanRunnerOverallWriter);
                threadArray[i].start();
                Thread.sleep(10000);
            }
        }
        if (suites.get("STXWRunner")) {
            STXWRunner[] threadArray = new STXWRunner[numOfThreads];
            PrintWriter STXWRunnerOverallWriter = Utilities.createReportFile(logsFolder, "STXWRunner", "OverallReport");
            PrintWriter STXWRunnerSummaryWriter = Utilities.createReportFile(logsFolder, "STXWRunner", "Summary");

            for (int i = 0; i < numOfThreads; i++) {
                threadArray[i] = new STXWRunner(i, STXWRunnerSummaryWriter, STXWRunnerOverallWriter);
                threadArray[i].start();
                Thread.sleep(10000);
            }
        }

        if (suites.get("AdminRunner")) {
            AdminRunner[] adminThreadArray = new AdminRunner[numOfThreads];
            PrintWriter AdminRunnerOverallWriter = Utilities.createReportFile(logsFolder, "AdminRunner", "OverallReport");
            PrintWriter AdminRunnerSummaryWriter = Utilities.createReportFile(logsFolder, "AdminRunner", "Summary");

            for (int i = 0; i < numOfThreads; i++) {
                adminThreadArray[i] = new AdminRunner(i, AdminRunnerSummaryWriter, AdminRunnerOverallWriter);
                adminThreadArray[i].start();
                Thread.sleep(10000);
            }
        }

        if (suites.get("STARunner")) {
            STARunner[] STAThreadArray = new STARunner[numOfThreads];
            PrintWriter STARunnerOverallWriter = Utilities.createReportFile(logsFolder, "STARunner", "OverallReport");
            PrintWriter STARunnerSummaryWriter = Utilities.createReportFile(logsFolder, "STARunner", "Summary");

            for (int i = 0; i < numOfThreads; i++) {
                STAThreadArray[i] = new STARunner(i, STARunnerSummaryWriter, STARunnerOverallWriter);
                STAThreadArray[i].start();
                Thread.sleep(10000);
            }
        }

        if (suites.get("STGridRunner")) {
            STGridRunner[] STGridRunnerArray = new STGridRunner[numOfThreads];
            PrintWriter STGridRunnerOverallWriter = Utilities.createReportFile(logsFolder, "STGridRunner", "OverallReport");
            PrintWriter STGridRunnerSummaryWriter = Utilities.createReportFile(logsFolder, "STGridRunner", "Summary");

            for (int i = 0; i < numOfThreads; i++) {
                STGridRunnerArray[i] = new STGridRunner(i, STGridRunnerSummaryWriter, STGridRunnerOverallWriter);
                STGridRunnerArray[i].start();
                Thread.sleep(10000);
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
