package STXWSuite.tests;

import STXWSuite.STXWBaseTest;
import Utils.Utilities;
import org.junit.Test;
import org.openqa.selenium.By;

import MyMain.Main;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Base64;

import org.json.JSONObject;
import org.junit.*;

public class Reboot extends STXWBaseTest {


    @Test
    public void test() {


        Utilities.log(runner, "Enter to Reboot testClass");
        Utilities.sleep(runner, 2000);
        try
        {
        	driver.findElement(By.xpath("//*[( contains(@id,'accordiongroup-') and contains(@id,'-panel'))]/div/md-list/md-list-item[2]/div/button[contains(@aria-label,'Reboot')]")).click();
        }
        catch(Exception e)
        {
        	driver.findElement(By.xpath("//*[@id='qReboot']/div/button")).click();
        }
        Utilities.log(runner, "click on reboot button");
        Utilities.sleep(runner, 2000);
        driver.findElement(By.xpath("/html/body/div[1]/div/div/reboot-confirm-dialog/div/div[3]/button[2]")).click();
        Utilities.log(runner, "click on confirm Button");

        Utilities.sleep(runner, 4000);
//        JSONObject deviceJson = null, deviceInfo = null;
//
//        try
//        {
//            deviceJson = getChosenDeviceJson(chosenDeviceName);
//        } 
//        catch (Exception e) {}
//        
//        deviceInfo = GetSpecificDevice(getStringFromJson(deviceJson, "id"));
        String udid = "";
//        udid = getStringFromJson(deviceInfo, "udid");
//        Utilities.log(runner, "Device udid is " + udid);
        
        udid = driver.findElement(By.xpath("/html/body/div[2]/div/div[1]/div/div/device-loupe/div/div/div[1]/h4")).getText();
        ArrayList<String> newTab = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(newTab.get(0));
        Utilities.log(runner, "Go To devices tab");
        Utilities.sleep(runner, 3000);
        
        waitForElement("//*[@id='content-after-toolbar']/div/md-content[2]/div/div/div[1]/md-input-container[label[contains(text(),'Search')]]/input");
        driver.findElement(By.xpath("//*[@id='content-after-toolbar']/div/md-content[2]/div/div/div[3]/md-menu/md-input-container[label[contains(text(),'Status')]]/div[textarea]/textarea")).click();
        Utilities.log(runner, "Click on status textarea");
        
        Utilities.sleep(runner, 2000);
        waitForElement("//*[contains(@id,'menu_container')]/md-menu-content[md-menu-item[md-checkbox[contains(@aria-label,'available')]]]/section/button[span[contains(text(),'All')]]");
        driver.findElement(By.xpath("//*[contains(@id,'menu_container')]/md-menu-content[md-menu-item[md-checkbox[contains(@aria-label,'available')]]]/section/button[span[contains(text(),'All')]]")).click();
        Utilities.log(runner, "Click on All button");
        
        driver.findElement(By.xpath("//*[@id='content-after-toolbar']/div/md-content[2]/div/div/div[1]/md-input-container[label[contains(text(),'Search')]]/input"));
        Utilities.log(runner, "Click on Search input");
        
        String status =  getStatus(udid);
        Utilities.log(runner, "Status is : " + status);
        
        driver.switchTo().window(newTab.get(1));
        Utilities.log(runner, "Go To device's tab");       
        
        if(!status.contains("In Use")) 
        {
        	Assert.fail("Device status doesn't convert to In Use ");
        }
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
//        boolean needToWait = true;
//        long startTime = System.currentTimeMillis();
//        while (needToWait && ((System.currentTimeMillis() - startTime) < 480000)) {
//            deviceInfo = GetSpecificDevice(getStringFromJson(deviceJson, "id"));            
//            if (getStringFromJson(deviceInfo, "currentStatus").equals("Offline") || getStringFromJson(deviceInfo, "currentStatus").equals("offline")) {
//                needToWait = false;
//            }
//            Utilities.sleep(runner, 5000);
//        }
//
//        Utilities.log(runner, "new device Info : " + deviceInfo);
//        if (getStringFromJson(deviceInfo, "currentStatus").equals("Offline") || getStringFromJson(deviceInfo, "currentStatus").equals("offline")) {
//            Utilities.log(runner, "the device's currentStatus is Offline");
//            needToWait = true;
//            startTime = System.currentTimeMillis();
//            while (needToWait && ((System.currentTimeMillis() - startTime) < 480000)) {
//
//                deviceInfo = GetSpecificDevice(getStringFromJson(deviceJson, "id"));
//                if (getStringFromJson(deviceInfo, "currentStatus").equals("online") || getStringFromJson(deviceInfo, "currentStatus").equals("Online")) {
//                    needToWait = false;
//                }
//                Utilities.sleep(runner, 1000);
//            }
//            Utilities.log(runner, GetSpecificDevice(getStringFromJson(deviceJson, "id")).toString());
//            if (!(getStringFromJson(GetSpecificDevice(getStringFromJson(deviceJson, "id")), "currentStatus").equals("online"))) {
//                Utilities.log(runner, "the device doesn't goes to online case");
//                Utilities.log(runner, "The displayStatus " + getStringFromJson(deviceInfo, "currentStatus"));
//                Assert.fail("The device doesn't go to online case");
//            } else {
//                Utilities.log(runner, "the device's currentStatus is online");
//            }
//        } else {
//        	deviceInfo = GetSpecificDevice(getStringFromJson(deviceJson, "id"));
//        	Utilities.log(runner, "Device CurrentStatus is " + getStringFromJson(deviceInfo, "currentStatus"));
//            Utilities.log(runner, "the device doesn't go to offline case");
//            Assert.fail("the device doesn't go to offline case");
//        }

    }

    private String getStatus(String udid) {
    	
    	driver.findElement(By.xpath("//*[@id='content-after-toolbar']/div/md-content[2]/div/div/div[1]/md-input-container[label[contains(text(),'Search')]]/input")).sendKeys(udid);
    	Utilities.log(runner, "Write " + udid + " in search input ");
		int count =0 ;
    	Utilities.sleep(runner, 5000);
    	while((!WaitForText("//*[@id='content-after-toolbar']/div/md-virtual-repeat-container/div/div[2]/div/md-content/table/tbody/tr/td[3]/div", "In Use")) || count < 8) 
    	{
    		Utilities.log(runner, "device current status: " + driver.findElement(By.xpath("//*[@id='content-after-toolbar']/div/md-virtual-repeat-container/div/div[2]/div/md-content/table/tbody/tr/td[3]/div")).getText());
    		count++;
    	}
        return driver.findElement(By.xpath("//*[@id='content-after-toolbar']/div/md-virtual-repeat-container/div/div[2]/div/md-content/table/tbody/tr/td[3]/div")).getText();
        
	}

	public String getStringFromJson(JSONObject jsonObject, String cell) {
        String result = "";
        try {
            result = jsonObject.getString(cell);
        } catch (Exception e) {
        }

        return result;
    }


    public JSONObject GetSpecificDevice(String deviceId) {
        JSONObject JSONObject = null;
        String DEVICES_URL = "/devices/";
        String host = Main.cs.URL_ADDRESS;
        String port = "";
        String webPage = "" + host + "" + port + "/api/v1";
        String authStringEnc;

        String name = "ayouba";//TODO: admin testPlanUser name is here
        String password = "Experitest2012";//TODO: admin password is here
        String authString = name + ":" + password;
        authStringEnc = Base64.getEncoder().encodeToString(authString.getBytes());
        String getURL = prepareURL(DEVICES_URL, deviceId);
        try {
            return (JSONObject) (new JSONObject(doGet(getURL, webPage, authStringEnc)).get("data"));
        } catch (Exception e) {
        }
        return JSONObject;
    }

    private String prepareURL(String DEVICES_URL, String deviceID) {
        String getURL = DEVICES_URL + deviceID;
        return getURL;
    }

    protected String doGet(String entity, String webPage, String authStringEnc) throws IOException {
        URL url = new URL(webPage + entity);
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
        //   printGet(url, (HttpURLConnection) urlConnection, result);
        boolean isResponseValid = ((HttpURLConnection) urlConnection).getResponseCode() < 300;
        Assert.assertTrue("Did not get a valid response", isResponseValid);
        return result;
    }


}
