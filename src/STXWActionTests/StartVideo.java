package STXWActionTests;

import Utils.Utilities;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

public class StartVideo extends BaseTest {

	@Test
	public void test() {
		
		
		try {
			
			Utilities.log(currentThread,"Enter to Start video testClass");
			
			Utilities.sleep(currentThread, 2000);
			driver.findElement(By.xpath("//*[( contains(@id,'accordiongroup-') and contains(@id,'-panel'))]/div/md-list/md-list-item[2]/div/button[contains(@aria-label,'Video')]")).click();
			Utilities.log(currentThread,"click on satrt video Button ");
			
			Utilities.sleep(currentThread, 2000);
			
			driver.findElement(By.xpath("/html/body/div[2]/div/video-recording-panel/div/div[2]/button")).click();
			Utilities.log(currentThread,"click on start");
			
			Utilities.sleep(currentThread, 5000);
				
			driver.findElement(By.xpath("/html/body/div[2]/div/video-recording-panel/div/div[2]/button")).click();
			Utilities.log(currentThread,"click on stop");
			
			Utilities.sleep(currentThread, 3000);
			
			try 
			{
				driver.findElement(By.xpath("/html/body/div[2]/div/video-recording-panel/div/div[2]/div[2]"));
				Utilities.log(currentThread,"Start video succeded");
				driver.findElement(By.xpath("/html/body/div[2]/div/video-recording-panel/div/div[1]/button")).click();
				Utilities.log(currentThread,"click on close for video window");
			}
			catch(Exception e) 
			{
				Utilities.log(currentThread,"the video doesn't found");				
				Assert.fail("the video doesn't found");
			}
			Utilities.sleep(currentThread, 2000);
		}
		catch(Exception e)
		{
			try{throw e;}catch(Exception e1) {Utilities.log(currentThread, "Throw Exception doesn't succeed");}
		}
		
	}

}
