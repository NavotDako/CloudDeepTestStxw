package STXWSuite.tests;

import STXWSuite.STXWBaseTest;
import Utils.Utilities;
import junit.framework.Assert;

import org.junit.Test;
import org.openqa.selenium.By;

public class OpenLogs extends STXWBaseTest {

    @Test
    public void test() {


        Utilities.log(runner, "Enter to Open Logs testClass");
        Utilities.sleep(runner, 2000);

        /****************************************************/
        if(!waitForElement("//*[@id='log']"))
        {
            Assert.fail("The device log doesn't found");
        }
        Utilities.sleep(runner, 5000);
        try {
            if (!driver.findElement(By.xpath("//*[@id='log']")).getText().equals(""))

            {
                Utilities.log(runner, "the logs after 5 seconds" + driver.findElement(By.xpath("//*[@id='log']")).getText());
            } else {
                Utilities.log(runner, "The logs are Empty");
                Assert.fail("The logs are Empty");
            }

        } catch (Exception e) {
            Utilities.log(runner, e.toString());
        }
    }


}
