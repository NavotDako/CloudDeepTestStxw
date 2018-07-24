package STGridSuite.tests.SeeTestTests;

import Applications.EriBankIOS;
import Framework.IdentificationMethods;
import STGridSuite.STGridRunner;
import Utils.Utilities;
import org.junit.Assert;

public class GridIOSEriBankNonInst extends STGridBaseTest {


    @Override
    public void test() {
        EriBankIOS appClass = new EriBankIOS((STGridRunner)runner, client, null);
        appClass.installAndLaunch(false);

        try {
            appClass.doLogin(IdentificationMethods.environment.SEETEST, false);
            Utilities.log(runner, "GridIOSEribankNonInst - logged in to eribank");
            appClass.doPayment(IdentificationMethods.environment.SEETEST, false);
            Utilities.log(runner, "GridIOSEribankNonInst - made payment in eribank");
            appClass.doLogout(IdentificationMethods.environment.SEETEST, false);
//            client.collectSupportData("C:\\Users\\khaled.abbas\\Documents\\Clients\\ruby\\rubyRegression\\suppData", "", "", "", "", "", true, false);
//            appClass.doUninstall();
            testPassed = true;
        } catch (Exception e) {
            Utilities.log(runner, e);
            Assert.fail("got exception" + e.getMessage());
        }
    }
}