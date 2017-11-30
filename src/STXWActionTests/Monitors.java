package STXWActionTests;

import Utils.Utilities;

import java.util.Currency;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

public class Monitors extends STXWBaseTest {


    @Test
    public void test() {

            Utilities.log(currentThread, "Enter to monitors testClass");

            Utilities.sleep(currentThread, 4000);
            if(currentThread.STXWType.equals("manual")) 
            {
	            driver.findElement(By.xpath("//*[( contains(@id,'accordiongroup-') and contains(@id,'-panel'))]/div/md-list/md-list-item[1]/div/button[contains(@aria-label,'Monitors')]")).click();
	            Utilities.log(currentThread, "click on monitors Button");
            }
            else 
            {
            	driver.findElement(By.xpath("//*[( contains(@id,'accordiongroup-') and contains(@id,'-panel'))]/div/md-list/md-list-item[1]/div/button[contains(@aria-label,'Network') or contains(@aria-label,'Monitors')]")).click();
            	Utilities.log(currentThread, "click on Network Button");
            }
            Utilities.sleep(currentThread, 3000);

           try
           {
        	   driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div"));
        	   Utilities.log(currentThread, "test if monitor popUp");
           }
           catch(Exception e) 
           {
        	   Utilities.log(currentThread, "monitor window doesn't popUp");               
               Assert.fail("monitor window doesn't popUp");
           }
           Utilities.log(currentThread, "monitor window is popUp");

       
    }


}
