package STXWSuite.tests;

import STXWSuite.STXWBaseTest;
import Utils.Utilities;
import org.junit.Test;
import org.openqa.selenium.By;

import MyMain.Main;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Base64;

import org.json.JSONObject;
import org.junit.*;
import org.openqa.selenium.Dimension;

public class Reboot extends STXWBaseTest {


    @Test
    public void test() {

        Utilities.log(runner, "Enter to Reboot testClass");
        Utilities.sleep(runner, 2000);
        driver.findElement(By.xpath("//*[@id='action_reboot']")).click();
        Utilities.log(runner, "click on reboot button");

        Utilities.sleep(runner, 2000);
        driver.findElement(By.xpath("//button[span[contains(text(),'Confirm')]]")).click();
        Utilities.log(runner, "click on confirm Button");

        Utilities.sleep(runner, 4000);
        String udid = driver.findElement(By.xpath("//*[@id='device_sn']")).getAttribute("value");
        ArrayList<String> newTab = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(newTab.get(0));
        Utilities.log(runner, "Go To devices tab");
        Utilities.sleep(runner, 3000);

        waitForElement("//*[label[contains(text(),'Search')]]/input");
        driver.findElement(By.xpath("//md-input-container[*[contains(text(),'Status')]]/div[1]")).click();
        Utilities.log(runner, "Click on status textarea");

        Utilities.sleep(runner, 2000);
        waitForElement("//*[(contains(@id,'menu_container') and @aria-hidden='false')]//button[*[contains(text(),'All')]]");
        driver.findElement(By.xpath("//*[(contains(@id,'menu_container') and @aria-hidden='false')]//button[*[contains(text(),'All')]]")).click();
        Utilities.log(runner, "Click on All button");

        driver.findElement(By.xpath("//*[label[contains(text(),'Search')]]/input"));
        Utilities.log(runner, "Click on Search input");

        String status =  getStatus(udid);
        Utilities.log(runner, "Status is : " + status);

        driver.switchTo().window(newTab.get(1));
        Utilities.log(runner, "Go To device's tab");

        if(!status.contains("In Use"))
        {
            Assert.fail("Device status doesn't convert to In Use ");
        }
        Utilities.sleep(runner,10000);

    }

    private String getStatus(String udid) {

        driver.findElement(By.xpath("//*[label[contains(text(),'Search')]]/input")).sendKeys(udid);
        Utilities.log(runner, "Write " + udid + " in search input ");
        int count = 0 ;
        Utilities.sleep(runner, 5000);
        while((!WaitForText("//table/tbody/tr/td[3]/div", "In Use")) && count < 8)
        {
            Utilities.log(runner, "device current status: " + driver.findElement(By.xpath("//table/tbody/tr/td[3]/div/span")).getText());
            count++;
        }
        return driver.findElement(By.xpath("//table/tbody/tr/td[3]/div/span")).getText();

    }

}
