package Cloud_API;

import static org.junit.Assert.*;


import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;



public class training {

	@Test
	public void test() throws JSONException, ParseException {
		
		// 2017-11-22T13:56:38.927+02:00
//		DateFormat tt = new SimpleDateFormat("yyyy-MM-dd" + " " + "hh:mm:ss.SSS+02:00" );
		                                      
//		java.util.Date d = tt.parse("2017-11-22 13:56:38.927+02:00");
//		System.out.println(d);
		
//		String s = "2017-11-22T13:56:38.927+02:00";
//		s=s.split("T")[0] + " " + s.split("T")[1];
//		System.out.println(s);
//		Date date = tt.parse(s);
//		Utilities.log(currentThread,"date : " + date);
//		Date currDate = getCurrentDate();
//		try{Thread.sleep(5000);}catch(Exception e) {}
//		Date afterTime = getCurrentDate();
//		System.out.println((afterTime.getTime() - currDate.getTime()));
//		
//		int year = currDate.getYear();
//		int month = currDate.getMonth();
//		int day = currDate.getDay();
//		int hour = currDate.getHours();
//		int minuts = currDate.getMinutes();
//		int seconds = currDate.getSeconds();
//		
//		
		
//		Date d = new Date();
//		Utilities.log(currentThread,"year " + d.getYear() + " day : " + d.getDay() + " month " + d.getMonth() + " hours " + d.getHours() + "minuts " + d.getMinutes() + " seconds " + d.getSeconds());
//		String s = "ayoubprojectadmindeeptest1@ayalas-mac-mini.local";
//		String t = "ayoubProjectAdminDeepTest1";
//		
//		if(s.contains(properCase(t))) 
//		{
//			Utilities.log(currentThread,"yes");
//		}
//		System.out.println(properCase(t));
//	
		
		Date d = new Date();
		Date t = new Date();
		
		t.setMinutes(t.getMinutes() + 30);
		
		System.out.println(t.getTime() - d.getTime());
	}

	
	String properCase (String inputVal) {
	    // Empty strings should be returned as-is.

	    if (inputVal.length() == 0) return "";

	    // Strings with only one character uppercased.

	    if (inputVal.length() == 1) return inputVal.toLowerCase();

	    // Otherwise uppercase first letter, lowercase the rest.

	    return inputVal.substring(0,1).toLowerCase()
	        + inputVal.substring(1).toLowerCase();
	}
	
	public Date getCurrentDate() throws ParseException 
	{
		DateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd" + " " + "hh:mm:ss.SSS+02:00" );
		Date currDate = new Date();
		String year = Integer.toString(currDate.getYear());
		String month = Integer.toString(currDate.getMonth());
		String day = Integer.toString(currDate.getDay());
		String hour = Integer.toString(currDate.getHours());
		String minuts = Integer.toString(currDate.getMinutes());
		String seconds = Integer.toString(currDate.getSeconds());
		
		String currDateText = year + "-" + month + "-" + day + " " + hour + ":" + minuts + ":" + seconds + ".000+02:00" ;
		currDate = DateFormat.parse(currDateText);
		
		return currDate;
	}
	
	public Time toTime(String strTime)
	{
		Time time = new Time(Integer.parseInt(strTime.split(":")[0]),Integer.parseInt(strTime.split(":")[1]),Integer.parseInt(strTime.split(":")[2].substring(0, 2)));
		return time;
	}

}
//<span aria-label="Remaining time for reservation">
//02:40:40  
//
//</span>