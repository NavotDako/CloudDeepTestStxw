package AdministrationSuite.tests;

import AdministrationSuite.AdminBaseTest;
import MyMain.Main;
import Utils.Utilities;
import junit.framework.Assert;
import org.junit.Test;
import org.openqa.selenium.By;

import java.util.Random;

public class DHMAction extends AdminBaseTest {
	

	String DHMName = "";
	String DHMIP = "";
	@Test
	public void test() {
		
		driver.get(Main.cs.URL_ADDRESS + "/index.html#" + "/hostmachines/devicehostmachines");
		Utilities.log(runner, "Go to " + Main.cs.URL_ADDRESS + "/index.html#" + "/hostmachines/devicehostmachines");
		
		WaitForElement("//*[@id='full-page-container']/div[1]/div/div/div/div[1]/button[contains(@aria-label,'Add')]");
		CreateDHM();   
		WaitForDisappearedElement("/html/body/div[1]/div/div/div/form/div[3]/button[text()='Create']");
		WaitForElement("//*[@id='content-after-toolbar']/div/div/div[1]/div/div/md-content/div/table/tbody/tr[td[contains(text(),'" + DHMIP + "')]]");
		driver.findElement(By.xpath("//*[@id='content-after-toolbar']/div/div/div[1]/div/div/md-content/div/table/tbody/tr[td[contains(text(),'" + DHMIP + "')]]")).click();
		Utilities.log(runner, "Click on DHM line in the table");
		
		EditDHM();
		WaitForElement("//*[@id='content-after-toolbar']/div/div/div[1]/div/div/md-content/div/table/tbody/tr[td[contains(text(),'" + DHMIP + "')]]");
		Utilities.sleep(runner, 4000);
		driver.findElement(By.xpath("//*[@id='content-after-toolbar']/div/div/div[1]/div/div/md-content/div/table/tbody/tr[td[contains(text(),'" + DHMIP + "')]]")).click();
		Utilities.log(runner, "Click on DHM line in the table"); 
		
		DeleteDHM();
	}
	
	private void DeleteDHM() 
	{		
		driver.findElement(By.xpath("//*[@id='full-page-container']/div[1]/div/div/div/div/button[contains(@aria-label,'Delete')]")).click();
		Utilities.log(runner, "Click on Delete button");
		
		WaitForElement("/html/body/div[1]/div/div/div/div[3]/button[text()='Delete']");
		driver.findElement(By.xpath("/html/body/div[1]/div/div/div/div[3]/button[text()='Delete']")).click();
		Utilities.log(runner, "Click on Delete button");
		
		Utilities.sleep(runner, 7000);
		WaitForElement("//*[@id='full-page-container']/div[1]/div/div/div/div[contains(@class,'search')]/input");
		driver.findElement(By.xpath("//*[@id='full-page-container']/div[1]/div/div/div/div[contains(@class,'search')]/input")).sendKeys(DHMName);
		Utilities.log(runner, "Write " + DHMName + " in Search");
		
		if(!waitForText("//*[@id='full-page-container']/div[1]/div/div/div/div[contains(@entity,'Device Host machine')]/span", "Device Host machines: 0 /"))
		{
			Utilities.log(runner, "Delete DHM failed");
		}

		
	}
	
	private void EditDHM() 
	{
		driver.findElement(By.xpath("//*[@id='full-page-container']/div[1]/div/div/div/div/button[contains(@aria-label,'Edit')]")).click();
		Utilities.log(runner, "Click on Edit button");
		
		DHMName = "DemoHostMachine" + System.currentTimeMillis();
		WaitForElement("/html/body/div[1]/div/div/div/form/div[2]/div[label[text()='Name']]/input");
		driver.findElement(By.xpath("/html/body/div[1]/div/div/div/form/div[2]/div[label[text()='Name']]/input")).clear();
		driver.findElement(By.xpath("/html/body/div[1]/div/div/div/form/div[2]/div[label[text()='Name']]/input")).sendKeys(DHMName);
		Utilities.log(runner, "Write " + DHMName + " in Name Input");
		
		driver.findElement(By.xpath("/html/body/div[1]/div/div/div/form/div[3]/button[contains(text(),'Save')]")).click();
		Utilities.log(runner, "Click on Save button");
		
		Utilities.sleep(runner, 2000);
		if(!WaitForElement("//*[@id='content-after-toolbar']/div[1]/div/div[1]/div/div/md-content/div/table/tbody/tr[td[text()='" + DHMName + "']]")) 
		{
			Assert.fail("Edit DHM failed");
		}
	}
	
	private void CreateDHM() 
	{
		driver.findElement(By.xpath("//*[@id='full-page-container']/div[1]/div/div/div/div[1]/button[contains(@aria-label,'Add')]")).click();
		Utilities.log(runner, "Click on ADD button");
		
		WaitForElement("/html/body/div[1]/div/div/div/form/div[2]/div[1]/input[@name='name']");
		DHMName = "DemoHostMachine" + System.currentTimeMillis();
		driver.findElement(By.xpath("/html/body/div[1]/div/div/div/form/div[2]/div[1]/input[@name='name']")).sendKeys(DHMName);;
		Utilities.log(runner, "Write " + DHMName + " in Name Input");
		
		GetIP();
		driver.findElement(By.xpath("/html/body/div[1]/div/div/div/form/div[2]/div[2]/input[contains(@name,'Ip')]")).sendKeys(DHMIP);
		Utilities.log(runner, "Write DHMIP in IP Input");
		
//		driver.findElement(By.xpath("/html/body/div[1]/div/div/div/form/div[2]/div[label[contains(text(),'External IP')]]/input")).sendKeys(DHMIP);
//		Utilities.log(runner, "Write " + DHMIP + " in External IP Input");
		
		driver.findElement(By.xpath("/html/body/div[1]/div/div/div/form/div[2]/div[label[text()='Port']]/input")).sendKeys("8080");
		Utilities.log(runner, "Write 8080 in Port input");
		
//		driver.findElement(By.xpath("/html/body/div[1]/div/div/div/form/div[2]/div[label[text()='External Port (optional)']]/input")).sendKeys("8080");
//		Utilities.log(runner, "Write 8080 in External Port (optional) input");
		
		driver.findElement(By.xpath("/html/body/div[1]/div/div/div/form/div[3]/button[text()='Create']")).click();
		Utilities.log(runner, "Click on Create button");
		
		Utilities.sleep(runner, 2000);
		if(!WaitForElement("//*[@id='content-after-toolbar']/div[1]/div/div[1]/div/div/md-content/div/table/tbody/tr[td[text()='" + DHMName + "']]")) 
		{
			Assert.fail("Create DHM failed");
		}
	}
	
	private void GetIP() 
	{
		Random rand = new Random();
		DHMIP = String.valueOf(rand.nextInt(100)+ 100);
		DHMIP+=".";
		DHMIP+=String.valueOf(rand.nextInt(100)+ 100);
		DHMIP+=".";
		DHMIP+=String.valueOf(rand.nextInt(9)+ 1);
		DHMIP+=".";
		DHMIP+=String.valueOf(rand.nextInt(100)+ 100);
	}

}
