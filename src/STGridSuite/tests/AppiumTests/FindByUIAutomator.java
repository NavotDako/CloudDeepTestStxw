package STGridSuite.tests.AppiumTests;

import Framework.IdentificationMethods;
import MyMain.Enums;
import MyMain.Main;
import Utils.Utilities;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

import static Framework.IdentificationMethods.appSource.CLOUD;
import static Framework.IdentificationMethods.appSource.PATH;

/**
 * this test tests android's find by uiautomator, as well as appium page factory mechanism
 */
public class FindByUIAutomator extends AppiumGridBaseTest{


//    @AndroidFindBy(uiAutomator = "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"Argentina\"));")
//    public MobileElement argentinaClick;
    // list of devices that this test fails in due to different behavior in EriBank non inst

    public void updateDevicesToAvoid(){
        Main.cs.devicesToAvoid.put(FindByUIAutomator.class, new ArrayList());
        Main.cs.devicesToAvoid.get(FindByUIAutomator.class).add("015d28568f541e0f");
        Main.cs.devicesToAvoid.get(FindByUIAutomator.class).add("016fd8f1dd4f22bd");
        Main.cs.devicesToAvoid.get(FindByUIAutomator.class).add("81HEBL222ND4");
    }
    @Override
    public void createCapabilities() {
        IdentificationMethods.appSource app = (Math.random() > Math.random()) ? CLOUD : PATH;
        String application = "";
//        if(app.equals(PATH)) {
//            application = "http://192.168.2.72:8181/AndroidApps/eribank.apk";
//            Utilities.log(runner, "working with app from path");
//        }
//        else{
//            application = "cloud:com.experitest.eribank/com.experitest.ExperiBank.LoginActivity";
//            Utilities.log(runner, "working with app from server");
//        }
            application = "cloud:com.experitest.eribank/com.experitest.ExperiBank.LoginActivity";
            Utilities.log(runner, "working with app from server");
        dc = new DesiredCapabilities();
        dc.setCapability(MobileCapabilityType.APP, application);
//        dc.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 60);
        dc.setCapability("instrumentApp", false);
        //This feature will work only with new uiautomator - added a predicate to make sure the version is > 4
        dc.setCapability("accessKey", Enums.getAccessKey(user));
    }
    @Override
    public void updateDevice(){
        updateDevicesToAvoid();
        device = Main.cs.getDevice(deviceOS, t -> (!Main.cs.devicesToAvoid.get(FindByUIAutomator.class).contains(t) &&
                !((String)Main.cs.devicesMap.get(t, IdentificationMethods.devicesMapTranslator.VERSION)).startsWith("4")));
    }
    @Override
    public void test() {
        System.out.println(driver.getCapabilities().getCapability("device.name"));
        try{
            ((AndroidDriver)driver).findElementsByAndroidUIAutomator("new UiSelector().textContains(\"Selddect\")");
        }catch (Exception e){
            System.out.println("got expected exception");
        }
        login();
        makePayment();
        selectCountry();
        testPassed = true;
    }
    private void selectCountry() {
        PageFactory.initElements(new AppiumFieldDecorator((AndroidDriver)driver), this);
        ((AndroidDriver) driver).findElementByAndroidUIAutomator("new UiSelector().textContains(\"Select\")").click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ((AndroidDriver) driver).findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\"Argentina\"));").click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElementByXPath("//*[@resource-id='com.experitest.eribank:id/countryTextField' and @text='Argentina']");
    }

    private void makePayment() {
        new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@text='Make Payment']")));
        List<WebElement> elems = ((AndroidDriver)driver).findElementsByAndroidUIAutomator("new UiSelector().clickable(true).instance(0)");
        elems.get(0).click();
        List<AndroidElement> elems2 = ((AndroidDriver)driver).findElementsByAndroidUIAutomator("new UiSelector()" +
                ".resourceId(\"com.experitest.eribank:id/makePaymentSubView\")");
        List<MobileElement> elems3 = elems2.get(0).findElementsByAndroidUIAutomator("new UiSelector().className(\"android.widget.EditText\")");
        elems3.get(0).sendKeys("000");
        elems3.get(1).sendKeys("khaled");
        elems3.get(2).sendKeys("11");
    }

    private void login() {
        List<WebElement> elems = ((AndroidDriver)driver).findElementsByAndroidUIAutomator("new UiSelector().className(\"android.widget.EditText\")");
        elems.get(0).sendKeys("company");
        elems.get(1).sendKeys("company");
        ((AndroidDriver)driver).findElementByAndroidUIAutomator("new UiSelector().textContains(\"Login\")").click();
    }
}
