package MyMain;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.util.Random;

/**
 * Created by navot.dako on 12/4/2017.
 */
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



}
