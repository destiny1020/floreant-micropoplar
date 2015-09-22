package com.floreantpos.model.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
  private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMdd");
  private static final SimpleDateFormat RECEIPT_SHORT = new SimpleDateFormat("yy/MM/dd HH:mm");
  private static final SimpleDateFormat RECEIPT = new SimpleDateFormat("yyyy/MM/dd HH:mm");
  private static final SimpleDateFormat SDF_E = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E");

  private static final SimpleDateFormat REPORT_FULL =
      new SimpleDateFormat("yyyy/MM/dd HH:mm:ss E a ");
  private static final SimpleDateFormat REPORT_SHORT = new SimpleDateFormat("yyyy/MM/dd E");

  private static final SimpleDateFormat TICKET_VIEW_TIME =
      new SimpleDateFormat("yy/MM/dd HH:mm:ss");
  private static final SimpleDateFormat DOB_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");

  private static final SimpleDateFormat WECHAT_TIMESTAMP = new SimpleDateFormat("yyyyMMddHHmmss");

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

  public static String getReportFullDate(Date date) {
    return REPORT_FULL.format(date);
  }

  public static String getReportShortDate(Date date) {
    return REPORT_SHORT.format(date);
  }

  public static String getTicketViewDate(Date date) {
    return TICKET_VIEW_TIME.format(date);
  }

  public static SimpleDateFormat getDOBFormatter() {
    return DOB_FORMATTER;
  }

  public static String getWeChatTimestamp(Date date) {
    return WECHAT_TIMESTAMP.format(date);
  }
}
