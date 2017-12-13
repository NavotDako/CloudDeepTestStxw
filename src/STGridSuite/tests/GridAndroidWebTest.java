package STGridSuite.tests;

import STGridSuite.STGridBaseTest;

/**
 * Created by navot.dako on 12/7/2017.
 */
public class GridAndroidWebTest extends STGridBaseTest {

    @Override
    public void test() {

        client.install("http://192.168.2.72:8181/AndroidApps/eribank.apk", true, false);
        client.launch("com.experitest.ExperiBank/.LoginActivity", true, true);
        client.syncElements(3000, 15000);
        client.verifyElementFound("NATIVE", "hint=Username", 0);
        client.click("NATIVE", "hint=Username", 0, 1);
        client.elementSendText("NATIVE", "hint=Username", 0, "company");
        client.verifyElementFound("NATIVE", "hint=Password", 0);
        client.elementSendText("NATIVE", "hint=Password", 0, "company");
        client.closeKeyboard();
        client.verifyElementFound("NATIVE", "text=Login", 0);
        client.click("NATIVE", "text=Login", 0, 1);
        client.verifyElementFound("NATIVE", "text=Make Payment", 0);
        client.click("NATIVE", "text=Make Payment", 0, 1);
        client.verifyElementFound("NATIVE", "hint=Phone", 0);
        client.elementSendText("NATIVE", "hint=Phone", 0, "050-7937021");
        client.verifyElementFound("NATIVE", "hint=Name", 0);
        client.elementSendText("NATIVE", "hint=Name", 0, "Long Run");
        client.verifyElementFound("NATIVE", "hint=Amount", 0);
        client.elementSendText("NATIVE", "hint=Amount", 0, "100");
        client.verifyElementFound("NATIVE", "hint=Country", 0);
        client.verifyElementFound("NATIVE", "text=Select", 0);
        client.click("NATIVE", "text=Select", 0, 1);
        client.sleep(1500);
        if (client.capture() == null) client.report("Can't Get Capture!!!", false);

        client.elementListSelect("", "text=Argentina", 0, false);
        client.click("NATIVE", "text=Argentina", 0, 1);
        client.verifyElementFound("NATIVE", "text=Argentina", 0);
        client.verifyElementFound("NATIVE", "text=Send Payment", 0);
        client.click("NATIVE", "text=Send Payment", 0, 1);
        client.click("NATIVE", "text=Yes", 0, 1);
        client.click("NATIVE", "text=Logout", 0, 1);
        client.uninstall("com.experitest.ExperiBank/.LoginActivity");
    }
}
