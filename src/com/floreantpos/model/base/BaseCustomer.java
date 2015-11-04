package com.floreantpos.model.base;

import java.io.Serializable;
import java.util.Date;


/**
 * This is an object that contains data related to the CUSTOMER table. Do not modify this class
 * because it will be overwritten if the configuration file related to this class is modified.
 *
 * @hibernate.class table="CUSTOMER"
 */

public abstract class BaseCustomer implements Comparable<BaseCustomer>, Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public static String REF = "Customer";
  public static String PROP_ID = "id";
  public static String PROP_PHONE = "phone";
  public static String PROP_AGE_RANGE = "ageRange";
  public static String PROP_GENDER = "gender";
  public static String PROP_NAME = "name";
  public static String PROP_DOB = "dob";
  public static String PROP_EMAIL = "email";
  public static String PROP_ADDRESS = "address";
  public static String PROP_NOTE = "note";
  public static String PROP_CREATE_TIME = "createTime";
  public static String PROP_LAST_ACTIVE_TIME = "lastActiveTime";

  // basic stat below
  public static String PROP_TOTAL_TICKET_NUMBER = "totalTicketNumber";
  public static String PROP_TOTAL_AMOUNT_BEFORE_DISCOUNT = "totalAmountBeforeDiscount";
  public static String PROP_TOTAL_DISCOUNT = "totalDiscount";
  public static String PROP_TOTAL_AMOUNT_AFTER_DISCOUNT = "totalAmountAfterDiscount";

  public static final int GENDER_FEMALE = 0;
  public static final int GENDER_MALE = 1;

  // constructors
  public BaseCustomer() {
    initialize();
  }

  /**
   * Constructor for primary key
   */
  public BaseCustomer(java.lang.Integer id) {
    this.setId(id);
    initialize();
  }

  protected void initialize() {}



  private int hashCode = Integer.MIN_VALUE;

  // primary key
  private java.lang.Integer id;

  java.util.Date modifiedTime;

  // fields
  private java.lang.String phone;
  private Integer ageRange = -1; // unknown as default
  private Integer gender = GENDER_FEMALE; // female as default
  private java.lang.String name;
  private Date dob;
  private java.lang.String email;
  private java.lang.String address;
  private java.lang.String note;

  private Date createTime;
  private Date lastActiveTime;

  private Integer totalTicketNumber = 0;
  private Double totalAmountBeforeDiscount = 0.0;
  private Double totalDiscount = 0.0;
  private Double totalAmountAfterDiscount = 0.0;

  // collections
  private java.util.Map<String, String> properties;

  @Override
  public boolean equals(Object obj) {
    if (null == obj)
      return false;
    if (!(obj instanceof com.floreantpos.model.Customer))
      return false;
    else {
      com.floreantpos.model.Customer customer = (com.floreantpos.model.Customer) obj;
      if (null == this.getId() || customer.getId() == null)
        return false;
      else
        return (this.getId().equals(customer.getId()));
    }
  }

  @Override
  public int hashCode() {
    if (Integer.MIN_VALUE == this.hashCode) {
      if (null == this.getId())
        return super.hashCode();
      else {
        String hashStr = this.getClass().getName() + ":" + this.getId().hashCode();
        this.hashCode = hashStr.hashCode();
      }
    }
    return this.hashCode;
  }

  @Override
  public int compareTo(BaseCustomer obj) {
    if (obj.hashCode() > hashCode())
      return 1;
    else if (obj.hashCode() < hashCode())
      return -1;
    else
      return 0;
  }

  @Override
  public String toString() {
    return super.toString();
  }

  public int getHashCode() {
    return hashCode;
  }

  public void setHashCode(int hashCode) {
    this.hashCode = hashCode;
  }

  public java.lang.Integer getId() {
    return id;
  }

  public void setId(java.lang.Integer id) {
    this.id = id;
  }

  public java.util.Date getModifiedTime() {
    return modifiedTime;
  }

  public void setModifiedTime(java.util.Date modifiedTime) {
    this.modifiedTime = modifiedTime;
  }

  public java.lang.String getPhone() {
    return phone;
  }

  public void setPhone(java.lang.String phone) {
    this.phone = phone;
  }

  public java.lang.String getName() {
    return name;
  }

  public void setName(java.lang.String name) {
    this.name = name;
  }

  public Date getDob() {
    return dob;
  }

  public void setDob(Date dob) {
    this.dob = dob;
  }

  public java.lang.String getEmail() {
    return email;
  }

  public void setEmail(java.lang.String email) {
    this.email = email;
  }

  public java.lang.String getAddress() {
    return address;
  }

  public void setAddress(java.lang.String address) {
    this.address = address;
  }

  public java.lang.String getNote() {
    return note;
  }

  public void setNote(java.lang.String note) {
    this.note = note;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Date getLastActiveTime() {
    return lastActiveTime;
  }

  public void setLastActiveTime(Date lastActiveTime) {
    this.lastActiveTime = lastActiveTime;
  }

  public Integer getTotalTicketNumber() {
    return totalTicketNumber;
  }

  public void setTotalTicketNumber(Integer totalTicketNumber) {
    this.totalTicketNumber = totalTicketNumber;
  }

  public Double getTotalAmountBeforeDiscount() {
    return totalAmountBeforeDiscount;
  }

  public void setTotalAmountBeforeDiscount(Double totalAmountBeforeDiscount) {
    this.totalAmountBeforeDiscount = totalAmountBeforeDiscount;
  }

  public Double getTotalDiscount() {
    return totalDiscount;
  }

  public void setTotalDiscount(Double totalDiscount) {
    this.totalDiscount = totalDiscount;
  }

  public Double getTotalAmountAfterDiscount() {
    return totalAmountAfterDiscount;
  }

  public void setTotalAmountAfterDiscount(Double totalAmountAfterDiscount) {
    this.totalAmountAfterDiscount = totalAmountAfterDiscount;
  }

  public java.util.Map<String, String> getProperties() {
    return properties;
  }

  public void setProperties(java.util.Map<String, String> properties) {
    this.properties = properties;
  }

  public Integer getAgeRange() {
    return ageRange;
  }

  public void setAgeRange(Integer ageRange) {
    this.ageRange = ageRange;
  }

  public Integer getGender() {
    return gender;
  }

  public void setGender(Integer gender) {
    this.gender = gender;
  }


}
