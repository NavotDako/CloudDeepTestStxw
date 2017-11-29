package STXWActionTests;

import Utils.Utilities;
import org.junit.Test;
import org.openqa.selenium.By;

public class Reboot extends BaseTest {

    @Test
    public void test() {

        try {

            Utilities.log(currentThread, "Enter to Reboot testClass");
            Utilities.log(currentThread,"rebootttttt");
            driver.findElement(By.xpath("//*[( contains(@id,'accordiongroup-') and contains(@id,'-panel'))]/div/md-list/md-list-item[2]/div/button[contains(@aria-label,'Reboot')]")).click();
            Utilities.log(currentThread, "click on reboot button ");


            Thread.sleep(2000);
            Utilities.log(currentThread, "wait 2 seconds");


            driver.findElement(By.xpath("/html/body/div[1]/div/div/reboot-confirm-dialog/div/div[3]/button[2]")).click();
            Utilities.log(currentThread, "click on confirm Button ");
        } catch (Exception e) {
            Utilities.log(currentThread,e);
        }
    }



}
