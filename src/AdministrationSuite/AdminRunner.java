package AdministrationSuite;

import MyMain.BaseRunner;
import MyMain.Main;
import Utils.Utilities;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import java.io.PrintWriter;

public class AdminRunner extends BaseRunner {

    public AdminRunner(int i, PrintWriter overallSummaryWriter, PrintWriter overallWriter) {
        super("AdminRunner", i, overallSummaryWriter, overallWriter);


    }

    @Override
    public void run() {
        pw = Utilities.CreateReportFile(this, iteration);
        Utilities.log("Starting Thread Num - " + iteration + " - Thread Name is - " + Thread.currentThread().getName());
        while (true) {        	
        	testClass = SelectTestToRun();
//            testClass = SelectTestToRun(rand.nextInt(6));
            testName = testClass.getSimpleName();
//            this.user = getUser(rand.nextInt(enums.USERS.length));
//            this.userType = getUserType(user);
            this.user = "khaleda";
            this.userType = "Admin";
            Result r = JUnitCore.runClasses(testClass);
            GoToSleep(10);
        }
    }
    private Class SelectTestToRun() {
        return Main.enums.AdminRunnerActions[rand.nextInt(enums.AdminRunnerActions.length)];
    }
//    private Class SelectTestToRun(int i) {
//
//            switch (i){
//                case  1:
//                    return ApplicationAction.class;
//                case  2:
//                    return DeviceGroupAction.class;
//                case  3:
//                    return DHMAction.class;
//                case  4:
//                    return ProjectAction.class;
//                case  5:
//                    return ScreenShotAction.class;
//                case  0:
//                    return UserAction.class;
//            }
//            return null;
//
//    }

}


