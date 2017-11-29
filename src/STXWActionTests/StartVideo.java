package STXWActionTests;

import Utils.Utilities;
import org.junit.Test;
import org.openqa.selenium.By;

public class StartVideo extends BaseTest {

    @Test
    public void test() {


        try {
            Utilities.log(currentThread, "start video");
            Utilities.log(currentThread, "Enter to Start video testClass");

            driver.findElement(By.xpath("//*[( contains(@id,'accordiongroup-') and contains(@id,'-panel'))]/div/md-list/md-list-item[2]/div/button[contains(@aria-label,'Video')]")).click();
            Utilities.log(currentThread, "click on satrt video Button ");


            Utilities.sleep(currentThread, 2000);
            Utilities.log(currentThread, "wait 2 seconds");

            driver.findElement(By.xpath("/html/body/div[2]/div/video-recording-panel/div/div[2]/button")).click();
            Utilities.log(currentThread, "click on start");

            driver.findElement(By.xpath("/html/body/div[2]/div/video-recording-panel/div/div[2]/button")).click();
            Utilities.log(currentThread, "click on stop");
        } catch (Exception e) {
            Utilities.log(currentThread, e);
        }

    }

}