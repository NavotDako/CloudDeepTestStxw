package ActionTests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import Cloud_API.GetSpecificDevice;

public class Install extends BaseTest {

	String text = "";
	@Before
	public void SetUp() 
	{
		super.SetUp();
	}
	@Test
	public void test() {
		
		try {
				System.out.println("installlllll");
				log("Enter to Install Test");
				
				if(CurrentThread.STXW.equals("manual")) 
				{
					driver.findElement(By.xpath("//*[( contains(@id,'accordiongroup-') and contains(@id,'-panel'))]/div/md-list/md-list-item[1]/div/button[contains(@aria-label,'Install')]")).click();
					log("click on Install Button");
				}
				else 
				{
					driver.findElement(By.xpath("//*[( contains(@id,'accordiongroup-') and contains(@id,'-panel')  )]/div/md-list/md-list-item[1]/div/button[contains(@aria-label,'Applications')]")).click();
					log("Click on application Button");
				}
				
				try{Thread.sleep(2000);
				log("wait 2 seconds");}catch(Exception e) {writeFailedLineInLog("failed to wait 2 seconds " + e);}
				
				driver.findElement(By.xpath("/html/body/div[2]/div/install-panel/div/md-list/md-list-item/div/button[contains(@aria-label,'ank')]")).click();
				log("click on the Experibank application to install");
				
				try{Thread.sleep(2000);
				log("wait 2 seconds");}catch(Exception e) {writeFailedLineInLog("failed to wait 2 seconds " + e);}
				
				if(CurrentThread.STXW.equals("manual"))
				{
					driver.findElement(By.xpath("/html/body/div[2]/div/install-panel/div/md-list/md-list-item/div/div[1]/div[contains(text(),'ank')]/md-card/md-card-actions/button")).click();																	
													
					log("click on install Button");
				}
				else 
				{
					driver.findElement(By.xpath("/html/body/div[2]/div/install-panel/div/md-list/md-list-item/div[button[(contains(@aria-label,'Experibank') or contains(@aria-label,'ank'))]]/div[1]/div[contains(text(),'Experibank') or contains(text(),'EriBank')]/md-card/md-card-actions/button[2]")).click();
					                             
					log("click on install Button");
				}
				
				try{Thread.sleep(5000);
				log("wait 5 seconds");}catch(Exception e) {writeFailedLineInLog("failed to wait 5 seconds " + e);}
				
				driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div"));
				log("test if application manager found");
				
				try{Thread.sleep(30000);
				log("wait 30 seconds");}catch(Exception e) {writeFailedLineInLog("failed to wait 30 seconds " + e);}
				
				try {text = driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/div[5]/div/div/install-progress-panel/div/code")).getText();}catch(Exception e) {System.out.println(e);}
				log("get the Text in application manager (" + text + ")");
				if(!(text.contains("Successfully") && text.contains("Starting")))
				{
					passed = "failed";
				}
				
				driver.findElement(By.xpath("/html/body/div[2]/div/install-panel/div/div[1]/button")).click();
				log("click on x (close the Install window)");
				
				System.out.println("my text: " + text);
		}
		catch(Exception e) 
		{
			System.out.println(e);
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


