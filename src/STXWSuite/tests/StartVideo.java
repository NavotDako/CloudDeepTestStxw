
package STXWSuite.tests;

import STXWSuite.STXWBaseTest;
import Utils.Utilities;
import org.junit.Test;
import org.openqa.selenium.By;

public class StartVideo extends STXWBaseTest {

    @Test
    public void test() {

        Utilities.log(runner, "Enter to Start video testClass");

        Utilities.sleep(runner, 2000);
        driver.findElement(By.xpath("//*[( contains(@id,'accordiongroup-') and contains(@id,'-panel'))]/div/md-list/md-list-item[2]/div/button[contains(@aria-label,'Video')]")).click();
        Utilities.log(runner, "click on satrt video Button ");

        Utilities.sleep(runner, 2000);

        driver.findElement(By.xpath("/html/body/div[2]/div/video-recording-panel/div/div[2]/button")).click();
        Utilities.log(runner, "click on start");

        Utilities.sleep(runner, 5000);

        driver.findElement(By.xpath("/html/body/div[2]/div/video-recording-panel/div/div[2]/button")).click();
        Utilities.log(runner, "click on stop");

        Utilities.sleep(runner, 3000);

        try {
            driver.findElement(By.xpath("/html/body/div[2]/div/video-recording-panel/div/div[2]/div[2]"));
            Utilities.log(runner, "Start video succeded");
            driver.findElement(By.xpath("/html/body/div[2]/div/video-recording-panel/div/div[1]/button")).click();
            Utilities.log(runner, "click on close for video window");
        } catch (Exception e) {
            Utilities.log(runner, "the video doesn't found");
            try {
                throw new Exception("the video doesn't found");
            } catch (Exception e1) {
            }
        }
        Utilities.sleep(runner, 2000);


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
//            Utilities.log(runner, "start video");
//            Utilities.log(runner, "Enter to Start video testClass");
//
//            driver.findElement(By.xpath("//*[( contains(@id,'accordiongroup-') and contains(@id,'-panel'))]/div/md-list/md-list-item[2]/div/button[contains(@aria-label,'Video')]")).click();
//            Utilities.log(runner, "click on satrt video Button ");
//
//
//            Utilities.sleep(runner, 2000);
//            Utilities.log(runner, "wait 2 seconds");
//
//            driver.findElement(By.xpath("/html/body/div[2]/div/video-recording-panel/div/div[2]/button")).click();
//            Utilities.log(runner, "click on start");
//
//            driver.findElement(By.xpath("/html/body/div[2]/div/video-recording-panel/div/div[2]/button")).click();
//            Utilities.log(runner, "click on stop");
//        } catch (Exception e) {
//            Utilities.log(runner, e);
//        }
//
//    }
//
//}
