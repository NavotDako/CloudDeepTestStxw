package AdministrationSuite.tests;

import AdministrationSuite.AdminBaseTest;
import MyMain.Main;
import Utils.Utilities;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;

public class DeviceGroupAction extends AdminBaseTest {
	
	String DeviceGroupName = "" ;

	@Test
	public void test() {
		
		driver.get(Main.cs.HOST + "/device-groups");
		Utilities.log(runner, "Go to " + Main.cs.HOST + "/device-groups");
		
		DeviceGroupName = "DemoDeviceGroup" + System.currentTimeMillis();
		CreateGroupDevice();
		AssignDeviceToGroupDevice();
		AssignDeviceGroupToProject();
		DeleteDeviceGroup();
		
	}
	
	private void DeleteDeviceGroup() 
	{
		driver.get(Main.cs.HOST + "/device-groups");
		Utilities.sleep(runner, 3000);
		
		driver.findElement(By.xpath("//*[@id='full-page-container']/div[1]/div/div/div/div[1]/input")).sendKeys(DeviceGroupName);
		Utilities.log(runner, "Write " + DeviceGroupName + " in search input");
		
		WaitForText("//*[@id='full-page-container']/div[1]/div/div/div/div[2]/span", "Groups: 1 / ");
		driver.findElement(By.xpath("//*[@id='content-after-toolbar']/div/div/div[1]/md-content/md-virtual-repeat-container/div/div[2]/div/table/tbody/tr")).click();
		Utilities.log(runner , "Click on GroupDevice line in table");
		
		Utilities.sleep(runner, 2000);
		driver.findElement(By.xpath("//*[@id='full-page-container']/div[1]/div/div/div/button[contains(@aria-label,'Delete')]")).click();
		Utilities.log(runner, "Click on Delete button");
		
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
		driver.get(Main.cs.HOST + "/projects");
		Utilities.log(runner, "Go to " + Main.cs.HOST + "/projects");
		
		Utilities.sleep(runner, 3000);
		driver.findElement(By.xpath("//*[@id='full-page-container']/div[1]/div/div/div/div/div/div/div[1]/input")).sendKeys("ayoubProjectDeepTest0");
		Utilities.log(runner, "Write ayoubProjectDeepTest0 in search input");
		
		WaitForText("//*[@id='full-page-container']/div[1]/div/div/div/div/div/div/div[2]/span", "Projects: 1 / ");
		driver.findElement(By.xpath("//*[@id='content-after-toolbar']/div/div/div[1]/md-content/md-virtual-repeat-container/div/div[2]/div/table/tbody/tr[td[contains(text(),'ayoubProjectDeepTest0')]]")).click();
		Utilities.log(runner, "Click on ayoubProjectDeepTest0 project line in table");
		
		Utilities.sleep(runner, 2000);
		driver.findElement(By.xpath("//*[@id='full-page-container']/div[1]/div/div/div/div/div/div/button[contains(@aria-label,'Manage')]")).click();
		Utilities.log(runner, "Click on Manage button");
		
		Utilities.sleep(runner, 2000);
		driver.findElement(By.xpath("//*[@id='full-page-container']/md-nav-bar/div/nav/ul/li[contains(@aria-label,'Devices')]/a")).click();
		Utilities.log(runner, "Click on Device button");
		
		Utilities.sleep(runner, 2000);
		driver.findElement(By.xpath("//*[@id='full-page-container']/ui-view/div/div/div[1]/md-card/md-card-title/div/div/div/span")).click();
		Utilities.log(runner, "Click on deviceGroup selector");
		
		driver.findElement(By.xpath("//*[@id='full-page-container']/ui-view/div/div/div[1]/md-card/md-card-title/div/div/input[1]")).sendKeys(DeviceGroupName);
		Utilities.log(runner, "Write " + DeviceGroupName + " in deviceGroup input");
		
		driver.findElement(By.xpath("//*[contains(@id,'ui-select-choices-row')]/a")).click();
		Utilities.log(runner, "Click on deviceGroup line in selector");
		
		driver.findElement(By.xpath("//*[@id='full-page-container']/ui-view/div/div/div[1]/md-card/md-card-actions/button")).click();
		Utilities.log(runner, "Click on save button");
		
		Utilities.sleep(runner, 5000);
		driver.get(Main.cs.HOST + "/projects");
		WaitForElement("//*[@id='content-after-toolbar']/div/div/div[1]/md-content/md-virtual-repeat-container/div/div[2]/div/table/tbody/tr[td[contains(text(),'ayoubProjectDeepTest0')]]/td[3]");
		if(!driver.findElement(By.xpath("//*[@id='content-after-toolbar']/div/div/div[1]/md-content/md-virtual-repeat-container/div/div[2]/div/table/tbody/tr[td[contains(text(),'ayoubProjectDeepTest0')]]/td[3]")).getText().contains(DeviceGroupName)) 
		{
			Assert.fail("Assign the DeviceGroup to the project failed");
		}
		
		
		
		
	}
	
	private void AssignDeviceToGroupDevice() 
	{
		driver.findElement(By.xpath("//*[@id='content-after-toolbar']/div/div/div[1]/md-content/md-virtual-repeat-container/div/div[2]/div/table/tbody/tr")).click();
		Utilities.log(runner, "Click on deviceGroup line in the table");
		
		Utilities.sleep(runner, 2000);
		driver.findElement(By.xpath("//*[@id='full-page-container']/div[1]/div/div/div/button[contains(@aria-label,'Manage')]")).click();
		Utilities.log(runner, "Click on Manage button");
		
		Utilities.sleep(runner, 3000);
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
		
		Utilities.sleep(runner, 1000);
		driver.findElement(By.xpath("//*[@id='content']/div/div[1]/div/div/div/div/button[contains(@aria-label,'Assign')]")).click();
		Utilities.log(runner, "Click on Assign button");
		
		Utilities.sleep(runner, 4000);
		if(!driver.findElement(By.xpath("//*[@id='content']/div/div[2]/md-virtual-repeat-container/div/div[2]/div/md-content/div/table/tbody/tr[1]/td[7]/span")).getText().contains("Yes")) 
		{
			Assert.fail("Assign device doesn't succeed");
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
			Assert.fail("Device Group doesn't create");
		}
		
	}

}
