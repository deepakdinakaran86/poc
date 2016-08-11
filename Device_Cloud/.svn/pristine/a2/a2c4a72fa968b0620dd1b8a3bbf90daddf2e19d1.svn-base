package com.pcs.analytics.util.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateTest {
	
	public static void main(String[] args) {
		//System.out.println(new SimpleDateFormat("MMMMM dd, yyyy ").format(new Date()));
		System.out.println(getFormattedTimeDuration(5400000l));
	}

	public static String getFormattedTimeDuration(long millis){
		String format = String.format("%02d:%02d:%02d", 
				TimeUnit.MILLISECONDS.toHours(millis),
				TimeUnit.MILLISECONDS.toMinutes(millis) -  
				TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), 
				TimeUnit.MILLISECONDS.toSeconds(millis) - 
				TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
		return format;
}

}