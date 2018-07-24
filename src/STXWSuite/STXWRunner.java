package STXWSuite;

import MyMain.BaseRunner;
import MyMain.Main;
import Utils.Utilities;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import java.io.PrintWriter;

public class STXWRunner extends BaseRunner {

//    public static Map<String, String> nameToID = new HashMap<>();
    public STXWRunner(int i, PrintWriter overallSummaryWriter, PrintWriter overallWriter) {
        super("STXWRunner", i, overallSummaryWriter, overallWriter);
    }

    @Override
    public void run() {
        pw = Utilities.CreateReportFile(this, iteration);
//        try {
//            updateDevicesMap();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        Utilities.log("Starting Thread Num - " + iteration + " - Thread Name is - " + Thread.currentThread().getName());
        while (true) {
//        	this.testClass = Reboot.class;
            this.testClass = getAction(rand.nextInt(enums.Actions.length));
            Utilities.log(this, testClass.getName());
            this.user = getUser(rand.nextInt(enums.USERS.length));
            this.userType = getUserType(user);
            testName = testClass.getSimpleName();

            Long currTime = System.currentTimeMillis();
            testName = testClass.getSimpleName() + " " + currTime;

            Result r = JUnitCore.runClasses(testClass);

            GoToSleep(10);
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




}


