package STXWActionTests.tests;

import STXWActionTests.STXWBaseTest;
import Utils.Utilities;
import junit.framework.Assert;

import org.junit.Test;
import org.openqa.selenium.By;

public class OpenLogs extends STXWBaseTest {

    @Test
    public void test() {


        Utilities.log(runner, "Enter to Open Logs testClass");
        Utilities.sleep(runner, 2000);
        driver.findElement(By.xpath("//*[( contains(@id,'accordiongroup-') and contains(@id,'-panel'))]/div/md-list/md-list-item/div/button[contains(@aria-label,'Logs')]")).click();
        Utilities.log(runner, "click on open log Button ");

        Utilities.sleep(runner, 2000);

        driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/md-toolbar"));
        Utilities.log(runner, "test if the logs open");

        Utilities.sleep(runner, 5000);

        try {
            if (!driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/div[5]/div/div/log-panel/md-content/code")).getText().equals(""))

            {
                Utilities.log(runner, "the logs after 5 seconds" + driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/div[5]/div/div/log-panel/md-content/code")).getText());
            } else {
                Utilities.log(runner, "The logs are Empty");
                Assert.fail("The logs are Empty");
            }

            driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/md-toolbar/button[3]")).click();
            Utilities.log(runner, "click on close logs");
            Utilities.sleep(runner, 2000);


        } catch (Exception e) {
            Utilities.log(runner, e.toString());
        }


    }


}
