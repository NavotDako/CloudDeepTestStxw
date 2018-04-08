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
            reservationString = driver.findElement(By.xpath("//*[@id='session_time_left']")).getText();

            try
            {
                Utilities.log(runner,reservationString);
                Utilities.sleep(runner,1000);
            }
            catch(Exception e) {}
        } while (reservationString.contains("00:00:00"));

        reservation = toTime(reservationString);

        Utilities.log(runner, "Reservation time ends after : " + reservation + " hours");
        Utilities.sleep(runner, 1000);

        if (waitForElement("//*[@id='session_extend']")) {
            driver.findElement(By.xpath("//*[@id='session_extend']")).click();
        } else {
            Assert.fail("Extend session element is not found");
        }

        Utilities.log(runner, "Clicked on Extend Session Button");
        Utilities.sleep(runner, 10000);


        reservationEnd = toTime(driver.findElement(By.xpath("//*[@id='session_time_left']")).getText());
        Utilities.log(runner, "The new reservation ends after : " + reservationEnd + " hours");


        Time extendTime = new Time(0, 0, 0);
        extendTime.setHours(reservationEnd.getHours() - reservation.getHours());
        extendTime.setMinutes(reservationEnd.getMinutes() - reservation.getMinutes());
        extendTime.setSeconds(reservationEnd.getSeconds() - reservation.getSeconds());
        Utilities.log(runner, "Extend time ends after : " + extendTime);


        if (!(extendTime.after(new Time(0, 28, 0)) && extendTime.before(new Time(0, 30, 0))) && runner.STXWType.equals("manual")) {
            Utilities.log(runner, "getPageSource - " + driver.getPageSource().replace("\n", "\t"));
            Assert.fail("The extended time is not in the range 28-30 minutes");
        }
        if (!(extendTime.after(new Time(1, 58, 0)) && extendTime.before(new Time(2, 00, 0))) && runner.STXWType.equals("automation")) {
            Utilities.log(runner, "getPageSource - " + driver.getPageSource().replace("\n", "\t"));
            Assert.fail("The extended time is not in the range 1:58 to 2:00 hours");
        }

    }


    public Time toTime(String strTime) {
        Time time = new Time(Integer.parseInt(strTime.split(":")[0]), Integer.parseInt(strTime.split(":")[1]), Integer.parseInt(strTime.split(":")[2].substring(0, 2)));
        return time;
    }


}
