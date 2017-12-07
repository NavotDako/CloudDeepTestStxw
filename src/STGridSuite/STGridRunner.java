package STGridSuite;

import MyMain.BaseRunner;
import MyMain.Main;
import STGridSuite.tests.GridAndroidEribankTest;
import STGridSuite.tests.GridAndroidWebTest;
import STGridSuite.tests.GridiOSEribankTest;
import STGridSuite.tests.GridiOSWebTest;
import Utils.Utilities;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import java.io.File;
import java.io.PrintWriter;


public class STGridRunner extends BaseRunner {

    public String deviceOS;

    public STGridRunner(int i, PrintWriter overallSummaryWriter, PrintWriter overallWriter) {
        super("STGridRunner", i, overallSummaryWriter, overallWriter);
        createReportsFolder();
    }

    private void createReportsFolder() {
        File logs = new File(Main.logsFolder.getAbsolutePath()+"/STGridRunner/reports");
        if (!logs.exists())
            logs.mkdir();
    }

    @Override
    public void run() {
        pw = Utilities.CreateReportFile(this, iteration);
        Utilities.log(this, "Starting Thread Num - " + iteration + " - Thread Name is - " + Thread.currentThread().getName());
        while (true) {

            this.user = GetUser(rand.nextInt(enums.USERS.length));
            Utilities.log(this,"selected USER - "+user);
            this.testClass = SelectTestsToRun(rand.nextInt(2));
            this.testName = testClass.getSimpleName();
            Utilities.log(this,"selected TEST - "+testName);
            Result r = JUnitCore.runClasses(testClass);

            GoToSleep(3);
        }
    }

    private Class SelectTestsToRun(int i) {
        switch (i){
            case  0:
                deviceOS = "android";
                return GridAndroidEribankTest.class;
            case  1:
                deviceOS = "ios";
                return GridiOSEribankTest.class;
            case  2:
                deviceOS = "android";
                return GridAndroidWebTest.class;
            case  3:
                deviceOS = "ios";
                return GridiOSWebTest.class;
        }
        return null;
    }
}
