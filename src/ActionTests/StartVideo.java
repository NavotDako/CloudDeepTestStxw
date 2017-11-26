package ActionTests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

public class StartVideo extends BaseTest {

	@Before
	public void SetUp() 
	{
		super.SetUp();
	}
	@Test
	public void test() {
		
		
		try {
			System.out.println("start video");
			log("Enter to Start video Test");
			
			driver.findElement(By.xpath("//*[( contains(@id,'accordiongroup-') and contains(@id,'-panel'))]/div/md-list/md-list-item[2]/div/button[contains(@aria-label,'Video')]")).click();
			log("click on satrt video Button ");
			
			try{Thread.sleep(2000);
			log("wait 2 seconds");}catch(Exception e) {writeFailedLineInLog("failed to wait 2 seconds " + e);}
			
			driver.findElement(By.xpath("/html/body/div[2]/div/video-recording-panel/div/div[2]/button")).click();
			log("click on start");
			//{
			
		
		
					
		
		
		
			//}
			
			
			driver.findElement(By.xpath("/html/body/div[2]/div/video-recording-panel/div/div[2]/button")).click();
			log("click on stop");
		}
		catch(Exception e)
		{
			writeFailedLineInLog(e.toString());
		}
		
	}
	@After
	public void finsh() 
	{
		super.finish();
	}

}
