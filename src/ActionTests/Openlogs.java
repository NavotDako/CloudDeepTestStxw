package ActionTests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

public class Openlogs extends BaseTest {

	@Before
	public void SetUp() 
	{
		super.SetUp();
	}
	@Test
	public void test() {
		
		try {
			
				System.out.println("openlogsssss");
				log("Enter to Open Logs Test");
				
				driver.findElement(By.xpath("//*[( contains(@id,'accordiongroup-') and contains(@id,'-panel'))]/div/md-list/md-list-item/div/button[contains(@aria-label,'Logs')]")).click();
				log("click on open log Button ");
				
				try{Thread.sleep(2000);
				log("wait 2 seconds");}catch(Exception e) {writeFailedLineInLog("failed to wait 2 seconds " + e);}
				
				driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/md-toolbar"));
				log("test if the logs open");
				
				try{Thread.sleep(5000);
				log("wait 5 seconds");}catch(Exception e) {writeFailedLineInLog("failed to wait 5 seconds " + e);}
				try
				{
					if(!driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/div[5]/div/div/log-panel/md-content/code/text()")).getText().equals("")) 
					
					{
						log("the logs after 5 seconds" + driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/div[5]/div/div/log-panel/md-content/code/text()")).getText());
					}
					else
					{	
						writeFailedLineInLog("the log is empty ");
						passed = "failed";
					}
				}
				catch(Exception e)
					{
						System.out.println(e);
					}
			}
		catch(Exception e)
		{
			log(e.toString());
		}
		
	}
	@After
	public void finsh() 
	{
		super.finish();
	}

}
// /html/body/div[2]/div/div[2]/div/div/div[5]/div/div/log-panel/md-content/code