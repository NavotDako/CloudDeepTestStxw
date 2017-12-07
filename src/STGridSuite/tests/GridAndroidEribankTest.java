package STGridSuite.tests;

import STGridSuite.STGridBaseTest;



public class GridAndroidEribankTest extends STGridBaseTest {
    final String searchBox = "//*[(@id='kw' and @name='_nkw') or @id='gh-ac-box2']";
    final String searchButton = "//*[@id='searchTxtBtn' or @id='gh-btn' or @id='ghs-submit']";
    final String tabElement = "//*[@class='srp-item__title' or @class='grVwBg' or @class='s-item']";

    @Override
    public void test() {

        client.launch("chrome:m.ebay.com", true, true);
        client.hybridWaitForPageLoad(30000);
        client.waitForElement("WEB", searchBox, 0, 30000);
        client.elementSendText("WEB", searchBox, 0, "Hello");
        client.click("WEB", searchButton, 0, 1);
        client.verifyElementFound("WEB", tabElement, 0);
        try {
            client.getAllValues("WEB", tabElement, "text");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@");
            client.getAllValues("WEB", tabElement, "text");
        }
        client.elementSendText("WEB", searchBox, 0, "Hello Again");
        client.click("WEB", searchButton, 0, 1);
        client.waitForElement("WEB", tabElement, 0, 20000);
        client.verifyElementFound("WEB", tabElement, 0);
        client.elementSendText("WEB", searchBox, 0, "3rd Time... Enough Already!");
        String searchText = client.elementGetProperty("WEB", searchBox, 0, "value");
        System.out.println(searchText);
        client.click("WEB", searchButton, 0, 1);
        client.waitForElement("WEB", tabElement, 0, 20000);
        client.verifyElementFound("WEB", tabElement, 0);

    }

}
