package STXWSuite.tests;

import STXWSuite.STXWBaseTest;
import Utils.Utilities;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;


public class Install extends STXWBaseTest {

    String text = "";

    @Test
    public void test() {


        Utilities.log(runner, "Enter to Install testClass");

        Utilities.sleep(runner, 2000);
        driver.findElement(By.xpath("//*[@id='install_panel_openclose']")).click();
        Utilities.log(runner, "click on Application button");

        Utilities.sleep(runner, 2000);
        if(!waitForElement("//*[@id='device-plane-id']/install-panel"))
        {
            Assert.fail("Install window doesn't found");
        }
        Utilities.sleep(runner, 1000);

        driver.findElement(By.xpath("//*[@id='menu_openclose']")).click();
        Utilities.log(runner,"Click on menu list icon");

        Utilities.sleep(runner,2000);
        if(waitForElement("/html/body/div[contains(@id,'menu_container')]"))
        {
            Assert.fail("Applications window didn't open");
        }
        driver.findElement(By.xpath("/html/body/div[contains(@id,'menu_container')]/md-menu-content/md-menu-item/button[span[contains(text(),'bank')]]")).click();
        Utilities.log(runner,"Click on Experibank app");

        driver.findElement(By.xpath("//*[@id='instrument_checkbox']/div[1]")).click();
        Utilities.log(runner,"Click on intrumentent checkbox");

        Utilities.sleep(runner,1000);
        driver.findElement(By.xpath("//*[@id='install']")).click();
        Utilities.log(runner,"Click on install action");

        if(!WaitForText("//*[@id='console']","Starting installation"))
        {
            Assert.fail("Starting installation failed");
        }
        Utilities.log(runner,"Starting the app succeed");

        if(!WaitForText("//*[@id='console']","Successfully installed"))
        {
            Assert.fail("intalling the app failed");
        }
        Utilities.log(runner,"Installing the app succeed");

        if(WaitForText("//*[@id='console']","Successfully launched"))
        {
            Assert.fail("launch the application failed");
        }
        Utilities.log(runner,"launch the app succeed");

        driver.findElement(By.xpath("//*[@id='close']")).click();
        Utilities.log(runner,"Click on x for closing the application manager window");

        Utilities.sleep(runner,2000);
        if(!WaitForDisappearedElement("//*[@id='close']"))
        {
            Assert.fail("The application manager window didn't disapear");
        }

    }


}
