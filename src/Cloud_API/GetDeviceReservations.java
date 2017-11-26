package Cloud_API;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import MyMain.JTestRunner;

public class GetDeviceReservations{
    private static final String DEVICES_URL  = "/devices/";
    private static final String RESERVATIONS = "/reservations";
     
    private String host = "qacloud.experitest.com";//TODO: host ip goes here
    private String port = "";//TODO: open port goes here
    private String webPage= "https://" + host + ":" + port + "/api/v1";
    private String authStringEnc;
    JTestRunner CurrentThread = (JTestRunner)Thread.currentThread();
   
    @Before
    public void setup() {
        String name = "ayouba";//TODO: admin user name is here
        String password = "Experitest2012";//TODO: admin password is here
           
        String authString = name + ":" + password;
        authStringEnc = Base64.getEncoder().encodeToString(authString.getBytes());
    }
   
   
    @Test
    public void getReservationDetails() throws IOException {
         
        String getURL = prepareURL();
        try
        {
        	CurrentThread.jsonArrayDeviceReservation =(JSONArray) (new JSONObject(doGet(getURL, webPage, authStringEnc))).get("data");
        	System.out.println("my reservation : "+CurrentThread.jsonArrayDeviceReservation);
        }
        catch(Exception e) 
        {
        	
        }
        
    }
     
    private String prepareURL() {
         
    	DateFormat Ft = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    	Date currTime = new Date();
    	Date dateEndTime = new Date();
    	currTime.setTime(currTime.getTime()-600000);
    	dateEndTime.setTime(dateEndTime.getTime() + 28800000);
        String deviceID = "";
        try{deviceID = CurrentThread.jsonDeviceInfo.getString("id");}catch(Exception e){}
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(Calendar.getInstance().getTime());
        String startTime = Integer.toString(currTime.getYear() + 1900) + "-" + Integer.toString(currTime.getMonth()+1) + "-" + Integer.toString(currTime.getDate()) + "-" + Integer.toString(currTime.getHours()) + "-" + Integer.toString(currTime.getMinutes()) + "-" + Integer.toString(currTime.getSeconds());//TODO: startTime
        String endTime = Integer.toString(dateEndTime.getYear()+1900) + "-" + Integer.toString(dateEndTime.getMonth()+1) + "-" + Integer.toString(dateEndTime.getDate()) + "-" + Integer.toString(dateEndTime.getHours()) + "-" + Integer.toString(dateEndTime.getMinutes()) + "-" + Integer.toString(dateEndTime.getSeconds());//TODO: startTime//TODO: endTime
         
        String getURL = DEVICES_URL + deviceID + RESERVATIONS +
                            "?current_timestamp=" + timeStamp +
                            "&start=" + startTime +
                            "&end=" + endTime ;
        return getURL;
    }
    protected void printGet(URL url, HttpURLConnection httpURLConnection, String result) throws IOException {
        int responseCode = httpURLConnection.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);
        System.out.println(result);
    }
    /**
     * @param entity can be "/users" / "/projects" / "/devices" etc
     */
    protected String doGet(String entity, String webPage, String authStringEnc) throws IOException {
        URL url = new URL(webPage+entity);
        URLConnection urlConnection = url.openConnection();
        urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);
        InputStream is = urlConnection.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        int numCharsRead;
        char[] charArray = new char[1024];
        StringBuffer sb = new StringBuffer();
        while ((numCharsRead = isr.read(charArray)) > 0) {
            sb.append(charArray, 0, numCharsRead);
        }
        String result = sb.toString();
        printGet(url, (HttpURLConnection) urlConnection, result);
        boolean isResponseValid = ((HttpURLConnection)urlConnection).getResponseCode() < 300;
        Assert.assertTrue("Did not get a valid response", isResponseValid);
        return result;
    }
}