package Administration.tests;

import java.util.Random;

import Administration.AdminBaseTest;
import org.junit.Test;
import org.openqa.selenium.By;

import Utils.Utilities;
import junit.framework.Assert;



public class UserAction extends AdminBaseTest {

	String userName="";
	String userType="";
	
	@Test
	public void test() {
		Utilities.log(currentThread, "enter UserTest class");
//		Utilities.sleep(runner, 2000);
//		driver.findElement(By.xpath("//*[@id='side-menu']/li/a[span[contains(text(),'More')]]")).click();
//		Utilities.sleep(runner, 2000);
		driver.get(currentThread.enums.hostName + "/users");
		Utilities.sleep(currentThread, 10000);
		CreateUser();		
		if(!WaitForElement("/html/body/div[1]/div/div/div/div[1]")) 
		{
			Assert.fail("Initial password window does't found");
		}
		String initialPassword = driver.findElement(By.xpath("/html/body/div[1]/div/div/div/div[2]/p[1]/b")).getText();
		Utilities.log(currentThread, "The Initial Password is :" + initialPassword);
		Utilities.sleep(currentThread, 3000);
		driver.findElement(By.xpath("/html/body/div[1]/div/div/div/div[3]/button")).click();
		Utilities.log(currentThread, "Click on ok");
		
		Utilities.sleep(currentThread, 12000);
		driver.findElement(By.xpath("//*[@id='page-wrapper']/div[1]/nav/ul[2]/md-menu/button")).click();
		Utilities.log(currentThread, "Click on logout Window");
		Utilities.sleep(currentThread, 2000);
		driver.findElement(By.xpath("//*[@id='logout-button']/button")).click();
		Utilities.log(currentThread, "Click on Logout");
		NewLogin(userName, initialPassword);		
		ChangePassword(initialPassword);
		Utilities.sleep(currentThread, 5000);
		if(!WaitForElement("//*[@id='nav-bar-current-user']")) 
		{
			Assert.fail("the adminstration doesn't succeed");
		}
		if(!driver.findElement(By.xpath("//*[@id='nav-bar-current-user']")).getText().equals(userName)) 
		{
			Assert.fail("The UserName for current user does't equal to " + userName);
		}
		driver.findElement(By.xpath("//*[@id='page-wrapper']/div[1]/nav/ul[2]/md-menu/button")).click();
		Utilities.log(currentThread, "Click on logout window");
		Utilities.sleep(currentThread, 2000);
		driver.findElement(By.xpath("//*[@id='logout-button']/button")).click();
		Utilities.log(currentThread, "Click on logout button");
		
		Utilities.sleep(currentThread, 3000);
		NewLogin("ayouba", "Experitest2012");
		driver.get(currentThread.enums.hostName + "/projects");
		Utilities.sleep(currentThread, 3000);
		if(!userName.contains("Admin")) 
		{
			AssignUser();
		}
		Utilities.sleep(currentThread, 10000);
		DeleteUser();
	}
	
	
	private void DeleteUser() 
	{
		driver.get(currentThread.enums.hostName + "/users");
		Utilities.log(currentThread, "Go to " + currentThread.enums.hostName + "/users");
		driver.findElement(By.xpath("//*[@id='full-page-container']/div[1]/div/div/div/div[2]/input")).click();
		Utilities.log(currentThread, "Click on Search button");
		
		driver.findElement(By.xpath("//*[@id='full-page-container']/div[1]/div/div/div/div[2]/input")).sendKeys(userName);
		Utilities.log(currentThread, "Write " + userName + " in search Button");
		Utilities.sleep(currentThread, 2000);
		
		WaitForText("//*[@id='full-page-container']/div[1]/div/div/div/div[3]/span", "Users: 1 /");
		driver.findElement(By.xpath("//*[@id='content-after-toolbar']/div/div/div[1]/md-content/md-virtual-repeat-container/div/div[2]/div/table/tbody/tr")).click();
		Utilities.log(currentThread, "Click on " + userName + " line in table");
		
		driver.findElement(By.xpath("//*[@id='full-page-container']/div[1]/div/div/div/button[span[contains(text(),'Delete')]]")).click();
		Utilities.log(currentThread, "Click on Delete button");
		
		WaitForElement("/html/body/div[1]/div/div/div");
		driver.findElement(By.xpath("/html/body/div[1]/div/div/div/div[3]/button[2]")).click();
		Utilities.log(currentThread, "Click on Delete button");
		
		Utilities.sleep(currentThread, 5000);
		if(!driver.findElement(By.xpath("//*[@id='full-page-container']/div[1]/div/div/div/div[3]/span")).getText().contains("Users: 0 /"))
		{
			Assert.fail("The User doesn't deleted ");
		}
		
		
	}	
	
	private void AssignUser() 
	{
		driver.findElement(By.xpath("//*[@id='full-page-container']/div[1]/div/div/div/div/div/div/div[1]/input")).sendKeys("ayoubProjectDeepTest1");
		if(!WaitForText("//*[@id='full-page-container']/div[1]/div/div/div/div/div/div/div[2]/span", "Projects: 1 / ")) 
		{
			Assert.fail("The Project doesn't found");
		}
		driver.findElement(By.xpath("//*[@id='content-after-toolbar']/div/div/div[1]/md-content/md-virtual-repeat-container/div/div[2]/div/table/tbody/tr")).click();
		Utilities.log(currentThread, "Click on ayoubProjectDeepTest1 Project");
		
		driver.findElement(By.xpath("//*[@id='full-page-container']/div[1]/div/div/div/div/div/div/button[contains(@aria-label,'Manage')]")).click();
		Utilities.log(currentThread, "Click on Manage");
		
		Utilities.sleep(currentThread, 2000);
		driver.findElement(By.xpath("//*[@id='full-page-container']/ui-view/div/div[1]/div/div/div/div/div/input")).click();
		Utilities.sleep(currentThread, 2000);
		
		driver.findElement(By.xpath("//*[@id='full-page-container']/ui-view/div/div[1]/div/div/div/div/div/input")).sendKeys(userName);
		Utilities.log(currentThread, "Write " + userName + " in Search TextVeiw");
		Utilities.sleep(currentThread, 2000);
		
		driver.findElement(By.xpath("//*[@id='full-page-container']/ui-view/div/div[2]/md-virtual-repeat-container/div/div[2]/div/md-content/div/table/tbody/tr")).click();
		Utilities.log(currentThread, "Click on " + userName + "'s line in table");
		
		WaitForElement("//*[@id='full-page-container']/ui-view/div/div[1]/div/div/div/div/button");
		driver.findElement(By.xpath("//*[@id='full-page-container']/ui-view/div/div[1]/div/div/div/div/button")).click();
		Utilities.log(currentThread, "Click on Assign button");
		
		WaitForElement("/html/body/div[1]/div/div/div/form");
		driver.findElement(By.xpath("/html/body/div[1]/div/div/div/form/div[2]/div[1]/select")).click();
		Utilities.log(currentThread, "Click on Role selector");
		
		driver.findElement(By.xpath("/html/body/div[1]/div/div/div/form/div[2]/div[1]/select/option[contains(text(),'User')]")).click();
		Utilities.log(currentThread, "Chose User Role");
		
		driver.findElement(By.xpath("/html/body/div[1]/div/div/div/form/div[3]/button[contains(text(),'Assign')]")).click();
		Utilities.log(currentThread, "Click on Assign button");
		
		Utilities.sleep(currentThread, 5000);
		if(!driver.findElement(By.xpath("//*[@id='full-page-container']/ui-view/div/div[2]/md-virtual-repeat-container/div/div[2]/div/md-content/div/table/tbody/tr/td[9]/span")).getText().contains("Yes"))
		{
			Assert.fail("The user doesn't added to the project");
		}
	} 
	
	private void CreateUser() 
	{
		
		Random rand = new Random();		
		switch(rand.nextInt(3))
		{
		case 0:
			userName = "DemoAdmin" + System.currentTimeMillis();
			userType = "Admin";
			break;
		case 1:
			userName = "DemoUser" + System.currentTimeMillis();
			userType = "User";
			break;
		case 2:
			userName = "DemoProjectAdm" + System.currentTimeMillis();
			userType = "ProjectAdm";
			break;
		}
		
		driver.findElement(By.xpath("//*[@id='full-page-container']/div[1]/div/div/div/button[contains(@aria-label,'Create')]")).click();
		Utilities.log(currentThread, "Click on Create Button");
		
		WaitForElement("/html/body/div[1]/div/div/div/form");
		
		
		driver.findElement(By.xpath("/html/body/div[1]/div/div/div/form/div[2]/div[label[contains(text(),'Username')]]/input")).sendKeys(userName);
		Utilities.log(currentThread, "Write " + userName + " in Username TextVeiw");
		
		driver.findElement(By.xpath("/html/body/div[1]/div/div/div/form/div[2]/div[label[contains(text(),'First Name')]]/input")).sendKeys(userName);
		Utilities.log(currentThread, "Write " + userName + " in First Name TextVeiw");
		
		driver.findElement(By.xpath("/html/body/div[1]/div/div/div/form/div[2]/div[label[contains(text(),'Last Name')]]/input")).sendKeys(userName);
		Utilities.log(currentThread, "Write " + userName + " in Last Name TextVeiw");
		
		driver.findElement(By.xpath("/html/body/div[1]/div/div/div/form/div[2]/div[label[contains(text(),'Email')]]/input")).sendKeys("ayoub.abuliel@experitest.com");
		Utilities.log(currentThread,"Write ayoub.abuliel@experitest.com in Email TextVeiw");
		
		driver.findElement(By.xpath("/html/body/div[1]/div/div/div/form/div[2]/fieldset/div[label[contains(text(),'Role')]]/select")).click();
		Utilities.sleep(currentThread, 2000);
		
		 switch (userType) {
         case "Admin":
             driver.findElement(By.xpath("/html/body/div[1]/div/div/div/form/div[2]/fieldset/div[label[contains(text(),'Role')]]/select/option[contains(text(),'Cloud Administrator')]")).click();
             Utilities.log(currentThread,"Chose Cloud Administrator");
             break;
         case "ProjectAdm": 
        	 driver.findElement(By.xpath("/html/body/div[1]/div/div/div/form/div[2]/fieldset/div[label[contains(text(),'Role')]]/select/option[contains(text(),'Project Administrator')]")).click();
        	 Utilities.log(currentThread,"Chose Project Administrator");
             break;
         case "User":
        	 driver.findElement(By.xpath("/html/body/div[1]/div/div/div/form/div[2]/fieldset/div[label[contains(text(),'Role')]]/select/option[contains(text(),'User')]")).click();
        	 Utilities.log(currentThread,"Chose User");
             break;
         default:            
             break;                          
     }
		 driver.findElement(By.xpath("/html/body/div[1]/div/div/div/form/div[3]/button[contains(@name,'create')]")).click();
		 Utilities.log(currentThread, "Click on create button");
		 		
	}

	private void NewLogin(String userName, String Password) 
	{
		driver.findElement(By.xpath("/html/body/div[2]/div[2]/div/form/div[1]/div[1]/input")).clear();
		driver.findElement(By.xpath("/html/body/div[2]/div[2]/div/form/div[1]/div[1]/input")).sendKeys(userName);
		Utilities.log(currentThread, "Write " + userName + " in User Name TextVeiw");
		
		driver.findElement(By.xpath("/html/body/div[2]/div[2]/div/form/div[2]/div[1]/input")).clear();
		driver.findElement(By.xpath("/html/body/div[2]/div[2]/div/form/div[2]/div[1]/input")).sendKeys(Password);
		Utilities.log(currentThread, "Write " + Password + " in password TextVeiw");
		
		driver.findElement(By.xpath("/html/body/div[2]/div[2]/div/form/button")).click();
		Utilities.sleep(currentThread, 2000);
	}
	
	private void ChangePassword(String initialPassword) 
	{
		driver.findElement(By.xpath("/html/body/div[2]/div/div/form/div[1]/input")).sendKeys(initialPassword);
		Utilities.log(currentThread, "Write " + initialPassword + " in Old Password TextVeiw");
		
		driver.findElement(By.xpath("/html/body/div[2]/div/div/form/div[2]/input")).sendKeys("Experitest2012");
		Utilities.log(currentThread, "Write Experirtest2012 in New Password TextVeiw");
		
		driver.findElement(By.xpath("/html/body/div[2]/div/div/form/div[3]/input")).sendKeys("Experitest2012");
		Utilities.log(currentThread, "Write Experitest2012 in Confirm Password TextVeiw");
		
		driver.findElement(By.xpath("/html/body/div[2]/div/div/form/div[4]/button[text()='Change Password']")).click();
		Utilities.log(currentThread, "Click on Change Password Button");		
	}
}
