package Administration;

import static org.junit.Assert.*;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import MyMain.Main;
import Utils.Utilities;

public class ScreenShotAction extends BaseTest{

	
	@Test
	public void test() {
		driver.get(currentThread.enums.hostName + "/testrequestspad");
		Utilities.sleep(currentThread, 6000);
		TakesScreenshot ts =  (TakesScreenshot)driver;
		File source = ts.getScreenshotAs(OutputType.FILE);		
		try
		{
			FileUtils.copyFile(source, new File(Main.logsFolder.getPath() + "/screenshot/Grid-" + currentThread.TestName));
		}		
		catch(Exception e) {}
	}

}
