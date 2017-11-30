package STXWActionTests;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

import Utils.Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.openqa.selenium.By;

import Cloud_API.GetDeviceReservations;


public class ExtendSession extends BaseTest {


    String popupMessage = "";
    Time reservation;
    Time reservationEnd;

    @Test
    public void test() {

    	
        	Date currentTime = getCurrentDate() ;        	        	
            Utilities.log(currentThread, "Enter to ExtendSessionTest");

            Utilities.sleep(currentThread, 2000);
            reservation = toTime(driver.findElement(By.xpath("/html/body/div[2]/div/div[1]/div/div/device-loupe/div/div/h3/span")).getText());
            Utilities.log(currentThread, "Reservation time by UI end after : " + reservation + " hours");

            driver.findElement(By.xpath("/html/body/div[2]/div/div[1]/div/div/device-loupe/div/div/div[2]/div[2]/button")).click();
            Utilities.log(currentThread, "click on Extend Session Button");

            Utilities.sleep(currentThread, 5000);

            try
            {
	            if (currentThread.STXWType.equals("manual")) 
	            {
	                popupMessage = driver.findElement(By.xpath("//*[@id=\"toast-container\"]/div")).getText();
	                Utilities.log(currentThread, "test if message is popUp " + popupMessage);
	            }
	            else 
	            {
	                popupMessage = driver.findElement(By.xpath("//*[@id=\"toast-container\"]/div/div[3]/div")).getText();
	                Utilities.log(currentThread, "test if message is popUp " + popupMessage);
	            }
            }
            catch(Exception e) 
            {
            	Utilities.log(currentThread, e.toString());
            	Assert.fail("ExtendSession message doesn't popUp");           
            }

            Utilities.sleep(currentThread, 2000);

            reservationEnd = toTime(driver.findElement(By.xpath("/html/body/div[2]/div/div[1]/div/div/device-loupe/div/div/h3/span")).getText());
            Utilities.log(currentThread, "the new reservation time end after : " + reservationEnd + " hours, by UI");

            Time extendTime = new Time(0, 0, 0);
            extendTime.setHours(reservationEnd.getHours() - reservation.getHours());
            extendTime.setMinutes(reservationEnd.getMinutes() - reservation.getMinutes());
            extendTime.setSeconds(reservationEnd.getSeconds() - reservation.getSeconds());
            Utilities.log(currentThread, "extend Time End : " + extendTime + " hours");
            Utilities.log(currentThread, "extend Time added : " + extendTime + " hours");


            if (!extendTime.after(new Time(0, 29, 0)) && extendTime.before(new Time(0, 30, 0)))
            {
            	Utilities.log(currentThread,"extend time must be almost 30 min in UI");
            	Assert.fail("extend time must be almost 30 min in UI");            	                
            }

            Utilities.sleep(currentThread, 5000);

            /**********************************TEST API*************************************/

            getDeviceReservations();
            Utilities.log(currentThread, "Get Device Reservations " + currentThread.jsonArrayDeviceReservation);
            JSONObject jsonDevcieReservationsObject = null;
            DateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd" + " " + "HH:mm:ss.SSS+02:00");
            int i = 0;
            boolean reserveFound = false;
            long startTime = System.currentTimeMillis();
            while((!reserveFound) && ((System.currentTimeMillis() - startTime) < 180000) ) {
	            for (i = 0; i < currentThread.jsonArrayDeviceReservation.length(); i++) {
	                try {
						if (currentThread.jsonArrayDeviceReservation.get(i) instanceof JSONObject) {
						    jsonDevcieReservationsObject = ((JSONObject) currentThread.jsonArrayDeviceReservation.get(i));
						    if (jsonDevcieReservationsObject.getString("title").contains(currentThread.properCase(currentThread.User)))
						        Utilities.log(currentThread, "test if its device reservation " + jsonDevcieReservationsObject + "suite");
						    {

						        Date first = DateFormat.parse(jsonDevcieReservationsObject.getString("start").split("T")[0] + " " + jsonDevcieReservationsObject.getString("start").split("T")[1]);
						        if ((Math.abs(currentTime.getTime() - first.getTime())) < 100000) {
						            Date End = DateFormat.parse(jsonDevcieReservationsObject.getString("end").split("T")[0] + " " + jsonDevcieReservationsObject.getString("end").split("T")[1]);
						            long extend = 0;
						            if (currentThread.STXWType.equals("manual")) {
						                extend = 1800000 * 2;
						            } else {
						                extend = 1800000 * 5;
						            }
						            if (End.getTime() - first.getTime() == extend) {
						                reserveFound = true;
						                Utilities.log(currentThread,"the reserve is found");
						            }
						        }
						    }

						}
					} catch (JSONException e) {
						e.printStackTrace();
					} catch (ParseException e) {
						e.printStackTrace();
					}
	            }
	            Utilities.sleep(currentThread, 1000);
            }
            
            if (i == currentThread.jsonArrayDeviceReservation.length() && !reserveFound)
            {                
            	Utilities.log(currentThread,"the reserve doesn't found"); 
            	Assert.fail("the reserve doesn't found");                
            }
            /**********************************TEST API*************************************/
     

    }

    public Time toTime(String strTime) {
        Time time = new Time(Integer.parseInt(strTime.split(":")[0]), Integer.parseInt(strTime.split(":")[1]), Integer.parseInt(strTime.split(":")[2].substring(0, 2)));
        return time;
    }

    public Date getCurrentDate() {
        DateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd" + " " + "HH:mm:ss.SSS+02:00");
        Date currDate = new Date();
        String year = Integer.toString(currDate.getYear() + 1900);
        String month = Integer.toString(currDate.getMonth() + 1);
        String day = Integer.toString(currDate.getDate());
        String hour = Integer.toString(currDate.getHours());
        String minuts = Integer.toString(currDate.getMinutes());
        String seconds = Integer.toString(currDate.getSeconds());

        String currDateText = year + "-" + month + "-" + day + " " + hour + ":" + minuts + ":" + seconds + ".000+02:00";
        try 
        {
			currDate = DateFormat.parse(currDateText);
		} 
        catch (ParseException e) 
        {
			e.printStackTrace();
		}

        return currDate;
    }
    
    /***********************************************************************************************************/
    
    
    public void getDeviceReservations()
    {
	         String DEVICES_URL  = "/devices/";
	         String RESERVATIONS = "/reservations";
	         String host = currentThread.enums.hostName;//TODO: host ip goes here
	         String port = "";//TODO: open port goes here
	         String webPage= "" + host + "" + port + "/api/v1";
	         String authStringEnc;
	         //SetUp
	         String name = "ayouba";//TODO: admin user name is here
             String password = "Experitest2012";//TODO: admin password is here
	         String authString = name + ":" + password;
	         authStringEnc = Base64.getEncoder().encodeToString(authString.getBytes());
	         //getReservationDetails
	         String getURL = prepareURL(DEVICES_URL, RESERVATIONS);
	         try
	         {
	         	currentThread.jsonArrayDeviceReservation =(JSONArray) (new JSONObject(doGet(getURL, webPage, authStringEnc))).get("data");
	         	Utilities.log("my reservation : "+currentThread.jsonArrayDeviceReservation);
	         }
	         catch(Exception e) 
	         {
	         	
	         }
	         
	         
    }

    private String prepareURL(String DEVICES_URL , String RESERVATIONS ) {
        
    	DateFormat Ft = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    	Date currTime = new Date();
    	Date dateEndTime = new Date();
    	currTime.setTime(currTime.getTime()-600000);
    	dateEndTime.setTime(dateEndTime.getTime() + 28800000);
        String deviceID = "";
        try{deviceID = currentThread.jsonDeviceInfo.getString("id");}catch(Exception e){}
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
        Utilities.log("Sending 'GET' request to URL : " + url);
        Utilities.log("Response Code : " + responseCode);
    }
    
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





