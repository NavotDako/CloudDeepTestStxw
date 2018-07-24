package STGridSuite.tests.AppiumTests;

import MyMain.Main;
import STGridSuite.GridBaseTest;
import STGridSuite.STGridRunner;
import Utils.Utilities;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.internal.ApacheHttpClient;

import java.net.URL;

public abstract class AppiumGridBaseTest  extends GridBaseTest {

    protected AppiumDriver driver = null;
    protected DesiredCapabilities dc = null;

    abstract public void createCapabilities();
    @Before
    public void setUp(){
        runner = (STGridRunner) Thread.currentThread();
        deviceOS = runner.deviceOS;
        getUser();
        updateDevice();
        createCapabilities();
        dc.setCapability("udid", device);
        dc.setCapability("testName", runner.testName);
        chosenDeviceName = device;

        try {

            if(deviceOS.equals("ios")){
                driver = new IOSDriver(new URL(Main.cs.URL_ADDRESS + "/wd/hub"), url -> {
                    RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.IGNORE_COOKIES).build();
                    CloseableHttpClient httpclient = HttpClients.custom().setDefaultRequestConfig(globalConfig).build();
                    return new ApacheHttpClient(httpclient, url);
                }, dc);
            }else{
                driver = new AndroidDriver(new URL(Main.cs.URL_ADDRESS + "/wd/hub"), url -> {
                    RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.IGNORE_COOKIES).build();
                    CloseableHttpClient httpclient = HttpClients.custom().setDefaultRequestConfig(globalConfig).build();
                    return new ApacheHttpClient(httpclient, url);
                }, dc);
            }
        } catch (Exception e) {
            Utilities.log(runner, "Can't create a grid client!!!");
            Utilities.log(runner, e);
            Assert.fail(e.getMessage());
        }

        getDeviceProperties();
        Utilities.log(runner, "Finished setUp with device - " + deviceName);
        Utilities.log(runner, "Starting test - " + runner.testName);

    }


    public void getDeviceProperties() {
        try {
            deviceName = (String) driver.getCapabilities().getCapability("device.name");
            this.reportURL = (String) driver.getCapabilities().getCapability("reportUrl");
            chosenDeviceName = deviceName;
            deviceOSVersion = (String) driver.getCapabilities().getCapability("device.version");
//            device = (String) driver.getCapabilities().getCapability("device.sn");

        }catch (Exception e){
            e.printStackTrace(Main.exceptionWriter);
            Utilities.log(runner, "Can't setup a grid test!!!");
            Utilities.log(runner, e);
            Assert.fail(e.getMessage());
        }
    }


    @After
    public void finish() {
        try {
            driver.quit();
            Utilities.log(runner, "driver was released");
        } catch (Exception e) {
            Utilities.log(runner, "Failed to quit driver!!!! - " + device);
            Utilities.log(runner, e);
        }
        Assert.assertTrue(testPassed);
        Utilities.log(runner, "Ending test - " + runner.testName + " For Device - " + device);
    }
}
