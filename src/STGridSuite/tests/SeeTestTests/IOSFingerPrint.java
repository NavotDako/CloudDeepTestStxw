package STGridSuite.tests.SeeTestTests;

import MyMain.Main;
import Utils.Utilities;
import org.junit.Assert;
import org.junit.Test;

public class IOSFingerPrint extends STGridBaseTest {

    @Override
    public void updateDevice(){
        device = Main.cs.getDevice(deviceOS, t -> t.equals("bb904d305dad81b8ae67386753143a28fe81cf1b") || t.equals("25da8511aef7ea8331fdd0da3ed9ebca19d5da8d"));
        //Devices have 300 seconds waiting period between tests, wait for these two until they are back available
        if(device.equals(null)){
            try {
                Utilities.log("no available device, sleeping for 5 minutes");
                Thread.sleep(5 * 60 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            device = Main.cs.getDevice(deviceOS, t -> t.equals("bb904d305dad81b8ae67386753143a28fe81cf1b") || t.equals("25da8511aef7ea8331fdd0da3ed9ebca19d5da8d"));
        }
    }
    @Test
    public void test() {

        Assert.assertTrue("Error: Installation failed", client.install("http://192.168.2.72:8181/daniel/FingerPrint.ipa", true, false));
        client.launch("Experitest.FingerPrintApp", true, true);
        client.setAuthenticationReply("Success", 2000);
        Assert.assertTrue("Error: Launch takes too long",client.waitForElement("NATIVE", "xpath=//*[@text='Finger Print Type I']", 0, 10000));
        client.click("NATIVE", "xpath=//*[@text='Finger Print Type I']", 0, 1);
        Assert.assertTrue("Error: Finger Print type I timed out", client.waitForElement("NATIVE", "xpath=//*[@text='SUCCESS!']", 0, 10000));

        client.verifyElementFound("NATIVE", "xpath=//*[@text='LocalAuthentication library']", 0);
        client.click("NATIVE", "xpath=//*[@text='Back']", 0, 1);
        client.click("NATIVE", "xpath=//*[@text='Finger Print Type II']", 0, 1);
        Assert.assertTrue("Error: Finger Print type II timed out", client.waitForElement("NATIVE", "xpath=//*[@text='SUCCESS!']", 0, 10000));
        client.verifyElementFound("NATIVE", "xpath=//*[@text='BiometricAuthentication library']", 0);
        client.click("NATIVE", "xpath=//*[@text='Back']", 0, 1);
        client.click("NATIVE", "xpath=//*[@text='Delete Password']", 0, 1);
        client.click("NATIVE", "xpath=//*[@text='Finger Print Type III']", 0, 1);
        client.sendText("RANDOM");
        client.click("NATIVE", "xpath=//*[@text='OK']", 0, 1);
        client.click("NATIVE", "xpath=//*[@text='Finger Print Type III']", 0, 1);
        Assert.assertTrue("Error: Finger Print type III timed out", client.waitForElement("NATIVE", "xpath=//*[@text='SUCCESS!']", 0, 10000));
        client.verifyElementFound("NATIVE", "xpath=//*[@text='RANDOM']", 0);
        client.click("NATIVE", "xpath=//*[@text='Back']", 0, 1);
        client.click("NATIVE", "xpath=//*[@text='Delete Password']", 0, 1);
        testPassed = true;
    }
}
