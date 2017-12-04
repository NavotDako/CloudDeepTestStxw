package Administration;

import java.net.MalformedURLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import Utils.Utilities;

public abstract class AdminBaseTest {

    protected AdminRunner runner = (AdminRunner) Thread.currentThread();
    protected WebDriver driver;
    private boolean needToQuitDriverOnFinish = false;


    @Before
    public void setUp() throws Exception {

        System.out.println("-----------------------------" + runner.getName() + " Starting A New Test!-----------------------------");
        try {
            driver = createDriver();
            needToQuitDriverOnFinish = true;
            LoginInToCloud();

        } catch (Exception e) {
            Utilities.log(runner, e);
            Utilities.log(runner, "SETUP FOR - " + Thread.currentThread().getName() + " HAS FAILED!!!");
            throw e;
        }

    }


    @Test
    abstract public void test();

    @After
    public void finish() {
        Utilities.log(runner, "finish");
//		driver.quit();
//        Utilities.log(runner, "driver.quit");
    }


    private WebDriver createDriver() throws MalformedURLException {

        Utilities.log(runner, "connect to selenium hub");

        Utilities.log(runner, "choose chrome capabilities");

        DesiredCapabilities dc = new DesiredCapabilities().chrome();
        dc.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        ChromeOptions chromeOption = new ChromeOptions();
        chromeOption.addArguments("--start-maximized");
        dc.setCapability(ChromeOptions.CAPABILITY, chromeOption);

        return new ChromeDriver(dc);
    }

    private void LoginInToCloud() {

        driver.get(runner.enums.hostName + "/index.html#/login");
        Utilities.log(runner, "go to " + runner.enums.hostName + "/index.html#/login");
        driver.findElement(By.name("username")).sendKeys("ayouba");
        Utilities.log(runner, "Write username (ayouba)");

        driver.findElement(By.name("password")).sendKeys("Experitest2012");
        Utilities.log(runner, "write the password ");

        driver.findElement(By.name("login")).click();
        Utilities.log(runner, "click on login");

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


    private String watchedLog;

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
            Utilities.writeToSummary(runner, "No Device", "--FAILED--\t" + e.getMessage().replace("\n", "\t"));

        }

        @Override
        protected void succeeded(Description description) {
            watchedLog = description + " " + "success!\n";
            Utilities.log(runner, Thread.currentThread().getName() + " PASSED!!!" + watchedLog);
            Utilities.log(runner, "TEST HAS PASSED!!!");
            Utilities.writeToSummary(runner, "No Device", "passed");
        }
    };
}
