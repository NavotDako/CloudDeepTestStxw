package ActionTests;


import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

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
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import MyMain.JTestRunner;
import MyMain.Main;


public abstract class BaseTest {

    WebDriver driver;
    URL Url = null;
    JTestRunner CurrentThread = (JTestRunner) Thread.currentThread();
    String DevicesInfo = "";
    int DevicesListSize = 0;
    int ManualIndex = 0;
    Random rand = new Random();
    public String passed = "passed";
    public String status = "passed";
    public Date startTime = new Date();
    public boolean enterToAfter = false;
    public Long currTime = System.currentTimeMillis();

    @Before
    public void SetUp() {
        try {

            log("Enter to setUp");

            driver = createDriver();

            LoginInToCloud();

            NavigateToAvailableDevicesView();

            int ChosenDevice = ChooseDeviceIndex(GetDeviceListSize());

            if (ChosenDevice == -1) {
                writeFailedLineInLog("doesn't found a valid device!!");
                System.out.println("doesn't found a valid device!!");
                return;
            }
            if (ChosenDevice == -2) {
                writeFailedLineInLog("doesn't found any device !!");
                System.out.println("doesn't found any device !!");
                return;
            }
            driver.navigate().back();
            driver.get("https://qacloud.experitest.com/index.html#/devices");
            driver.findElement(By.xpath("//*[@id=\"content-after-toolbar\"]/div/md-virtual-repeat-container/div/div[2]/div/md-content/table/tbody/tr[" + ChosenDevice + "]/td[4]/div")).click();
//            driver.findElement(By.xpath("//*[@id=\"content-after-toolbar\"]/div/md-virtual-repeat-container/div/div[2]/div/md-content/table/tbody/tr[" + ChosenDevice + "]")).click();

            CurrentThread.choosenDeviceName = driver.findElement(By.xpath("//*[@id='content-after-toolbar']/div/md-virtual-repeat-container/div/div[2]/div/md-content/table/tbody/tr[" + ChosenDevice + "]/td[4]")).getText();
            log("choosing device by xpath :" + CurrentThread.choosenDeviceName);

            switch (CurrentThread.UserType) {
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


            System.out.println("ManualIndex :" + ManualIndex);
            if (rand.nextInt(2) == 0) {
                log("choosing STMW");
                OpenSTM();
            } else {
                log("choosing STAW");
                OpenSTA();
            }

            boolean needToWaitFlag = switchToTab();

            if (needToWaitFlag) {
                throw new Exception("tab didn't open");
            }

            getChosenDeviceJson(CurrentThread.choosenDeviceName);
            log("build json object for chosen device ");

//            CurrentThread.choosenDeviceUdid = driver.findElement(By.xpath("/html/body/div[2]/div/div[1]/div/div/device-loupe/div/div/div[1]/h4")).getText();

        } catch (Exception e) {
           e.printStackTrace();
            writeFailedLineInLog(e.toString());
            passed = "failed";
            driver.quit();
        }

    }

    private boolean switchToTab() {
        boolean needToWaitFlag = true;
        long startWaitTime = System.currentTimeMillis();
        while (needToWaitFlag || (System.currentTimeMillis() - startWaitTime) < 120000) {
            try {
                ArrayList<String> newTab = new ArrayList<String>(driver.getWindowHandles());
                driver.switchTo().window(newTab.get(1));
                log("Switched window to the STXW tab");
                needToWaitFlag = false;
            } catch (Exception e) {
                log("waiting for tab to open");
            }

        }
        return needToWaitFlag;
    }

    private void OpenSTA() {
        System.out.println("automation");
        CurrentThread.STXW = "automation";
        driver.findElement(By.xpath("//*[@id=\"full-page-container\"]/div[1]/div/div/div/button[" + (ManualIndex + 1) + "]")).click();
        log("click on Automation Button");

    }

    private void OpenSTM() {
        System.out.println("manual");
        CurrentThread.STXW = "manual";
        driver.findElement(By.xpath("//*[@id=\"full-page-container\"]/div[1]/div/div/div/button[" + ManualIndex + "]")).click();
        log("click on Manual Button");
    }

    private int GetDeviceListSize() {
        try {
            DevicesInfo = (driver.findElement(By.xpath("//*[@id=\"full-page-container\"]/div[1]/div/div/div/div[3]/span")).getText());
        } catch (Exception e) {
            try {
                DevicesInfo = (driver.findElement(By.xpath("//*[@id=\"full-page-container\"]/div[1]/div/div/div/div[2]/span")).getText());
                log("get information about number available devices");
            } catch (Exception e1) {
                throw e1;
            }
        }

        log("Devices Number Info : " + DevicesInfo);

        DevicesInfo = DevicesInfo.split("Devices: ")[1];
        DevicesListSize = Integer.parseInt(DevicesInfo.split(" /")[0]);
        System.out.println(DevicesListSize);
        return DevicesListSize;
    }

    private void NavigateToAvailableDevicesView() {
        driver.get("https://qacloud.experitest.com/index.html#/devices");
        log("go to the devices - https://qacloud.experitest.com/index.html#/devices");

        try {
            Thread.sleep(3000);
            log("wait 3 seconds");
        } catch (Exception e) {
            writeFailedLineInLog("failed to wait 3 seconds " + e);
        }

        driver.findElement(By.xpath("//*[@id=\"content-after-toolbar\"]/div/md-content[2]/div/div/div[3]/md-menu/md-input-container/div[1]")).click();
        log("click on status");

        try {
            Thread.sleep(5000);
            log("wait 5 seconds");
        } catch (Exception e) {
            writeFailedLineInLog("failed to wait 5 seconds " + e);
        }

        log("trying to click on clear ");
        driver.findElement(By.xpath("//*[(contains(@id,'menu_container') and @aria-hidden='false')]/md-menu-content/section/button[2] ")).click();

        log("trying to click on Available");
        driver.findElement(By.xpath("//*[(contains(@id,'menu_container') and @aria-hidden='false')]/md-menu-content/md-menu-item[1]/md-checkbox")).click();
        try {
            Thread.sleep(2000);
            log("wait 2 seconds");
        } catch (Exception e) {
            writeFailedLineInLog("failed to wait 2 seconds " + e);
        }
    }

    private void LoginInToCloud() {
        driver.get(CurrentThread.enums.hostName);

        log("go to " + CurrentThread.enums.hostName);
        driver.findElement(By.name("username")).sendKeys(CurrentThread.User);
        log("Write username (" + CurrentThread.User + ")");

        driver.findElement(By.name("password")).sendKeys(CurrentThread.enums.Password);
        log("write the password ");

        driver.findElement(By.name("login")).click();
        log("click on login");

        try {
            Thread.sleep(3000);
            log("wait 3 seconds");
        } catch (Exception e) {
            writeFailedLineInLog("failed to wait 3 seconds " + e);
        }
        try {
            driver.findElement(By.xpath("/html/body/md-backdrop")).click();
            log("click on place in page");
        } catch (Exception e) {
            writeFailedLineInLog(e.toString());
        }
        log("click on place in page");

    }

    private WebDriver createDriver() throws MalformedURLException {
//        Url = new URL("http://192.168.2.141:4444/wd/hub");

        log("connect to selenium hub");

        log("choose chrome capabilities");

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
//            System.out.println("finish");
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
        log("driver.quit");
        CurrentThread.pw.close();


        Date CurrentTime = new Date();

        String line = String.format("%-30s, %-30s, %-30s, %-30s, %-30s, %-5s", CurrentTime, CurrentThread.User, CurrentThread.Test.getName(), ((CurrentTime.getTime() - startTime.getTime()) / 60000), passed, "C:\\Users\\ayoub.abuliel\\eclipse-workspace\\CloudDeepTestStxw\\" + Main.LogsFile.getName() + "\\" + CurrentThread.TestName);
        Main.SummaryTestsWriter.println(line);
        Main.SummaryTestsWriter.flush();
        enterToAfter = true;

    }

    public boolean OsValid(WebDriver driver, String Xpath) {
        log("Enter to OsValid function with xpath " + Xpath);

        String Os = "";
        boolean Valid = true;
        double Version;
        try {
            Os = driver.findElement(By.xpath(Xpath)).getText();
        } catch (Exception e) {
            System.out.println(e);
        }
        log("get text (deviceOS = " + Os + ") from " + Xpath);

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
        log("Enter to ChooseDeviceIndex");

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

                if (!OsValid(driver, "//*[@id=\"content-after-toolbar\"]/div/md-virtual-repeat-container/div/div[2]/div/md-content/table/tbody/tr[" + Choosedevice + "]/td[5]/div")) {
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

    public void log(String command) {
        SimpleDateFormat ft = new SimpleDateFormat("yyyy.MM.dd  HH:mm:ss,SS");
        Date CurrentTime = new Date();
        String line = String.format("%-30s,%-20s, %-15s, %-20s, %-5s", ft.format(CurrentTime), CurrentThread.User, CurrentThread.Test.getName().substring(12, CurrentThread.Test.getName().length()) + " " + currTime, command, status);
        CurrentThread.pw.println(line);
        CurrentThread.pw.flush();
    }

    public void writeFailedLineInLog(String command) {
        status = "failed";
        log(command);
        status = "passed";
    }

    public JSONObject getChosenDeviceJson(String deviceName) throws JSONException {
        JSONObject jsonObject = new JSONObject(CurrentThread.CloudDevicesInfo);
        JSONArray jsonArray = (JSONArray) jsonObject.get("data");
        JSONObject jsondevcieObject = null;
        for (int i = 0; i < jsonArray.length(); i++) {
            if (jsonArray.get(i) instanceof JSONObject) {
                jsondevcieObject = ((JSONObject) jsonArray.get(i));
                if (jsondevcieObject.getString("deviceName").contains(deviceName) || (jsondevcieObject.getString("deviceName").contains("hadar.zarihan") && deviceName.contains("hadar.zarihan")) ||
                        ((jsondevcieObject.getString("deviceName").contains("navot D’s iPad")) && (deviceName.contains("navot D’s iPad")))) {
                    System.out.println("arraive in !!");
                    CurrentThread.jsonDeviceInfo = jsondevcieObject;
                    return jsondevcieObject;
                }
            }
        }
        return jsondevcieObject;
    }


}


