package com.micropoplar.pos.model.base;

/**
 * Represent the shift for the compaign.
 * 
 * @author Destiny
 *
 */
public class CompaignShift {

  public static String REF = "CompaignShift";
  public static String PROP_ID = "id";
  public static String PROP_NAME = "name";
  public static String PROP_DESCRIPTION = "description";
  public static String PROP_SHIFT_LENGTH = "shiftLength"; // in minutes
  public static String PROP_ALL_DAY = "allDay";
  public static String PROP_START_TIME = "startTime";
  public static String PROP_END_TIME = "endTime";
  public static String PROP_DAY_BITSET = "dayBitset";

  //constructors
  public CompaignShift() {
    initialize();
  }

  /**
  * Constructor for primary key
  */
  public CompaignShift(Integer id) {
    this.setId(id);
    initialize();
  }

  /**
  * Constructor for required fields
  */
  public CompaignShift(Integer id, String name) {

    this.setId(id);
    this.setName(name);
    initialize();
  }

  protected void initialize() {}

  private int hashCode = Integer.MIN_VALUE;

  //primary key
  private Integer id;

  // fields
  private String name;
  private String description;
  private Integer shiftLength;
  private Boolean allDay;
  private String startTime;
  private String endTime;
  private String dayBitset;

  public int getHashCode() {
    return hashCode;
  }

  public void setHashCode(int hashCode) {
    this.hashCode = hashCode;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getShiftLength() {
    return shiftLength;
  }

  public void setShiftLength(Integer shiftLength) {
    this.shiftLength = shiftLength;
  }

  public Boolean getAllDay() {
    return allDay;
  }

  public void setAllDay(Boolean allDay) {
    this.allDay = allDay;
  }

  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public String getEndTime() {
    return endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  public String getDayBitset() {
    return dayBitset;
  }

  public void setDayBitset(String dayBitset) {
    this.dayBitset = dayBitset;
  }

}
