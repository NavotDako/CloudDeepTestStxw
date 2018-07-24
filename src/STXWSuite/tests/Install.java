package STXWSuite.tests;

import STXWSuite.STXWBaseTest;
import Utils.Utilities;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;


public class Install extends STXWBaseTest {

    String text = "";

    @Test
    public void test() {


        Utilities.log(runner, "Enter to Install testClass");

        Utilities.sleep(runner, 2000);
        driver.findElement(By.xpath("//*[@id='install_panel_openclose']")).click();
        Utilities.log(runner, "click on Application button");

        Utilities.sleep(runner, 2000);
        if(!waitForElement("//*[@id='install_panel_openclose']"))
        {
            Assert.fail("Install window wasn't found");
        }
        Utilities.sleep(runner, 1000);


//        if(waitForElement("/html/body/div[contains(@id,'menu_container')]"))
//        {
//            Assert.fail("Applications window didn't open");
//        }
        if(!waitForText("//*[@id='selected_app_name']", "EriBank")&& !waitForText("//*[@id='selected_app_name']", "Experibank")){
            chooseEriBank();
        }

        driver.findElement(By.xpath("//*[@id='instrument_checkbox']/div[1]")).click();
        Utilities.log(runner,"Click on intrumentent checkbox");

        Utilities.sleep(runner,1000);
        driver.findElement(By.xpath("//*[@id='install']")).click();
        Utilities.log(runner,"Click on install action");

        if(!waitForText("//*[@id='console']","Starting installation"))
        {
            Assert.fail("Starting installation failed");
        }
        Utilities.log(runner,"Starting the app succeed");

        if(!waitForText("//*[@id='console']","Successfully installed"))
        {
            Assert.fail("intalling the app failed");
        }
        Utilities.log(runner,"Installing the app succeed");

        try{
            driver.findElement(By.xpath("//*[text()='launch']")).click();
            Utilities.log(runner,"Launching the app in STAWI");
        }catch (Exception e){
            Utilities.log(runner,"Not launching the app in STMW");
        }
//        Utilities.log(runner, driver.findElement(By.xpath("//*[@id='console']")).getText());
        if(!waitForText("//*[@id='console']","Successfully launched"))
        {
            Utilities.log(runner, driver.findElement(By.xpath("//*[@id='console']")).getText());
            Assert.fail("launch the application failed");
        }
        Utilities.log(runner,"launch the app succeed");

        driver.findElement(By.xpath("//*[@id='close']")).click();
        Utilities.log(runner,"Click on x for closing the application manager window");

        Utilities.sleep(runner,2000);
        if(!WaitForDisappearedElement("//*[@id='close']"))
        {
            Assert.fail("The application manager window didn't disapear");
        }

    }

    public void chooseEriBank() {
        driver.findElement(By.xpath("//*[@id='menu_openclose']")).click();
        Utilities.log(runner,"Click on menu list icon");

        Utilities.sleep(runner,10000);
        List<WebElement> elems;
        try {
            elems = driver.findElements(By.xpath("//*[text()='EriBank' or text()='Experibank']"));
        }catch (Exception e){
            elems = driver.findElements(By.xpath("//*[text()='Experibank']"));
        }
        int countTries;
        for(countTries = 0; countTries < 10 ; countTries++){
            try {
                elems.get(countTries).click();
                break;
            }catch (Exception e1){
                Utilities.log(runner,"Element is not visible, retrying");
            }
        }
        if(countTries >= 10){
            try{
                driver.findElement(By.xpath("//*[@class='close-panel-icon material-icons']")).click();
            }catch (Exception e){

            }
            Assert.fail("Could not click on eri bank app");
        }
        Utilities.log(runner,"Click on Experibank app");
    }


}
