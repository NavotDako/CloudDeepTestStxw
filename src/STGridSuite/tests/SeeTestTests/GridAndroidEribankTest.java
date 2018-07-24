package STGridSuite.tests.SeeTestTests;

import Applications.EriBankAndroid;
import Framework.IdentificationMethods;
import STGridSuite.STGridRunner;
import Utils.Utilities;
import org.junit.Assert;


public class GridAndroidEribankTest extends STGridBaseTest {

    @Override
    public void test() {
        EriBankAndroid appClass = new EriBankAndroid((STGridRunner)runner, client, null);
        appClass.installAndLaunch(true);

        client.deviceAction("Portrait");
        client.syncElements(3000, 15000);
        try {
            Assert.assertTrue(appClass.doLogin(IdentificationMethods.environment.SEETEST, true));
            Utilities.log(runner, "GridAndroidEribankNonInst - logged in to eribank");

            Assert.assertTrue(appClass.doPayment(IdentificationMethods.environment.SEETEST, true));
            Utilities.log(runner, "GridAndroidEribankNonInst - made payment in eribank");

            Assert.assertTrue(appClass.doLogout(IdentificationMethods.environment.SEETEST, true));
            appClass.doUninstall();
            testPassed = true;
        }catch (Exception e){
            Utilities.log(runner, e);
            Assert.fail("got exception" + e.getMessage());
        }
    }

}
