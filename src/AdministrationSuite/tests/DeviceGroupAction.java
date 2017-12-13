package AdministrationSuite.tests;

import static org.junit.Assert.*;

import org.apache.tools.ant.taskdefs.WaitFor;

import AdministrationSuite.AdminBaseTest;
import MyMain.Main;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;

import Utils.Utilities;

public class DeviceGroupAction extends AdminBaseTest {
	
	String DeviceGroupName = "" ;

	@Test
	public void test() {
		
		driver.get(Main.cs.URL_ADDRESS + "/index.html#" + "/device-groups");
		Utilities.log(runner, "Go to " + Main.cs.URL_ADDRESS + "/index.html#" + "/device-groups");
		
		DeviceGroupName = "DemoDeviceGroup" + System.currentTimeMillis();
		CreateGroupDevice();
		AssignDeviceToGroupDevice();
		AssignDeviceGroupToProject();
		DeleteDeviceGroup();
		
	}
	
	private void DeleteDeviceGroup() 
	{
		driver.get(Main.cs.URL_ADDRESS + "/index.html#" + "/device-groups");
		Utilities.sleep(runner, 3000);
		
		driver.findElement(By.xpath("//*[@id='full-page-container']/div[1]/div/div/div/div[1]/input")).sendKeys(DeviceGroupName);
		Utilities.log(runner, "Write " + DeviceGroupName + " in search input");
		
		WaitForText("//*[@id='full-page-container']/div[1]/div/div/div/div[2]/span", "Groups: 1 / ");
		Utilities.sleep(runner, 3000);
		driver.findElement(By.xpath("//*[@id='content-after-toolbar']/div/div/div[1]/md-content/md-virtual-repeat-container/div/div[2]/div/table/tbody/tr")).click();
		Utilities.log(runner , "Click on GroupDevice line in table");
		
		waitUntilElementMarked("//*[@id='content-after-toolbar']/div/div/div[1]/md-content/md-virtual-repeat-container/div/div[2]/div/table/tbody/tr");
		
		Utilities.sleep(runner, 2000);
		driver.findElement(By.xpath("//*[@id='full-page-container']/div[1]/div/div/div/button[contains(@aria-label,'Delete')]")).click();
		Utilities.log(runner, "Click on Delete button");
		
		if(!WaitForElement("//*[contains(@id,'dialogContent')]/div/form/button[span[contains(text(),'Delete')]]")) 
		{
			waitUntilElementMarked("//*[@id='content-after-toolbar']/div/div/div[1]/md-content/md-virtual-repeat-container/div/div[2]/div/table/tbody/tr");
			Utilities.sleep(runner, 500);
			driver.findElement(By.xpath("//*[@id='full-page-container']/div[1]/div/div/div/button[contains(@aria-label,'Delete')]")).click();
			Utilities.log(runner, "Click on Delete button");
		}
		driver.findElement(By.xpath("//*[contains(@id,'dialogContent')]/div/form/button[span[contains(text(),'Delete')]]")).click();
		Utilities.log(runner, "Click on Delete button");
		
		Utilities.sleep(runner, 5000);
		if(!driver.findElement(By.xpath("//*[@id='full-page-container']/div[1]/div/div/div/div[2]/span")).getText().contains("Groups: 0 / ")) 
		{
			Assert.fail("Delete DeviceGroup failed");
		}
		
	}
	private void AssignDeviceGroupToProject() 
	{
		driver.get(Main.cs.URL_ADDRESS + "/index.html#" + "/projects");
		Utilities.log(runner, "Go to " + Main.cs.URL_ADDRESS + "/index.html#" + "/projects");
		
		
		driver.findElement(By.xpath("//*[@id='full-page-container']/div[1]/div/div/div/div/div/div/button[span[contains(text(),'Add')]]")).click();
		Utilities.log(runner, "Click on Add button");
		
		WaitForElement("/html/body/div[1]/div/div/div/form/div[2]/div[1]/input[@name='name']");
		String DemoProject = "DemoProject0" + System.currentTimeMillis();
		driver.findElement(By.xpath("/html/body/div[1]/div/div/div/form/div[2]/div[1]/input[@name='name']")).sendKeys(DemoProject);
		Utilities.log(runner, "Write " + DemoProject + " in Name input");
		
		driver.findElement(By.xpath("/html/body/div[1]/div/div/div/form/div[2]/div[2]/div/div/span")).click();
		Utilities.log(runner, "Click on DeviceGroup Selector");
		
		driver.findElement(By.xpath("/html/body/div[1]/div/div/div/form/div[2]/div[2]/div/input[1]")).sendKeys(DeviceGroupName);
		Utilities.log(runner, "Write " + DeviceGroupName + "in DeviceGroupName");
		
		driver.findElement(By.xpath("//*[contains(@id,'ui-select-choices-row')]/a/div[contains(text()," + DeviceGroupName + ")]")).click();
		Utilities.log(runner, "Click on " + DeviceGroupName + " deviceGroup in Selector");
		
		WaitForElement("/html/body/div[1]/div/div/div/form/div[3]/button[contains(text(),'Create')]");
		driver.findElement(By.xpath("/html/body/div[1]/div/div/div/form/div[3]/button[contains(text(),'Create')]")).click();
		Utilities.log(runner, "Click on Create button");
		
		Utilities.sleep(runner, 2000);
		driver.findElement(By.xpath("//*[@id='full-page-container']/div[1]/div/div/div/div/div/div/div[1]/input[contains(@placeholder,'Search')]")).sendKeys(DeviceGroupName);
		Utilities.log(runner, "Write " + DeviceGroupName + " in Search input");
		
		WaitForText("//*[@id='full-page-container']/div[1]/div/div/div/div/div/div/div[2]/span", "Projects: 1 / ");
		if(!WaitForText("//*[@id='content-after-toolbar']/div/div/div[1]/md-content/md-virtual-repeat-container/div/div[2]/div/table/tbody/tr/td[3]", DeviceGroupName)) 
		{
			Assert.fail("Assign the DeviceGroup to the project failed");
		}
		driver.findElement(By.xpath("//*[@id='content-after-toolbar']/div/div/div[1]/md-content/md-virtual-repeat-container/div/div[2]/div/table/tbody/tr")).click();
		Utilities.log(runner, "Click on project line ib the table");
		
		driver.findElement(By.xpath("//*[@id='full-page-container']/div[1]/div/div/div/div/div/div/button[span[contains(text(),'Delete')]]")).click();
		Utilities.log(runner, "Click on Delete button");
		
		WaitForElement("/html/body/div[1]/div/div/div/div[3]/button[contains(text(),'Delete')]");
		driver.findElement(By.xpath("/html/body/div[1]/div/div/div/div[3]/button[contains(text(),'Delete')]")).click();
		Utilities.log(runner, "Click on another Delete");
		
		WaitForDisappearedElement("/html/body/div[1]/div/div/div/div[3]/button[contains(text(),'Delete')]");
		driver.findElement(By.xpath("//*[@id='full-page-container']/div[1]/div/div/div/div/div/div/div[1]/input[contains(@placeholder,'Search')]")).sendKeys(DemoProject);
		Utilities.log(runner, "Write " + DemoProject + " Search button");
		
		Utilities.sleep(runner, 3000);
		if(WaitForText("//*[@id='full-page-container']/div[1]/div/div/div/div/div/div/div[2]/span", "Projects: 0 /")) 
		{
			Utilities.log(runner, "Delete DemoProject success");
		}
		else
		{
			Utilities.log(runner, "Delete DemoProject failed");
		}
		
		
	}
	
	private void AssignDeviceToGroupDevice() 
	{
		driver.get(Main.cs.URL_ADDRESS + "/index.html#" + "/device-groups");
		Utilities.log(runner, "Go to " + Main.cs.URL_ADDRESS + "/index.html#" + "/device-groups");
		
		WaitForElement("//*[@id='full-page-container']/div[1]/div/div/div/div[1]/input");
		driver.findElement(By.xpath("//*[@id='full-page-container']/div[1]/div/div/div/div[1]/input")).sendKeys(DeviceGroupName);
		Utilities.log(runner, "Write " + DeviceGroupName + " in search input");
				
		WaitForText("//*[@id='full-page-container']/div[1]/div/div/div/div[2]/span", "Groups: 1 /");
		driver.findElement(By.xpath("//*[@id='content-after-toolbar']/div/div/div[1]/md-content/md-virtual-repeat-container/div/div[2]/div/table/tbody/tr")).click();
		Utilities.log(runner, "Click on deviceGroup line in the table");
//		boolean tt = waitForEnableButton("//*[@id='content-after-toolbar']/div/div/div[1]/md-content/md-virtual-repeat-container/div/div[2]/div/table/tbody/tr/td[2]", "//*[@id='full-page-container']/div[1]/div/div/div/button[contains(@aria-label,'Manage')]", "deviceGroup line in the table" );
//		Utilities.sleep(runner, 500);
//		if(!tt) 
//		{
//			System.out.println();
//		}
//		try 
//		{
//			if(driver.findElement(By.xpath("//*[@id='full-page-container']/div[1]/div/div/div/button[contains(@aria-label,'Manage')]")).getAttribute("disable").contains("true"))
//			{
//				driver.findElement(By.xpath("//*[@id='content-after-toolbar']/div/div/div[1]/md-content/md-virtual-repeat-container/div/div[2]/div/table/tbody/tr/td[2]")).click();
//				Utilities.log(runner, "Click on GroupDevice in table");				
//			}
//		}
//		catch(Exception e) 
//		{
//			System.err.println();
//		}

		waitUntilElementMarked("//*[@id='content-after-toolbar']/div/div/div[1]/md-content/md-virtual-repeat-container/div/div[2]/div/table/tbody/tr");
		driver.findElement(By.xpath("//*[@id='full-page-container']/div[1]/div/div/div/button[contains(@aria-label,'Manage')]")).click();
		Utilities.log(runner, "Click on Manage button");
		
		WaitForElement("//*[@id='content']/div/div[2]/md-virtual-repeat-container/div/div[2]/div/md-content/div/table/tbody/tr[1]");
		try
		{                      
			Utilities.log(runner, "Valid if found devices in cloud");
			driver.findElement(By.xpath("//*[@id='content']/div/div[2]/md-virtual-repeat-container/div/div[2]/div/md-content/div/table/tbody/tr[1]"));
		}
		catch(Exception e) 
		{
			Assert.fail("doesn't found any device in the cloud");
		}
		driver.findElement(By.xpath("//*[@id='content']/div/div[2]/md-virtual-repeat-container/div/div[2]/div/md-content/div/table/tbody/tr[1]")).click();
		Utilities.log(runner, "Click on the first device line");
		
		WaitForElement("//*[@id='content']/div/div[1]/div/div/div/div/button[contains(@aria-label,'Assign')]");
		driver.findElement(By.xpath("//*[@id='content']/div/div[1]/div/div/div/div/button[contains(@aria-label,'Assign')]")).click();
		Utilities.log(runner, "Click on Assign button");
		
		Utilities.sleep(runner, 4000);
		
		if(!WaitForText("//*[@id='content']/div/div[2]/md-virtual-repeat-container/div/div[2]/div/md-content/div/table/tbody/tr[1]/td[7]/span", "Yes")) 
		{
			driver.get(driver.getCurrentUrl());
			Utilities.sleep(runner, 5000);
			WaitForElement("//*[@id='content']/div/div[2]/md-virtual-repeat-container/div/div[2]/div/md-content/div/table/tbody/tr/td[7]/span[contains(text(),'Yes')]");
			if(!WaitForText("//*[@id='content']/div/div[2]/md-virtual-repeat-container/div/div[2]/div/md-content/div/table/tbody/tr[1]/td[7]/span", "Yes"))
			{
				Assert.fail("Assign device doesn't succeed");
			}
		}
		
	} 
	
	private void CreateGroupDevice() 
	{
		WaitForElement("//*[@id='full-page-container']/div[1]/div/div/div/button[contains(@aria-label,'Add')]");
		driver.findElement(By.xpath("//*[@id='full-page-container']/div[1]/div/div/div/button[contains(@aria-label,'Add')]")).click();
		Utilities.log(runner, "Click on Add button");
		
		WaitForElement("//*[@name='groupName']");
		driver.findElement(By.xpath("//*[@name='groupName']")).sendKeys(DeviceGroupName);
		Utilities.log(runner, "Write DemoDeviceGroup in Group Name input " + DeviceGroupName);
		
		driver.findElement(By.xpath("//*[@name='testplanform']/button[span[contains(text(),'Create')]]")).click();
		Utilities.log(runner, "Click on Create button");
		
		Utilities.sleep(runner, 3000);
		driver.findElement(By.xpath("//*[@id='full-page-container']/div[1]/div/div/div/div[1]/input")).sendKeys(DeviceGroupName);
		Utilities.log(runner, "Write " + DeviceGroupName  + "in Search input ");
		
		if(!WaitForText("//*[@id='full-page-container']/div[1]/div/div/div/div[2]/span", "Groups: 1 / ")) 
		{
			driver.get(driver.getCurrentUrl());
			Utilities.sleep(runner, 3000);
			driver.findElement(By.xpath("//*[@id='full-page-container']/div[1]/div/div/div/div[1]/input")).sendKeys(DeviceGroupName);
			Utilities.log(runner, "Write " + DeviceGroupName  + "in Search input ");
			if(!WaitForText("//*[@id='full-page-container']/div[1]/div/div/div/div[2]/span", "Groups: 1 / ")) 
			{
				driver.get(driver.getCurrentUrl());
				driver.findElement(By.xpath("//*[@id='full-page-container']/div[1]/div/div/div/div[1]/input")).sendKeys(DeviceGroupName);
				Utilities.log(runner, "Write " + DeviceGroupName  + "in Search input "); 
				if(!WaitForText("//*[@id='full-page-container']/div[1]/div/div/div/div[2]/span", "Groups: 1 / ")) 
				{ 
					Assert.fail("Device Group doesn't create");
				}
			}
			
		}
		
	}

}
