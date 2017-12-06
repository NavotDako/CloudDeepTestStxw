package AdministrationSuite;

import java.io.PrintWriter;

import AdministrationSuite.tests.UserAction;
import MyMain.BaseRunner;
import Utils.Utilities;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

public class AdminRunner extends BaseRunner {

    public AdminRunner(int i, PrintWriter overallSummaryWriter, PrintWriter overallWriter) {
        super("AdminRunner", i, overallSummaryWriter, overallWriter);


    }

    @Override
    public void run() {
        pw = Utilities.CreateReportFile(this, iteration);
        Utilities.log("Starting Thread Num - " + iteration + " - Thread Name is - " + Thread.currentThread().getName());
        while (true) {
            this.testClass = UserAction.class;

            testName = testClass.getSimpleName();
            Result r = JUnitCore.runClasses(testClass);
            GoToSleep();
        }
    }

}


