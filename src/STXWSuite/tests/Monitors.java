package STXWSuite.tests;

import STXWSuite.STXWBaseTest;
import Utils.Utilities;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;

public class Monitors extends STXWBaseTest {


    @Test
    public void test() {

        Utilities.log(runner, "Enter to monitors testClass");

        Utilities.sleep(runner, 4000);
        /***********************************************************************/
        driver.findElement(By.xpath("//md-tab-item[span[contains(text(),'Monitors')]]")).click();
        Utilities.log(runner,"Click on Monitors button");

        Utilities.sleep(runner,2000);
        driver.findElement(By.xpath("//monitor-battery-panel//div[contains(@role,'button')]")).click();
        Utilities.log(runner,"Click on add bettery graph");

        if(!waitForElement("//monitor-battery-panel//canvas[1]"))
        {
            Assert.fail("The bettary graph didn't appear");
        }

        driver.findElement(By.xpath("//monitor-panel/div/div/div[*[contains(text(),'CPU')]]//div[contains(@role,'button')]")).click();
        Utilities.log(runner,"Click on add CPU graph");
        if(!waitForElement("//monitor-panel/div/div/div[*[contains(text(),'CPU')]]//canvas[1]"))
        {
            Assert.fail("The CPU Graph didn't appear");
        }

        driver.findElement(By.xpath("//monitor-panel/div/div/div[*[contains(text(),'Memory')]]//div[contains(@role,'button')]")).click();
        Utilities.log(runner,"Click on add Memory graph");
        if(!waitForElement("//monitor-panel/div/div/div[*[contains(text(),'Memory')]]//canvas[1]"))
        {
            Assert.fail("The Memory Graph didn't appear");
        }

    }


}

