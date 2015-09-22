package com.floreantpos.ui.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.jdesktop.swingx.JXDatePicker;

public class UiUtil {
  private static final SimpleDateFormat DOB_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");

  public static JXDatePicker getCurrentMonthStart() {
    Locale locale = Locale.getDefault();

    Calendar c = Calendar.getInstance(locale);
    c.set(Calendar.DAY_OF_MONTH, 1);
    JXDatePicker datePicker = new JXDatePicker(c.getTime(), locale);

    return datePicker;
  }

  public static JXDatePicker getCurrentMonthEnd() {
    Locale locale = Locale.getDefault();

    Calendar c = Calendar.getInstance(locale);
    c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
    JXDatePicker datePicker = new JXDatePicker(c.getTime(), locale);

    return datePicker;
  }

  public static String getDobStr(Date dob) {
    if (dob != null) {
      return DOB_FORMATTER.format(dob);
    } else {
      return "";
    }
  }
}
