package MyMain;

import Utils.GetAttachmentsFromReporter;
import Utils.Utilities;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.Future;


public abstract class BaseBaseTest {
    protected RemoteWebDriver driver;
    protected BaseRunner runner;
    protected String devicesInfo = "";
    protected int devicesListSize = 0;
    protected int manualIndex = 0;
    protected Random rand = new Random();
    public Date startTime = new Date();
    protected boolean needToReleaseOnFinish = false;
    protected boolean needToQuitDriverOnFinish = false;
    public String chosenDeviceName = "";
    private String watchedLog;
    protected String reportURL;
    protected String device;
    protected String deviceOS;


    protected RemoteWebDriver createDriver(String testName) throws MalformedURLException {
        Utilities.log(runner, "Creating Driver");

        DesiredCapabilities dc = new DesiredCapabilities().chrome();
        dc.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        ChromeOptions chromeOption = new ChromeOptions();
        chromeOption.addArguments("--start-maximized");
        dc.setCapability("accessKey", Enums.getAccessKey(runner.user));
//        dc.setCapability("username", Main.cs.getUser());
//        dc.setCapability("password", Main.cs.getPass());
//        dc.setCapability("projectName", "Default");
        dc.setCapability("generateReport", true);
        dc.setCapability("testName", testName);
//        dc.setCapability("newSessionWaitTimeout", 90);
        dc.setCapability(CapabilityType.BROWSER_NAME, BrowserType.CHROME);
        dc.setCapability("newCommandTimeout",300);
        dc.setCapability(ChromeOptions.CAPABILITY, chromeOption);
        needToQuitDriverOnFinish = true;
        String cloudURL = (Main.cs.getIsSecured()? "https://" : "http://") + Main.cs.getServerHostName() + ":" + Main.cs.getPort() + "/wd/hub/";
        Utilities.log(runner, "Connecting to RWD with URL " + cloudURL);
        RemoteWebDriver driver = null;
        try {
            driver = new RemoteWebDriver(new URL(cloudURL), dc);
        }catch (Exception e){
            Utilities.log(runner, e);
            Assert.fail("failed to create driver");
        }
        reportURL = (String) driver.getCapabilities().getCapability("reportUrl");
        Utilities.log(runner, "got the url in reporter is " + reportURL);
        return driver;
    }

//    protected void chooseDevice(String os){
//        if(os.equals(null)){
//            deviceOS = Math.random() > Math.random() ?  "android" : "ios";
//        }else {
//            deviceOS = os;
//        }
//        device = Main.cs.getDevice(deviceOS);
//
//    }
    @Rule
    public TestWatcher watchman = new TestWatcher() {
        @Override
        protected void failed(Throwable e, Description description) {
            if(testingOnADevice() && !device.equals(null)) {
                Utilities.log(runner, "releasing device with os " + deviceOS + " and udid " + device);
                Main.cs.releaseDevice(device, deviceOS);
            }
            if(reportURL != null){
                Future<String> future = Main.executorService.submit(() -> GetAttachmentsFromReporter.downloadAttachmentsFromReporter(reportURL, runner.user));
            }

            watchedLog = description + "";
            Utilities.log(runner, Thread.currentThread().getName() + " FAILED !!! - " + watchedLog);
            Utilities.log(runner, "TEST HAS FAILED!!!");
            if (e instanceof Exception) {
                Utilities.log(runner, (Exception) e);
            } else {
                Utilities.log(runner, e.getMessage());
            }

//            takeScreenShot();
            if (needToQuitDriverOnFinish) {
                try {
                    Utilities.logAndDontPrint(runner, "getPageSource - " + driver.getPageSource().replace("\n", "\t"));
                } catch (Exception e1) {
                    Utilities.log(runner, "UNABLE TO GET PAGE SOURCE");
                }
                try {
                    driver.quit();
                    Utilities.log(runner, "driver.quit");
                } catch (Exception e1) {
                    Utilities.log(runner, "Failed to driver.quit");
                    Utilities.log(runner, e1);
                }
            }

            if(e.getMessage()!=null){
                Utilities.writeToSummary(runner, chosenDeviceName, "--FAILED--\t" + e.getMessage().replace("\n", "\t"), reportURL);
            }else{
                Utilities.writeToSummary(runner, chosenDeviceName, "--FAILED--\t", reportURL);
            }


        }

        @Override
        protected void succeeded(Description description) {
            if(testingOnADevice()) {
                Main.cs.releaseDevice(device, deviceOS);
            }
            watchedLog = description + " " + "success!\n";
            Utilities.log(runner, Thread.currentThread().getName() + " PASSED!!!" + watchedLog);
            Utilities.log(runner, "TEST HAS PASSED!!!");
            if (needToQuitDriverOnFinish) {
                driver.quit();
                Utilities.log(runner, "driver.quit");
            }
            Utilities.writeToSummary(runner, chosenDeviceName, "passed", reportURL);
        }
    };

    protected void takeScreenShot() {
        //            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
//            File destFile = new File(Main.logsFolder + "/" + Thread.runner().getName() + "_" + System.currentTimeMillis() + ".png");
//            try {
//                FileUtils.copyFile(scrFile, destFile);
//            } catch (IOException e1) {
//                e1.printStackTrace();
//            }
//            scrFile.delete();
    }

    public boolean WaitForElement(String xPath) {

        boolean needToWaitToElement = true;
        long startWaitTime = System.currentTimeMillis();

        while (needToWaitToElement && (System.currentTimeMillis() - startWaitTime) < 60000) {
            try {
                driver.findElement(By.xpath(xPath));
                needToWaitToElement = false;
            } catch (Exception e) {
                Utilities.log(runner, "waiting for Element - " + xPath);
                Utilities.sleep(runner, 1000);
            }

        }
        return !needToWaitToElement;

    }
    
        public boolean WaitForDisappearedElement(String xPath) 
    { 	
    	boolean needToWaitToElement = true;
    	long startWaitTime = System.currentTimeMillis();
    	while(needToWaitToElement && (System.currentTimeMillis() - startWaitTime) < 60000) 
    	{
    		try
    		{
    			driver.findElement(By.xpath(xPath));
    			Utilities.log(runner, "waiting for Element - " + xPath);
                Utilities.sleep(runner, 1000);
    		}
    		catch(Exception e) 
    		{
    			needToWaitToElement = false;
    		}
    		
    	}
    	return !needToWaitToElement;
    	
    }

    public boolean waitForText(String xPath, String text) {
        return waitForText(xPath, text, 60000);
    }
    public boolean waitForText(String xPath, String Text, int timeout) {
        long startWaitTime = System.currentTimeMillis();

        while ((System.currentTimeMillis() - startWaitTime) < timeout) {
            try {
//                System.out.println("Text issss " + driver.findElement(By.xpath(xPath)).getText());
                if (driver.findElement(By.xpath(xPath)).getText().contains(Text))
                    return true;
            } catch (Exception e) {
                Utilities.log(runner, "waiting for Text - " + Text);
                Utilities.sleep(runner, 1000);
            }

        }
        return false;
    }

    protected void LoginInToCloud() {
        driver.get(Main.cs.URL_ADDRESS + "/index.html#");
        Utilities.log(runner, "go to " + Main.cs.URL_ADDRESS + "/index.html#");

        waitForElement("//*[@name='username']");
        Utilities.sleep(runner, 2000);
        driver.findElement(By.xpath("//*[@name='username']")).sendKeys(runner.user);
        Utilities.log(runner, "Write username (" + runner.user + ")");
        int counter = 0;
        Utilities.sleep(runner, 2000);
//        waitForElement("//*[@name='username' and contains(@class,'ng-not-empty')]");
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

        if(!WaitForElement("//*[@id='side-menu']/li[a/span[contains(text(),'Devices')]]")) {
            if (waitForElement("/html/body/div[2]/div[1]/div/form/div[label[contains(text(),'Select Project')]]/select")) {
                switch (runner.userType) {
                    case "Admin":
                        driver.findElement(By.xpath("/html/body/div[2]/div[1]/div/form/div[label[contains(text(),'Select Project')]]/select")).sendKeys("Dafault");
                        break;
                    case "ProjectAdmin":
                        if (runner.user.contains("1")) {
                            driver.findElement(By.xpath("/html/body/div[2]/div[1]/div/form/div[label[contains(text(),'Select Project')]]/select")).sendKeys("ayoubProjectDeepTest1");
                        } else {
                            driver.findElement(By.xpath("/html/body/div[2]/div[1]/div/form/div[label[contains(text(),'Select Project')]]/select")).sendKeys("ayoubProjectDeepTest2");
                        }
                        break;
                    case "User":
                        if (runner.user.contains("2")) {
                            driver.findElement(By.xpath("/html/body/div[2]/div[1]/div/form/div[label[contains(text(),'Select Project')]]/select")).sendKeys("ayoubProjectDeepTest2");
                        } else {
                            driver.findElement(By.xpath("/html/body/div[2]/div[1]/div/form/div[label[contains(text(),'Select Project')]]/select")).sendKeys("ayoubProjectDeepTest1");
                        }
                        break;
                }
                driver.findElement(By.xpath("/html/body/div[2]/div[1]/div/form/button[1]")).click();
                Utilities.sleep(runner, 3000);
            }
        }
    }
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

    //each class inheriting from here should specify wether it needs a device or not
    protected abstract boolean testingOnADevice();

    protected void updateDevice(){
        //these devices should be used only for fingerprint test
        device = Main.cs.getDevice(deviceOS, t -> !(t.equals("bb904d305dad81b8ae67386753143a28fe81cf1b") || t.equals("25da8511aef7ea8331fdd0da3ed9ebca19d5da8d")));
        if(device.equals(null)){
            Utilities.log(runner, "No available device -- Device is null -- ");
        }
    }
}
