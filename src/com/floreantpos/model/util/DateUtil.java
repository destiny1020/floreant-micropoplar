package com.floreantpos.model.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMdd");
	private static final SimpleDateFormat RECEIPT_SHORT = new SimpleDateFormat("yy/MM/dd HH:mm");
	private static final SimpleDateFormat RECEIPT = new SimpleDateFormat("yyyy/MM/dd HH:mm");
	private static final SimpleDateFormat SDF_E = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E");

	public static String getTodayString() {
		return SDF.format(new Date());
	}

	public static Date startOfDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);

		return new Date(cal.getTimeInMillis());
	}

	public static Date endOfDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		// cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + 1);

		return new Date(cal.getTimeInMillis());
	}

	public static String getNowString() {
		return getDateString(new Date());
	}

	public static String getDateString(Date date) {
		return SDF_E.format(date);
	}
	
	public static String getReceiptDateTime(Date date) {
		return RECEIPT.format(date);
	}
	
	public static String getReceiptDate(Date date) {
		return RECEIPT_SHORT.format(date);
	}
}
