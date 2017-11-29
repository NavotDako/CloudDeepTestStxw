package ActionTests;

import Utils.Utilities;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

public class Monitors extends BaseTest {

    @Test
    public void test() {


        try {

            Utilities.log(currentThread,"Monitorssssss");
            Utilities.log(currentThread, "Enter to monitors testClass");

            driver.findElement(By.xpath("//*[( contains(@id,'accordiongroup-') and contains(@id,'-panel'))]/div/md-list/md-list-item[1]/div/button[contains(@aria-label,'Monitors')]")).click();
            Utilities.log(currentThread, "click on monitors Button");

            Thread.sleep(2000);
            Utilities.log(currentThread, "wait 2 seconds");


            driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div"));
            Utilities.log(currentThread, "test if monitor popUp");

        } catch (Exception e) {
            Utilities.log(currentThread,e);

        }
    }


}
