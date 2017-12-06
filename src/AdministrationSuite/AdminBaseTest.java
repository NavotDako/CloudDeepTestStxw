package AdministrationSuite;

import java.net.MalformedURLException;

import AdministrationSuite.AdminRunner;
import MyMain.BaseBaseTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import Utils.Utilities;

public abstract class AdminBaseTest extends BaseBaseTest {


    @Before
    public void setUp() throws Exception {
        runner = (AdminRunner) Thread.currentThread();
        Utilities.log(runner,"-----------------------------" + runner.getName() + " Starting A New Test!-----------------------------");
        try {
            driver = createDriver();
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
        Utilities.log(runner, "Finishing");

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



    void waitForLoad(WebDriver driver) {
        new WebDriverWait(driver, 30).until(wd ->
                ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
    }



}
