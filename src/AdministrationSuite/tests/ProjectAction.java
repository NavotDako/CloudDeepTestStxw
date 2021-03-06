package AdministrationSuite.tests;

import AdministrationSuite.AdminBaseTest;
import MyMain.Main;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;

import Utils.Utilities;

public class ProjectAction extends AdminBaseTest {

	String projectName = "";
	@Test
	public void test() {
		
		Utilities.log(runner, "enter ProjectTest class");
		driver.get(Main.cs.URL_ADDRESS + "/index.html#" + "/projects");
		Utilities.log(runner, "Go to " + Main.cs.URL_ADDRESS + "/index.html#" + "/projects");
		waitForText("//*[@id='full-page-container']/div[1]/div/div/div/div/div/div/div[2]/span", "Projects:");
		WaitForElement("//*[@id='full-page-container']/div[1]/div/div/div/div/div/div/button[contains(@aria-label,'Add')]");
		CreateProject();
		AssignProject();
		DeleteProject();
	}
	private void DeleteProject() 
	{
		driver.get(Main.cs.URL_ADDRESS + "/index.html#" + "/projects");
		Utilities.log(runner, "Go to " + Main.cs.URL_ADDRESS + "/index.html#" + "/projects");
		
		driver.findElement(By.xpath("//*[@id='full-page-container']/div[1]/div/div/div/div/div/div/div[1]/input")).sendKeys(projectName);
		Utilities.log(runner, "Write " + projectName + " in Search button" );
		
		waitForText("//*[@id='full-page-container']/div[1]/div/div/div/div/div/div/div[2]/span", "Projects: 1 /");
		Utilities.sleep(runner, 2000);
		findProjAndClickDelete();
		
		if(!WaitForElement("/html/body/div[1]/div/div/div")){
			Utilities.log(runner, "someone refreshed the page, retrying");
			findProjAndClickDelete();
			WaitForElement("/html/body/div[1]/div/div/div");

		}
		driver.findElement(By.xpath("/html/body/div[1]/div/div/div/div[3]/button[contains(text(),'Delete')]")).click();
		Utilities.log(runner, "Click on Delete button");
		
		Utilities.sleep(runner, 30000); 
		if(!(waitForText("//*[@id='full-page-container']/div[1]/div/div/div/div/div/div/div[2]/span", "Projects: 0 /")))
		{
			Assert.fail("Delete Action is fail");
		}
		
		
	}

	private void findProjAndClickDelete() {
		driver.findElement(By.xpath("//*[@cs-select-row='project']")).click();
		Utilities.log(runner, "Click on " + projectName + " line in the table");

		Utilities.sleep(runner, 2000);
		driver.findElement(By.xpath("//*[@aria-label='Delete']")).click();
		Utilities.log(runner, "Click on Delete button");
	}

	private void AssignProject() 
	{
		Utilities.sleep(runner, 2000);
		driver.findElement(By.xpath("//*[@id='content-after-toolbar']/div/div/div[1]/md-content/md-virtual-repeat-container/div/div[2]/div/table/tbody/tr")).click();
		Utilities.log(runner, "Click on " + projectName + " project line");
		
		driver.findElement(By.xpath("//*[@id='full-page-container']/div[1]/div/div/div/div/div/div/button[contains(@aria-label,'Manage')]")).click();
		Utilities.log(runner, "Click on Manage button");
		
		Utilities.sleep(runner, 3000);
		driver.findElement(By.xpath("//*[@id='full-page-container']/ui-view/div/div[1]/div/div/div/div/div/input")).sendKeys("ayoubUserDeepTest1");
		Utilities.log(runner, "Write ayoubUserDeepTest1 in Search TextVeiw");
		
		Utilities.sleep(runner, 2000);
		driver.findElement(By.xpath("//*[@id='full-page-container']/ui-view/div/div[2]/md-virtual-repeat-container/div/div[2]/div/md-content/div/table/tbody/tr")).click();
		Utilities.log(runner, "Click on ayoubUserDeepTest1 line in users table");
		
		Utilities.sleep(runner, 2000);
		driver.findElement(By.xpath("//*[@id='full-page-container']/ui-view/div/div[1]/div/div/div/div/button[contains(@aria-label,'Assign')]")).click();
		Utilities.log(runner, "Click on Assign button");
		
		WaitForElement("/html/body/div[1]/div/div/div/form");
		driver.findElement(By.xpath("/html/body/div[1]/div/div/div/form/div[2]/div[1]/select")).click();
		Utilities.log(runner, "Click on Role selector");
		
		driver.findElement(By.xpath("/html/body/div[1]/div/div/div/form/div[2]/div[1]/select/option[contains(text(),'User')]")).click();
		Utilities.log(runner, "Click on User option");
		
		driver.findElement(By.xpath("/html/body/div[1]/div/div/div/form/div[3]/button[contains(text(),'Assign')]")).click();
		Utilities.log(runner, "Click on Assign button");
		
		Utilities.sleep(runner, 2000);
		if(!driver.findElement(By.xpath("//*[@id='full-page-container']/ui-view/div/div[2]/md-virtual-repeat-container/div/div[2]/div/md-content/div/table/tbody/tr/td[9]/span")).getText().contains("Yes")) 
		{
			Assert.fail("The Assigned field for ayoubUserDeepTest1 doesn't Yes ");
		}
	} 
	
	private void CreateProject() 
	{
		projectName = "DemoProject" + System.currentTimeMillis();
		driver.findElement(By.xpath("//*[@id='full-page-container']/div[1]/div/div/div/div/div/div/button[contains(@aria-label,'Add')]")).click();
		Utilities.log(runner, "Click on Add button");
		
		WaitForElement("/html/body/div[1]/div/div/div/form");
		driver.findElement(By.xpath("/html/body/div[1]/div/div/div/form/div[2]/div[1]/input")).sendKeys(projectName);
		Utilities.log(runner, "Write " + projectName + " in Name TextVeiw");
		
		driver.findElement(By.xpath("/html/body/div[1]/div/div/div/form/div[2]/div[2]/div/div/span")).sendKeys("ayoubDeviceGroupDeepTest");
		Utilities.log(runner, "Write ayoubDeviceGroupDeepTest in Device Group");
		
		driver.findElement(By.xpath("/html/body/div[1]/div/div/div/form/div[3]/button[contains(text(),'Create')]")).click();
		Utilities.log(runner, "Click on Create button");
		
		Utilities.sleep(runner, 8000);
		driver.findElement(By.xpath("//*[@id='full-page-container']/div[1]/div/div/div/div/div/div/div[1]/input")).sendKeys(projectName);
		Utilities.log(runner, "Write " + projectName + " in Search TextVeiw");
		
		if(!waitForText("//*[@id='full-page-container']/div[1]/div/div/div/div/div/div/div[2]/span", "Projects: 1 /"))
		{
			Assert.fail(projectName + " doesn't found");
		}

	}

}
