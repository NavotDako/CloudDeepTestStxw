package STXWSuite;

import java.io.PrintWriter;

import MyMain.BaseRunner;
import MyMain.Main;
import STXWSuite.tests.ExtendSession;
import STXWSuite.tests.Install;
import STXWSuite.tests.Monitors;
import STXWSuite.tests.OpenLogs;
import STXWSuite.tests.Reboot;
import STXWSuite.tests.StartVideo;
import Utils.Utilities;
import org.junit.runner.JUnitCore;

import org.junit.runner.Result;

import com.experitest.selenium.MobileApplication;
import com.google.common.util.concurrent.Monitor;

public class STXWRunner extends BaseRunner {

    public STXWRunner(int i, PrintWriter overallSummaryWriter, PrintWriter overallWriter) {
        super("STXWRunner", i, overallSummaryWriter, overallWriter);
    }

    @Override
    public void run() {
        pw = Utilities.CreateReportFile(this, iteration);
        Utilities.log("Starting Thread Num - " + iteration + " - Thread Name is - " + Thread.currentThread().getName());
        while (true) {
//        	this.testClass = ExtendSession.class;
            this.testClass = getAction(rand.nextInt(enums.Actions.length));
            Utilities.log(this, testClass.getName());
            this.user = getUser(rand.nextInt(enums.USERS.length));
            testName = testClass.getSimpleName();
            this.userType = getUserType(user);

            Long currTime = System.currentTimeMillis();
            testName = testClass.getSimpleName() + " " + currTime;

            Result r = JUnitCore.runClasses(testClass);

            GoToSleep(3);
        }
    }

    public String properCase(String inputVal) {

        if (inputVal.length() == 0) return "";

        if (inputVal.length() == 1) return inputVal.toLowerCase();

        return inputVal.substring(0, 1).toLowerCase()
                + inputVal.substring(1).toLowerCase();
    }

    public Class getAction(int actionNum) {
        return Main.enums.Actions[actionNum];
    }

    public String getUser(int userNum) {
        return Main.enums.USERS[userNum];
    }

    public String getUserType(String UserName) {
        if (UserName.contains("ProjectAdmin")) {
            Utilities.log(currentThread().getName() + " - ProjectAdmin");
            return "ProjectAdmin";
        } else {
            if (UserName.contains("Admin")) {
                Utilities.log(currentThread().getName() + " - Admin");
                return "Admin";
            }
        }
        Utilities.log(currentThread().getName() + " - User");
        return "User";
    }


}


