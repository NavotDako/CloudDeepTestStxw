package MyMain;


import Utils.Utilities;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;


public class Main {

    public static Enums enums = new Enums();
    public static File logsFolder = Utilities.CreateLogsFolderForRun();
    public static PrintWriter overallWriter = Utilities.createOverallReportFile(logsFolder);

    public static void main(String[] args) throws IOException, InterruptedException {
        System.setProperty("webdriver.chrome.driver", "lib\\chromedriver.exe");
        for (int i = 0; i < 2; i++) {
            JTestRunner Test = new JTestRunner(i);
            Test.start();
            Thread.sleep(5000);
        }
//				overallWriter.close();
    }


}
