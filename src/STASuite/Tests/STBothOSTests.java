package STASuite.Tests;

import STASuite.STABaseTest;

import org.junit.Test;


public class STBothOSTests extends STABaseTest{

    @Test
    public void test() {
        STA.androidEriBankTestInstrumented();
        STA.iOSEriBankTestInstrumented();

        STA.androidEriBankTestNonInstrumented();
        STA.iOSEriBankTestNonInstrumented();

        STA.webWikipediaTest("@os='android'");
        STA.webWikipediaTest("@os='ios'");

        STA.webAutomationSiteTest("@os='android'");
        STA.webAutomationSiteTest("@os='ios'");

        STA.androidSimulateCaptureTest();
        STA.iOSMobileTimerTest();
        }



}
