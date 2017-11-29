package STXWActionTests;

import Utils.Utilities;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;


public class Install extends BaseTest {

    String text = "";

    @Test
    public void test() {

        
            Utilities.log(currentThread, "Enter to Install testClass");

            Utilities.sleep(currentThread, 2000);
            if (currentThread.STXWType.equals("manual")) {
                driver.findElement(By.xpath("//*[( contains(@id,'accordiongroup-') and contains(@id,'-panel'))]/div/md-list/md-list-item[1]/div/button[contains(@aria-label,'Install')]")).click();
                Utilities.log(currentThread, "click on Install Button");
            } else {
                driver.findElement(By.xpath("//*[( contains(@id,'accordiongroup-') and contains(@id,'-panel')  )]/div/md-list/md-list-item[1]/div/button[contains(@aria-label,'Applications')]")).click();
                Utilities.log(currentThread, "Click on application Button");
            }


            Utilities.sleep(currentThread, 2000);


            driver.findElement(By.xpath("/html/body/div[2]/div/install-panel/div/md-list/md-list-item/div/button[contains(@aria-label,'ank')]")).click();
            Utilities.log(currentThread, "click on the Experibank application to install");


            Utilities.sleep(currentThread, 2000);

            if (currentThread.STXWType.equals("manual")) {
                driver.findElement(By.xpath("/html/body/div[2]/div/install-panel/div/md-list/md-list-item/div/div[1]/div[contains(text(),'ank')]/md-card/md-card-actions/button")).click();

                Utilities.log(currentThread, "click on install Button");
            } else {
                driver.findElement(By.xpath("/html/body/div[2]/div/install-panel/div/md-list/md-list-item/div[button[(contains(@aria-label,'Experibank') or contains(@aria-label,'ank'))]]/div[1]/div[contains(text(),'Experibank') or contains(text(),'EriBank')]/md-card/md-card-actions/button[2]")).click();

                Utilities.log(currentThread, "click on install Button");
            }


            Utilities.sleep(currentThread, 1000);


            driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div"));
            Utilities.log(currentThread, "test if application manager found");


            Utilities.sleep(currentThread, 30000);


            try {
                text = driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/div[5]/div/div/install-progress-panel/div/code")).getText();
            } catch (Exception e) {
                Utilities.log(e);
                throw e;
            }
            Utilities.log(currentThread, "get the Text in application manager (" + text + ")");
            if (!(text.contains("Successfully") && text.contains("Starting"))) 
            {
                try {throw (new Exception("the install doesn't success"));}catch(Exception e) {}
            }

            driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/md-toolbar/button[3]")).click();
            Utilities.log(currentThread, "click on x (close the Install's text window)");
            
            driver.findElement(By.xpath("/html/body/div[2]/div/install-panel/div/div[1]/button")).click();
            Utilities.log(currentThread, "click on x (close the Install window)");

            Utilities.sleep(currentThread, 3000);
       

    }


}
