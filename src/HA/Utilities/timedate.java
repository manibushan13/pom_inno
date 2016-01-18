package HA.Utilities;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import HA.TestAutomation.Driver;

public class timedate {

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
			Driver.logger.info("Test Script Exetime in Secs: "+minutes);
		}
		else{
			time=String.valueOf(minutes)+" Mins";
			Driver.logger.info("Test Script Exetime in Mins: "+minutes);
		}
		return time;
		
	}
	
	public static String getlocaltimeformat(String time) throws Exception{
		
		DateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");	    
	    DateFormat indianFormat = new SimpleDateFormat("yyyy-MM-dd KK:mm:ss a");	    
	    Date timestamp = utcFormat.parse(time);
	    String output = indianFormat.format(timestamp);
		System.out.println(output);
		return output;
		
	}
	
	public static String getbuildtime() throws Exception, SQLException{
		
		String buildname = DB.getDBdata("SELECT TOP 1 NAME FROM S_APP_VERSION ORDER BY IDX DESC");
		String deploytime = DB.getDBdata("SELECT TOP 1 CREATED_DATE FROM S_APP_VERSION ORDER BY IDX DESC");

		deploytime = getlocaltimeformat(deploytime);
		
		String HACPMVerison = buildname+" deployed on "+deploytime;
		System.out.println(HACPMVerison);
		
		return HACPMVerison;
		
	}
	
	

	
	
}
