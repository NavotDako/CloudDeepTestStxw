package MyMain;

import Utils.Utilities;
import org.boon.Str;
import org.json.JSONArray;
import org.json.JSONObject;
import TestPlanSuite.cloudEntities.*;
import java.io.PrintWriter;
import java.util.Random;


public class BaseRunner extends Thread{

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

    public String VMAddress = "";
    public int VMClientNumber = 1;
    public String VMPassword;
    public int UserIndex;
    public String VMUser;
    public String VMSTAVersion;

    public User testPlanUser;
    public Project project;

    public PrintWriter pw = null;
    public PrintWriter overallWriter;
    public PrintWriter overallSummaryWriter;

    public BaseRunner(String TYPE, int i, PrintWriter overallSummaryWriter, PrintWriter overallWriter) {
        iteration = i;
        this.overallSummaryWriter = overallSummaryWriter;
        this.overallWriter = overallWriter;
        this.TYPE = TYPE;
    }


    protected void GoToSleep(){
        try {
            int sleepTime = rand.nextInt(20);
            Utilities.log(this, Thread.currentThread().getName() + " is Going to sleep for - " + sleepTime + " minutes");
            for (int i = 0; i < sleepTime; i++) {
                Thread.sleep(60000);
                Utilities.log(this,currentThread().getName() + " Is Sleeping - " + (sleepTime - i) + " minutes remaining ");
            }
        } catch (Exception e) {
            Utilities.log(this,e);
        }
    }


}
