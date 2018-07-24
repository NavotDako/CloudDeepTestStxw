
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
        driver.findElement(By.xpath("//*[@id='video_recording_panel_openclose']")).click();
        Utilities.log(runner, "click on satrt video Button ");

        Utilities.sleep(runner, 2000);
        //Start video
        driver.findElement(By.xpath("//*[@id='start']")).click();
        Utilities.log(runner, "click on start");

        Utilities.sleep(runner, 5000);

        driver.findElement(By.xpath("//*[@id='stop']")).click();
        Utilities.log(runner, "click on stop");

        Utilities.sleep(runner, 3000);

        try {
            driver.findElement(By.xpath("//*[@id='device-plane-id']/video-recording-panel"));
            Utilities.log(runner, "Start video succeded");
            driver.findElement(By.xpath("//video-recording-panel//button[*[contains(text(),'close')]]")).click();
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


