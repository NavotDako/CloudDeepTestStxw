package MyMain;


import org.junit.runner.JUnitCore;

import ActionTests.Extendsession;
import ActionTests.Install;
import ActionTests.Monitors;
import ActionTests.Openlogs;
import ActionTests.Reboot;
import ActionTests.StartVideo;
import Cloud_API.GetDevices;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


public class Main {

    public static Enums enums = new Enums();
    public static file_operations operations = new file_operations();
    public static File LogsFile = operations.createParentFile();
    public static PrintWriter SummaryTestsWriter = operations.createSummaryFile();

    public static void main(String[] args) throws IOException, InterruptedException {
        System.setProperty("webdriver.chrome.driver", "lib\\chromedriver.exe");
        for (int i = 0; i < 2; i++) {
            JTestRunner Test = new JTestRunner(i);
            Test.start();
            Thread.sleep(5000);
        }
//				SummaryTestsWriter.close();
    }


}
