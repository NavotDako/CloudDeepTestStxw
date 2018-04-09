package MyMain;

import Utils.Utilities;
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


public class BaseBaseTest {
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
    private String reportURL;

    protected RemoteWebDriver createDriver(String testName) throws MalformedURLException {
        Utilities.log(runner, "Creating Driver");
        DesiredCapabilities dc = new DesiredCapabilities().chrome();
        dc.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        ChromeOptions chromeOption = new ChromeOptions();
        chromeOption.addArguments("--start-maximized");
        dc.setCapability("platformName", "chrome");
        dc.setCapability("username", Main.cloudServer.getUser());
        dc.setCapability("password", Main.cloudServer.getPass());
//        dc.setCapability("projectName", Main.cloudServer.getProject()); //only required if your user has several projects assigned to it. Otherwise, exclude this capability.
        dc.setCapability("generateReport", true);
        dc.setCapability("testName", testName);
        dc.setCapability("newSessionWaitTimeout", 90);
        dc.setCapability(CapabilityType.BROWSER_NAME, BrowserType.CHROME);
        dc.setCapability("newCommandTimeout", 120);
        dc.setCapability(ChromeOptions.CAPABILITY, chromeOption);
        needToQuitDriverOnFinish = true;
        String cloudURL = (Main.cloudServer.getIsSecured()? "https://" : "http://") + Main.cloudServer.getServerHostName() + ":" + Main.cloudServer.getPort() + "/wd/hub/";
        Utilities.log(runner, "Connecting to RWD with URL " + cloudURL);
        Utilities.log(runner, "got the url in reporter is " + reportURL);
        RemoteWebDriver driver = new RemoteWebDriver(new URL(cloudURL), dc);
        reportURL = (String) driver.getCapabilities().getCapability("reportUrl");
        return driver;
    }

    @Rule
    public TestWatcher watchman = new TestWatcher() {
        @Override
        protected void failed(Throwable e, Description description) {
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
                    Utilities.log(runner, "getPageSource - " + driver.getPageSource().replace("\n", "\t"));
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

    public boolean WaitForText(String xPath, String Text) {
        boolean needToWaitToText = true;
        long startWaitTime = System.currentTimeMillis();

        while (needToWaitToText && (System.currentTimeMillis() - startWaitTime) < 60000) {
            try {
                if (driver.findElement(By.xpath(xPath)).getText().contains(Text))
                    needToWaitToText = false;
            } catch (Exception e) {
                Utilities.log(runner, "waiting for Text - " + Text);
                Utilities.sleep(runner, 1000);
            }

        }
        return !needToWaitToText;
    }
}
