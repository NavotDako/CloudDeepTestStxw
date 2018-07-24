package STGridSuite;

import MyMain.BaseBaseTest;
import MyMain.BaseRunner;
import MyMain.Main;
import Utils.Utilities;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public abstract class GridBaseTest extends BaseBaseTest{
    protected String user;
    public String deviceOSVersion = null;
    public String deviceName = null;
    protected boolean testPassed = false;
    public void getUser() {
        Utilities.log(runner, "Enter to setUp");
        user = Main.enums.USERS[rand.nextInt(Main.enums.USERS.length)];
        Utilities.log(runner,"selected USER - " + user);
        ((BaseRunner) Thread.currentThread()).user = user;
    }
    @Before
    public abstract void setUp();

    @Test
    abstract public void test();

    @After
    public abstract void finish();

    abstract public void getDeviceProperties();
    @Override
    protected boolean testingOnADevice() {
        return true;
    }

}
