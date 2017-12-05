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

            testName = testClass.getName().substring(12, testClass.getName().length());
            Long currTime = System.currentTimeMillis();
            TestName = testClass.getName().substring(12, testClass.getName().length()) + " " + currTime;

            Result r = JUnitCore.runClasses(testClass);

            try {
                int sleepTime = rand.nextInt(20);
                Utilities.log(this, Thread.currentThread().getName() + " is Going to sleep for - " + sleepTime + " minutes");
                for (int i = 0; i < sleepTime; i++) {
                    Thread.sleep(60000);
                    Utilities.log(currentThread().getName() + " Is Sleeping - " + (sleepTime - i) + " minutes remaining ");
                }
            } catch (Exception e) {
                Utilities.log(e);
            }
        }
    }

}


