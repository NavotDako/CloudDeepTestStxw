package STXWActionTests.tests;

import STXWActionTests.STXWBaseTest;
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
            if (runner.STXWType.equals("manual")) {
                driver.findElement(By.xpath("//*[( contains(@id,'accordiongroup-') and contains(@id,'-panel'))]/div/md-list/md-list-item[1]/div/button[contains(@aria-label,'Install')]")).click();
                Utilities.log(runner, "click on Install Button");
            } else {
                driver.findElement(By.xpath("//*[( contains(@id,'accordiongroup-') and contains(@id,'-panel')  )]/div/md-list/md-list-item[1]/div/button[contains(@aria-label,'Applications')]")).click();
                Utilities.log(runner, "Click on application Button");
            }


            Utilities.sleep(runner, 2000);


            driver.findElement(By.xpath("/html/body/div[2]/div/install-panel/div/md-list/md-list-item/div/button[contains(@aria-label,'ank')]")).click();
            Utilities.log(runner, "click on the Experibank application to install");


            Utilities.sleep(runner, 2000);

            if (runner.STXWType.equals("manual")) {
                driver.findElement(By.xpath("/html/body/div[2]/div/install-panel/div/md-list/md-list-item/div/div[1]/div[contains(text(),'ank')]/md-card/md-card-actions/button")).click();

                Utilities.log(runner, "click on install Button");
            } else {
                driver.findElement(By.xpath("/html/body/div[2]/div/install-panel/div/md-list/md-list-item/div[button[(contains(@aria-label,'Experibank') or contains(@aria-label,'ank'))]]/div[1]/div[contains(text(),'Experibank') or contains(text(),'EriBank')]/md-card/md-card-actions/button[2]")).click();

                Utilities.log(runner, "click on install Button");
            }


            Utilities.sleep(runner, 1000);


            driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div"));
            Utilities.log(runner, "test if application manager found");


            Utilities.sleep(runner, 30000);


            try {
                text = driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/div[5]/div/div/install-progress-panel/div/code")).getText();
            } catch (Exception e) {
                Utilities.log(e);
                Assert.fail("result window doesn't found ");
            }
            Utilities.log(runner, "get the Text in application manager (" + text + ")");
            if (!(text.contains("Successfully") && text.contains("Starting"))) 
            {
            	Assert.fail("the install doesn't success");                
            }

            driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/md-toolbar/button[3]")).click();
            Utilities.log(runner, "click on x (close the Install's text window)");
            
            driver.findElement(By.xpath("/html/body/div[2]/div/install-panel/div/div[1]/button")).click();
            Utilities.log(runner, "click on x (close the Install window)");

            Utilities.sleep(runner, 3000);
       

    }


}
