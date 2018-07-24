package STGridSuite.tests.SeeTestTests;

import MyMain.Enums;
import MyMain.Main;
import STGridSuite.GridBaseTest;
import STGridSuite.STGridRunner;
import Utils.Utilities;
import com.experitest.client.Client;
import com.experitest.client.GridClient;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

/**
 * Created by navot.dako on 12/5/2017.
 */
public abstract class STGridBaseTest extends GridBaseTest {
    protected Client client = null;
    protected GridClient grid;

    @Before
    public void setUp(){
        runner = (STGridRunner) Thread.currentThread();
        deviceOS = (runner).deviceOS;
        System.out.println("device os "+ deviceOS);
        updateDevice();
        chosenDeviceName = device;
        getUser();
        try {
            grid = new GridClient(Enums.getAccessKey(user), Main.cs.URL_ADDRESS);
//          client = grid.lockDeviceForExecution(runner.testName, "@os='" + ((STGridRunner) runner).deviceOS + "'", 30, 300000);
            client = grid.lockDeviceForExecution(runner.testName, "@serialnumber='" + device + "'", 30, 300000);
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
            Assert.assertNotNull(client);
            deviceName = client.getDeviceProperty("device.name");
            deviceOS = runner.deviceOS;
            client.setReporter("xml", Main.logsFolder.getAbsolutePath() + "/STGridRunner/reports", deviceName + " - " + deviceOS + " - " + runner.testName + "_" + (runner.iteration + 1));
            deviceOSVersion = client.getDeviceProperty("device.version");
            client.deviceAction("unlock");
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
            reportURL = client.generateReport(false);
            Utilities.log(runner, "generateReport" + reportURL);

            client.releaseClient();
            Utilities.log(runner, "Client was released");
        } catch (Exception e) {
            Utilities.log(runner, "Failed to releaseClient!!!! - " + device);
            Utilities.log(runner, e);
        }
        Assert.assertTrue(testPassed);
        Utilities.log(runner, "Ending test - " + runner.testName + " For Device - " + device);
    }
}
