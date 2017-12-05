package MyMain;

import Utils.Utilities;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.util.Random;


public class BaseRunner extends Thread{

    public String TYPE;
    public int iteration;
    public Class testClass;
    public String testName = "";
    public String User = "";
    public String TestName = "";
    public String UserType = "";
    public String STXWType;
    public PrintWriter pw = null;
    public Enums enums = new Enums();
    public String CloudDevicesInfo = Main.CloudDevicesInfo;
    public JSONObject jsonDeviceInfo = null;
    public JSONArray jsonArrayDeviceReservation = null;
    public Random rand = new Random();

    public String VMAddress = "";
    public int VMClientNumber = 1;
    public String VMPassword;
    public int UserIndex;
    public String VMUser;
    public String VMSTAVersion;



    public static PrintWriter overallWriter;
    public static PrintWriter overallSummaryWriter;

    public BaseRunner(String TYPE, int i, PrintWriter overallSummaryWriter, PrintWriter overallWriter) {
        iteration = i;
        this.overallSummaryWriter = overallSummaryWriter;
        this.overallWriter = overallWriter;
        this.TYPE = TYPE;
    }
}
