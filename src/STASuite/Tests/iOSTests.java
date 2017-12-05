package STASuite.Tests;

import STASuite.STABaseTest;
import STASuite.STARunner;
import org.junit.Test;

/**
 * Created by navot.dako on 12/4/2017.
 */
public class iOSTests extends STABaseTest{
    @Test
    public void test() {
        SeeTestTestsStandAlone.iOSEriBankTestInstrumented((STARunner) runner);

        SeeTestTestsStandAlone.iOSEriBankTestNonInstrumented((STARunner) runner);

        SeeTestTestsStandAlone.webWikipediaTest((STARunner) runner,"@os='ios'");

        SeeTestTestsStandAlone.webAutomationSiteTest((STARunner) runner, "@os='ios'");

        SeeTestTestsStandAlone.iOSMobileTimerTest((STARunner) runner);
    }
}
