package Administration;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;
import org.openqa.selenium.By;

import Utils.Utilities;
import junit.framework.Assert;

public class DHMAction extends BaseTest {

	String DHMName = "";
	String DHMIP = "";
	@Test
	public void test() {
		
		driver.get(currentThread.enums.hostName + "/hostmachines/devicehostmachines");
		Utilities.log(currentThread, "Go to " + currentThread.enums.hostName + "/hostmachines/devicehostmachines");
		
		WaitForElement("//*[@id='full-page-container']/div[1]/div/div/div/div[1]/button[contains(@aria-label,'Add')]");
		CreateDHM() ;   
		WaitForElement("//*[@id='content-after-toolbar']/div/div/div[1]/div/div/md-content/div/table/tbody/tr[td[contains(text(),'" + DHMIP + "')]]");
		driver.findElement(By.xpath("//*[@id='content-after-toolbar']/div/div/div[1]/div/div/md-content/div/table/tbody/tr[td[contains(text(),'" + DHMIP + "')]]")).click();
		Utilities.log(currentThread, "Click on DHM line in the table");
		
		EditDHM();
		WaitForElement("//*[@id='content-after-toolbar']/div/div/div[1]/div/div/md-content/div/table/tbody/tr[td[contains(text(),'" + DHMIP + "')]]");
		Utilities.sleep(currentThread, 4000);
		driver.findElement(By.xpath("//*[@id='content-after-toolbar']/div/div/div[1]/div/div/md-content/div/table/tbody/tr[td[contains(text(),'" + DHMIP + "')]]")).click();
		Utilities.log(currentThread, "Click on DHM line in the table"); 
		
		DeleteDHM();
	}
	
	private void DeleteDHM() 
	{		
		driver.findElement(By.xpath("//*[@id='full-page-container']/div[1]/div/div/div/div/button[contains(@aria-label,'Delete')]")).click();
		Utilities.log(currentThread, "Click on Delete button");
		
		WaitForElement("/html/body/div[1]/div/div/div/div[3]/button[text()='Delete']");
		driver.findElement(By.xpath("/html/body/div[1]/div/div/div/div[3]/button[text()='Delete']")).click();
		Utilities.log(currentThread, "Click on Delete button");
		
		Utilities.sleep(currentThread, 7000);
		WaitForElement("//*[@id='full-page-container']/div[1]/div/div/div/div[contains(@class,'search')]/input");
		driver.findElement(By.xpath("//*[@id='full-page-container']/div[1]/div/div/div/div[contains(@class,'search')]/input")).sendKeys(DHMName);
		Utilities.log(currentThread, "Write " + DHMName + " in Search");
		
		if(!WaitForText("//*[@id='full-page-container']/div[1]/div/div/div/div[contains(@entity,'Device Host machine')]/span", "Device Host machines: 0 /")) 
		{
			Utilities.log(currentThread, "Delete DHM failed");
		}

		
	}
	
	private void EditDHM() 
	{
		driver.findElement(By.xpath("//*[@id='full-page-container']/div[1]/div/div/div/div/button[contains(@aria-label,'Edit')]")).click();
		Utilities.log(currentThread, "Click on Edit button");
		
		DHMName = "DemoHostMachine" + System.currentTimeMillis();
		WaitForElement("/html/body/div[1]/div/div/div/form/div[2]/div[label[text()='Name']]/input");
		driver.findElement(By.xpath("/html/body/div[1]/div/div/div/form/div[2]/div[label[text()='Name']]/input")).clear();
		driver.findElement(By.xpath("/html/body/div[1]/div/div/div/form/div[2]/div[label[text()='Name']]/input")).sendKeys(DHMName);
		Utilities.log(currentThread, "Write " + DHMName + " in Name Input");
		
		driver.findElement(By.xpath("/html/body/div[1]/div/div/div/form/div[3]/button[contains(text(),'Save')]")).click();
		Utilities.log(currentThread, "Click on Save button");
		
		if(!WaitForElement("//*[@id='content-after-toolbar']/div[1]/div/div[1]/div/div/md-content/div/table/tbody/tr[td[text()='" + DHMName + "']]")) 
		{
			Assert.fail("Edit DHM failed");
		}
	}
	
	private void CreateDHM() 
	{
		driver.findElement(By.xpath("//*[@id='full-page-container']/div[1]/div/div/div/div[1]/button[contains(@aria-label,'Add')]")).click();
		Utilities.log(currentThread, "Click on ADD button");
		
		WaitForElement("/html/body/div[1]/div/div/div/form/div[2]/div[1]/input[@name='name']");
		DHMName = "DemoHostMachine" + System.currentTimeMillis();
		driver.findElement(By.xpath("/html/body/div[1]/div/div/div/form/div[2]/div[1]/input[@name='name']")).sendKeys(DHMName);;
		Utilities.log(currentThread, "Write " + DHMName + " in Name Input");
		
		GetIP();
		driver.findElement(By.xpath("/html/body/div[1]/div/div/div/form/div[2]/div[2]/input[contains(@name,'Ip')]")).sendKeys(DHMIP);
		Utilities.log(currentThread, "Write DHMIP in IP Input");
		
		driver.findElement(By.xpath("/html/body/div[1]/div/div/div/form/div[2]/div[label[contains(text(),'External IP')]]/input")).sendKeys(DHMIP);
		Utilities.log(currentThread, "Write " + DHMIP + " in External IP Input");
		
		driver.findElement(By.xpath("/html/body/div[1]/div/div/div/form/div[2]/div[label[text()='Port']]/input")).sendKeys("8080");
		Utilities.log(currentThread, "Write 8080 in Port input");
		
		driver.findElement(By.xpath("/html/body/div[1]/div/div/div/form/div[2]/div[label[text()='External Port (optional)']]/input")).sendKeys("8080");
		Utilities.log(currentThread, "Write 8080 in External Port (optional) input");
		
		driver.findElement(By.xpath("/html/body/div[1]/div/div/div/form/div[3]/button[text()='Create']")).click();
		Utilities.log(currentThread, "Click on Create button");
		
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
