package ActionTests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

public class Monitors extends BaseTest {

	@Before
	public void SetUp() 
	{
		super.SetUp();
	}
	@Test
	public void test() {
		
		
		try {
			
		System.out.println("Monitorssssss");
		log("Enter to monitors Test");
		
		driver.findElement(By.xpath("//*[( contains(@id,'accordiongroup-') and contains(@id,'-panel'))]/div/md-list/md-list-item[1]/div/button[contains(@aria-label,'Monitors')]")).click();
		log("click on monitors Button");
		
		try{Thread.sleep(2000);
		log("wait 2 seconds");}catch(Exception e) {writeFailedLineInLog("failed to wait 2 seconds " + e);}
		
		driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div"));
		log("test if monitor popUp");
		
		}
		catch(Exception e)
		{
			writeFailedLineInLog(e.toString());
			passed = "failed";
		}
	}
	@After
	public void finsh() 
	{
		super.finish();
	}
}
