package STASuite.Tests;

import STASuite.STABaseTest;
import STASuite.STARunner;
import org.junit.Test;

/**
 * Created by navot.dako on 12/4/2017.
 */
public class STiOSTests extends STABaseTest{


    @Test
    public void test() {

        STA.iOSEriBankTestInstrumented();

        STA.iOSEriBankTestNonInstrumented();

        STA.webWikipediaTest("@os='ios'");

        STA.webAutomationSiteTest("@os='ios'");

        STA.iOSMobileTimerTest();
    }
}
