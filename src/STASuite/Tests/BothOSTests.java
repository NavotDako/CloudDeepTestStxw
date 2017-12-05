package STASuite.Tests;

import STASuite.STABaseTest;
import STASuite.STARunner;
import Utils.MyILogger;
import com.experitest.client.Client;
import org.junit.Test;

/**
 * Created by navot.dako on 12/4/2017.
 */
public class BothOSTests extends STABaseTest{

    @Test
    public void test() {
        SeeTestTestsStandAlone.androidEriBankTestInstrumented((STARunner) runner);
        SeeTestTestsStandAlone.iOSEriBankTestInstrumented((STARunner) runner);

        SeeTestTestsStandAlone.androidEriBankTestNonInstrumented((STARunner) runner);
        SeeTestTestsStandAlone.iOSEriBankTestNonInstrumented((STARunner) runner);

        SeeTestTestsStandAlone.webWikipediaTest((STARunner) runner, "@os='android'");
        SeeTestTestsStandAlone.webWikipediaTest((STARunner) runner,"@os='ios'");

        SeeTestTestsStandAlone.webAutomationSiteTest((STARunner) runner,"@os='android'");
        SeeTestTestsStandAlone.webAutomationSiteTest((STARunner) runner, "@os='ios'");

        SeeTestTestsStandAlone.androidSimulateCaptureTest((STARunner) runner);
        SeeTestTestsStandAlone.iOSMobileTimerTest((STARunner) runner);
        }



}
