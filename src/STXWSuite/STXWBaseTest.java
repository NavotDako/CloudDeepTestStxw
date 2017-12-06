package STXWSuite;


import java.util.ArrayList;
import java.util.Date;

import MyMain.BaseBaseTest;
import Utils.Utilities;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.*;
import org.openqa.selenium.*;

import MyMain.Main;
import org.openqa.selenium.support.ui.WebDriverWait;


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
            Assert.fail("Can't find any device on the cloud");
        }

        driver.findElement(By.xpath("//*[@id='content-after-toolbar']/div/md-virtual-repeat-container/div/div[2]/div/md-content/table/tbody/tr[" + ChosenDevice + "]/td[4]/div")).click();
        chosenDeviceName = driver.findElement(By.xpath("//*[@id='content-after-toolbar']/div/md-virtual-repeat-container/div/div[2]/div/md-content/table/tbody/tr[" + ChosenDevice + "]/td[4]")).getText();
        Utilities.log(runner, "choosing device by xpath :" + chosenDeviceName);

        switch (runner.UserType) {
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
        while (needToWaitFlag && (System.currentTimeMillis() - startWaitTime) < 120000) {
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
            Assert.fail("Tab Didn't Opened!");
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
            Assert.fail("Tab Didn't Loaded!!");
        }

    }

    private void OpenSTA() {
        Utilities.log(runner, "OPENING AUTOMATION");
        runner.STXWType = "automation";
        driver.findElement(By.xpath("//*[@id='full-page-container']/div[1]/div/div/div/button[" + (manualIndex + 1) + "]")).click();
        Utilities.log(runner, "click on Automation Button");

    }

    private void OpenSTM() {
        Utilities.log(runner, "OPENING MANUAL");
        runner.STXWType = "manual";
        driver.findElement(By.xpath("//*[@id='full-page-container']/div[1]/div/div/div/button[" + manualIndex + "]")).click();
        Utilities.log(runner, "click on Manual Button");
    }

    private int GetDeviceListSize() {
        int index = 0;
        try {
            driver.findElement(By.xpath("//*[@id='full-page-container']/div[1]/div/div/div/div[3]/span"));
            index = 3;
        } catch (Exception e) {
            index = 2;
        }

        int timeOutCounter = 0;
        boolean needToWait = true;
        while (needToWait && timeOutCounter < 10) {
            try {
                needToWait = driver.findElement(By.xpath("//*[@id='full-page-container']/div[1]/div/div/div/div[" + index + "]/span")).getText().contains("0 / 0") || driver.findElement(By.xpath("//*[@id='full-page-container']/div[1]/div/div/div/div[" + index + "]/span")).getText().equals("");
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
        driver.get(runner.enums.hostName + "/devices");
        Utilities.log(runner, "go to the devices - " + runner.enums.hostName + "/devices");

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
        driver.findElement(By.xpath("//*[@id='content-after-toolbar']/div/md-content[2]/div/div/div[3]/md-menu/md-input-container/div[1]")).click();
        Utilities.log(runner, "click on status");
        Utilities.sleep(runner, 2000);
        Utilities.log(runner, "trying to click on clear ");
        driver.findElement(By.xpath("//*[(contains(@id,'menu_container') and @aria-hidden='false')]/md-menu-content/section/button[2] ")).click();
        Utilities.sleep(runner, 2000);
        Utilities.log(runner, "trying to click on Available");
        driver.findElement(By.xpath("//*[(contains(@id,'menu_container') and @aria-hidden='false')]/md-menu-content/md-menu-item[1]/md-checkbox")).click();
        Utilities.sleep(runner, 2000);
        driver.navigate().back();
        driver.get(runner.enums.hostName + "/devices");

    }

    private void LoginInToCloud() {
        driver.get(runner.enums.hostName);

        Utilities.log(runner, "go to " + runner.enums.hostName);
        waitForElement("//*[@name='username']");
        driver.findElement(By.xpath("//*[@name='username']")).sendKeys(runner.User);
        Utilities.log(runner, "Write username (" + runner.User + ")");

        driver.findElement(By.name("password")).sendKeys(runner.enums.STXWPassword);
        Utilities.log(runner, "write the password ");

        driver.findElement(By.name("login")).click();
        Utilities.log(runner, "click on login");

//        try {
//            driver.findElement(By.xpath("/html/body/md-backdrop")).click();
//            Utilities.log(runner, "click on place in page");
//        } catch (Exception e) {
//            writeFailedLineInLog(e.toString());
//        }

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

        String line = String.format("%-30s%-30s%-30s%-30s%-5s", CurrentTime, runner.User, runner.testClass.getName(), (((double) (CurrentTime.getTime() - startTime.getTime())) / 60000), "C:\\Users\\ayoub.abuliel\\eclipse-workspace\\CloudDeepTestStxw\\" + Main.logsFolder.getName() + "\\" + runner.TestName);
        runner.overallWriter.println(line);
        runner.overallWriter.flush();


    }

    public boolean OsValid(String Xpath) {
        Utilities.log(runner, "Enter to OsValid function with xpath " + Xpath);

        String Os = "";
        boolean Valid = true;
        double Version;
        try {
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
                Valid = Version >= 4.3;
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
            if (arrayValidDevices[Choosedevice] == 1) {
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




}


