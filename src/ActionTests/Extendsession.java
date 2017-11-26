package ActionTests;


import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.openqa.selenium.By;

import Cloud_API.GetDeviceReservations;


public class Extendsession extends BaseTest {

	
	String popupMessage = "";
	Time reservation;
	Time reservationEnd;

	@Test
	public void test() {
		
		try {
			    Date currentTime = getCurrentDate();	
				System.out.println("Extendsession!!");
				log("Enter to ExtendSessionTest");

				reservation = toTime(driver.findElement(By.xpath("/html/body/div[2]/div/div[1]/div/div/device-loupe/div/div/h3/span")).getText());
				log("reservation Time end after : " + reservation + " hours");
				
				driver.findElement(By.xpath("/html/body/div[2]/div/div[1]/div/div/device-loupe/div/div/div[2]/div[2]/button")).click();
				log("click on Extend Session Button");
				
				try{Thread.sleep(2000);
				log("wait 2 seconds");}catch(Exception e) {writeFailedLineInLog("failed to wait 2 seconds " + e);}
				
				
				
				if(CurrentThread.STXW.equals("manual"))
				{
					popupMessage = driver.findElement(By.xpath("//*[@id=\"toast-container\"]/div")).getText();
					log("test if message is popup " + popupMessage);
				}
				else
				{
					popupMessage = driver.findElement(By.xpath("//*[@id=\"toast-container\"]/div/div[3]/div")).getText();
					log("test if message is popup " + popupMessage);
					
					
				}
				
				try{Thread.sleep(2000);
				log("wait 2 seconds");}catch(Exception e) {writeFailedLineInLog("failed to wait 2 seconds " + e);}
				
				reservationEnd = toTime(driver.findElement(By.xpath("/html/body/div[2]/div/div[1]/div/div/device-loupe/div/div/h3/span")).getText());
				log("the new reservation Time End after : " + reservationEnd + " hours");
				
				Time extendTime = new Time(0,0,0);
				extendTime.setHours (reservationEnd.getHours() - reservation.getHours()) ;
				extendTime.setMinutes(reservationEnd.getMinutes() - reservation.getMinutes());
				extendTime.setSeconds(reservationEnd.getSeconds() - reservation.getSeconds());
				System.out.println("extend Time End : " + extendTime );
				log("extend Time added : " + extendTime + " hours");
				
				
				if(!extendTime.after(new Time(0,29,0)) && extendTime.before(new Time(0,30,0))) 
				{
					passed = "failed";
					writeFailedLineInLog("extend time must be almost 30 min in UI");
				}
				
				String ID = CurrentThread.jsonDeviceInfo.getString("id");
				log("the device Id : " + ID);
				
				try{Thread.sleep(5000);
				log("wait 5 seconds");}catch(Exception e) {writeFailedLineInLog("failed to wait 5 seconds " + e);}
				
				
				/***********************************************************************/
				JUnitCore.runClasses(GetDeviceReservations.class);
				log("Get Device Reservations " + CurrentThread.jsonArrayDeviceReservation );
				JSONObject jsonDevcieReservationsObject = null;
				DateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd" + " " + "HH:mm:ss.SSS+02:00");
				int i; boolean reserveFound = false;
				for(i=0;i<CurrentThread.jsonArrayDeviceReservation.length();i++)
				{
					   if ( CurrentThread.jsonArrayDeviceReservation.get(i) instanceof JSONObject ) 
				        {
						   jsonDevcieReservationsObject = ((JSONObject)CurrentThread.jsonArrayDeviceReservation.get(i));
						   if(jsonDevcieReservationsObject.getString("title").contains(CurrentThread.properCase(CurrentThread.User))) 
							   log("test if its device reservation " + jsonDevcieReservationsObject + "suite");
						   {
							   
							   Date first = DateFormat.parse(jsonDevcieReservationsObject.getString("start").split("T")[0] + " " + jsonDevcieReservationsObject.getString("start").split("T")[1]);
							   if((Math.abs(currentTime.getTime() - first.getTime())) < 100000) 
							   {   								
								   Date End = DateFormat.parse(jsonDevcieReservationsObject.getString("end").split("T")[0] + " " + jsonDevcieReservationsObject.getString("end").split("T")[1]);
								   long extend = 0;
								   if(CurrentThread.STXW.equals("manual")) 
								   {
									   extend = 1800000*2;
								   }
								   else 
								   {
									   extend = 1800000*5;
								   }
								   if(End.getTime()-first.getTime() == extend) 
								   {
									   reserveFound = true;
								   }
							   }
						   }
						   
				        }
				}
				if(i==CurrentThread.jsonArrayDeviceReservation.length() && !reserveFound) 
				{
					writeFailedLineInLog("the reserve doesn't found");
					passed = "failed";
				}
				/***********************************************************************/							
		}
		catch(Exception e)
		{
			writeFailedLineInLog(e.toString());
			passed = "failed";
		}
		
	}

	public Time toTime(String strTime){
		Time time = new Time(Integer.parseInt(strTime.split(":")[0]),Integer.parseInt(strTime.split(":")[1]),Integer.parseInt(strTime.split(":")[2].substring(0, 2)));
		return time;
	}
	
	public Date getCurrentDate() throws ParseException{
		DateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd" + " " + "hh:mm:ss.SSS+02:00" );
		Date currDate = new Date();
		String year = Integer.toString(currDate.getYear()+1900);
		String month = Integer.toString(currDate.getMonth()+1);
		String day = Integer.toString(currDate.getDate());
		String hour = Integer.toString(currDate.getHours());
		String minuts = Integer.toString(currDate.getMinutes());
		String seconds = Integer.toString(currDate.getSeconds());
		
		String currDateText = year + "-" + month + "-" + day + " " + hour + ":" + minuts + ":" + seconds + ".000+02:00" ;
		currDate = DateFormat.parse(currDateText);
		
		return currDate;
	}
}
