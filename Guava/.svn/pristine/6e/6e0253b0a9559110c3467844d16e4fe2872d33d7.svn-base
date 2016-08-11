/**
 * Copyright 2014 Pacific Controls Software Services LLC (PCSS). All Rights
 * Reserved.
 * 
 * This software is the property of Pacific Controls Software Services LLC and
 * its suppliers. The intellectual and technical concepts contained herein are
 * proprietary to PCSS. Dissemination of this information or reproduction of
 * this material is strictly forbidden unless prior written permission is
 * obtained from Pacific Controls Software Services.
 * 
 * PCSS MAKES NO REPRESENTATION OR WARRANTIES ABOUT THE SUITABILITY OF THE
 * SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED
 * WARRANTIES OF MERCHANTANILITY, FITNESS FOR A PARTICULAR PURPOSE, OR
 * NON-INFRINGMENT. PCSS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY
 * LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 */
package com.pcs.guava.commons.util;

import static org.apache.commons.lang.StringUtils.isBlank;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pcs.guava.constant.CommonConstants;

/**
 * The util for all Date and Time based calculations and formatting.
 * 
 * @author Riyas PH (pcseg296)
 * 
 */

public final class DateAndTimeUtil {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(DateAndTimeUtil.class);
	static final long NUM_100NS_INTERVALS_SINCE_UUID_EPOCH = 0x01b21dd213814000L;

	private DateAndTimeUtil() {

	}

	public static String getCurrentDateAndTime(String formatStrng) {
		return DateTimeFormat.forPattern(formatStrng).print(new DateTime());
	}

	public static String convertDateTimeToString(String formatStrng,
			DateTime date) {
		String dateString = null;
		if (!isBlank(formatStrng) && date != null) {
			DateTimeFormatter dateFormat = DateTimeFormat
					.forPattern(formatStrng);
			dateString = dateFormat.print(date.getMillis());
		}
		return dateString;
	}

	public static DateTime convertStringToDateTime(String formatStrng,
			String dateString) {
		DateTime dateTime = null;
		if (!isBlank(formatStrng) && !isBlank(dateString)) {
			dateTime = DateTime.parse(dateString,
					DateTimeFormat.forPattern(formatStrng));
		}
		return dateTime;
	}

	public static Date convertStringToDate(String formatStrng, String dateString) {
		Date date = null;
		if (!isBlank(formatStrng) && !isBlank(dateString)) {
			DateTime dateTime = DateTime.parse(dateString,
					DateTimeFormat.forPattern(formatStrng));
			date = dateTime.toDate();
		}
		return date;
	}

	public static Date convertLongToDate(Long dateLong) {
		Date date = new Date(dateLong);
		return date;
	}

	public static Long convertDateToLong(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		Long dateLong = calendar.getTimeInMillis();
		return dateLong;
	}

	public static String convertDateToString(String formatStrng, Date date) {
		String dateString = null;
		if (!isBlank(formatStrng) && date != null) {
			DateTimeFormatter dateFormat = DateTimeFormat
					.forPattern(formatStrng);
			dateString = dateFormat.print(date.getTime());
		}
		return dateString;
	}

	public static Date ceilDate(Date d) {
		try {
			if (d != null) {
				Calendar c = new GregorianCalendar();
				c.setTime(d);
				c.set(Calendar.HOUR, 23);
				c.set(Calendar.MINUTE, 59);
				c.set(Calendar.SECOND, 59);
				c.set(Calendar.MILLISECOND, 998);
				return c.getTime();
			}
		} catch (Exception e) {
			LOGGER.error("Error occured : ", e);
		}
		return null;
	}

	public static Date floorDate(Date d) {
		try {
			if (d != null) {
				Calendar c = new GregorianCalendar();
				c.setTime(d);
				c.set(Calendar.HOUR, 0);
				c.set(Calendar.MINUTE, 0);
				c.set(Calendar.SECOND, 0);
				c.set(Calendar.MILLISECOND, 0);
				return c.getTime();

			}
		} catch (Exception e) {
			LOGGER.error("Exception: ", e);
		}
		return null;
	}

	public static Date convertDateTimeToDate(Date d) {
		try {
			if (d != null) {
				Calendar c = Calendar.getInstance();
				c.setTime(d);
				c.set(Calendar.HOUR_OF_DAY, 0);
				c.set(Calendar.MINUTE, 0);
				c.set(Calendar.SECOND, 0);
				c.set(Calendar.MILLISECOND, 0);
				return c.getTime();

			}
		} catch (Exception e) {
			LOGGER.error("Exception: ", e);
		}
		return null;
	}

	public static List<Date> getDatesBetween(final Date date1, final Date date2) {
		List<Date> dates = new ArrayList<>();
		Calendar c1 = new GregorianCalendar();
		c1.setTime(date1);
		Calendar c2 = new GregorianCalendar();
		c2.setTime(date2);
		while ((c1.get(Calendar.YEAR) != c2.get(Calendar.YEAR))
				|| (c1.get(Calendar.MONTH) != c2.get(Calendar.MONTH))
				|| (c1.get(Calendar.DATE) != c2.get(Calendar.DATE))) {
			c1.add(Calendar.DATE, 1);
			dates.add(new Date(c1.getTimeInMillis()));
		}
		LOGGER.debug("Dates between {} and {}", date1, date2);
		return dates;
	}

	public static Date getDateFromUUID(UUID uuid) {
		Date date = new Date(
				(uuid.timestamp() - NUM_100NS_INTERVALS_SINCE_UUID_EPOCH) / 10000);
		return date;
	}
	

	public static Timestamp getCurrentTimestamp() {
	    java.util.Date date= new java.util.Date();
	    return(new Timestamp(date.getTime()));
    }
	
	public static Date addDaysToDate(final Date date, int noOfDays) {
	    Date newDate = new Date(date.getTime());
	    GregorianCalendar calendar = new GregorianCalendar();
	    calendar.setTime(newDate);
	    calendar.add(Calendar.DATE, noOfDays);
	    newDate.setTime(calendar.getTime().getTime());
	    return newDate;
	}
	
	public static int getDaysBetweenDates(DateTime startDate,DateTime endDate){
		return (Days.daysBetween(startDate, endDate)).getDays();
	}

	public static Date getCurrentUTCtime(){
		DateFormat format = new SimpleDateFormat(CommonConstants.DATEFORMAT_YYYY_MM_DD_HH_MM_SS);
		DateFormat dateFormatLocal = new SimpleDateFormat(CommonConstants.DATEFORMAT_YYYY_MM_DD_HH_MM_SS);
		format.setTimeZone(TimeZone.getTimeZone("GMT"));
		Date gmtTime = null;
		try {
			gmtTime = dateFormatLocal.parse( format.format(new Date()) );
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return gmtTime;
	}
}



/*
 * Add this 2014-07-26 09:06:24 2014-07-26T09:06:24.000+04:00
 * 2014-07-26T09:06:24.943+04:00 <dependency> <groupId>joda-time</groupId>
 * <artifactId>joda-time</artifactId> <version>2.3</version> </dependency>
 */