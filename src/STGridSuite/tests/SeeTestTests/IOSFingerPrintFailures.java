package STGridSuite.tests.SeeTestTests;

import MyMain.Main;
import org.junit.Assert;
import org.junit.Test;

public class IOSFingerPrintFailures extends STGridBaseTest{
    @Override
    public void updateDevice(){
        device = Main.cs.getDevice(deviceOS, t -> t.equals("bb904d305dad81b8ae67386753143a28fe81cf1b") || t.equals("25da8511aef7ea8331fdd0da3ed9ebca19d5da8d"));
    }
    @Test
    public void test(){

        Assert.assertTrue("Error: Installation failed", client.install("http://192.168.2.72:8181/daniel/FingerPrint.ipa", true, false));
        client.launch("Experitest.FingerPrintApp", true, true);
        client.setAuthenticationReply("AuthenticationFailedError", 2000);
        Assert.assertTrue("Error: Launch takes too long",client.waitForElement("NATIVE", "xpath=//*[@text='Finger Print Type I']", 0, 10000));

        client.click("NATIVE", "xpath=//*[@text='Finger Print Type I']", 0, 1);
        Assert.assertFalse("Error at FP I: Passed touch id while it shouldn't", client.waitForElement("NATIVE", "xpath=//*[@text='SUCCESS!']", 0, 10000));
        cancelTry();
        cancelTry();
        client.click("NATIVE", "xpath=//*[@text='Finger Print Type II']", 0, 1);
        Assert.assertFalse("Error at FP II: Passed touch id while it shouldn't", client.waitForElement("NATIVE", "xpath=//*[@text='SUCCESS!']", 0, 10000));
        cancelTry();
        client.click("NATIVE", "xpath=//*[@text='Delete Password']", 0, 1);
        client.click("NATIVE", "xpath=//*[@text='Finger Print Type III']", 0, 1);
        client.sendText("RANDOM");
        client.click("NATIVE", "xpath=//*[@text='OK']", 0, 1);
        client.click("NATIVE", "xpath=//*[@text='Finger Print Type III']", 0, 1);
        Assert.assertFalse("Error at FP III: Passed touch id while it shouldn't", client.waitForElement("NATIVE", "xpath=//*[@text='SUCCESS!']", 0, 10000));
        cancelTry();
        client.click("NATIVE", "xpath=//*[@text='Delete Password']", 0, 1);
        System.out.println("Done");
        testPassed = true;

    }

    void cancelTry() {
        if(client.isElementFound("NATIVE", "xpath=//*[@text='Cancel']")) {
            client.click("NATIVE", "xpath=//*[@text='Cancel']", 0, 1);
        }
        else if(client.isElementFound("NATIVE", "xpath=//*[@text='Ok']")) {
            client.click("NATIVE", "xpath=//*[@text='Ok']", 0, 1);
        }
    }
}
