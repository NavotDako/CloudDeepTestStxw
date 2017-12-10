package STXWSuite;

import MyMain.BaseRunner;
import MyMain.Main;
import Utils.Utilities;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import java.io.PrintWriter;

public class STXWRunner extends BaseRunner {

    public STXWRunner(int i, PrintWriter overallSummaryWriter, PrintWriter overallWriter) {
        super("STXWRunner", i, overallSummaryWriter, overallWriter);
    }

    @Override
    public void run() {
        pw = Utilities.CreateReportFile(this, iteration);
        Utilities.log(this, "Starting Thread Num - " + iteration + " - Thread Name is - " + Thread.currentThread().getName());
        while (true) {
            this.testClass = getAction(rand.nextInt(enums.Actions.length));
            Utilities.log(this, testClass.getName());
            this.user = getUser(rand.nextInt(enums.USERS.length));
            testName = testClass.getSimpleName();
            this.userType = getUserType(user);

            Result r = JUnitCore.runClasses(testClass);

            GoToSleep(10);
        }
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


