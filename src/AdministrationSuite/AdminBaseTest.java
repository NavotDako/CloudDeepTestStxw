package AdministrationSuite;

import MyMain.BaseBaseTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import Utils.Utilities;

public abstract class AdminBaseTest extends BaseBaseTest{


    @Before
    public void setUp() throws Exception {
        runner = (AdminRunner) Thread.currentThread();
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
		driver.quit();
        Utilities.log(runner, "driver.quit");
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

}
