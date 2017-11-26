package ActionTests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

public class Reboot extends BaseTest {

	@Before
	public void SetUp() 
	{
		super.SetUp();
	}
	@Test
	public void test() {
		
		try {
			
			    log("Enter to Reboot Test");
				System.out.println("rebootttttt");
				driver.findElement(By.xpath("//*[( contains(@id,'accordiongroup-') and contains(@id,'-panel'))]/div/md-list/md-list-item[2]/div/button[contains(@aria-label,'Reboot')]")).click();
				log("click on reboot button ");
				
				try{Thread.sleep(2000);
				log("wait 2 seconds");}catch(Exception e) {writeFailedLineInLog("failed to wait 2 seconds " + e);}
				
				driver.findElement(By.xpath("/html/body/div[1]/div/div/reboot-confirm-dialog/div/div[3]/button[2]")).click();
				log("click on confirm Button ");
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
