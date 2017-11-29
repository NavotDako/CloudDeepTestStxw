package ActionTests;


import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import Utils.Utilities;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.openqa.selenium.By;

import Cloud_API.GetDeviceReservations;


public class ExtendSession extends BaseTest {


    String popupMessage = "";
    Time reservation;
    Time reservationEnd;

    @Test
    public void test() {

//        Date currentTime = null;
//
//        try {
//            currentTime = getCurrentDate();
//        } catch (ParseException e) {
//
//        }
//
//        Utilities.log(currentThread, "ExtendSession Test Starts");
//        String reservationString;
//        do {
//            reservationString = driver.findElement(By.xpath("/html/body/div[2]/div/div[1]/div/div/device-loupe/div/div/h3/span")).getText();
//            System.out.println(reservationString);
//            Utilities.sleep(currentThread,1000);
//        } while (reservationString.contains("00:00:00"));
//
//        reservation = toTime(reservationString);
//
//        Utilities.log(currentThread, "reservation Time end after : " + reservation + " hours");
//        Utilities.sleep(currentThread, 1000);
//
//        if (waitForElement("/html/body/div[2]/div/div[1]/div/div/device-loupe/div/div/div[2]/div[2]/button/md-icon")) {
//            driver.findElement(By.xpath("/html/body/div[2]/div/div[1]/div/div/device-loupe/div/div/div[2]/div[2]/button/md-icon")).click();
//        } else {
//            throw new Exception("The Extend Session element was not found");
//        }
//
//        Utilities.log(currentThread, "clicked on Extend Session Button");
//        Utilities.sleep(currentThread, 1000);
//
//        if (currentThread.STXWType.equals("MANUAL")) {
//            popupMessage = driver.findElement(By.xpath("//*[@id=\"toast-container\"]/div")).getText();
//            Utilities.log(currentThread, "test if message is popup " + popupMessage.replace("\n", "\t"));
//        } else {
//            popupMessage = driver.findElement(By.xpath("//*[@id=\"toast-container\"]/div/div[3]/div")).getText();
//            Utilities.log(currentThread, "test if message is popup " + popupMessage.replace("\n", "\t"));
//
//
//        }
//
//        Utilities.sleep(currentThread, 1000);
//
//        reservationEnd = toTime(driver.findElement(By.xpath("/html/body/div[2]/div/div[1]/div/div/device-loupe/div/div/h3/span")).getText());
//        Utilities.log(currentThread, "the new reservation Time End after : " + reservationEnd + " hours");
//
//        Time extendTime = new Time(0, 0, 0);
//        extendTime.setHours(reservationEnd.getHours() - reservation.getHours());
//        extendTime.setMinutes(reservationEnd.getMinutes() - reservation.getMinutes());
//        extendTime.setSeconds(reservationEnd.getSeconds() - reservation.getSeconds());
//        Utilities.log(currentThread, "extend Time End : " + extendTime);
//        Utilities.log(currentThread, "extend Time added : " + extendTime + " hours");
//
//
//        if (!extendTime.after(new Time(0, 29, 0)) && extendTime.before(new Time(0, 30, 0))) {
//            Utilities.log(currentThread, "extend time must be almost 30 min in UI");
//        }
//
//        String ID = currentThread.jsonDeviceInfo.getString("id");
//        Utilities.log(currentThread, "the device Id : " + ID);
//
//        JUnitCore.runClasses(GetDeviceReservations.class);
//        Utilities.log(currentThread, "Get Device Reservations " + currentThread.jsonArrayDeviceReservation);
//        JSONObject jsonDevcieReservationsObject = null;
//        DateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd" + " " + "HH:mm:ss.SSS+02:00");
//        int i;
//        boolean reserveFound = false;
//        for (i = 0; i < currentThread.jsonArrayDeviceReservation.length(); i++) {
//            if (currentThread.jsonArrayDeviceReservation.get(i) instanceof JSONObject) {
//                jsonDevcieReservationsObject = ((JSONObject) currentThread.jsonArrayDeviceReservation.get(i));
//                if (jsonDevcieReservationsObject.getString("title").contains(currentThread.properCase(currentThread.User))) {
//                    Utilities.log(currentThread, "test if its device reservation " + jsonDevcieReservationsObject + "suite");
//
//                    Date first = DateFormat.parse(jsonDevcieReservationsObject.getString("start").split("T")[0] + " " + jsonDevcieReservationsObject.getString("start").split("T")[1]);
//                    if ((Math.abs(currentTime.getTime() - first.getTime())) < 100000) {
//                        Date End = DateFormat.parse(jsonDevcieReservationsObject.getString("end").split("T")[0] + " " + jsonDevcieReservationsObject.getString("end").split("T")[1]);
//                        long extend = 0;
//                        if (currentThread.STXWType.equals("manual")) {
//                            extend = 1800000 * 2;
//                        } else {
//                            extend = 1800000 * 5;
//                        }
//                        if (End.getTime() - first.getTime() == extend) {
//                            reserveFound = true;
//                        }
//                    }
//                }
//
//            }
//        }
//        if (i == currentThread.jsonArrayDeviceReservation.length() && !reserveFound) {
//            Utilities.log(currentThread, "the reserve doesn't found");
//            Utilities.log(currentThread, "TEST - " + currentThread.testName + " HAS FAILED!!!");
//
//        }


    }


    public Time toTime(String strTime) {
        Time time = new Time(Integer.parseInt(strTime.split(":")[0]), Integer.parseInt(strTime.split(":")[1]), Integer.parseInt(strTime.split(":")[2].substring(0, 2)));
        return time;
    }

    public Date getCurrentDate() throws ParseException {
        DateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd" + " " + "hh:mm:ss.SSS+02:00");
        Date currDate = new Date();
        String year = Integer.toString(currDate.getYear() + 1900);
        String month = Integer.toString(currDate.getMonth() + 1);
        String day = Integer.toString(currDate.getDate());
        String hour = Integer.toString(currDate.getHours());
        String minuts = Integer.toString(currDate.getMinutes());
        String seconds = Integer.toString(currDate.getSeconds());

        String currDateText = year + "-" + month + "-" + day + " " + hour + ":" + minuts + ":" + seconds + ".000+02:00";
        currDate = DateFormat.parse(currDateText);

        return currDate;
    }
}
