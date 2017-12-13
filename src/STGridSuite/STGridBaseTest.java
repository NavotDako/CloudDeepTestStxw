package STGridSuite;

import MyMain.BaseBaseTest;
import MyMain.Main;
import Utils.Utilities;
import com.experitest.client.Client;
import com.experitest.client.GridClient;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by navot.dako on 12/5/2017.
 */
public abstract class STGridBaseTest extends BaseBaseTest {

    public String deviceOSVersion = null;
    public String deviceName = null;
    protected String deviceOS;
    protected String deviceSN;
    protected Client client = null;
    protected GridClient grid;


    @Before
    public void SetUp() throws Exception {
        runner = (STGridRunner) Thread.currentThread();
        Utilities.log(runner, "Enter to setUp");
        try {
            grid = new GridClient("ayoubadmindeeptest1"/*runner.user*/, "Experitest2012", "Default", Main.cs.HOST, Main.cs.PORT, Main.cs.SECURED);
            client = grid.lockDeviceForExecution(runner.testName, "@os='" + ((STGridRunner) runner).deviceOS + "'", 5, 30000);
        } catch (Exception e) {
            Utilities.log(runner, "Can't create a grid client!!!");
            Utilities.log(runner, e);
            Assert.fail(e.getMessage());
        }

        deviceName = client.getDeviceProperty("device.name");
        deviceOS = ((STGridRunner) runner).deviceOS;
        client.setReporter("xml", Main.logsFolder.getAbsolutePath() + "/STGridRunner/reports", deviceName + " - " + deviceOS + " - " + runner.testName + "_" + (runner.iteration + 1));
        chosenDeviceName = deviceName;
        deviceOSVersion = client.getDeviceProperty("device.version");
        deviceSN = client.getDeviceProperty("device.sn");
        client.deviceAction("unlock");
        Utilities.log(runner, "Finished setUp with device - " + deviceName);
        Utilities.log(runner, "Starting test - " + runner.testName);

    }

    @Test
    abstract public void test();

    @After
    public void finish() {

        try {
            Utilities.log(runner, "generateReport" + client.generateReport(false));
            client.releaseClient();
            Utilities.log(runner, "Client was released");
        } catch (Exception e) {
            Utilities.log(runner, "Failed to releaseClient!!!! - " + deviceSN);
            Utilities.log(runner, e);
        }
        Utilities.log(runner, "Ending test - " + runner.testName + " For Device - " + deviceSN);
    }


}
