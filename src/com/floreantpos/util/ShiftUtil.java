package com.floreantpos.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.floreantpos.model.Shift;
import com.floreantpos.model.dao.ShiftDAO;

public class ShiftUtil {

  private static final String DEFAULT_SHIFT = "DEFAULT SHIFT";
  private static final Calendar calendar = Calendar.getInstance();
  private static final Calendar calendar2 = Calendar.getInstance();
  private static final NumberFormat format = new DecimalFormat("00");

  static {
    calendar.clear();
  }

  /**
   * For shift, we only care for hour and minute, not date.
   * 
   * @param shiftTime
   * @return
   */
  public static Date formatShiftTime(Date shiftTime) {
    calendar.clear();
    calendar2.setTime(shiftTime);

    calendar.set(Calendar.HOUR, calendar2.get(Calendar.HOUR));
    calendar.set(Calendar.MINUTE, calendar2.get(Calendar.MINUTE));
    calendar.set(Calendar.AM_PM, calendar2.get(Calendar.AM_PM));

    return calendar.getTime();
  }

  public static Date buildShiftStartTime(int startHour, int startMin, int startAmPm, int endHour,
      int endMin, int endAmPm) {
    startHour = startHour == 12 ? 0 : startHour;

    calendar.clear();

    calendar.set(Calendar.HOUR, startHour);
    calendar.set(Calendar.MINUTE, startMin);

    calendar.set(Calendar.AM_PM, startAmPm);

    return calendar.getTime();
  }

  public static Date buildShiftEndTime(int startHour, int startMin, int startAmPm, int endHour,
      int endMin, int endAmPm) {
    endHour = endHour == 12 ? 0 : endHour;

    calendar.clear();

    calendar.set(Calendar.HOUR, endHour);
    calendar.set(Calendar.MINUTE, endMin);

    calendar.set(Calendar.AM_PM, endAmPm);

    if (startAmPm == Calendar.PM && endAmPm == Calendar.AM) {
      calendar.add(Calendar.DAY_OF_MONTH, 1);
    }

    return calendar.getTime();
  }


  public static String buildShiftTimeRepresentation(Date shiftTime) {
    calendar.setTime(shiftTime);

    String s = "";
    s = format.format(calendar.get(Calendar.HOUR) == 0 ? 12 : calendar.get(Calendar.HOUR));
    s += ":" + format.format(calendar.get(Calendar.MINUTE));
    s += calendar.get(Calendar.AM_PM) == Calendar.AM ? " AM" : " PM";
    return s;
  }

  public static String getDateRepresentation(Date date) {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MMM-dd hh:mm:ss a");
    return formatter.format(date);
  }

  public static Shift getCurrentShift() {
    Calendar calendar = Calendar.getInstance();
    Calendar calendar2 = Calendar.getInstance();
    calendar.clear();

    calendar.set(Calendar.HOUR, calendar2.get(Calendar.HOUR));
    calendar.set(Calendar.MINUTE, calendar2.get(Calendar.MINUTE));
    calendar.set(Calendar.AM_PM, calendar2.get(Calendar.AM_PM));

    Date currentTime = calendar.getTime();

    ShiftDAO shiftDAO = new ShiftDAO();
    List<Shift> shifts = shiftDAO.findAll();

    Shift defaultShift = findDefaultShift(shifts);

    Shift currentShift = findCurrentShift(currentTime, shifts);
    if (currentShift != null) {
      return currentShift;
    }

    calendar.add(Calendar.DATE, 1);
    currentTime = calendar.getTime();

    currentShift = findCurrentShift(currentTime, shifts);
    if (currentShift != null) {
      return currentShift;
    }

    if (defaultShift == null) {
      return getDefaultShift();
    }

    return defaultShift;
  }

  private static Shift findDefaultShift(List<Shift> shifts) {
    for (Iterator iterator = shifts.iterator(); iterator.hasNext();) {
      Shift shift = (Shift) iterator.next();

      if (DEFAULT_SHIFT.equalsIgnoreCase(shift.getName()) && shift.getShiftLength() == 86400000) {
        iterator.remove();
        return shift;
      }
    }

    return null;
  }

  private static Shift findCurrentShift(Date currentTime, List<Shift> shifts) {
    for (Shift shift : shifts) {
      Date startTime = new Date(shift.getStartTime().getTime());
      Date endTime = new Date(shift.getEndTime().getTime());

      if (currentTime.after(startTime) && currentTime.before(endTime)) {
        return shift;
      }
    }

    return null;
  }

  private static Shift getDefaultShift() {
    Calendar calendar = Calendar.getInstance();
    Calendar calendar2 = Calendar.getInstance();

    calendar.clear();
    calendar.set(Calendar.HOUR, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.AM_PM, Calendar.AM);

    calendar2.clear();
    calendar2.add(Calendar.DATE, 1);
    calendar2.set(Calendar.HOUR, 0);
    calendar2.set(Calendar.MINUTE, 0);
    calendar2.set(Calendar.AM_PM, Calendar.AM);

    Shift defaultShift = new Shift();
    defaultShift.setName(DEFAULT_SHIFT);
    defaultShift.setStartTime(calendar.getTime());
    defaultShift.setEndTime(calendar2.getTime());
    defaultShift.setShiftLength(calendar2.getTimeInMillis() - calendar.getTimeInMillis());

    ShiftDAO shiftDAO = ShiftDAO.getInstance();
    shiftDAO.saveOrUpdate(defaultShift);

    return defaultShift;
  }
}
