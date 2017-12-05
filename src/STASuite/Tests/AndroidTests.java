package STASuite.Tests;

import STASuite.STABaseTest;
import STASuite.STARunner;
import Utils.MyILogger;
import Utils.VMProperties;
import com.experitest.client.Client;
import org.junit.Test;

/**
 * Created by navot.dako on 12/4/2017.
 */
public class AndroidTests extends STABaseTest {
    private String vmClientQuery;

    @Test
    public void test() {

        SeeTestTestsStandAlone.androidEriBankTestInstrumented((STARunner) runner);
        SeeTestTestsStandAlone.androidEriBankTestNonInstrumented((STARunner) runner);
        SeeTestTestsStandAlone.androidSimulateCaptureTest((STARunner) runner);
        SeeTestTestsStandAlone.webAutomationSiteTest((STARunner) runner,"@os='android'");
        SeeTestTestsStandAlone.webWikipediaTest((STARunner) runner,"@os='android'");

    }
}
