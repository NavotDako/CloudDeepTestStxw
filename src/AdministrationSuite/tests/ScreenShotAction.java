package AdministrationSuite.tests;

import static org.junit.Assert.*;

import java.io.File;

import AdministrationSuite.AdminBaseTest;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import MyMain.Main;
import Utils.Utilities;

public class ScreenShotAction extends AdminBaseTest {

	
	@Test
	public void test() {
		driver.get(runner.enums.hostName + "/testrequestspad");
		Utilities.sleep(runner, 6000);
		TakesScreenshot ts =  (TakesScreenshot)driver;
		File source = ts.getScreenshotAs(OutputType.FILE);		
		try
		{
			FileUtils.copyFile(source, new File(Main.logsFolder.getPath() + "/screenshot/Grid-" + runner.testName));
		}		
		catch(Exception e) {}
	}

}
