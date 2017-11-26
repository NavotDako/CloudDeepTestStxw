package MyMain;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import ActionTests.Extendsession;
import Cloud_API.GetDevices;
import Utils.Utilities;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.runner.JUnitCore;

import org.junit.runner.Result;

public class JTestRunner extends Thread {
    private int iteration;
    public Class testClass;
    public String testName = "";
    public String User="";
    public String TestName = "";
    public String UserType ="";
    public String STXW;
    public PrintWriter pw = null;
    public Enums enums = new Enums();
    public String chosenDeviceUdid = "";
    public String CloudDevicesInfo = "";
    public String choosenDeviceName = "";
    public String chosenDeviceInfo = "";
    public JSONObject jsonDeviceInfo = null;
    public JSONArray jsonArrayDeviceReservation = null;
    Random rand = new Random();

    public JTestRunner(int i) {
        pw = Utilities.CreateReportFile(i);
        Utilities.log("Starting Thread Num - " + i);
        this.iteration = i;
    }

    @Override
    public void run() {
        while (true) {
            this.testClass = Extendsession.class;
            //       this.testClass = getAction(rand.nextInt(enums.Actions.length));
            this.User = getUser(rand.nextInt(enums.Users.length));
            testName = testClass.getName().substring(12, testClass.getName().length());
            this.UserType = getUserType(User);
            try {
                this.CloudDevicesInfo = (new GetDevices()).doGet();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Long currTime = System.currentTimeMillis();
            TestName = testClass.getName().substring(12, testClass.getName().length()) + " " + currTime;

            Result r = JUnitCore.runClasses(testClass);

            try {
                int sleepTime = rand.nextInt(20);
                Utilities.log(this,"Thread - " + iteration + " is Going to sleep for - " + sleepTime + " minutes");
                Thread.sleep(rand.nextInt(20) * 60000);
            } catch (Exception e) {
                System.out.println(e);
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
            Utilities.log("ProjectAdmin");
            return "ProjectAdmin";
        } else {
            if (UserName.contains("Admin")) {
                Utilities.log("Admin");
                return "Admin";
            }
        }
        Utilities.log("User");
        return "User";
    }


}


