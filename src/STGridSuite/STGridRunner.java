package STGridSuite;

import MyMain.BaseRunner;
import MyMain.Main;
import Utils.Utilities;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import java.io.File;
import java.io.PrintWriter;


public class STGridRunner extends BaseRunner {



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
            this.testClass = SelectTestsToRun();
            this.testName = testClass.getSimpleName();
            Utilities.log(this,"selected TEST - " + testName);
            Result r = JUnitCore.runClasses(testClass);

            GoToSleep(2);
        }
    }

    private Class SelectTestsToRun() {
        if(Main.cs.getNextOS().equalsIgnoreCase("android")){
            deviceOS = "android";
            return Main.enums.GridAndroidTests[rand.nextInt(enums.GridAndroidTests.length)];
        }
        else {
            deviceOS = "ios";
            return Main.enums.GridIOSTests[rand.nextInt(enums.GridIOSTests.length)];
        }
    }
}
