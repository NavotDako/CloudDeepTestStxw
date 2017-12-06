package AdministrationSuite;


import MyMain.BaseBaseTest;
import MyMain.Main;
import Utils.Utilities;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

public abstract class AdminBaseTest extends BaseBaseTest {


    @Before
    public void setUp() throws Exception {
        runner = (AdminRunner) Thread.currentThread();
        Utilities.log(runner,"-----------------------------" + runner.getName() + " Starting A New Test!-----------------------------");
            driver = createDriver();
            LoginInToCloud();
    }


    @Test
    abstract public void test();

    @After
    public void finish() {
        Utilities.log(runner, "Finishing");
        Utilities.log(runner, "Quit driver");
        driver.quit();
    }


    private void LoginInToCloud() {
    	
        driver.get(Main.cs.HOST + "/index.html#/login");
        Utilities.log(runner, "go to " + Main.cs.HOST + "/index.html#/login");
        driver.findElement(By.name("username")).sendKeys("ayouba");
        driver.findElement(By.name("password")).sendKeys("Experitest2012");
        driver.findElement(By.name("login")).click();
        Utilities.log(runner, "click on login");

    }





}
