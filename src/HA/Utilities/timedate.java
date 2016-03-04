package HA.Utilities;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import HA.Properties.logApp;

public class timedate {

	public static String sDateTime;
	
	public static String getlocaltime(String time) throws Exception{

		DateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		DateFormat indianFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a");
		indianFormat.setTimeZone(TimeZone.getTimeZone("IST"));
		Date timestamp = utcFormat.parse(time);
		String output = indianFormat.format(timestamp);
		System.out.println(output);
		return output;

	}

	public static String getminsectime(String time) throws Exception{

		long minutes = TimeUnit.MILLISECONDS.toMinutes(Long.valueOf(time).longValue());
		if(minutes<1){
			minutes = TimeUnit.MILLISECONDS.toSeconds(Long.valueOf(time).longValue());
			time=String.valueOf(minutes)+" Secs";
			logApp.logger.info("Test Script Exetime in Secs: "+minutes);
		}
		else{
			time=String.valueOf(minutes)+" Mins";
			logApp.logger.info("Test Script Exetime in Mins: "+minutes);
		}
		return time;

	}

	public static String getCurrentTimeStamp() throws Exception, SQLException{

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
		String currentTime = sdf.format(date);
		System.out.println(currentTime); 

		return currentTime;
	}

	public static String getbuildtime() throws Exception, SQLException{

		String buildname = "Sample Build";
		String deploytime = getCurrentTimeStamp();
		String Verison = buildname+" deployed on "+deploytime;
		System.out.println(Verison);

		return Verison;
	}
	
	public static String GetDateTimeforHTML()
	{
		String strDate = null;
		SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");

		Date now = new Date();
		strDate = sdfDate.format(now);
		String strTime = sdfTime.format(now);

		strTime = strTime.replace(":", "-");
		sDateTime = strDate + "_" + strTime;
		
		return sDateTime;
	}





}
