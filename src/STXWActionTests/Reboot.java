package STXWActionTests;

import Utils.Utilities;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.openqa.selenium.By;



import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;

import org.json.JSONObject;
import org.junit.*;

public class Reboot extends BaseTest  {



    @Test
    public void test() {


            Utilities.log(currentThread, "Enter to Reboot testClass");
            Utilities.sleep(currentThread, 2000);
            driver.findElement(By.xpath("//*[( contains(@id,'accordiongroup-') and contains(@id,'-panel'))]/div/md-list/md-list-item[2]/div/button[contains(@aria-label,'Reboot')]")).click();
            Utilities.log(currentThread, "click on reboot button ");
            Utilities.sleep(currentThread, 2000);
           
            driver.findElement(By.xpath("/html/body/div[1]/div/div/reboot-confirm-dialog/div/div[3]/button[2]")).click();
            Utilities.log(currentThread, "click on confirm Button ");
            
            Utilities.sleep(currentThread, 4000);
            JSONObject deviceJson = null, deviceInfo = null;
         
            try 
            {            
	            deviceJson = getChosenDeviceJson(currentThread.chosenDeviceName);
            }
            catch(Exception e) {}
	        deviceInfo = GetSpecificDevice(getStringFromJson(deviceJson,"id"));
            
            
            /*********************************************************************************************************************************************/
            boolean needToWait = true;
            long startTime = System.currentTimeMillis();
             
            
	            while(needToWait && ((System.currentTimeMillis() - startTime) < 240000) ) 
	            {    
	            	 deviceInfo = GetSpecificDevice(getStringFromJson(deviceJson,"id"));
	            	 if(getStringFromJson(deviceInfo,"currentStatus").equals("Offline") ||getStringFromJson(deviceInfo,"currentStatus").equals("offline") ) 
	            	 {
	            		 needToWait = false;
	            	 }
	            	 Utilities.sleep(currentThread, 1000);
	            }
            
            
            /*********************************************************************************************************************************************/
            Utilities.log(currentThread, "new device Info : " + deviceInfo);
            
            if(getStringFromJson(deviceInfo,"currentStatus").equals("Offline") ||getStringFromJson(deviceInfo,"currentStatus").equals("offline") )
            {
            	Utilities.log(currentThread, "the device's currentStatus is Offline");
            	
            	/******************************************************************************************/
            	  needToWait = true;
                  startTime = System.currentTimeMillis();
                 while(needToWait && ((System.currentTimeMillis() - startTime) < 240000) ) 
                 {
                	 
                 	 deviceInfo = GetSpecificDevice(getStringFromJson(deviceJson,"id"));
                 	 if(getStringFromJson(deviceInfo,"currentStatus").equals("online") ||getStringFromJson(deviceInfo,"currentStatus").equals("Online") ) 
                 	 {
                 		 needToWait= false;
                 	 }
                 	 Utilities.sleep(currentThread, 1000);
                 }
            	/******************************************************************************************/
            	Utilities.log(currentThread,GetSpecificDevice(getStringFromJson(deviceJson,"id")).toString());
            	if(! (getStringFromJson(GetSpecificDevice(getStringFromJson(deviceJson,"id")),"currentStatus").equals("online")) )
            	{            		
                	Utilities.log(currentThread, "the device doesn't transformation to online");
                	System.out.println("the displayStatus " + getStringFromJson(deviceInfo,"currentStatus"));
                	Assert.fail("the device doesn't transformation to online");
            	}
            	else 
            	{
            		Utilities.log(currentThread, "the device's currentStatus is online");
            	}
            }
            else 
            {
            	Utilities.log(currentThread, "the device doesn't transformation to offline");            	
            	Assert.fail("the device doesn't transformation to offline");
            }
            
    }

    public String getStringFromJson(JSONObject jsonObject, String cell) 
    {
    	String result = "";
    	try
    	{
    		result = jsonObject.getString(cell);
    	}
    	catch(Exception e) {}
    	
    	return result;
    }


    
    public JSONObject GetSpecificDevice(String deviceId)
    {
    	 JSONObject JSONObject = null;
         String DEVICES_URL = "/devices/";
	     String host = "qacloud.experitest.com";
	     String port = "";
	     String webPage= "https://" + host + "" + port + "/api/v1";
	     String authStringEnc;
	     
	     String name = "ayouba";//TODO: admin user name is here
	     String password = "Experitest2012";//TODO: admin password is here
	        String authString = name + ":" + password;
	        authStringEnc = Base64.getEncoder().encodeToString(authString.getBytes());
	        String getURL = prepareURL(DEVICES_URL, deviceId) ;
	        try {return (JSONObject) (new JSONObject(doGet(getURL, webPage, authStringEnc)).get("data"));}catch(Exception e) {}
	        return JSONObject;
    }
	        
		 private String prepareURL(String DEVICES_URL, String deviceID) 
		 {
		    String getURL = DEVICES_URL + deviceID;
		    return getURL;
		 } 
	 
		 protected void printGet(URL url, HttpURLConnection httpURLConnection, String result) throws IOException
		 {
		         int responseCode = httpURLConnection.getResponseCode();
			     System.out.println("\nSending 'GET' request to URL : " + url);
			     System.out.println("Response Code : " + responseCode);
			     System.out.println(result);
		 }
	    
	    
		    protected String doGet(String entity, String webPage, String authStringEnc) throws IOException
		    {
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
