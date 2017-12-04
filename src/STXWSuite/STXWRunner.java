package STXWSuite;

import java.io.PrintWriter;

import MyMain.BaseRunner;
import MyMain.Main;
import Utils.Utilities;
import org.junit.runner.JUnitCore;

import org.junit.runner.Result;

public class STXWRunner extends BaseRunner {

    public STXWRunner(int i, PrintWriter overallSummaryWriter, PrintWriter overallWriter) {
        super("STXWRunner", i, overallSummaryWriter, overallWriter);
    }

    @Override
    public void run() {
        pw = Utilities.CreateReportFile(this, iteration);
        Utilities.log("Starting Thread Num - " + iteration + " - Thread Name is - " + Thread.currentThread().getName());
        while (true) {
//            this.testClass = ExtendSession.class;
            this.testClass = getAction(rand.nextInt(enums.Actions.length));
            Utilities.log(this, testClass.getName());
            this.User = getUser(rand.nextInt(enums.Users.length));
            testName = testClass.getName().substring(12, testClass.getName().length());
            this.UserType = getUserType(User);

            Long currTime = System.currentTimeMillis();
            TestName = testClass.getName().substring(12, testClass.getName().length()) + " " + currTime;

            Result r = JUnitCore.runClasses(testClass);

            try {
                int sleepTime = rand.nextInt(20);
                Utilities.log(this, Thread.currentThread().getName() + " is Going to sleep for - " + sleepTime + " minutes");
                for (int i = 0; i < sleepTime; i++) {
                    Thread.sleep(60000);
                    Utilities.log(currentThread().getName() + " Is Sleeping - " + (sleepTime - i) + "minutes remaining ");
                }
            } catch (Exception e) {
                Utilities.log(e);
            }
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
        return Main.enums.Users[userNum];
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


