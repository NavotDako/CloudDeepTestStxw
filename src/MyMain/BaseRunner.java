package MyMain;

import TestPlanSuite.cloudEntities.Project;
import TestPlanSuite.cloudEntities.User;
import Utils.Utilities;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.util.Random;


public class BaseRunner extends Thread {
    public String TYPE;
    public int iteration;
    public Random rand = new Random();
    public Enums enums = new Enums();

    public Class testClass;
    public String testName = "";
    public String user = "";
    public String userType = "";

    public String CloudDevicesInfo = Main.CloudDevicesInfo;
    public JSONObject jsonDeviceInfo = null;
    public String STXWType;

    public User testPlanUser;
    public Project project;

    public PrintWriter pw = null;
    public PrintWriter overallWriter;
    public PrintWriter overallSummaryWriter;

    public String deviceOS;
    public BaseRunner(String TYPE, int i, PrintWriter overallSummaryWriter, PrintWriter overallWriter) {
        iteration = i;
        this.overallSummaryWriter = overallSummaryWriter;
        this.overallWriter = overallWriter;
        this.TYPE = TYPE;
    }

    protected void GoToSleep(int maxTime) {
        try {
            int sleepTime = rand.nextInt(maxTime);
            Utilities.log(this, Thread.currentThread().getName() + " is Going to sleep for - " + sleepTime + " minutes");
            for (int i = 0; i < sleepTime; i++) {
                Thread.sleep(60000);
                Utilities.log(this, currentThread().getName() + " Is Sleeping - " + (sleepTime - i) + " minutes remaining ");
            }
        } catch (Exception e) {
            Utilities.log(this, e);
        }
    }
    public String getUser(int userNum) {
        return Main.enums.USERS[userNum];
    }

    public String getUserType(String userName) {
        if (userName.contains("ProjectAdmin")) {
            Utilities.log(currentThread().getName() + " - ProjectAdmin");
            return "ProjectAdmin";
        } else {
            if (userName.contains("Admin") || userName.equals("khaleda")) {
                Utilities.log(currentThread().getName() + " - Admin");
                return "Admin";
            }
        }
        Utilities.log(currentThread().getName() + " - User");
        return "User";
    }

}
