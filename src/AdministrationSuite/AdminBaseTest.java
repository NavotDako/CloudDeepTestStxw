package AdministrationSuite;


import AdministrationSuite.AdminRunner;
import MyMain.BaseBaseTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;


import Utils.Utilities;

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
    	
        driver.get(runner.enums.hostName + "/login");
        Utilities.log(runner, "go to " + runner.enums.hostName + "/login");
        driver.findElement(By.name("username")).sendKeys("ayouba");
        Utilities.log(runner, "Write username (ayouba)");

        driver.findElement(By.name("password")).sendKeys("Experitest2012");
        Utilities.log(runner, "write the password ");
        
        Utilities.sleep(runner, 2000);
        driver.findElement(By.name("login")).click();
        Utilities.log(runner, "click on login");
        
        Utilities.sleep(runner, 2000);

    }





}
