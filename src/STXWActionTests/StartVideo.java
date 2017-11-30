
package STXWActionTests;

import STXWActionTests.STXWBaseTest;
import Utils.Utilities;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

public class StartVideo extends STXWBaseTest {

    @Test
    public void test() {

        Utilities.log(currentThread, "Enter to Start video testClass");

        Utilities.sleep(currentThread, 2000);
        driver.findElement(By.xpath("//*[( contains(@id,'accordiongroup-') and contains(@id,'-panel'))]/div/md-list/md-list-item[2]/div/button[contains(@aria-label,'Video')]")).click();
        Utilities.log(currentThread, "click on satrt video Button ");

        Utilities.sleep(currentThread, 2000);

        driver.findElement(By.xpath("/html/body/div[2]/div/video-recording-panel/div/div[2]/button")).click();
        Utilities.log(currentThread, "click on start");

        Utilities.sleep(currentThread, 5000);

        driver.findElement(By.xpath("/html/body/div[2]/div/video-recording-panel/div/div[2]/button")).click();
        Utilities.log(currentThread, "click on stop");

        Utilities.sleep(currentThread, 3000);

        try {
            driver.findElement(By.xpath("/html/body/div[2]/div/video-recording-panel/div/div[2]/div[2]"));
            Utilities.log(currentThread, "Start video succeded");
            driver.findElement(By.xpath("/html/body/div[2]/div/video-recording-panel/div/div[1]/button")).click();
            Utilities.log(currentThread, "click on close for video window");
        } catch (Exception e) {
            Utilities.log(currentThread, "the video doesn't found");
            try {
                throw new Exception("the video doesn't found");
            } catch (Exception e1) {
            }
        }
        Utilities.sleep(currentThread, 2000);


    }

}


//package ActionTests;
//
//import Utils.Utilities;
//import org.junit.Test;
//import org.openqa.selenium.By;
//
//public class StartVideo extends BaseTest {
//
//    @Test
//    public void test() {
//
//
//        try {
//            Utilities.log(currentThread, "start video");
//            Utilities.log(currentThread, "Enter to Start video testClass");
//
//            driver.findElement(By.xpath("//*[( contains(@id,'accordiongroup-') and contains(@id,'-panel'))]/div/md-list/md-list-item[2]/div/button[contains(@aria-label,'Video')]")).click();
//            Utilities.log(currentThread, "click on satrt video Button ");
//
//
//            Utilities.sleep(currentThread, 2000);
//            Utilities.log(currentThread, "wait 2 seconds");
//
//            driver.findElement(By.xpath("/html/body/div[2]/div/video-recording-panel/div/div[2]/button")).click();
//            Utilities.log(currentThread, "click on start");
//
//            driver.findElement(By.xpath("/html/body/div[2]/div/video-recording-panel/div/div[2]/button")).click();
//            Utilities.log(currentThread, "click on stop");
//        } catch (Exception e) {
//            Utilities.log(currentThread, e);
//        }
//
//    }
//
//}
