package AdministrationSuite.tests;

import static org.junit.Assert.*;

import java.io.File;

import AdministrationSuite.AdminBaseTest;
import MyMain.Main;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;

import com.sun.media.sound.SimpleSoundbank;

import Utils.Utilities;

public class ApplicationAction extends AdminBaseTest {

	@Test
	public void test() {
		
		driver.get(Main.cs.URL_ADDRESS + "/index.html#" + "/applications");
		Utilities.log(runner, "Go to " + Main.cs.URL_ADDRESS + "/index.html#" + "/applications");
		UploadAndAssignApplication();
		DeleteApplication();
		
	}
	
	private void DeleteApplication() 
	{
		driver.findElement(By.xpath("//*[@id='full-page-container']/div[1]/div/div/div/button[contains(@aria-label,'Delete')]")).click();
		Utilities.log(runner, "Click on Delete button");
		
		WaitForElement("//*[@id='full-page-container']/div[1]/div/div/div/button[contains(@aria-label,'Delete')]");
		driver.findElement(By.xpath("/html/body/div[1]/div/div/div/div[3]/button[contains(text(),'Delete Selected Version')]")).click();
		Utilities.log(runner, "Click on Delete Selected Version button");
		
		try 
		{
			Utilities.log(runner, "Valid if the assign application found");
			driver.findElement(By.xpath("//*[@id='content-after-toolbar']/div[1]/div/div[1]/div/div/md-content/div/div[2]/div/div/table/tbody/tr/td[5][contains(text(),'ayoubProjectDeepTest1')]"));
		}
		catch(Exception e) 
		{
			Assert.fail("Delete application failed");
		}
	}
	
	private void UploadAndAssignApplication() 
	{
		Utilities.sleep(runner, 2000);
		driver.findElement(By.xpath("//*[@id='full-page-container']/div[1]/div/div/div/button[contains(@aria-label,'Upload')]")).click();
		Utilities.log(runner, "Click on Upload button");
		
		WaitForElement("/html/body/div[1]/div/div/div/form");
		driver.findElement(By.xpath("/html/body/div[1]/div/div/div/form/div[2]/div[1]/input")).sendKeys(System.getProperty("user.dir") + runner.enums.applicationPath);
		Utilities.log(runner, "Write " + runner.enums.applicationPath + " in chose application");
		
		Utilities.sleep(runner, 2000);
		driver.findElement(By.xpath("/html/body/div[1]/div/div/div/form/div[2]/div[5]/div/div/span")).click();
		Utilities.log(runner, "Click on project TextView");
		
		driver.findElement(By.xpath("/html/body/div[1]/div/div/div/form/div[2]/div[5]/div/input[1]")).sendKeys("ayoubProjectDeepTest1");
		Utilities.log(runner, "Write ayoubProjectDeepTest1 in project TextView");
		
		Utilities.sleep(runner, 1500);
		driver.findElement(By.xpath("//*[contains(@id,'ui-select-choices-row')]/a[div/span[contains(text(),'ayoubProjectDeepTest1')]]")).click();
		Utilities.log(runner, "Click on ayoubProjectDeepTest1 project");
		
		driver.findElement(By.xpath("/html/body/div[1]/div/div/div/form/div[3]/button[2]")).click();
		Utilities.log(runner, "Click on Upload button");
		
		Utilities.sleep(runner, 20000);
		try
		{
			Utilities.sleep(runner, 2000);
			driver.findElement(By.xpath("//*[@id='content-after-toolbar']/div/div/div[1]/div/div/md-content/div/div/table/tbody/tr[td[contains(@aria-label,'com.experitest.uicatalog')]]")).click();
			Utilities.log(runner, "Click on uicatalog application");
		}
		catch(Exception e) 
		{
			Assert.fail("The Upload failed");
		}
		
		if(!WaitForElement("//*[@id='content-after-toolbar']/div[1]/div/div[1]/div/div/md-content/div/div[2]/div"))
		{
			try
			{
				driver.findElement(By.xpath("//*[@id='content-after-toolbar']/div/div/div[1]/div/div/md-content/div/div/table/tbody/tr[td[contains(@aria-label,'com.experitest.uicatalog')]]")).click();
				Utilities.log(runner, "Click on uicatalog application");
			}
			catch(Exception e) 
			{
				Assert.fail("The Upload failed");
			}
		}
		
		try 
		{
			Utilities.log(runner, "Valid if the application is found in project");
			driver.findElement(By.xpath("//*[@id='content-after-toolbar']/div[1]/div/div[1]/div/div/md-content/div/div[2]/div/div/table/tbody/tr/td[5][contains(text(),'ayoubProjectDeepTest1')]"));
		}
		catch(Exception e) 
		{
			Assert.fail("The assign doesn't found");
		}
		
	}

}



