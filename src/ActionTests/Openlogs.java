package ActionTests;

import Utils.Utilities;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

public class Openlogs extends BaseTest {



    @Test
    public void test() {

        try {

            Utilities.log(currentThread,"openlogsssss");
            Utilities.log(currentThread, "Enter to Open Logs testClass");

            driver.findElement(By.xpath("//*[( contains(@id,'accordiongroup-') and contains(@id,'-panel'))]/div/md-list/md-list-item/div/button[contains(@aria-label,'Logs')]")).click();
            Utilities.log(currentThread, "click on open log Button ");


                Thread.sleep(2000);
                Utilities.log(currentThread, "wait 2 seconds");


            driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/md-toolbar"));
            Utilities.log(currentThread, "test if the logs open");


                Thread.sleep(5000);
                Utilities.log(currentThread, "wait 5 seconds");

            try {
                if (!driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/div[5]/div/div/log-panel/md-content/code/text()")).getText().equals(""))

                {
                    Utilities.log(currentThread, "the logs after 5 seconds" + driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div/div/div[5]/div/div/log-panel/md-content/code/text()")).getText());
                } else {

                }
            } catch (Exception e) {
                Utilities.log(e);
            }
        } catch (Exception e) {
            Utilities.log(currentThread,e.getMessage());
        }

    }


}
// /html/body/div[2]/div/div[2]/div/div/div[5]/div/div/log-panel/md-content/code