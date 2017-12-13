package AdministrationSuite;

import java.io.PrintWriter;

import AdministrationSuite.tests.*;
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
        	testClass = SelectTestToRun(1);
//            testClass = SelectTestToRun(rand.nextInt(6));
            testName = testClass.getSimpleName();
            Result r = JUnitCore.runClasses(testClass);
            GoToSleep(10);
        }
    }

    private Class SelectTestToRun(int i) {

            switch (i){
                case  0:
                    return ApplicationAction.class;
                case  1:
                    return DeviceGroupAction.class;
                case  2:
                    return DHMAction.class;
                case  3:
                    return ProjectAction.class;
                case  4:
                    return ScreenShotAction.class;
                case  5:
                    return UserAction.class;
            }
            return null;

    }

}


