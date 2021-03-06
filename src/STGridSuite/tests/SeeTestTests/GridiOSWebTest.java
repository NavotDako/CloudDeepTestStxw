package STGridSuite.tests.SeeTestTests;

import MyMain.Main;
import Utils.Utilities;

/**
 * Created by navot.dako on 12/7/2017.
 */
public class GridiOSWebTest extends STGridBaseTest {


    @Override
    public void test() {
        client.deviceAction("Portrait");
        try {
            String search = "xpath=//*[@text='About Wikipedia' and @top='true' or @id='searchInput' or @value='Search' or @id='searchIcon']";
            try {
                client.launch("http://www.apple.com", true, true);
                client.verifyElementFound("Native", "//*[@id='ac-gn-firstfocus-small' or @id='ac-gn-firstfocus']", 0);
            } catch (Exception e) {
                if (client.isElementFound("Native", "xpath=//*[@text='Submit']", 0)) {
                    client.click("Native", "xpath=//*[@text='Cancel' and ./parent::*[./following-sibling::*[@class='UIAView' and ./*[@class='UIAView']]]]", 0, 1);
                    client.launch("safari:m.ebay.com", true, true);
                }
            }

            client.hybridWaitForPageLoad(30000);
            client.launch("safari:http://wikipedia.org", true, false);
            if (!client.isElementFound("WEB", "xpath=//*[@id='searchInput']", 0)) {
                client.report("Failed on first launch - Will try again", false);
                client.launch("safari:http://wikipedia.org", true, true);
            }
            if (client.waitForElement("WEB", "xpath=//*[@id='searchInput']", 0, 15000)) {
                client.elementSendText("WEB", "xpath=//*[@id='searchInput']", 0, "Long Run");
            } else {
                System.err.println("No Internet Connection!");
                //fail();
            }
            client.click("WEB", "xpath=//*[@nodeName='BUTTON']", 0, 1);
            client.waitForElement("WEB", "xpath=//*[@id='mw-mf-main-menu-button']", 0, 30000);
            client.click("WEB", "xpath=//*[@id='mw-mf-main-menu-button']", 0, 1);
            client.sleep(1000);
            client.click("WEB", "xpath=//*[@class='transparent-shield cloaked-element']", 0, 1);
            client.waitForElementToVanish("WEB", "xpath=//*[@text='About Wikipedia' and @top='true']", 0, 30000);
            client.click("WEB", search, 0, 1);
            client.syncElements(2000, 15000);
            try {
                client.sendText("LONG RUN{ENTER}");
            } catch (Exception e) {
                System.out.println("**********FALIED TO SEND TEXT**************");
                e.printStackTrace();
                client.sendText("LONG RUN{ENTER}");
            }
            client.syncElements(2000, 30000);
            client.waitForElement("WEB", "xpath=//*[@id='ca-edit']", 0, 30000);
            String[] str0 = client.getAllValues("WEB", "xpath=//*[@id='ca-edit']", "hidden");

            if (str0[0].equals("false")) {
                client.click("WEB", "id=ca-edit", 0, 1);
                client.swipeWhileNotFound("DOWN", 250, 1000, "web", "xpath=//*[@text='Sign up']"
                        , 0, 0, 2, true);
            /*client.syncElements(1000, 20000);
            client.sendText("{LANDSCAPE}");*/
                client.sleep(15000);
                getTextInWrapper("xpath=//*[@id='section_0']", "WEB");

                client.elementSendText("WEB", "xpath=//*[contains(@id,'wpName')]", 0, "LONG");
                client.elementSendText("WEB", "id=wpPassword2", 0, "RUN");
                client.elementSendText("WEB", "id=wpRetype", 0, "123456");
                client.closeKeyboard();
                client.swipeWhileNotFound("DOWN", 250, 1000, "WEB", "id=wpEmail", 0, 0, 2, true);
                client.elementSendText("WEB", "id=wpEmail", 0, "LONG@RUN.COM");
                client.sendText("{PORTRAIT}");
                client.sendText("{LANDSCAPE}");
                client.sendText("{CLOSEKEYBOARD}");
                client.swipeWhileNotFound("DOWN", 150, 1000, "WEB", "xpath=//*[@id='wpCreateaccount' and @onScreen='true']", 0, 0, 2, true);
                client.syncElements(1000, 20000);
                if (client.isElementFound("native", "xpath=//*[@text='Save Password']", 0)) {
                    client.click("Native", "xpath=//*[@text='Not Now']", 0, 1);
                }


                try {
                    client.verifyElementFound("WEB", "xpath=//*[@class='error']", 0);
                } catch (Exception e) {
                    System.out.println("No error - trying to insert text and click again");
                    client.click("WEB", "//*[@id='mw-input-captchaWord']", 0, 1);
                    client.sendText("SOME TEXT");
                    client.closeKeyboard();
                    client.swipeWhileNotFound("DOWN", 150, 1000, "WEB", "xpath=//*[@id='wpCreateaccount' and @onScreen='true']", 0, 0, 2, true);
                    client.verifyElementFound("WEB", "xpath=//*[@class='error']", 0);
                }

                client.click("WEB", "xpath=//*[@id='mw-mf-main-menu-button']", 0, 1);
                client.sendText("{PORTRAIT}");
                client.syncElements(1000, 20000);
                //client.sleep(500);
                client.click("WEB", "xpath=//*[@text='Home']", 0, 1);

            } else {
                String str1 = getTextInWrapper("xpath=//*[@id='mw-mf-last-modified']", "Inside");
                if (str1.contains("Last modified")) {
                    System.out.println("Text was identified!");
                }
                client.click("WEB", "id=mw-mf-main-menu-button", 0, 1);
                String str2 = client.getText("WEB");
                if (str2.contains("HOME")) {
                    client.click("WEB", "xpath=//*[@text='Home' and @nodeName='A']", 0, 1);
                    client.waitForElementToVanish("WEB", "xpath=//*[@text='Watch List' and @nodeName='A']", 0, 1);
                }
            }
            client.launch("http://www.google.com", true, false);
        }
        catch (Exception e){
//            logDeviceAndRunJS();
            throw e;
        }
        testPassed = true;
    }
    private String getTextInWrapper(String element, String textZone){
        String res = "";
        try{
            res = client.getTextIn("WEB", element, 0, textZone, "Inside", 0, 0);
        }catch (Exception e){
//            logDeviceAndRunJS();
            throw e;

        }
        return res;
    }

    private void logDeviceAndRunJS() {
        client.startLoggingDevice(Main.reporterAttachmentsFolder.getAbsolutePath() + "devicelog" + System.currentTimeMillis() + ".log");
        for (int i = 0; i < 10; i++) {
            try {
                System.out.println(client.hybridRunJavascript("\"\"", 0, "var result = document.location"));
            }catch (Exception e1){
                Utilities.log(runner, "failed to run JS" + e1.getMessage());
            }
        }
        client.stopLoggingDevice();
        System.out.println(client.hybridRunJavascript("\"\"", 0, "var result = document.location"));
    }
}
