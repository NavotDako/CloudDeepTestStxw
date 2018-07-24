package STGridSuite.tests.AppiumTests;

import Applications.EriBankIOS;
import Framework.IdentificationMethods;
import MyMain.Enums;
import STGridSuite.STGridRunner;
import Utils.Utilities;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.Assert;
import org.openqa.selenium.remote.DesiredCapabilities;

public class AppiumIOSEriBankNonInst extends AppiumGridBaseTest{

    @Override
    public void createCapabilities() {
        dc = new DesiredCapabilities();
        dc.setCapability(MobileCapabilityType.APP, "cloud:com.experitest.ExperiBankO");
//        dc.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 60);
        dc.setCapability("instrumentApp", false);
        dc.setCapability("os", this.deviceOS);
        dc.setCapability("accessKey", Enums.getAccessKey(user));
    }

    @Override
    public void test() {
        EriBankIOS appClass = new EriBankIOS((STGridRunner)runner, null, driver);
        try {
            Assert.assertTrue(appClass.doLogin(IdentificationMethods.environment.APPIUM, false));
            Utilities.log(runner, "AppiumIOSEriBankNonInst - logged in to eribank");
            Assert.assertTrue(appClass.doPayment(IdentificationMethods.environment.APPIUM, false));
            Utilities.log(runner, "AppiumIOSEriBankNonInst - made payment in eribank");
            Assert.assertTrue(appClass.doLogout(IdentificationMethods.environment.APPIUM, false));
            Utilities.log(runner, "AppiumIOSEriBankNonInst - logged out from eribank");
            testPassed = true;
        } catch (Exception e) {
            Utilities.log(runner, e);
            Assert.fail("got exception" + e.getMessage());
        }
        testPassed = true;
    }
}
