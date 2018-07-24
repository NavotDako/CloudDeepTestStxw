package STGridSuite.tests.AppiumTests;

import MyMain.Enums;
import Utils.Utilities;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class IOSAppiumUICatalogInst extends AppiumGridBaseTest{

    @Override
    public void createCapabilities() {
        dc = new DesiredCapabilities();
        dc.setCapability("app", "http://192.168.2.72:8181/AndroidApps/UICatalog11-2.apk");
        dc.setCapability("appActivity", ".MainActivity");
        dc.setCapability("instrumentApp", true);
        dc.setCapability("os", this.deviceOS);
        dc.setCapability("accessKey", Enums.getAccessKey(user));
        dc.setCapability(MobileCapabilityType.NO_RESET, false);
    }

    @Override
    public void test() {
        driver.findElement(By.xpath("//*[@accessibilityLabel='Web']")).click();
        driver.context("WEBVIEW_1");
        try {
            new WebDriverWait(driver, 20).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='ac-gn-firstfocus-small' or @id='ac-gn-firstfocus']")));
            driver.findElement(By.xpath("//*[@text='iPhone X']")).click();
        }catch (Exception e) {
            Utilities.log(runner, "Seems like a network issue - retrying");
            driver.context("NATIVE_APP_INSTRUMENTED");
            driver.findElement(By.xpath("//*[@text='Back']")).click();
            driver.context("WEBVIEW_1");
            new WebDriverWait(driver, 20).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='ac-gn-firstfocus-small' or @id='ac-gn-firstfocus']")));
            driver.findElement(By.xpath("//*[@text='iPhone X']")).click();
        }
        driver.context("NATIVE_APP_INSTRUMENTED");
        driver.findElement(By.xpath("//*[@text='Back']")).click();

        driver.findElement(By.xpath("//*[@accessibilityLabel='Buttons']")).click();
        driver.findElement(By.xpath("//*[@text='Gray']"));
        driver.findElement(By.xpath("//*[@text='Back']")).click();

        driver.findElement(By.xpath("//*[@text='Controls']")).click();
        driver.findElement(By.xpath("xpath=//*[@text='Customized Slider']"));
        driver.findElement(By.xpath("//*[@text='Back']")).click();

        driver.findElement(By.xpath("//*[@accessibilityLabel='Pickers']")).click();
        driver.findElement(By.xpath("//*[@class='UIDatePicker']"));
        Assert.assertEquals(driver.findElement(By.xpath("xpath=//*[@accessibilityLabel='John Appleseed']")).getText(), "John Appleseed");
        driver.findElement(By.xpath("//*[@text='Back']")).click();
    }
}
