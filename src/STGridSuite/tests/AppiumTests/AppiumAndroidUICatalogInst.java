package STGridSuite.tests.AppiumTests;

import MyMain.Enums;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AppiumAndroidUICatalogInst  extends AppiumGridBaseTest{

    @Override
    public void createCapabilities() {
        dc = new DesiredCapabilities();
//        dc.setCapability("app", "http://192.168.2.72:8181/AndroidApps/UICatalog11-2.apk");
        dc.setCapability("app","cloud:com.experitest.uicatalog/.MainActivity");
        dc.setCapability("appActivity", ".MainActivity");
        dc.setCapability("instrumentApp", true);
        dc.setCapability("os", this.deviceOS);
        dc.setCapability("accessKey", Enums.getAccessKey(user));
        dc.setCapability(MobileCapabilityType.NO_RESET, false);
    }

    @Override
    public void test() {
        runAndroidTest();
        testPassed = true;
    }

    public void runAndroidTest() {
        boolean flag = false;
        long loopStartTime = System.currentTimeMillis();
        while (!flag) {
            try {
                driver.findElement(By.xpath("xpath=//*[@text='Css WebView' and @top='true']"));
                flag = true;
            } catch (Exception e) {
                driver.swipe(500, 1000, 500, 200, 1000);

                if (System.currentTimeMillis() > (loopStartTime + 30000)) {
                    flag = true;
                }
            }
        }
        driver.findElement(By.xpath("xpath=//*[@text='Css WebView' and @top='true']")).click();
        driver.context("WEBVIEW_1");
        driver.findElement(By.id("usr")).click();
        driver.executeScript("client:client.sendText(\"a\")\n");
        driver.findElement(By.id("pswd")).sendKeys("a");
        driver.findElement(By.xpath("xpath=//*[@text='Login']")).click();

        driver.context("NATIVE_APP_INSTRUMENTED");
        driver.findElement(By.xpath("//*[@text='OK']")).click();
        driver.context("WEBVIEW_1");
        try {
            new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath("xpath=//*[@id='5']")));
            driver.findElement(By.xpath("xpath=//*[@id='5']"));
        }catch(Exception e){
            driver.getPageSource();
        }
    }
}
