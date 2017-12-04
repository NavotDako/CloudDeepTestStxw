package STXWSuite.tests;


import java.sql.Time;

import STXWSuite.STXWBaseTest;
import Utils.Utilities;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;



public class ExtendSession extends STXWBaseTest {

    Time reservation;
    Time reservationEnd;

    @Test
    public void test() {

        Utilities.log(runner, "ExtendSession Test Starts");
        String reservationString;
        do {
            reservationString = driver.findElement(By.xpath("/html/body/div[2]/div/div[1]/div/div/device-loupe/div/div/h3/span")).getText();
            Utilities.log(runner,reservationString);
            Utilities.sleep(runner,1000);
        } while (reservationString.contains("00:00:00"));

        reservation = toTime(reservationString);

        Utilities.log(runner, "reservation Time end after : " + reservation + " hours");
        Utilities.sleep(runner, 1000);

        if (waitForElement("/html/body/div[2]/div/div[1]/div/div/device-loupe/div/div/div[2]/div[2]/button/md-icon")) {
            driver.findElement(By.xpath("/html/body/div[2]/div/div[1]/div/div/device-loupe/div/div/div[2]/div[2]/button/md-icon")).click();
        } else {
            Assert.fail("The Extend Session element was not found");
        }

        Utilities.log(runner, "Clicked on Extend Session Button");
        Utilities.sleep(runner, 1000);


        reservationEnd = toTime(driver.findElement(By.xpath("/html/body/div[2]/div/div[1]/div/div/device-loupe/div/div/h3/span")).getText());
        Utilities.log(runner, "The new reservation Time End after : " + reservationEnd + " hours");


        Time extendTime = new Time(0, 0, 0);
        extendTime.setHours(reservationEnd.getHours() - reservation.getHours());
        extendTime.setMinutes(reservationEnd.getMinutes() - reservation.getMinutes());
        extendTime.setSeconds(reservationEnd.getSeconds() - reservation.getSeconds());
        Utilities.log(runner, "extend Time End : " + extendTime);


        if (!extendTime.after(new Time(0, 28, 0)) && extendTime.before(new Time(0, 30, 0))) {
          Assert.fail("The extended time is not in the range 28-30 minutes");
        }

    }


    public Time toTime(String strTime) {
        Time time = new Time(Integer.parseInt(strTime.split(":")[0]), Integer.parseInt(strTime.split(":")[1]), Integer.parseInt(strTime.split(":")[2].substring(0, 2)));
        return time;
    }


}
