package Administration;

import java.net.MalformedURLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import STXWActionTests.STXWRunner;
import Utils.Utilities;

public abstract class AdminBaseTest {

	protected AdminRunner currentThread = (AdminRunner) Thread.currentThread();
	protected WebDriver driver;
	private boolean needToQuitDriverOnFinish = false;
	

	@Before
	public void setUp()throws Exception{
		
		System.out.println("-----------------------------" + currentThread.getName() + " Starting A New Test!-----------------------------");
		try {
			driver = createDriver();
			needToQuitDriverOnFinish = true;
            LoginInToCloud();

		} catch (Exception e)
		  {
	            Utilities.log(currentThread, e);
	            Utilities.log(currentThread, "SETUP FOR - " + Thread.currentThread().getName() + " HAS FAILED!!!");
	            throw e;
	      }
		
	}
	
	

	
	@Test
	abstract public void test(); 
		
	@After
    public void finish() {
		Utilities.log(currentThread, "finish");
//		driver.quit();
//        Utilities.log(runner, "driver.quit");
	}
	
	
	
	 private WebDriver createDriver() throws MalformedURLException {

	        Utilities.log(currentThread, "connect to selenium hub");

	        Utilities.log(currentThread, "choose chrome capabilities");

	        DesiredCapabilities dc = new DesiredCapabilities().chrome();
	        dc.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
	        ChromeOptions chromeOption = new ChromeOptions();
	        chromeOption.addArguments("--start-maximized");
	        dc.setCapability(ChromeOptions.CAPABILITY, chromeOption);

	        return new ChromeDriver(dc);
	    }
	 
	    private void LoginInToCloud() {
	    	
	        driver.get(currentThread.enums.hostName + "/index.html#/login");
	        Utilities.log(currentThread, "go to " + currentThread.enums.hostName + "/index.html#/login");
	        driver.findElement(By.name("username")).sendKeys("ayouba");
	        Utilities.log(currentThread, "Write username (ayouba)");

	        driver.findElement(By.name("password")).sendKeys("Experitest2012");
	        Utilities.log(currentThread, "write the password ");

	        driver.findElement(By.name("login")).click();
	        Utilities.log(currentThread, "click on login");

	    }
	    public boolean WaitForElement(String xPath) {

	        boolean needToWaitToElement = true;
	        long startWaitTime = System.currentTimeMillis();

	        while (needToWaitToElement && (System.currentTimeMillis() - startWaitTime) < 60000) {
	            try {
	                driver.findElement(By.xpath(xPath));
	                needToWaitToElement = false;
	            } catch (Exception e) {
	                Utilities.log(currentThread, "waiting for Element - " + xPath);
	                Utilities.sleep(currentThread, 1000);
	            }

	        }
	        return !needToWaitToElement;

	    }
	    
	    public boolean WaitForText(String xPath, String Text) 
	    {
	    	   boolean needToWaitToText = true;
		        long startWaitTime = System.currentTimeMillis();

		        while (needToWaitToText && (System.currentTimeMillis() - startWaitTime) < 60000) {
		            try {
		                if(driver.findElement(By.xpath(xPath)).getText().contains(Text))
		                needToWaitToText = false;
		            } catch (Exception e) {
		                Utilities.log(currentThread, "waiting for Text - " + Text);
		                Utilities.sleep(currentThread, 1000);
		            }

		        }
		        return !needToWaitToText;
	    }
	    
	    private String watchedLog;
	    
	    @Rule
	    public TestWatcher watchman = new TestWatcher() {
	        @Override
	        protected void failed(Throwable e, Description description) {
	            watchedLog += description + "\n";
	            Utilities.log(currentThread, Thread.currentThread().getName() + " FAILED !!!" + watchedLog);
	            Utilities.log(currentThread, (Exception) e);
	            Utilities.log(currentThread, "TEST HAS FAILED!!!");
	        }

	        @Override
	        protected void succeeded(Description description) {
	            watchedLog += description + " " + "success!\n";
	            Utilities.log(currentThread, Thread.currentThread().getName() + " PASSED!!!" + watchedLog);
	            Utilities.log(currentThread, "TEST HAS PASSED!!!");
	        }
	    };

}
