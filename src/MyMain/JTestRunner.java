package MyMain;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import ActionTests.Extendsession;
import Cloud_API.GetDevices;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.runner.JUnitCore;

import org.junit.runner.Result;

public class JTestRunner extends Thread {
    private final int iteration;
    public Class Test;
    public String User;
    public String TestName = "";
    public String UserType;
    public String STXW;
    public PrintWriter pw = null;
    public Enums enums = new Enums();
    public String choosenDeviceUdid = "";
    public String CloudDevicesInfo = "";
    public String choosenDeviceName = "";
    public String choosenDeviceInfo = "";
    public JSONObject jsonDeviceInfo = null;
    public JSONArray jsonArrayDeviceReservation = null;
    Random rand = new Random();

    public JTestRunner(int i) {
        System.out.println("Starting Thread Num - " + i);
        this.iteration = i;
    }

    @Override
    public void run() {
        while (true) {
            this.Test = Extendsession.class;
            //       this.Test = getAction(rand.nextInt(enums.Actions.length));
            this.User = getUser(rand.nextInt(enums.Users.length));
            ;
            this.UserType = getUserType(User);
            try {
                this.CloudDevicesInfo = (new GetDevices()).doGet();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Long currTime = System.currentTimeMillis();
            TestName = Test.getName().substring(12, Test.getName().length()) + " " + currTime;
            pw = Main.operations.CreateReportFile(TestName);
            Result r = JUnitCore.runClasses(Test);

            try {
                int sleepTime = rand.nextInt(20);
                System.out.println("Thread - " + iteration + " is Going to sleep for - " + sleepTime + " minutes");
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
            System.out.println("ProjectAdmin");
            return "ProjectAdmin";
        } else {
            if (UserName.contains("Admin")) {
                System.out.println("Admin");
                return "Admin";
            }
        }
        System.out.println("User");
        return "User";
    }


}


