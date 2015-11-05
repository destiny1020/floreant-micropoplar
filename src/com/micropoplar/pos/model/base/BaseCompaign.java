package com.micropoplar.pos.model.base;

import java.util.Date;
import java.util.Properties;
import java.util.Set;

import com.floreantpos.model.MenuCategory;
import com.floreantpos.model.MenuGroup;
import com.floreantpos.model.MenuItem;
import com.floreantpos.model.User;
import com.micropoplar.pos.model.CompaignType;

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
  public static String PROP_MEMBERSHIP = "membership";
  public static String PROP_CATEGORIES = "categories";
  public static String PROP_GROUPS = "groups";
  public static String PROP_ITEMS = "items";
  public static String PROP_EXCLUDED_ITEMS = "excludedItems";

  public static String PROP_SHIFTS = "compaignShift";
  public static String PROP_TYPE = "compaignType";
  public static String PROP_PROPERTIES = "compaignProperties";

  public static String PROP_CREATE_DATE = "createDate";
  public static String PROP_CREATOR = "creator";

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

  private Date modifiedTime;

  // fields
  private Integer id;
  private String name;
  private String description;
  private Boolean enabled = true;
  private Boolean exclusive;
  private Integer priority = 5;
  private Boolean repeatable;
  private Boolean global;
  private Boolean perTicket;
  private Boolean membership = true;

  // collections
  private Set<MenuCategory> categories;
  private Set<MenuGroup> groups;
  private Set<MenuItem> items; // include MenuItemSet as well
  private Set<MenuItem> excludedItems;

  private CompaignShift compaignShift;
  private CompaignType compaignType;
  private Properties compaignProperties;

  private Date createDate;
  private User creator;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public int getHashCode() {
    return hashCode;
  }

  public void setHashCode(int hashCode) {
    this.hashCode = hashCode;
  }

  public Date getModifiedTime() {
    return modifiedTime;
  }

  public void setModifiedTime(Date modifiedTime) {
    this.modifiedTime = modifiedTime;
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

  public Boolean getEnabled() {
    return enabled;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  public Boolean getExclusive() {
    return exclusive;
  }

  public void setExclusive(Boolean exclusive) {
    this.exclusive = exclusive;
  }

  public Integer getPriority() {
    return priority;
  }

  public void setPriority(Integer priority) {
    this.priority = priority;
  }

  public Boolean getRepeatable() {
    return repeatable;
  }

  public void setRepeatable(Boolean repeatable) {
    this.repeatable = repeatable;
  }

  public Boolean getGlobal() {
    return global;
  }

  public void setGlobal(Boolean global) {
    this.global = global;
  }

  public Boolean getPerTicket() {
    return perTicket;
  }

  public void setPerTicket(Boolean perTicket) {
    this.perTicket = perTicket;
  }

  public Boolean getMembership() {
    return membership;
  }

  public void setMembership(Boolean membership) {
    this.membership = membership;
  }

  public Set<MenuCategory> getCategories() {
    return categories;
  }

  public void setCategories(Set<MenuCategory> categories) {
    this.categories = categories;
  }

  public Set<MenuGroup> getGroups() {
    return groups;
  }

  public void setGroups(Set<MenuGroup> groups) {
    this.groups = groups;
  }

  public Set<MenuItem> getItems() {
    return items;
  }

  public void setItems(Set<MenuItem> items) {
    this.items = items;
  }

  public Set<MenuItem> getExcludedItems() {
    return excludedItems;
  }

  public void setExcludedItems(Set<MenuItem> excludedItems) {
    this.excludedItems = excludedItems;
  }

  public CompaignShift getCompaignShift() {
    return compaignShift;
  }

  public void setCompaignShift(CompaignShift compaignShift) {
    this.compaignShift = compaignShift;
  }

  public CompaignType getCompaignType() {
    return compaignType;
  }

  public void setCompaignType(CompaignType compaignType) {
    this.compaignType = compaignType;
  }

  public Properties getCompaignProperties() {
    return compaignProperties;
  }

  public void setCompaignProperties(Properties compaignProperties) {
    this.compaignProperties = compaignProperties;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public User getCreator() {
    return creator;
  }

  public void setCreator(User creator) {
    this.creator = creator;
  }

}
