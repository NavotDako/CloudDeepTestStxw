package STGridSuite.tests.SeeTestTests;

import Applications.EriBankAndroid;
import Framework.IdentificationMethods;
import STGridSuite.STGridRunner;
import Utils.Utilities;
import org.junit.Assert;


public class GridAndroidEribankNonInst extends STGridBaseTest {

    @Override
    public void test() {
        EriBankAndroid appClass = new EriBankAndroid((STGridRunner)runner, client, null);
        appClass.installAndLaunch(false);

        try {
            Assert.assertTrue(appClass.doLogin(IdentificationMethods.environment.SEETEST, false));
            Utilities.log(runner, "GridAndroidEribankNonInst - logged in to eribank");

            Assert.assertTrue(appClass.doPayment(IdentificationMethods.environment.SEETEST, false));
            Utilities.log(runner, "GridAndroidEribankNonInst - made payment in eribank");

            Assert.assertTrue(appClass.doLogout(IdentificationMethods.environment.SEETEST, false));
            Utilities.log(runner, "GridAndroidEribankNonInst - logged out from eribank");
            testPassed = true;
        } catch (Exception e) {
            Utilities.log(runner, e);
            Assert.fail("got exception" + e.getMessage());
        }
    }
}
