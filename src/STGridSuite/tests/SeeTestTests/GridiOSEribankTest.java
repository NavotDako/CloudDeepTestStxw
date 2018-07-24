package STGridSuite.tests.SeeTestTests;

import Applications.EriBankIOS;
import Framework.IdentificationMethods;
import STGridSuite.STGridRunner;
import Utils.Utilities;
import org.junit.Assert;

/**
 * Created by navot.dako on 12/6/2017.
 */
public class GridiOSEribankTest extends STGridBaseTest {


    @Override
    public void test() {
        EriBankIOS appClass = new EriBankIOS((STGridRunner)runner, client, null);
        appClass.installAndLaunch(true);

        try {
            Assert.assertTrue(appClass.doLogin(IdentificationMethods.environment.SEETEST, true));
            Utilities.log(runner, "GridIOSEribankNonInst - logged in to eribank");
            Assert.assertTrue(appClass.doPayment(IdentificationMethods.environment.SEETEST, true));
            Utilities.log(runner, "GridIOSEribankNonInst - made payment in eribank");
            Assert.assertTrue(appClass.doLogout(IdentificationMethods.environment.SEETEST, true));
            testPassed = true;
        }catch (Exception e){
            Utilities.log(runner, e);
            Assert.fail("got exception" + e.getMessage());
        }
    }
}