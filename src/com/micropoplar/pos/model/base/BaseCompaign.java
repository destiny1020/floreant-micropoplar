package com.micropoplar.pos.model.base;

import java.util.Date;
import java.util.Set;

import com.floreantpos.model.MenuCategory;
import com.floreantpos.model.MenuGroup;
import com.floreantpos.model.MenuItem;
import com.micropoplar.pos.model.MenuItemSet;

public class BaseCompaign {

  public static String REF = "Compaign";
  public static String PROP_ID = "id";
  public static String PROP_NAME = "name";
  public static String PROP_DESCRIPTION = "description";
  public static String PROP_ENABLED = "enabled";
  public static String PROP_EXCLUSIVE = "exclusive";
  public static String PROP_PRIORITY = "priority";
  public static String PROP_REPEATABLE = "repeatable";
  public static String PROP_GLOBAL = "global";
  public static String PROP_PER_TICKET = "perTicket";
  public static String PROP_BEVERAGE = "beverage";
  public static String PROP_MEMBERSHIP = "membership";
  public static String PROP_CATEGORIES = "categories";
  public static String PROP_GROUPS = "groups";
  public static String PROP_ITEMS = "items";
  public static String PROP_EXCLUDED_ITEMS = "excludedItems";
  public static String PROP_CREATE_DATE = "createDate";
  public static String PROP_CREATOR = "creator";

  public static String PROP_SHIFTS = "compaignShifts";
  public static String PROP_TYPE = "compaignType";
  public static String PROP_PROPERTIES = "compaignProperties";

  //constructors
  public BaseCompaign() {
    initialize();
  }

  /**
  * Constructor for primary key
  */
  public BaseCompaign(Integer id) {
    this.setId(id);
    initialize();
  }

  protected void initialize() {}

  private int hashCode = Integer.MIN_VALUE;

  // fields
  private Integer id;
  private Date modifiedTime;
  private String name;
  private String compaign;
  private Boolean enabled;
  private Boolean exclusive;
  private Integer priority;
  private Boolean repeatable;
  private Boolean global;
  private Boolean perTicket;
  private Boolean beverage;
  private Boolean membership;

  // collections
  private Set<MenuCategory> categories;
  private Set<MenuGroup> groups;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

}
