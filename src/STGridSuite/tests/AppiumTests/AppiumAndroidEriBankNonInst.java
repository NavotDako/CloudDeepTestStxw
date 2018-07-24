package STGridSuite.tests.AppiumTests;

import Applications.EriBankAndroid;
import Framework.IdentificationMethods;
import MyMain.Enums;
import STGridSuite.STGridRunner;
import Utils.Utilities;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.Assert;
import org.openqa.selenium.remote.DesiredCapabilities;

import static Framework.IdentificationMethods.appSource.CLOUD;
import static Framework.IdentificationMethods.appSource.PATH;

public class AppiumAndroidEriBankNonInst extends AppiumGridBaseTest{
    EriBankAndroid appClass;

    @Override
    public void createCapabilities() {
        IdentificationMethods.appSource app = (Math.random() > Math.random()) ? CLOUD : PATH;
        String application = "";
        if(app.equals(PATH)) {
            application = "http://192.168.2.72:8181/AndroidApps/eribank.apk";
            Utilities.log(runner, "working with app from path");
        }
        else{
            application = "cloud:com.experitest.eribank/com.experitest.ExperiBank.LoginActivity";
            Utilities.log(runner, "working with app from server");
        }
        application = "cloud:com.experitest.eribank/com.experitest.ExperiBank.LoginActivity";

        dc = new DesiredCapabilities();
        dc.setCapability(MobileCapabilityType.APP, application);
//        dc.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 60);
        dc.setCapability("instrumentApp", false);
        dc.setCapability("os", this.deviceOS);
        dc.setCapability("accessKey", Enums.getAccessKey(user));
    }

    @Override
    public void test() {
        try {
            appClass = new EriBankAndroid((STGridRunner)runner, null, driver);
            Assert.assertTrue(appClass.doLogin(IdentificationMethods.environment.APPIUM, false));
            Utilities.log(runner, "AppiumAndroidEriBankNonInst - logged in to eribank");
            Assert.assertTrue(appClass.doPayment(IdentificationMethods.environment.APPIUM, false));
            Utilities.log(runner, "AppiumAndroidEriBankNonInst - made payment in eribank");
            Assert.assertTrue(appClass.doLogout(IdentificationMethods.environment.APPIUM, false));
            Utilities.log(runner, "AppiumAndroidEriBankNonInst - Logged out from eribank");
            testPassed = true;
        } catch (Exception e) {
            Utilities.log(runner, e);
            Assert.fail("got exception" + e.getMessage());
        }
    }
}
