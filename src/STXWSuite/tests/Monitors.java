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
            if(runner.STXWType.equals("manual"))
            {
	            driver.findElement(By.xpath("//*[( contains(@id,'accordiongroup-') and contains(@id,'-panel'))]/div/md-list/md-list-item[1]/div/button[contains(@aria-label,'Monitors')]")).click();
	            Utilities.log(runner, "click on monitors Button");
            }
            else 
            {
            	driver.findElement(By.xpath("//*[( contains(@id,'accordiongroup-') and contains(@id,'-panel'))]/div/md-list/md-list-item[1]/div/button[contains(@aria-label,'Network') or contains(@aria-label,'Monitors')]")).click();
            	Utilities.log(runner, "click on Network Button");
            }
            Utilities.sleep(runner, 3000);

           try
           {
        	   driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div"));
        	   Utilities.log(runner, "test if monitor popUp");
           }
           catch(Exception e) 
           {
        	   Utilities.log(runner, "monitor window doesn't popUp");
               Assert.fail("monitor window doesn't popUp");
           }
           Utilities.log(runner, "monitor window is popUp");

       
    }


}
