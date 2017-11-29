package MyMain;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import ActionTests.ExtendSession;
import Cloud_API.GetDevices;
import Utils.Utilities;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.runner.JUnitCore;

import org.junit.runner.Result;

public class STXWRunner extends Thread {
    private int iteration;
    public Class testClass;
    public String testName = "";
    public String User = "";
    public String TestName = "";
    public String UserType = "";
    public String STXWType;
    public PrintWriter pw = null;
    public Enums enums = new Enums();
    public String CloudDevicesInfo = Main.CloudDevicesInfo;
    public String chosenDeviceName = "";
    public JSONObject jsonDeviceInfo = null;
    public JSONArray jsonArrayDeviceReservation = null;
    Random rand = new Random();

    public STXWRunner(int i) {
        pw = Utilities.CreateReportFile(i);
        Utilities.log("Starting Thread Num - " + i +" - Thread Name is - "+Thread.currentThread().getName());
        this.iteration = i;
    }

    @Override
    public void run() {
        while (true) {
            this.testClass = ExtendSession.class;
            //       this.testClass = getAction(rand.nextInt(enums.Actions.length));
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
                    Utilities.log("Thread-" + iteration + " isSleeping");
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
            Utilities.log("Thread-" + iteration + " - ProjectAdmin");
            return "ProjectAdmin";
        } else {
            if (UserName.contains("Admin")) {
                Utilities.log("Thread-" + iteration + " - Admin");
                return "Admin";
            }
        }
        Utilities.log("Thread-" + iteration + " - User");
        return "User";
    }


}

