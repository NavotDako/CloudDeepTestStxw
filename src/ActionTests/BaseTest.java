package ActionTests;


import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import Utils.Utilities;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.chrome.ChromeOptions;

import MyMain.JTestRunner;
import MyMain.Main;


public abstract class BaseTest {

    WebDriver driver;
    JTestRunner currentThread = (JTestRunner) Thread.currentThread();
    String DevicesInfo = "";
    int DevicesListSize = 0;
    int ManualIndex = 0;
    Random rand = new Random();
    public String passed = "passed";
    public String status = "passed";
    public Date startTime = new Date();
    public boolean enterToAfter = false;


    @Before
    public void SetUp() {
        try {

            Utilities.log(currentThread,"Enter to setUp");

            driver = createDriver();

            LoginInToCloud();

            NavigateToAvailableDevicesView();

            int ChosenDevice = ChooseDeviceIndex(GetDeviceListSize());

            if (ChosenDevice == -1) {
                writeFailedLineInLog("doesn't found a valid device!!");
                Utilities.log(currentThread,"doesn't found a valid device!!");
                return;
            }
            if (ChosenDevice == -2) {
                writeFailedLineInLog("doesn't found any device !!");
                Utilities.log(currentThread,"doesn't found any device !!");
                return;
            }
            driver.navigate().back();
            driver.get("https://qacloud.experitest.com/index.html#/devices");
            driver.findElement(By.xpath("//*[@id=\"content-after-toolbar\"]/div/md-virtual-repeat-container/div/div[2]/div/md-content/table/tbody/tr[" + ChosenDevice + "]/td[4]/div")).click();
//            driver.findElement(By.xpath("//*[@id=\"content-after-toolbar\"]/div/md-virtual-repeat-container/div/div[2]/div/md-content/table/tbody/tr[" + ChosenDevice + "]")).click();

            currentThread.choosenDeviceName = driver.findElement(By.xpath("//*[@id='content-after-toolbar']/div/md-virtual-repeat-container/div/div[2]/div/md-content/table/tbody/tr[" + ChosenDevice + "]/td[4]")).getText();
            Utilities.log(currentThread, "choosing device by xpath :" + currentThread.choosenDeviceName);

            switch (currentThread.UserType) {
                case "ProjectAdmin":
                    ManualIndex = 5;
                    break;
                case "Admin":
                    ManualIndex = 5;
                    break;
                case "User":
                    ManualIndex = 4;
                    break;
                default:
                    ManualIndex = 4;
                    break;
            }


            Utilities.log(currentThread,"ManualIndex :" + ManualIndex);
            if (rand.nextInt(2) == 0) {
                Utilities.log(currentThread, "choosing STMW");
                OpenSTM();
            } else {
                Utilities.log(currentThread, "choosing STAW");
                OpenSTA();
            }

            boolean needToWaitFlag = switchToTab();

            if (needToWaitFlag) {
                throw new Exception("tab didn't open");
            }

            getChosenDeviceJson(currentThread.choosenDeviceName);
            Utilities.log(currentThread, "build json object for chosen device ");

//            currentThread.chosenDeviceUdid = driver.findElement(By.xpath("/html/body/div[2]/div/div[1]/div/div/device-loupe/div/div/div[1]/h4")).getText();

        } catch (Exception e) {
           e.printStackTrace();
            writeFailedLineInLog(e.toString());
            passed = "failed";
            driver.quit();
        }

    }

    private boolean switchToTab() throws InterruptedException {
        boolean needToWaitFlag = true;
        long startWaitTime = System.currentTimeMillis();
        while (needToWaitFlag || (System.currentTimeMillis() - startWaitTime) > 120000) {
            try {
                ArrayList<String> newTab = new ArrayList<String>(driver.getWindowHandles());
                driver.switchTo().window(newTab.get(1));
                Utilities.log(currentThread, "Switched window to the STXW tab");
                needToWaitFlag = false;
            } catch (Exception e) {
                Utilities.log(currentThread, "waiting for tab to open");
                Thread.sleep(1000);
            }

        }
        return needToWaitFlag;
    }

    private void OpenSTA() {
        Utilities.log(currentThread,"automation");
        currentThread.STXW = "automation";
        driver.findElement(By.xpath("//*[@id=\"full-page-container\"]/div[1]/div/div/div/button[" + (ManualIndex + 1) + "]")).click();
        Utilities.log(currentThread, "click on Automation Button");

    }

    private void OpenSTM() {
        Utilities.log(currentThread,"manual");
        currentThread.STXW = "manual";
        driver.findElement(By.xpath("//*[@id=\"full-page-container\"]/div[1]/div/div/div/button[" + ManualIndex + "]")).click();
        Utilities.log(currentThread, "click on Manual Button");
    }

    private int GetDeviceListSize() {
        try {
            DevicesInfo = (driver.findElement(By.xpath("//*[@id=\"full-page-container\"]/div[1]/div/div/div/div[3]/span")).getText());
        } catch (Exception e) {
            try {
                DevicesInfo = (driver.findElement(By.xpath("//*[@id=\"full-page-container\"]/div[1]/div/div/div/div[2]/span")).getText());
                Utilities.log(currentThread, "get information about number available devices");
            } catch (Exception e1) {
                throw e1;
            }
        }

        Utilities.log(currentThread, "Devices Number Info : " + DevicesInfo);

        DevicesInfo = DevicesInfo.split("Devices: ")[1];
        DevicesListSize = Integer.parseInt(DevicesInfo.split(" /")[0]);
        System.out.println(DevicesListSize);
        return DevicesListSize;
    }

    private void NavigateToAvailableDevicesView() {
        driver.get("https://qacloud.experitest.com/index.html#/devices");
        Utilities.log(currentThread, "go to the devices - https://qacloud.experitest.com/index.html#/devices");

        try {
            Thread.sleep(3000);
            Utilities.log(currentThread, "wait 3 seconds");
        } catch (Exception e) {
            writeFailedLineInLog("failed to wait 3 seconds " + e);
        }

        driver.findElement(By.xpath("//*[@id=\"content-after-toolbar\"]/div/md-content[2]/div/div/div[3]/md-menu/md-input-container/div[1]")).click();
        Utilities.log(currentThread, "click on status");

        try {
            Thread.sleep(5000);
            Utilities.log(currentThread, "wait 5 seconds");
        } catch (Exception e) {
            writeFailedLineInLog("failed to wait 5 seconds " + e);
        }

        Utilities.log(currentThread, "trying to click on clear ");
        driver.findElement(By.xpath("//*[(contains(@id,'menu_container') and @aria-hidden='false')]/md-menu-content/section/button[2] ")).click();

        Utilities.log(currentThread, "trying to click on Available");
        driver.findElement(By.xpath("//*[(contains(@id,'menu_container') and @aria-hidden='false')]/md-menu-content/md-menu-item[1]/md-checkbox")).click();
        try {
            Thread.sleep(2000);
            Utilities.log(currentThread, "wait 2 seconds");
        } catch (Exception e) {
            writeFailedLineInLog("failed to wait 2 seconds " + e);
        }
    }

    private void LoginInToCloud() {
        driver.get(currentThread.enums.hostName);

        Utilities.log(currentThread, "go to " + currentThread.enums.hostName);
        driver.findElement(By.name("username")).sendKeys(currentThread.User);
        Utilities.log(currentThread, "Write username (" + currentThread.User + ")");

        driver.findElement(By.name("password")).sendKeys(currentThread.enums.Password);
        Utilities.log(currentThread, "write the password ");

        driver.findElement(By.name("login")).click();
        Utilities.log(currentThread, "click on login");

        try {
            Thread.sleep(3000);
            Utilities.log(currentThread, "wait 3 seconds");
        } catch (Exception e) {
            writeFailedLineInLog("failed to wait 3 seconds " + e);
        }
        try {
            driver.findElement(By.xpath("/html/body/md-backdrop")).click();
            Utilities.log(currentThread, "click on place in page");
        } catch (Exception e) {
            writeFailedLineInLog(e.toString());
        }
        Utilities.log(currentThread, "click on place in page");

    }

    private WebDriver createDriver() throws MalformedURLException {
//        Url = new URL("http://192.168.2.141:4444/wd/hub");

        Utilities.log(currentThread, "connect to selenium hub");

        Utilities.log(currentThread, "choose chrome capabilities");

        DesiredCapabilities dc = new DesiredCapabilities().chrome();
        dc.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        ChromeOptions chromeOption = new ChromeOptions();
        chromeOption.addArguments("--start-maximized");
        dc.setCapability(ChromeOptions.CAPABILITY, chromeOption);

        return new ChromeDriver(dc);
    }

    @Test
    abstract public void test();

    @After
    public void finish() {
        if (!enterToAfter) {
//            Utilities.log(currentThread,"finish");
//            try {
//                Thread.sleep(3000);
//                log("wait 3 seconds");
//            } catch (Exception e) {
//                writeFailedLineInLog("failed to wait 3 seconds " + e);
//            }
//            try {
//                driver.findElement(By.xpath("/html/body/div[2]/div/div[1]/div/div/device-loupe/div/div/div[2]/div[3]/button")).click();
//                log("release device");
//            } catch (Exception e) {
//                passed = "failed";
//                writeFailedLineInLog(e.toString());
//                System.out.println(e);
//            }
//
//            try {
//                Thread.sleep(5000);
//                log("wait 5 seconds");
//            } catch (Exception e) {
//                writeFailedLineInLog("failed to wait 5 seconds " + e);
//            }
//
//            try {
//                driver.findElement(By.xpath("/html/body/div[1]/div/div/before-exit-dialog/div/div[3]/button[1]")).click();
//                log("click Relese");
//            } catch (Exception e) {
//                passed = "failed";
//                writeFailedLineInLog(e.toString());
//                System.out.println(e);
//            }
//
//
//            try {
//                Thread.sleep(2000);
//                log("wait 2 seconds");
//            } catch (Exception e) {
//                writeFailedLineInLog("failed to wait 2 seconds " + e);
//            }
        }
        driver.quit();
        Utilities.log(currentThread, "driver.quit");
        currentThread.pw.close();


        Date CurrentTime = new Date();

        String line = String.format("%-30s, %-30s, %-30s, %-30s, %-30s, %-5s", CurrentTime, currentThread.User, currentThread.testClass.getName(), ((CurrentTime.getTime() - startTime.getTime()) / 60000), passed, "C:\\Users\\ayoub.abuliel\\eclipse-workspace\\CloudDeepTestStxw\\" + Main.logsFolder.getName() + "\\" + currentThread.TestName);
        Main.overallWriter.println(line);
        Main.overallWriter.flush();
        enterToAfter = true;

    }

    public boolean OsValid(String Xpath) {
        Utilities.log(currentThread, "Enter to OsValid function with xpath " + Xpath);

        String Os = "";
        boolean Valid = true;
        double Version;
        try {
            Os = driver.findElement(By.xpath(Xpath)).getText();
        } catch (Exception e) {
            System.out.println(e);
        }
        Utilities.log(currentThread, "get text (deviceOS = " + Os + ") from " + Xpath);

        if (Os.contains("ios")) {
            String VersionParts = (Os.split("IOS ")[1]);
            VersionParts = VersionParts.substring(0, 3);
            Version = Double.parseDouble(VersionParts);
            VersionParts = VersionParts.substring(0, 3);
            if (Version <= 8) {
                Valid = false;
            } else {
                Valid = true;
            }
        } else {
            if (Os.contains("Android")) {
                String VersionParts = (Os.split("Android ")[1]);
                VersionParts = VersionParts.substring(0, 3);

                Version = Double.parseDouble(VersionParts);
                if (Version < 4.3) {
                    Valid = false;
                } else {
                    Valid = true;
                }
            }
        }
        return Valid;
    }

    public int ChooseDeviceIndex(int DevicesSize) {
        Utilities.log(currentThread, "Enter to ChooseDeviceIndex");

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

                if (!OsValid("//*[@id=\"content-after-toolbar\"]/div/md-virtual-repeat-container/div/div[2]/div/md-content/table/tbody/tr[" + Choosedevice + "]/td[5]/div")) {
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



    public void writeFailedLineInLog(String command) {
        status = "failed";
        Utilities.log(currentThread, command);
        status = "passed";
    }

    public JSONObject getChosenDeviceJson(String deviceName) throws JSONException {
        JSONObject jsonObject = new JSONObject(currentThread.CloudDevicesInfo);
        JSONArray jsonArray = (JSONArray) jsonObject.get("data");
        JSONObject jsondevcieObject = null;
        for (int i = 0; i < jsonArray.length(); i++) {
            if (jsonArray.get(i) instanceof JSONObject) {
                jsondevcieObject = ((JSONObject) jsonArray.get(i));
                if (jsondevcieObject.getString("deviceName").contains(deviceName) || (jsondevcieObject.getString("deviceName").contains("hadar.zarihan") && deviceName.contains("hadar.zarihan")) ||
                        ((jsondevcieObject.getString("deviceName").contains("navot D’s iPad")) && (deviceName.contains("navot D’s iPad")))) {
                    Utilities.log(currentThread,"arraive in !!");
                    currentThread.jsonDeviceInfo = jsondevcieObject;
                    return jsondevcieObject;
                }
            }
        }
        return jsondevcieObject;
    }


}


