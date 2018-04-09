package STXWSuite;


import MyMain.BaseBaseTest;
import MyMain.Main;
import Utils.Utilities;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import java.util.ArrayList;
import java.util.Date;



public abstract class STXWBaseTest extends BaseBaseTest{

    String NUM_DEVICES = "//*[@class='text-muted']";
    @Before
    public void SetUp() throws Exception {
        runner = (STXWRunner) Thread.currentThread();

        Utilities.log(runner,"-----------------------------" + runner.getName() + " Starting A New Test!-----------------------------");

        Utilities.log(runner, "Enter to setUp");

        driver = createDriver(runner.testName);


        LoginInToCloud();
        Utilities.sleep(runner, 1000);
        NavigateToAvailableDevicesView();


        //added this to handle a case where a device was reserved from another session
        int counter = 0;
        while(counter < 5) {
            chooseDevice();

            if(openDevice()){
                if(switchToTab()) {
                    break;
                }
                else{
                    NavigateToAvailableDevicesView();
                    counter ++;
                    continue;
                }
            }
            else{
                counter ++;
                continue;
            }
        }
        int timeOutCounter = 0;

        while (timeOutCounter < 10) {
            try {
                driver.findElement(By.xpath("/html/body/div[2]/div/div[1]/div/div/device-loupe/div/div/h3/span")).getText();
                break;
            } catch (Exception e) {
                Utilities.log(runner, "Waiting For Elements");
                Utilities.sleep(runner, 500);
                timeOutCounter++;
            }
        }

        needToReleaseOnFinish = true;
        getChosenDeviceJson(chosenDeviceName);
        Utilities.log(runner, "build json object for chosen device ");

    }

    public synchronized void chooseDevice() throws Exception {
        chooseDevice(getDeviceListSize());
//        driver.findElement(By.xpath("//*[@id='content-after-toolbar']/div/md-virtual-repeat-container/div/div[2]/div/md-content/table/tbody/tr[" + ChosenDevice + "]/td[4]/div")).click();
//        waitUntilElementMarked("//*[@id='content-after-toolbar']/div/md-virtual-repeat-container/div/div[2]/div/md-content/table/tbody/tr[" + ChosenDevice + "]");
//        chosenDeviceName = driver.findElement(By.xpath("//*[@id='content-after-toolbar']/div/md-virtual-repeat-container/div/div[2]/div/md-content/table/tbody/tr[" + ChosenDevice + "]/td[4]")).getText();
//        Utilities.log(runner, "choosing device by xpath :" + chosenDeviceName);

        switch (runner.userType) {
            case "ProjectAdmin":
                manualIndex = 5;
                break;
            case "Admin":
                manualIndex = 5;
                break;
            case "User":
                manualIndex = 4;
                break;
            default:
                manualIndex = 4;
                break;
        }

        Utilities.sleep(runner, 2000);
        Utilities.log(runner, "manualIndex :" + manualIndex);

    }

    public boolean openDevice() {
        if (rand.nextInt(2) == 0) {
            Utilities.log(runner, "choosing MANUAL");
            return openSTM();
        } else {
            Utilities.log(runner, "choosing AUTOMATION");
            return openSTA();
        }
    }

    private boolean checkIfDeviceCantBeOpened(){
        if(waitForElement("/html/body/div[1]/div/div/div/div[1]/h4[contains(text(),'The device is already In-Use')]", 1000)){
            Utilities.log(runner, "The device is already In-Use");
            return false;
        }
        else if(waitForElement("/html/body/div[1]/div/div/div[div[h4[contains(text(),'Could not open device')]]]/div[2]", 1000)){
            if(driver.findElement(By.xpath("/html/body/div[1]/div/div/div[div[h4[contains(text(),'Could not open device')]]]/div[2]")).getText().contains("Device is currently reserved to different user"));
            {
                Utilities.log(runner, "Device is currently reserved to different user");
                return false;
            }
        }

        else if(waitForElement("/html/body/div[1]/div/div/div/div[1]/h4[contains(text(),'Could not open device')]",1000))
        {
            Assert.fail("Tab Didn't Loaded!! because : "+ driver.findElement(By.xpath("/html/body/div[1]/div/div/div[div[h4[contains(text(),'Could not open device')]]]/div[2]")).getText());
        }
        else if (Main.checkIfDeviceIsReservedForDifferentUser(chosenDeviceName, Main.cloudServer.getUser())) {
            Utilities.log("Device is reserved for different user, selecting new device");
            return false;
        }
        return true;
    }
    private boolean switchToTab() throws InterruptedException {
        boolean found = false;
        long startWaitTime = System.currentTimeMillis();
        while (!found && (System.currentTimeMillis() - startWaitTime) < 150000) {
            try{
                ArrayList<String> tabList = new ArrayList<String>(driver.getWindowHandles());
            if (tabList.size() > 1) {
                driver.switchTo().window(tabList.get(1));
                Utilities.log(runner, "Switched window to the STXWType tab");
                found = true;
                return true;
            } else {
                Utilities.log(runner, "waiting for tab to open");
                Utilities.sleep(runner, 1000);
            }
            } catch (Exception e) {
                Utilities.log(runner, "got exception while waiting for tab to open ");
                e.printStackTrace();
            }

        }
        if(!checkIfDeviceCantBeOpened()) {
            Utilities.log(runner, "Device can't be opened, exiting");
            return false;
        }

////                if(driver.findElement(By.xpath("//*[contains(text(), 'My Button')]")).isDisplayed());
//            }

        if (!found) {

            if(!checkIfDeviceCantBeOpened()){
                return false;
            }
            else {
                Assert.fail("Tab Didn't Opened!");
            }
        }

        boolean needToWaitToLoadFlag = true;
        startWaitTime = System.currentTimeMillis();
        while (needToWaitToLoadFlag && (System.currentTimeMillis() - startWaitTime) < 120000) {
            try {
                driver.findElement(By.xpath("/html/body/div[2]/div/div[1]/div/div/device-loupe/div/div/h3/span")).isEnabled();
                needToWaitToLoadFlag = false;
//                waitForPageToLoad(driver);
                Utilities.sleep(runner, 5000);
            } catch (Exception e) {
                Utilities.log(runner, "waiting for tab to load");
                Thread.sleep(1000);
            }

        }
        if (needToWaitToLoadFlag) {
            if (waitForElement("/html/body/div[2]/div/md-card")) {
                Assert.fail(driver.findElement(By.xpath("/html/body/div[2]/div/md-card/md-card-title/md-card-title-text/span")).getText() + "\n" + driver.findElement(By.xpath("/html/body/div[2]/div/md-card/md-card-content/p[1]")).getText());
            }
                Assert.fail("Tab Didn't Loaded!!");
        }
        return true;
    }


    private boolean openSTA() {
        Utilities.log(runner, "OPENING AUTOMATION");

        String webElementIsDisabled = driver.findElement(By.xpath("//*[@id='full-page-container']/div[1]/div/div/div/button[" + (manualIndex + 1) +"]")).getAttribute("disabled");
        if( webElementIsDisabled != null){
            if(webElementIsDisabled.contains("disabled")) {
                Utilities.log(runner, "Removing the device " + chosenDeviceName + " from used devices");
                STXWRunner.usedDevices.replace(chosenDeviceName, false);
                return false;
            }
        }

        runner.STXWType = "automation";
        driver.findElement(By.xpath("//*[@id='full-page-container']/div[1]/div/div/div/button[" + (manualIndex + 1) + "]")).click();
        Utilities.log(runner, "Removing the device " + chosenDeviceName + " from used devices");
        STXWRunner.usedDevices.replace(chosenDeviceName, false);
        Utilities.log(runner, "click on Automation Button");

        return true;
    }

    private boolean openSTM() {
        Utilities.log(runner, "OPENING MANUAL");
        runner.STXWType = "manual";
        String webElementIsDisabled = driver.findElement(By.xpath("//*[@id='full-page-container']/div[1]/div/div/div/button[" + manualIndex + "]")).getAttribute("disabled");
        if( webElementIsDisabled != null){
            if(webElementIsDisabled.equalsIgnoreCase("true") || webElementIsDisabled.equalsIgnoreCase("disabled")) {
                Utilities.log(runner, "Removing the device " + chosenDeviceName + " from used devices");
                STXWRunner.usedDevices.replace(chosenDeviceName, false);
                return false;
            }
        }
        driver.findElement(By.xpath("//*[@id='full-page-container']/div[1]/div/div/div/button[" + manualIndex + "]")).click();
        Utilities.log(runner, "Removing the device " + chosenDeviceName + " from used devices");
        STXWRunner.usedDevices.replace(chosenDeviceName, false);
        Utilities.log(runner, "click on Manual Button");
        return true;
    }

    private int getDeviceListSize() {
        int index = 0;
        try {
        	waitForElement("//*[@id='full-page-container']/div[1]/div/div/div/div[3]/span");
            driver.findElement(By.xpath("//*[@id='full-page-container']/div[1]/div/div/div/div[3]/span"));
            index = 3;
        } catch (Exception e) {
            index = 2;
        }

        int timeOutCounter = 0;
//        boolean needToWait = true;
        while (!((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete") && timeOutCounter < 10) {
            try {
//                WebElement DevicesElement = driver.findElements(By.xpath(NUM_DEVICES)).stream().filter(q -> q.getText().contains("Devices:")).collect(Collectors.toList()).get(0);
//                needToWait = DevicesElement.getText().contains("0 / 0");
//                needToWait = driver.findElement(By.xpath("//*[@id='full-page-container']/div[1]/div/div/div/div[" + index + "]/span")).getText().contains("0 / 0") || driver.findElement(By.xpath("//*[@id='full-page-container']/div[1]/div/div/div/div[" + index + "]/span")).getText().equals("");
            } catch (Exception e) {
                Utilities.log(runner, "Waiting For Devices To Update");
                Utilities.sleep(runner, 500);
                timeOutCounter++;
            }
        }

        try {
            devicesInfo = (driver.findElement(By.xpath("//*[@id='full-page-container']/div[1]/div/div/div/div[" + index + "]/span")).getText());
            Utilities.log(runner, "get information about number available devices");
        } catch (Exception e1) {
            throw e1;
        }


        Utilities.log(runner, "Devices Number Info : " + devicesInfo);
        try{
        devicesInfo = devicesInfo.split("Devices: ")[1];
        devicesListSize = Integer.parseInt(devicesInfo.split(" /")[0]);

        }catch (Exception e){
            Assert.fail("Can't get devices numbers");
        }
        Utilities.log(runner, "" + devicesListSize);
        if (devicesListSize > 10) devicesListSize -= 10;
        Utilities.log(runner, "Taking the device list below 10 - " + devicesListSize);

        return devicesListSize;
    }

    private void NavigateToAvailableDevicesView() {
        driver.get(Main.cs.URL_ADDRESS + "/index.html#" + "/devices");
        Utilities.log(runner, "go to the devices - " + Main.cs.URL_ADDRESS + "/index.html#" + "/devices");

        boolean needToWaitForPageLoad = true;

        try {
            Utilities.log(runner, "Checking if we are on launchPad");
            driver.findElement(By.xpath("//*[@id='content']/md-content/md-toolbar/div/div/a")).click();
            Utilities.log(runner, "We Clicked to move to devices page from launchPad");
        } catch (Exception e) {
            Utilities.log(runner, "On Devices Page");
        }
        long startWaitTime = System.currentTimeMillis();
        while (needToWaitForPageLoad && (System.currentTimeMillis() - startWaitTime) < 120000) {
            try {
                driver.findElement(By.xpath("//*[@id='content-after-toolbar']/div/md-content[2]/div/div/div[3]/md-menu/md-input-container/div[1]"));
                needToWaitForPageLoad = false;
            } catch (Exception e) {
                Utilities.log(runner, "waiting for devices page to load");
                Utilities.sleep(runner, 1000);
            }

        }
        if (needToWaitForPageLoad) {
            Assert.fail("Devices Page Did Not Load!");
        }
        Utilities.sleep(runner, 2000);
        waitUntilVisible("//*[@id='content-after-toolbar']/div/md-content[2]/div/div/div[3]/md-menu/md-input-container/div[1]");
        driver.findElement(By.xpath("//*[@id='content-after-toolbar']/div/md-content[2]/div/div/div[3]/md-menu/md-input-container/div[1]")).click();
        Utilities.log(runner, "click on status");
        Utilities.sleep(runner, 2000);
        waitForElement("//*[(contains(@id,'menu_container') and @aria-hidden='false')]/md-menu-content/section/button[2]");
        Utilities.log(runner, "trying to click on clear ");
        driver.findElement(By.xpath("//*[(contains(@id,'menu_container') and @aria-hidden='false')]/md-menu-content/section/button[2]")).click();
        Utilities.sleep(runner, 2000);
        Utilities.log(runner, "trying to click on Available");
        driver.findElement(By.xpath("//*[(contains(@id,'menu_container') and @aria-hidden='false')]/md-menu-content/md-menu-item[1]/md-checkbox")).click();
        Utilities.sleep(runner, 2000);
        driver.navigate().back();
        driver.get(Main.cs.URL_ADDRESS + "/index.html#" + "/devices");
        
    }
    
    private void LoginInToCloud() {
        driver.get(Main.cs.URL_ADDRESS + "/index.html#");
        Utilities.log(runner, "go to " + Main.cs.URL_ADDRESS + "/index.html#");
        
        waitForElement("//*[@name='username']");
        Utilities.sleep(runner, 2000);        
        driver.findElement(By.xpath("//*[@name='username']")).sendKeys(runner.user);
        Utilities.log(runner, "Write username (" + runner.user + ")");
        int counter = 0;
        Utilities.sleep(runner, 2000);
        waitForElement("//*[@name='username' and contains(@class,'ng-not-empty')]");
        while(driver.findElement(By.xpath("//*[@name='username']")).getAttribute("class").contains("ng-empty") && counter < 20) 
        {
        	try{
        		driver.findElement(By.xpath("//*[@name='username']")).clear();
        		Utilities.log(runner, "Clear the userName input");
        		
        	}catch(Exception e) {}
        	driver.findElement(By.xpath("//*[@name='username']")).sendKeys(runner.user);
            Utilities.log(runner, "Write username (" + runner.user + ")");
            Utilities.sleep(runner, 1000);
            counter++;
        }

        driver.findElement(By.name("password")).sendKeys(runner.enums.STXWPassword);
        Utilities.log(runner, "write the password ");

        driver.findElement(By.name("login")).click();
        Utilities.log(runner, "click on login");
        
        if(!WaitForElement("//*[@id='side-menu']/li[a/span[contains(text(),'Devices')]]")) 
        {
        	if(waitForElement("/html/body/div[2]/div[1]/div/form/div[label[contains(text(),'Select Project')]]/select"))
        	{
        		switch(runner.userType)
        		{
        		case "Admin" :
        			driver.findElement(By.xpath("/html/body/div[2]/div[1]/div/form/div[label[contains(text(),'Select Project')]]/select")).sendKeys("Dafault");
        			break;
        		case "ProjectAdmin":
        			if(runner.user.contains("1")) 
        			{
        				driver.findElement(By.xpath("/html/body/div[2]/div[1]/div/form/div[label[contains(text(),'Select Project')]]/select")).sendKeys("ayoubProjectDeepTest1");
        			}
        			else 
        			{
        				driver.findElement(By.xpath("/html/body/div[2]/div[1]/div/form/div[label[contains(text(),'Select Project')]]/select")).sendKeys("ayoubProjectDeepTest2");
        			}
        			break;
        		case "User":
        			if(runner.user.contains("2")) 
        			{
        				driver.findElement(By.xpath("/html/body/div[2]/div[1]/div/form/div[label[contains(text(),'Select Project')]]/select")).sendKeys("ayoubProjectDeepTest2");
        			}
        			else 
        			{
        				driver.findElement(By.xpath("/html/body/div[2]/div[1]/div/form/div[label[contains(text(),'Select Project')]]/select")).sendKeys("ayoubProjectDeepTest1");
        			}
        			break;
        		}
        		driver.findElement(By.xpath("/html/body/div[2]/div[1]/div/form/button[1]")).click();
        		Utilities.sleep(runner, 3000);
        	}
        	
        }


    }

    @Test
    abstract public void test();

    @After
    public void finish() {
        Utilities.log(runner, "Finishing");


        if (needToReleaseOnFinish) {
            try {
                driver.findElement(By.xpath("/html/body/div[2]/div/div[1]/div/div/device-loupe/div/div/div[2]/div[3]/button")).click();
                Utilities.log(runner, "release device");
            } catch (Exception e) {

                Utilities.log(runner, e);
            }

            Utilities.sleep(runner, 5000);

            try {
                driver.findElement(By.xpath("/html/body/div[1]/div/div/before-exit-dialog/div/div[3]/button[1]")).click();
                Utilities.log(runner, "click Release");
            } catch (Exception e) {
                Utilities.log(runner, e);
            }

            Utilities.sleep(runner, 2000);

        }


//        runner.pw.close();

        Date CurrentTime = new Date();

        String line = String.format("%-30s%-30s%-30s%-30s%-5s", CurrentTime, runner.user, runner.testClass.getName(), (((double) (CurrentTime.getTime() - startTime.getTime())) / 60000), "C:\\Users\\ayoub.abuliel\\eclipse-workspace\\CloudDeepTestStxw\\" + Main.logsFolder.getName() + "\\" + runner.testName);
        runner.overallWriter.println(line);
        runner.overallWriter.flush();


    }

    public boolean isOSValid(String Xpath) {
        Utilities.log(runner, "Enter to isOSValid function with xpath " + Xpath);

        String Os = "";
        boolean Valid = true;
        double Version;
        try {
        	waitForElement(Xpath);
            Os = driver.findElement(By.xpath(Xpath)).getText();
        } catch (Exception e) {
            Utilities.log(e);
        }
        Utilities.log(runner, "get text (deviceOS = " + Os + ") from " + Xpath);

        if (Os.contains("ios")) {
            String VersionParts = (Os.split("IOS ")[1]);
            VersionParts = VersionParts.substring(0, 3);
            Version = Double.parseDouble(VersionParts);
            VersionParts = VersionParts.substring(0, 3);
            Valid = Version > 8;
        } else {
            if (Os.contains("Android")) {
                if(Os.toUpperCase().contains("P")){
                    return true;
                }
                String VersionParts = (Os.split("Android ")[1]);
                VersionParts = VersionParts.substring(0, 3);

                Version = Double.parseDouble(VersionParts);
                Valid = Version >= 4.3;
            }
        }
        Utilities.log(runner, "device has valid version");
        return Valid;
    }

    public synchronized int chooseDevice(int DevicesSize) {
        Utilities.log(runner, "Enter to ChooseDeviceIndex");

        if (DevicesSize > 15) {
            DevicesSize = 15;
        }

        if (DevicesSize == 0) {
            Utilities.log(runner, "doesn't found any device !!");
            Assert.fail("Can't find any device in the cloud");
        }
        int[] arrayValidDevices = new int[DevicesSize + 1];
        arrayValidDevices[0] = -1;
        for (int i = 1; i < DevicesSize + 1; i++) {

            arrayValidDevices[i] = 1;
        }
        int chooseDevice = rand.nextInt(Math.min(DevicesSize, 6)) + 1;
        int count = 0;
        while(count < 5){
            try {
                updateChoseDeviceName(chooseDevice);
                break;
            }catch (Exception e){
                chooseDevice = rand.nextInt(Math.min(DevicesSize, 6)) + 1;
                Utilities.log(runner, "Got exception in choose device function " + e.getMessage());
                count ++;
            }
        }

        int j = 0;

        while((!isOSValid("//*[@id='content-after-toolbar']/div/md-virtual-repeat-container/div/div[2]/div/md-content/table/tbody/tr[" + chooseDevice + "]/td[5]/div") || STXWRunner.usedDevices.get(chosenDeviceName)) && j < 20) {
            try{
                chooseDevice = updateChosenDevice(getDeviceListSize());
            }catch(Exception e){
                chooseDevice = updateChosenDevice(getDeviceListSize());
            }
            j++;
        }
        if(j == 20){
            Utilities.log(runner, "doesn't found a valid device!!");
           Assert.fail("Can't find any device on the cloud");
        }
        Utilities.log(runner, "Adding the device " + chosenDeviceName + " to used devices");
        STXWRunner.usedDevices.put(chosenDeviceName, true);
        return chooseDevice;
//        while (chooseDevice != -1) {
//            while (arrayValidDevices[chooseDevice] == 0) {
////                chooseDevice = getNextValid(arrayValidDevices, chooseDevice);
//
//            } else {
//
//                if (!isOSValid("//*[@id='content-after-toolbar']/div/md-virtual-repeat-container/div/div[2]/div/md-content/table/tbody/tr[" + chooseDevice + "]/td[5]/div")) {
//                    arrayValidDevices[chooseDevice] = 0;
//                } else {
//                    return (chooseDevice);
//                }
//            }
//        }
    }

    public void updateChoseDeviceName(int chooseDevice) {
        chosenDeviceName = driver.findElement(By.xpath("//*[@id='content-after-toolbar']/div/md-virtual-repeat-container/div/div[2]/div/md-content/table/tbody/tr[" + chooseDevice + "]/td[4]")).getText();
        driver.findElement(By.xpath("//*[@id=\"content-after-toolbar\"]/div/md-virtual-repeat-container/div/div[2]/div/md-content/table/tbody/tr[td/div/span[contains(text(),'"
                + chooseDevice + "')]]")).click();
        waitUntilElementMarked("//*[@id=\"content-after-toolbar\"]/div/md-virtual-repeat-container/div/div[2]/div/md-content/table/tbody/tr[td/div/span[contains(text(),'"
                + chooseDevice + "')]]");
        Utilities.log(runner, "choosing device by xpath :" + chosenDeviceName);
        if(!STXWRunner.usedDevices.containsKey(chosenDeviceName)){
            Utilities.log(runner, "First encounter with the device " + chosenDeviceName + " adding to used devices");
            STXWRunner.usedDevices.put(chosenDeviceName, false);
        }
        Utilities.log(runner, "Current chose device " + chosenDeviceName);
    }

    public int updateChosenDevice(int DevicesSize) {
        int chooseDevice;
        Utilities.log(runner, "choosing a new device");
        chosenDeviceName = null;
        chooseDevice = rand.nextInt(Math.min(DevicesSize, 6)) + 1;
        updateChoseDeviceName(chooseDevice);

        return chooseDevice;
    }

    public int getNextValid(int[] arrayValidDevices, int Choosedevice) {
        for (int i = Choosedevice; i < arrayValidDevices.length; i++) {
            if (arrayValidDevices[i] == 1) {
                return i;
            }
        }
        for (int i = 1; i < Choosedevice; i++) {
            if (arrayValidDevices[i] == 1) {
                return i;
            }
        }
        return -1;
    }

    public JSONObject getChosenDeviceJson(String deviceName) throws JSONException {
        Utilities.log(runner, "getting The Chosen Device Json");
        JSONObject jsonObject = new JSONObject(runner.CloudDevicesInfo);
        JSONArray jsonArray = (JSONArray) jsonObject.get("data");
        JSONObject jsondevcieObject = null;
        for (int i = 0; i < jsonArray.length(); i++) {
            if (jsonArray.get(i) instanceof JSONObject) {
                jsondevcieObject = ((JSONObject) jsonArray.get(i));
                if (jsondevcieObject.getString("deviceName").contains(deviceName) || (jsondevcieObject.getString("deviceName").contains("hadar.zarihan") && deviceName.contains("hadar.zarihan")) ||
                        ((jsondevcieObject.getString("deviceName").contains("navot D’s iPad")) && (deviceName.contains("navot D’s iPad")))) {
                    Utilities.log(runner, "DATA has arrived in !!");
                    runner.jsonDeviceInfo = jsondevcieObject;
                    return jsondevcieObject;
                }
            }
        }
        return jsondevcieObject;
    }

//    void waitForPageToLoad(WebDriver driver) {
//        new WebDriverWait(driver, 30).until(wd ->
//                ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
//    }

    public boolean waitForElement(String xpath) {

        return waitForElement(xpath, 60000);

    }

    public boolean waitForElement(String xpath, int timeoutMilliSec) {

        boolean printToLogs = false;
        boolean needToWaitToElement = true;
        long startWaitTime = System.currentTimeMillis();

        while (needToWaitToElement && (System.currentTimeMillis() - startWaitTime) < timeoutMilliSec) {
            try {
                driver.findElement(By.xpath(xpath));
                needToWaitToElement = false;
            } catch (Exception e) {
                if(!printToLogs){
                    Utilities.log(runner, "waiting for Element - " + xpath);
                }
                printToLogs = true;
                Utilities.sleep(runner, 1000);
            }

        }
        Utilities.log(runner, "finished waiting for Element - " + xpath);
        return !needToWaitToElement;

    }
    protected boolean waitUntilElementMarked(String markedXPath) 
    {
    	Utilities.sleep(runner, 3000);
    	int count = 0;
    	boolean needToWait = true;
    	while( needToWait && count<100)  
    	{    		
    		try
    		{
    			if(driver.findElement(By.xpath(markedXPath)).getAttribute("class").contains("st-selected")) 
    			{
    				needToWait = false;
    			}
    			else 
    			{
    				driver.findElement(By.xpath(markedXPath)).click();
    			}
    		}catch(Exception e) {}
    		Utilities.sleep(runner, 500);
    	}
    	return !needToWait;    	
    }

    protected boolean waitUntilVisible(String XPath) 
    {
    	int counter = 0;
    	boolean needToWait = true;
    	while( needToWait && counter<100)  
    	{
    		if(driver.findElement(By.xpath(XPath)).isDisplayed()) 
    		{
    			needToWait = false;
    			Utilities.log(runner, "Element is Visible");
    		}
    		else 
    		{
    			counter++;
    		}
    	}
    	
    	return !needToWait;
    }



}


