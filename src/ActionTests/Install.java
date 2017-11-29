package ActionTests;

import Utils.Utilities;
import org.junit.Test;
import org.openqa.selenium.By;


public class Install extends BaseTest {

    String text = "";

    @Test
    public void test() {

        try {
            Utilities.log(currentThread,"installlllll");
            Utilities.log(currentThread, "Enter to Install testClass");

            if (currentThread.STXWType.equals("manual")) {
                driver.findElement(By.xpath("//*[( contains(@id,'accordiongroup-') and contains(@id,'-panel'))]/div/md-list/md-list-item[1]/div/button[contains(@aria-label,'Install')]")).click();
                Utilities.log(currentThread, "click on Install Button");
            } else {
                driver.findElement(By.xpath("//*[( contains(@id,'accordiongroup-') and contains(@id,'-panel')  )]/div/md-list/md-list-item[1]/div/button[contains(@aria-label,'Applications')]")).click();
                Utilities.log(currentThread, "Click on application Button");
            }


            Thread.sleep(2000);
            Utilities.log(currentThread, "wait 2 seconds");


            driver.findElement(By.xpath("/html/body/div[2]/div/install-panel/div/md-list/md-list-item/div/button[contains(@aria-label,'ank')]")).click();
            Utilities.log(currentThread, "click on the Experibank application to install");


            Thread.sleep(2000);
            Utilities.log(currentThread, "wait 2 seconds");

            if (currentThread.STXWType.equals("manual")) {
                driver.findElement(By.xpath("/html/body/div[2]/div/install-panel/div/md-list/md-list-item/div/div[1]/div[contains(text(),'ank')]/md-card/md-card-actions/button")).click();

                Utilities.log(currentThread, "click on install Button");
            } else {
                driver.findElement(By.xpath("/html/body/div[2]/div/install-panel/div/md-list/md-list-item/div[button[(contains(@aria-label,'Experibank') or contains(@aria-label,'ank'))]]/div[1]/div[contains(text(),'Experibank') or contains(text(),'EriBank')]/md-card/md-card-actions/button[2]")).click();

                Utilities.log(currentThread, "click on install Button");
            }


            Thread.sleep(1000);
            Utilities.log(currentThread, "wait 5 seconds");


            driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div"));
            Utilities.log(currentThread, "test if application manager found");


            Thread.sleep(30000);
            Utilities.log(currentThread, "wait 30 seconds");


            try {
                text = driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/div[5]/div/div/install-progress-panel/div/code")).getText();
            } catch (Exception e) {
                Utilities.log(e);
            }
            Utilities.log(currentThread, "get the Text in application manager (" + text + ")");
            if (!(text.contains("Successfully") && text.contains("Starting"))) {

            }

            driver.findElement(By.xpath("/html/body/div[2]/div/install-panel/div/div[1]/button")).click();
            Utilities.log(currentThread, "click on x (close the Install window)");

            Utilities.log(currentThread,"my text: " + text);
        } catch (Exception e) {
            Utilities.log(e);
            Utilities.log(currentThread,e);

        }

    }


}


