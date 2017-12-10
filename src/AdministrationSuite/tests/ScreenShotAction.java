package AdministrationSuite.tests;

import AdministrationSuite.AdminBaseTest;
import MyMain.Main;
import Utils.Utilities;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;

public class ScreenShotAction extends AdminBaseTest {

	
	@Test
	public void test() {
		driver.get(Main.cs.getServerHostName() + "/testrequestspad");
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
