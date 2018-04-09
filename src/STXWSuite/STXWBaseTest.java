package STXWSuite;


import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import MyMain.BaseBaseTest;
import Utils.Utilities;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import MyMain.Main;


public abstract class STXWBaseTest extends BaseBaseTest{


    @Before
    public void SetUp() throws Exception {
        runner = (STXWRunner) Thread.currentThread();

        Utilities.log(runner,"-----------------------------" + runner.getName() + " Starting A New Test!-----------------------------");

        Utilities.log(runner, "Enter to setUp");

        driver = createDriver();
        LoginInToCloud();
        Utilities.sleep(runner, 1000);
        NavigateToAvailableDevicesView();

        int ChosenDevice = ChooseDeviceIndex(GetDeviceListSize());

        if (ChosenDevice == -1) {
            Utilities.log(runner, "doesn't found a valid device!!");
            throw new Exception("Can't find any device on the cloud");
        }
        if (ChosenDevice == -2) {
            Utilities.log(runner, "doesn't found any device !!");
            Assert.fail("Can't find any device in the cloud");
        }

        waitForElement("//*[@id='content-after-toolbar']/div/md-virtual-repeat-container/div/div[2]/div/md-content/table/tbody/tr[" + ChosenDevice + "]/td[4]/div");
        driver.findElement(By.xpath("//*[@id='content-after-toolbar']/div/md-virtual-repeat-container/div/div[2]/div/md-content/table/tbody/tr[" + ChosenDevice + "]/td[4]/div")).click();
        waitUntilElementMarked("//*[@id='content-after-toolbar']/div/md-virtual-repeat-container/div/div[2]/div/md-content/table/tbody/tr[" + ChosenDevice + "]");
        chosenDeviceName = driver.findElement(By.xpath("//*[@id='content-after-toolbar']/div/md-virtual-repeat-container/div/div[2]/div/md-content/table/tbody/tr[" + ChosenDevice + "]/td[4]")).getText();
        Utilities.log(runner, "choosing device by xpath :" + chosenDeviceName);

        Utilities.sleep(runner, 2000);
        Utilities.log(runner, "manualIndex :" + manualIndex);
        if (rand.nextInt(2) == 0) {
            Utilities.log(runner, "choosing MANUAL");
            OpenSTM();
        } else {
            Utilities.log(runner, "choosing AUTOMATION");
            OpenSTA();
        }


        switchToTab();

        needToReleaseOnFinish = true;
        getChosenDeviceJson(chosenDeviceName);
        Utilities.log(runner, "build json object for chosen device ");

    }

    private void switchToTab() throws InterruptedException {
        boolean needToWaitFlag = true;
        long startWaitTime = System.currentTimeMillis();
        while (needToWaitFlag && (System.currentTimeMillis() - startWaitTime) < 150000) {
            try {
                ArrayList<String> newTab = new ArrayList<String>(driver.getWindowHandles());
                driver.switchTo().window(newTab.get(1));
                Utilities.log(runner, "Switched window to the STXWType tab");
                needToWaitFlag = false;
            } catch (Exception e) {
                Utilities.log(runner, "waiting for tab to open");
                Utilities.sleep(runner, 1000);
//                if(driver.findElement(By.xpath("//*[contains(text(), 'My Button')]")).isDisplayed());
            }

        }
        if (needToWaitFlag) {
            if(waitForElement("//*[contains(text(),'Could not open device')]"))
            {
                Assert.fail("Tab Didn't Load!! because : "+ driver.findElement(By.xpath("//*[contains(text(),'Could not open device')]/div[2]")).getText());
            }
            Assert.fail("Tab didn't Open!");
        }

        boolean needToWaitToLoadFlag = true;
        startWaitTime = System.currentTimeMillis();
        while (needToWaitToLoadFlag && (System.currentTimeMillis() - startWaitTime) < 120000) {
            try {
                driver.findElement(By.xpath("//*[@id='session_time_left']")).isEnabled();
                needToWaitToLoadFlag = false;
//                waitForPageToLoad(driver);
                Utilities.sleep(runner, 5000);
            } catch (Exception e) {
                Utilities.log(runner, "waiting for tab to load");
                Thread.sleep(1000);
            }

        }
        if (needToWaitToLoadFlag) {
            if(waitForElement("//div[.//*[contains(text(),'Could not open device') ]and ./*[div[contains(@class,'header')]]]"))
            {
                Assert.fail("Tab Didn't Load!! because : "+ driver.findElement(By.xpath("//div[contains(@class,'modal-body')]")).getText());
            }
            Assert.fail("Tab didn't Load!!");
        }

    }

    private void OpenSTA() {
        Utilities.log(runner, "OPENING AUTOMATION");
        runner.STXWType = "automation";
        driver.findElement(By.xpath("//button[*[contains(text(),'Automation')]]")).click();
        Utilities.log(runner, "click on Automation Button");
        Utilities.sleep(runner, 3000);
        try
        {
            if(driver.findElement(By.xpath("//button[*[contains(text(),'Automation')]]")).getAttribute("disabled").contains("true")) {}
        }
        catch(Exception e)
        {
            driver.findElement(By.xpath("//button[*[contains(text(),'Automation')]]")).click();
            Utilities.log(runner, "click on Automation Button");
        }

    }

    private void OpenSTM() {
        Utilities.log(runner, "OPENING MANUAL");
        runner.STXWType = "manual";
        driver.findElement(By.xpath("//button[*[contains(text(),'Manual')]]")).click();
        Utilities.log(runner, "click on Manual Button");
        Utilities.sleep(runner, 3000);
        try
        {
            if(driver.findElement(By.xpath("//button[*[contains(text(),'Manual')]]")).getAttribute("disabled").contains("true")) {}
        }
        catch(Exception e)
        {
            driver.findElement(By.xpath("//button[*[contains(text(),'Manual')]]")).click();
            Utilities.log(runner, "click on Manual Button");
        }
    }

    private int GetDeviceListSize() {
        waitForElement("//span[contains(text(),'Devices') and contains(text(),'/')]");
        int timeOutCounter = 0;
        boolean needToWait = true;
        while (needToWait && timeOutCounter < 10) {
            try {
                needToWait = driver.findElement(By.xpath("//span[contains(text(),'Devices') and contains(text(),'/')]")).getText().contains("0 / 0") || driver.findElement(By.xpath("//span[contains(text(),'Devices') and contains(text(),'/')]")).getText().equals("");
            } catch (Exception e) {
                Utilities.log(runner, "Waiting For Devices To Update");
                Utilities.sleep(runner, 500);
                timeOutCounter++;
            }
        }

        try {
            devicesInfo = (driver.findElement(By.xpath("//span[contains(text(),'Devices') and contains(text(),'/')]")).getText());
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

//        driver.get(Main.cloudServer.URL_ADDRESS + "/index.html#" + "/devices");
        driver.findElement(By.xpath("//*[@id='side-menu']/li[a/span[contains(text(),'Devices')]]")).click();
        Utilities.sleep(runner,1000);
        driver.findElement(By.xpath("//*[contains(@name,'list-view')]")).click();
        Utilities.log(runner, "go to the devices - " + Main.cloudServer.URL_ADDRESS + "/index.html#" + "/devices");

        boolean needToWaitForPageLoad = true;

        try {
            Utilities.log(runner, "Checking if we are on launchPad");
            driver.findElement(By.xpath("//*[contains(@name,'list-view')]")).click();
            Utilities.log(runner, "We Clicked to move to devices page from launchPad");
        } catch (Exception e) {
            Utilities.log(runner, "On Devices Page");
        }
        long startWaitTime = System.currentTimeMillis();
        while (needToWaitForPageLoad && (System.currentTimeMillis() - startWaitTime) < 120000) {
            try {
                driver.findElement(By.xpath("//md-input-container[*[contains(text(),'Status')]]/div[1]"));
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
        waitUntilVisible("//md-input-container[*[contains(text(),'Status')]]/div[1]");
        driver.findElement(By.xpath("//md-input-container[*[contains(text(),'Status')]]/div[1]")).click();
        Utilities.log(runner, "click on status");
        Utilities.sleep(runner, 2000);
        waitForElement("//*[(contains(@id,'menu_container') and @aria-hidden='false')]//button[*[contains(text(),'Clear')]]");
        Utilities.log(runner, "trying to click on clear ");
        driver.findElement(By.xpath("//*[(contains(@id,'menu_container') and @aria-hidden='false')]//button[*[contains(text(),'Clear')]]")).click();
        Utilities.sleep(runner, 2000);
        Utilities.log(runner, "trying to click on Available");
        driver.findElement(By.xpath("//md-checkbox[div[*[contains(text(),'Available')]]]/div[contains(@class,'md-container')]")).click();
        Utilities.sleep(runner, 2000);
        driver.navigate().back();
        driver.get(Main.cloudServer.URL_ADDRESS + "/index.html#" + "/devices");

    }

    private void LoginInToCloud() {
        driver.get(Main.cloudServer.URL_ADDRESS + "/index.html#");
        Utilities.log(runner, "go to " + Main.cloudServer.URL_ADDRESS + "/index.html#");

        waitForElement("//*[@name='username']");
        Utilities.sleep(runner, 5000);
        driver.findElement(By.xpath("//*[@name='username']")).sendKeys(runner.user);
        Utilities.log(runner, "Write username (" + runner.user + ")");
        int counter = 0;
        Utilities.sleep(runner, 5000);
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

        Utilities.sleep(runner, 3000);
        driver.findElement(By.name("password")).sendKeys(runner.enums.STXWPassword);
        Utilities.log(runner, "write the password ");

        driver.findElement(By.name("login")).click();
        Utilities.log(runner, "click on login");

        if(!WaitForElement("//*[@id='side-menu']/li[a/span[contains(text(),'Devices')]]"))
        {
            if(waitForElement("//*[label[contains(text(),'Select Project')]]/select"))
            {
                switch(runner.userType)
                {
                    case "Admin" :
                        driver.findElement(By.xpath("//*[label[contains(text(),'Select Project')]]/select")).sendKeys("Dafault");
                        break;
                    case "ProjectAdmin":
                        if(runner.user.contains("1"))
                        {
                            driver.findElement(By.xpath("//*[label[contains(text(),'Select Project')]]/select")).sendKeys("ayoubProjectDeepTest1");
                        }
                        else
                        {
                            driver.findElement(By.xpath("//*[label[contains(text(),'Select Project')]]/select")).sendKeys("ayoubProjectDeepTest2");
                        }
                        break;
                    case "User":
                        if(runner.user.contains("2"))
                        {
                            driver.findElement(By.xpath("//*[label[contains(text(),'Select Project')]]/select")).sendKeys("ayoubProjectDeepTest2");
                        }
                        else
                        {
                            driver.findElement(By.xpath("//*[label[contains(text(),'Select Project')]]/select")).sendKeys("ayoubProjectDeepTest1");
                        }
                        break;
                }
                driver.findElement(By.xpath("//button[contains(text(),'Select Project')]")).click();
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
                driver.findElement(By.xpath("//*[@id='session_end']")).click();
                Utilities.log(runner, "release device");
            } catch (Exception e) {

                Utilities.log(runner, e);
            }

            Utilities.sleep(runner, 5000);

            try {
                driver.findElement(By.xpath("//before-exit-dialog//button[*[contains(text(),'Release')]]")).click();
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

    public boolean OsValid(String Xpath) {
        Utilities.log(runner, "Enter to OsValid function with xpath " + Xpath);

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
                String VersionParts = (Os.split("Android ")[1]);
                VersionParts = VersionParts.substring(0, 3);

                Version = Double.parseDouble(VersionParts);
                Valid = Version >= 4.4;
            }
        }
        return Valid;
    }

    public int ChooseDeviceIndex(int DevicesSize) {
        Utilities.log(runner, "Enter to ChooseDeviceIndex");

        if (DevicesSize > 15) {
            DevicesSize = 15;
        }
        if (DevicesSize == 0) {
            return -2; //no devices
        }
        int[] arrayValidDevices = new int[DevicesSize + 1];
        arrayValidDevices[0] = -1;
        for (int i = 1; i < DevicesSize + 1; i++) {

            arrayValidDevices[i] = 1;
        }
        int Choosedevice = rand.nextInt(DevicesSize) + 1;

        while (Choosedevice != -1) {
            Choosedevice = rand.nextInt(DevicesSize) + 1;
            if (arrayValidDevices[Choosedevice] == 0) {
                Choosedevice = getNextValid(arrayValidDevices, Choosedevice);

            } else {

                if (!OsValid("//*[@id='content-after-toolbar']/div/md-virtual-repeat-container/div/div[2]/div/md-content/table/tbody/tr[" + Choosedevice + "]/td[5]/div")) {
                    arrayValidDevices[Choosedevice] = 0;
                } else {
                    return (Choosedevice);
                }
            }
        }

        return -1;
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

        boolean needToWaitToElement = true;
        long startWaitTime = System.currentTimeMillis();

        while (needToWaitToElement && (System.currentTimeMillis() - startWaitTime) < 60000) {
            try {
                driver.findElement(By.xpath(xpath));
                needToWaitToElement = false;
            } catch (Exception e) {
                Utilities.log(runner, "waiting for Element - " + xpath);
                Utilities.sleep(runner, 1000);
            }

        }
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


