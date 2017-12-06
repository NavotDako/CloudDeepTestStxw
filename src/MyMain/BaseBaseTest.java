package MyMain;

import Utils.Utilities;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.util.Date;
import java.util.Random;


public class BaseBaseTest {
    protected WebDriver driver;
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


    protected WebDriver createDriver() throws MalformedURLException {
        Utilities.log(runner, "Creating Driver");
        DesiredCapabilities dc = new DesiredCapabilities().chrome();
        dc.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        ChromeOptions chromeOption = new ChromeOptions();
        chromeOption.addArguments("--start-maximized");
        dc.setCapability(ChromeOptions.CAPABILITY, chromeOption);
        needToQuitDriverOnFinish = true;
        return new ChromeDriver(dc);
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
            }
            driver.quit();
            Utilities.log(runner, "driver.quit");
            Utilities.writeToSummary(runner, chosenDeviceName, "--FAILED--\t" + e.getMessage().replace("\n", "\t"));

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
            Utilities.writeToSummary(runner, chosenDeviceName, "passed");
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
