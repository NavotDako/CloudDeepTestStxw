package AdministrationSuite;

import java.io.PrintWriter;

import AdministrationSuite.tests.ApplicationAction;
import AdministrationSuite.tests.DHMAction;
import AdministrationSuite.tests.DeviceGroupAction;
import AdministrationSuite.tests.ProjectAction;
import AdministrationSuite.tests.ScreenShotAction;
import AdministrationSuite.tests.UserAction;
import MyMain.BaseRunner;
import MyMain.Main;
import Utils.Utilities;

import org.apache.tools.ant.Project;
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
//            this.testClass = ApplicationAction.class;  
        	this.testClass = getAction(rand.nextInt(enums.AdminstrationActions.length));
            testName = testClass.getName().substring(12, testClass.getName().length());
            Long currTime = System.currentTimeMillis();
            TestName = testClass.getName().substring(12, testClass.getName().length()) + " " + currTime;

            Result r = JUnitCore.runClasses(testClass);


            GoToSleep();
        }
    }

    public Class getAction(int actionNum) {
        return Main.enums.AdminstrationActions[actionNum];
    }
}


