package AdministrationSuite.tests;

import AdministrationSuite.AdminBaseTest;
import MyMain.Main;
import Utils.Utilities;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ApplicationAction extends AdminBaseTest {

	//To work, you must add the application SampleRecycle to the selenium agetn and update the app path in
	String appPath = "/Users/Khaled.abbas/Downloads/sampleApp.apk";
	@Test
	public void test() {
		
		driver.get(Main.cs.URL_ADDRESS + "/index.html#" + "/applications");
		Utilities.log(runner, "Go to " + Main.cs.URL_ADDRESS + "/index.html#" + "/applications");
		UploadAndAssignApplication();
		DeleteApplication();
		verifyAppWasDeleted();
	}
	
	private void DeleteApplication() 
	{
		driver.get(Main.cs.URL_ADDRESS + "/index.html#/devices");
		Utilities.log(runner, "Go to " + Main.cs.URL_ADDRESS + "/index.html#" + "/applications");
		driver.get(Main.cs.URL_ADDRESS + "/index.html#" + "/applications");
		WaitForElement("//*[@aria-label='SampleRecyclerView4RnD']");
		driver.findElement(By.xpath("//*[@aria-label='SampleRecyclerView4RnD']")).click();

		//Click the uploaded app
		driver.findElement(By.xpath("//*[text()='ayoubProjectDeepTest1']")).click();

		Utilities.log(runner, "Click on Delete button");
		WaitForElement("//*[@aria-label='Delete']");
		driver.findElement(By.xpath("//*[@aria-label='Delete']")).click();
		Utilities.log(runner, "Click on Delete Selected Version button");
		driver.findElement(By.xpath("/html/body/div[1]/div/div/div/div[3]/button[contains(text(),'Delete Selected Version')]")).click();
	}

	private void verifyAppWasDeleted(){
		driver.get(Main.cs.URL_ADDRESS + "/index.html#/devices");
		Utilities.log(runner, "Go to " + Main.cs.URL_ADDRESS + "/index.html#" + "/applications");
		driver.get(Main.cs.URL_ADDRESS + "/index.html#" + "/applications");
		driver.findElement(By.xpath("//*[@aria-label='SampleRecyclerView4RnD']")).click();
		try
		{
			Utilities.log(runner, "Valid if the assign application found");
			driver.findElement(By.xpath("//*[@id='content-after-toolbar']/div[1]/div/div[1]/div/div/md-content/div/div[2]/div/div/table/tbody/tr/td[5][contains(text(),'ayoubProjectDeepTest1')]"));
			Assert.fail("Delete application failed");
		}
		catch(Exception e)
		{
			Utilities.log(runner, "App was deleted");
		}
	}
	private void UploadAndAssignApplication() 
	{
		Utilities.sleep(runner, 2000);
        new WebDriverWait(driver, 30000).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
        driver.findElement(By.xpath("//*[@id='full-page-container']/div[1]/div/div/div/button[contains(@aria-label,'Upload')]")).click();
		Utilities.log(runner, "Click on Upload button");
		
		WaitForElement("/html/body/div[1]/div/div/div/form");
//		driver.findElement(By.xpath("/html/body/div[1]/div/div/div/form/div[2]/div[1]/input")).sendKeys(System.getProperty("user.dir") + runner.enums.applicationPath);
		//This path is related to the machine running the selenium test

		driver.findElement(By.xpath("/html/body/div[1]/div/div/div/form/div[2]/div[1]/input")).sendKeys(appPath);
		Utilities.log(runner, "Write " + runner.enums.applicationPath + " in chose application");
		
		Utilities.sleep(runner, 20000);
		driver.findElement(By.xpath("//*[@aria-label='Select box activate']")).click();
		Utilities.log(runner, "Click on project TextView");
		
		driver.findElement(By.xpath("//*[@aria-label='Select box']")).sendKeys("ayoubProjectDeepTest1");
		Utilities.log(runner, "Write ayoubProjectDeepTest1 in project TextView");
		
		Utilities.sleep(runner, 1500);
		driver.findElement(By.xpath("//*[contains(@id,'ui-select-choices-row')]/a[div/span[contains(text(),'ayoubProjectDeepTest1')]]")).click();
		Utilities.log(runner, "Click on ayoubProjectDeepTest1 project");
		
		driver.findElement(By.xpath("//*[@name='create-application-button']")).click();
		Utilities.log(runner, "Click on Upload button");
		
		Utilities.sleep(runner, 20000);
		try
		{
			driver.get(Main.cs.URL_ADDRESS + "/index.html#/devices");
			Utilities.log(runner, "Go to " + Main.cs.URL_ADDRESS + "/index.html#" + "/applications");
			driver.get(Main.cs.URL_ADDRESS + "/index.html#" + "/applications");
			Utilities.sleep(runner, 2000);
			driver.findElement(By.xpath("//*[@id='content-after-toolbar']/div/div/div[1]/div/div/md-content/div/div/table/tbody/tr[td[contains(@aria-label,'SampleRecyclerView4RnD')]]")).click();
			Utilities.log(runner, "Click on sample application");
		}
		catch(Exception e) 
		{
			Assert.fail("The Upload failed");
		}
		
		if(!WaitForElement("//*[@id='content-after-toolbar']/div[1]/div/div[1]/div/div/md-content/div/div[2]/div"))
		{
			try
			{
				driver.findElement(By.xpath("//*[@id='content-after-toolbar']/div/div/div[1]/div/div/md-content/div/div/table/tbody/tr[td[contains(@aria-label,'SampleRecyclerView4RnD')]]")).click();
				Utilities.log(runner, "Click on sample application");
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
			Assert.fail("The assign did not work");
		}
		
	}

}



