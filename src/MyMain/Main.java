package MyMain;

import AdministrationSuite.AdminRunner;
import Cloud_API.GetDevices;
import STXWSuite.STXWRunner;
import Utils.Utilities;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;


public class Main {

    public static Enums enums = new Enums();
    public static File logsFolder = Utilities.CreateLogsFolderForRun();
    public static Map<String, Boolean> suites = new HashMap<>();
    public static PrintWriter overallWriter = Utilities.createReportFile(logsFolder, "", "OverallReport");
    public static PrintWriter summaryWriter = Utilities.createReportFile(logsFolder, "", "Summary");

    private static int numOfThreads = 5;
    public static String CloudDevicesInfo;


    public static void main(String[] args) throws IOException, InterruptedException {

        suites.put("STXWRunner", true);
        suites.put("AdminRunner", true);

        CloudDevicesInfo = (new GetDevices("MAIN")).doGet();
        System.out.println(CloudDevicesInfo);
        System.setProperty("webdriver.chrome.driver", "lib/chromedriver.exe");

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


//        overallWriter.close();

    }


}
