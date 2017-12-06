package STGridSuite.tests;

import STGridSuite.STGridBaseTest;

/**
 * Created by navot.dako on 12/6/2017.
 */
public class GridiOSTests extends STGridBaseTest {


    @Override
    public void test() {

        client.install("http://192.168.2.72:8181/iOSApps/EriBank.ipa", true, false);
        client.launch("com.experitest.ExperiBank", true, true);
        client.syncElements(3000, 15000);
        if (client.isElementFound("native", "//*[@text='“EriBankO” May Slow Down Your iPad']", 0)) {
            client.click("navite", "//*[@text='OK']", 0, 1);
        }
        client.verifyElementFound("NATIVE", "//*[@placeholder='Username']", 0);
        client.elementSendText("NATIVE", "//*[@placeholder='Username']", 0, "company");

        client.verifyElementFound("NATIVE", "//*[@placeholder='Password']", 0);
        client.elementSendText("NATIVE", "//*[@placeholder='Password']", 0, "company");
        client.closeKeyboard();

        client.verifyElementFound("NATIVE", "//*[@text='Login' or @text='loginButton']", 0);
        client.click("NATIVE", "//*[@text='Login' or @text='loginButton']", 0, 1);
        client.sendText("{LANDSCAPE}");
        client.sleep(1000);
        client.verifyElementFound("NATIVE", "//*[@text='Make Payment']", 0);
        client.syncElements(1000, 5000);
        client.click("NATIVE", "//*[@text='Make Payment']", 0, 1);

        client.verifyElementFound("NATIVE", "//*[@accessibilityLabel='phoneTextField']", 0);
        client.syncElements(1000, 5000);
        client.elementSendText("NATIVE", "//*[@accessibilityLabel='phoneTextField']", 0, "050-7937021");
        client.closeKeyboard();
        client.verifyElementFound("NATIVE", "//*[@accessibilityLabel='nameTextField']", 0);
        client.elementSendText("NATIVE", "//*[@accessibilityLabel='nameTextField']", 0, "Long Run");
        client.closeKeyboard();
        client.verifyElementFound("NATIVE", "//*[@accessibilityLabel='amountTextField']", 0);
        client.elementSendText("NATIVE", "//*[@accessibilityLabel='amountTextField']", 0, "100");
        client.closeKeyboard();
        client.verifyElementFound("NATIVE", "//*[@accessibilityLabel='Country']", 0);
        client.verifyElementFound("NATIVE", "//*[@text='Select']", 0);
        client.sleep(1000);
        client.click("NATIVE", "//*[@text='Select']", 0, 1);
        client.elementListSelect("", "text=Argentina", 0, false);
        client.click("NATIVE", "//*[@accessibilityLabel='Argentina']", 0, 1);
        client.sendText("{PORTRAIT}");
        client.sleep(1000);
        client.verifyElementFound("NATIVE", "//*[@accessibilityLabel='Argentina' or @text='Argentina']", 0);
        client.verifyElementFound("NATIVE", "//*[@text='Send Payment']", 0);
        client.syncElements(1000, 5000);
        client.click("NATIVE", "//*[@text='Send Payment']", 0, 1);
        client.sleep(500);
        client.click("NATIVE", "//*[@text='Yes']", 0, 1);
        client.click("NATIVE", "//*[@text='Logout']", 0, 1);
        client.sendText("{HOME}");
    }
}