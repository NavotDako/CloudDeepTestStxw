package AdministrationSuite;


import AdministrationSuite.AdminRunner;
import MyMain.BaseBaseTest;
import MyMain.Main;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;


import Utils.Utilities;

public abstract class AdminBaseTest extends BaseBaseTest {


    @Before
    public void setUp() throws Exception {
        runner = (AdminRunner) Thread.currentThread();
        Utilities.log(runner,"-----------------------------" + runner.getName() + " Starting A New Test!-----------------------------");
            driver = createDriver();
            LoginInToCloud();
    }


    @Test
    abstract public void test();

    @After
    public void finish() {
        Utilities.log(runner, "Finishing");
        Utilities.log(runner, "Quit driver");
        driver.quit();
        

    }


    private void LoginInToCloud() {
    	
        driver.get(Main.cs.URL_ADDRESS + "/index.html#" + "/login");
        Utilities.log(runner, "go to " + Main.cs.URL_ADDRESS + "/index.html#" + "/login");
        driver.findElement(By.name("username")).sendKeys("ayouba");
        Utilities.log(runner, "Write username (ayouba)");

        driver.findElement(By.name("password")).sendKeys("Experitest2012");
        Utilities.log(runner, "write the password ");
        
        Utilities.sleep(runner, 2000);
        driver.findElement(By.name("login")).click();
        Utilities.log(runner, "click on login");
        
        Utilities.sleep(runner, 2000);

    }
    
    protected boolean waitUntilElementMarked(String markedXPath) 
    {
    	int count = 0;
    	boolean needToWait = true;
    	while( needToWait && count<100)  
    	{    		
    		try
    		{
    			if(driver.findElement(By.xpath(markedXPath)).getAttribute("class").contains("st-selected")) 
    			{
    				needToWait = false;
    			}
    			else 
    			{
    				driver.findElement(By.xpath(markedXPath)).click();
    			}
    		}catch(Exception e) {}
    		Utilities.sleep(runner, 500);
    	}
    	return !needToWait;    	
    }
    
    protected boolean waitForEnableButton(String markedXPath, String buttonXPath, String markedXPathName) 
    {
		int count = 0;
		boolean needToWait = true;
		while( needToWait && count<20) 
		{
			try 
			{
				if(driver.findElement(By.xpath(buttonXPath)).getAttribute("disabled").contains("true") || driver.findElement(By.xpath(buttonXPath)).getAttribute("disabled").contains("disabled") ) 
				{					
					driver.findElement(By.xpath(markedXPath)).click();
					Utilities.log(runner, "Click on " + markedXPathName);
				}
				else 
				{					
					needToWait = false;
				}

			}
			catch(Exception e)
			{				
				try 
				{
					if(driver.findElement(By.xpath(markedXPath)).getAttribute("class").contains("st-selected")) 
					{
						needToWait = false;
						Utilities.sleep(runner, 800);
					}
				}
				catch(Exception e1) 
				{
					
				}
			}
			count++;
			
		}
		return !needToWait;
    }





}
