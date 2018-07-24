package Framework;

import STGridSuite.STGridRunner;
import Utils.Utilities;
import com.experitest.client.Client;
import io.appium.java_client.AppiumDriver;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static Framework.IdentificationMethods.identifications.*;
import static Framework.IdentificationMethods.identificationsZone.*;
import static Framework.IdentificationMethods.elementsMapTranslator.*;
import static Framework.QueryBuilder.getSeeTestFriendlyQuery;
import static Framework.QueryBuilder.getSeeTestZone;


public class CommandTranslator {
    private Client client = null;
    private STGridRunner runner;
    private AppiumDriver driver = null;
    public CommandTranslator(STGridRunner runner, Client client, AppiumDriver driver){
        this.runner = runner;
        this.client = client;
        this.driver = driver;
    }

    public void elementSendText(QuadMap map, String element, IdentificationMethods.environment environment, String text) throws Exception {
        verifyElementFound(map, element, environment);
        if (environment.equals(IdentificationMethods.environment.APPIUM)) {
            myFindElement(map, element).sendKeys(text);
        }else{
            client.elementSendText(getSeeTestZone(map,element), getSeeTestFriendlyQuery(map, element), 0, text);
        }
    }
    public void click(QuadMap map, String element, IdentificationMethods.environment environment) throws Exception {
        if (environment.equals(IdentificationMethods.environment.APPIUM)) {
            myFindElement(map, element).click();
        }
        else {
            client.click(getSeeTestZone(map, element), getSeeTestFriendlyQuery(map, element), 0, 1);
        }
    }
    public boolean waitForElement(QuadMap map, String element, IdentificationMethods.environment environment, int seconds) throws Exception {
        if (environment.equals(IdentificationMethods.environment.APPIUM)) {
            verifyCorrectZoneAppium(map, element);
            new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(getBy(map, element)));
            return myFindElement(map, element) != null;
        }else{
            return client.waitForElement(getSeeTestZone(map,element), getSeeTestFriendlyQuery(map, element), 0, 1000 * seconds);
        }
    }


    public WebElement myFindElement(QuadMap map, String element){
        verifyCorrectZoneAppium(map, element);
        return driver.findElement(getBy(map, element));
    }

    public void verifyCorrectZoneAppium(QuadMap map, String element) {
        if(map.get(element,ZONE).equals(NON_INSTRUMENTED)) {
            if (driver.getContext().equals("NATIVE_APP")) {
                return;
            } else {
                driver.context("NATIVE_APP");
            }
        }else if(map.get(element,ZONE).equals(INSTRUMENTED)) {
            if (driver.getContext().equals("NATIVE_APP_INSTRUMENTED")) {
                return;
            } else {
                driver.context("NATIVE_APP_INSTRUMENTED");
            }
        } else if(map.get(element,ZONE).equals(WEB)) {
                if(driver.getContext().equals("CHROMIUM")){
                    return;
                }
                else{
                    driver.context("CHROMIUM");
                }
        }
    }

    public void verifyElementFound(QuadMap map, String element, IdentificationMethods.environment environment) throws Exception {
        Utilities.log(runner, "searching for element: " + map.get(element, IdentificationMethods.elementsMapTranslator.ELEMENT));
        if (environment.equals(IdentificationMethods.environment.APPIUM)) {
            myFindElement(map, element);
        }else{
            client.verifyElementFound(getSeeTestZone(map,element), getSeeTestFriendlyQuery(map, element), 0);
        }
        Utilities.log(runner, "found element: " + map.get(element, IdentificationMethods.elementsMapTranslator.ELEMENT));

    }

    public By getBy(QuadMap map, String element){
        if(map.get(element, IDENTIFICATION).equals(XPATH)) {
            return By.xpath((String) map.get(element, ELEMENT));

        }else if(map.get(element, IDENTIFICATION).equals(ID)) {
            return By.id((String) map.get(element, ELEMENT));

        }else if(map.get(element, IDENTIFICATION).equals(TEXT)) {
//            return By.xpath("//*[@text='" + map.get(element, ELEMENT) + "']");
            return By.linkText((String) map.get(element, ELEMENT));

        }else if(map.get(element, IDENTIFICATION).equals(HINT)) {
            return By.xpath("//*[@hint='" + map.get(element, ELEMENT) + "']");
        }
        else {
            Assert.fail("Did not get correct identification method, got: " + map.get(element, IDENTIFICATION));
        }
        return null;
    }

    public Client getClient() {
        return client;
    }

    public AppiumDriver getDriver() {
        return driver;
    }

    public boolean isElementFound(QuadMap map, String element, IdentificationMethods.environment environment) throws Exception {
        if (environment.equals(IdentificationMethods.environment.APPIUM)) {
            try {
                myFindElement(map, element);
                return true;
            } catch (Exception e) {
                return false;
            }
        }else {
            return client.isElementFound(getSeeTestZone(map, element), getSeeTestFriendlyQuery(map, element), 0);
        }
    }

    public void setOrientation(IdentificationMethods.environment environment, ScreenOrientation screenOrientation){
        if (environment.equals(IdentificationMethods.environment.APPIUM)) {
            driver.rotate(screenOrientation);
        }else{
            if(screenOrientation.equals(screenOrientation.PORTRAIT)) {
                client.deviceAction("Portrait");
            }else{
                client.deviceAction("Landscape");
            }
        }
    }

    public void closeKeyboard(IdentificationMethods.environment environment) {
        if (environment.equals(IdentificationMethods.environment.APPIUM)) {
            driver.hideKeyboard();
        }else{
            client.closeKeyboard();
        }
    }
}
