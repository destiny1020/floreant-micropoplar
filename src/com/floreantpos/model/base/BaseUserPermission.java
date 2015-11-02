package com.floreantpos.model.base;

import java.io.Serializable;


/**
 * This is an object that contains data related to the USER_PERMISSION table. Do not modify this
 * class because it will be overwritten if the configuration file related to this class is modified.
 *
 * @hibernate.class table="USER_PERMISSION"
 */

public abstract class BaseUserPermission implements Comparable<BaseUserPermission>, Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public static String REF = "UserPermission";
  public static String PROP_NAME = "name";
  public static String PROP_VALUE = "value";

  // constructors
  public BaseUserPermission() {
    initialize();
  }

  /**
   * Constructor for primary key
   */
  public BaseUserPermission(int value, java.lang.String name) {
    this.value = value;
    this.setName(name);
    initialize();
  }

  protected void initialize() {}



  private int hashCode = Integer.MIN_VALUE;

  // primary key
  private Integer value;
  private java.lang.String name;


  /**
   * Return the unique identifier of this class
   * 
   * @hibernate.id generator-class="assigned" column="NAME"
   */
  public java.lang.String getName() {
    return name;
  }

  /**
   * Set the unique identifier of this class
   * 
   * @param name the new ID
   */
  public void setName(java.lang.String name) {
    this.name = name;
    this.hashCode = Integer.MIN_VALUE;
  }



  public boolean equals(Object obj) {
    if (null == obj)
      return false;
    if (!(obj instanceof com.floreantpos.model.UserPermission))
      return false;
    else {
      com.floreantpos.model.UserPermission userPermission =
          (com.floreantpos.model.UserPermission) obj;
      if (null == this.getValue() || null == userPermission.getValue())
        return false;
      else
        return (this.getValue().equals(userPermission.getValue()));
    }
  }

  public int hashCode() {
    if (Integer.MIN_VALUE == this.hashCode) {
      if (null == this.getName())
        return super.hashCode();
      else {
        String hashStr = this.getClass().getName() + ":" + this.getName().hashCode();
        this.hashCode = hashStr.hashCode();
      }
    }
    return this.hashCode;
  }

  public int compareTo(BaseUserPermission obj) {
    if (obj.hashCode() > hashCode())
      return 1;
    else if (obj.hashCode() < hashCode())
      return -1;
    else
      return 0;
  }

  public String toString() {
    return super.toString();
  }

  public Integer getValue() {
    return value;
  }

  public void setValue(Integer value) {
    this.value = value;
  }

}
