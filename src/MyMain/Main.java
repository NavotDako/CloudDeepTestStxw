package MyMain;

import Cloud_API.GetDevices;
import Utils.Utilities;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;


public class Main {

    public static Enums enums = new Enums();
    public static File logsFolder = Utilities.CreateLogsFolderForRun();
    public static PrintWriter overallWriter = Utilities.createOverallReportFile(logsFolder,"OverallReport");
    public static PrintWriter overallSummaryWriter = Utilities.createOverallReportFile(logsFolder,"Summary");
    private static int numOfThreads = 4;
    public static String CloudDevicesInfo;
    public static void main(String[] args) throws IOException, InterruptedException {

        CloudDevicesInfo = (new GetDevices("MAIN")).doGet();
        System.out.println(CloudDevicesInfo);
        System.setProperty("webdriver.chrome.driver", "lib/chromedriver.exe");
        STXWRunner[] threadArray = new STXWRunner[numOfThreads];

        for (int i = 0; i < numOfThreads; i++) {
            threadArray[i] = new STXWRunner(i);
            threadArray[i].start();
            Thread.sleep(60000);
        }

//        overallWriter.close();

    }


}
