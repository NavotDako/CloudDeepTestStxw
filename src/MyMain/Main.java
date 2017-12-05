package MyMain;

import AdministrationSuite.AdminRunner;
import STXWSuite.STXWRunner;
import TestPlanSuite.CloudServer;
import TestPlanSuite.TestPlanRunner;
import Utils.CloudApiShit;
import Utils.Utilities;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;


public class Main {
    public static CloudServer cloudServer = new CloudServer(CloudServer.CloudServerName.RELEASE);
    public static Enums enums = new Enums();
    public static File logsFolder = Utilities.CreateLogsFolderForRun();
    public static Map<String, Boolean> suites = new HashMap<>();
    public static PrintWriter overallWriter = Utilities.createReportFile(logsFolder, "", "OverallReport");
    public static PrintWriter summaryWriter = Utilities.createReportFile(logsFolder, "", "Summary");

    private static int numOfThreads = 1;
    public static String CloudDevicesInfo;


    public static void main(String[] args) throws IOException, InterruptedException {

        suites.put("STXWRunner", false);
        suites.put("AdminRunner", false);
        suites.put("STARunner", false);
        suites.put("TestPlanRunner", true);

        CloudDevicesInfo = CloudApiShit.doGet("devices");
        System.out.println(CloudDevicesInfo);
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

//        if (suites.get("STARunner")) {
//            AdminRunner[] adminThreadArray = new AdminRunner[numOfThreads];
//            PrintWriter AdminRunnerOverallWriter = Utilities.createReportFile(logsFolder, "AdminRunner", "OverallReport");
//            PrintWriter AdminRunnerSummaryWriter = Utilities.createReportFile(logsFolder, "AdminRunner", "Summary");
//
//            for (int i = 0; i < numOfThreads; i++) {
//                adminThreadArray[i] = new AdminRunner(i, AdminRunnerSummaryWriter, AdminRunnerOverallWriter);
//                adminThreadArray[i].start();
//                Thread.sleep(10000);
//            }
//        }

//        overallWriter.close();

    }


}
