package com.micropoplar.pos.bo.ui.explorer.search;

import java.util.Date;

import com.floreantpos.bo.ui.ComboOption;

public class CustomerSearchDto {

  public static final int CUSTOMER_GENDER_ALL = -1;

  public static final int CUSTOMER_AGE_RANGE_ALL = 0;

  private String phone;
  private Date createTimeStart;
  private Date createTimeEnd;
  private Date lastActiveTimeStart;
  private Date lastActiveTimeEnd;
  private ComboOption gender;
  private ComboOption ageRange;

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public Date getCreateTimeStart() {
    return createTimeStart;
  }

  public void setCreateTimeStart(Date createTimeStart) {
    this.createTimeStart = createTimeStart;
  }

  public Date getCreateTimeEnd() {
    return createTimeEnd;
  }

  public void setCreateTimeEnd(Date createTimeEnd) {
    this.createTimeEnd = createTimeEnd;
  }

  public Date getLastActiveTimeStart() {
    return lastActiveTimeStart;
  }

  public void setLastActiveTimeStart(Date lastActiveTimeStart) {
    this.lastActiveTimeStart = lastActiveTimeStart;
  }

  public Date getLastActiveTimeEnd() {
    return lastActiveTimeEnd;
  }

  public void setLastActiveTimeEnd(Date lastActiveTimeEnd) {
    this.lastActiveTimeEnd = lastActiveTimeEnd;
  }

  public ComboOption getGender() {
    return gender;
  }

  public void setGender(ComboOption gender) {
    this.gender = gender;
  }

  public ComboOption getAgeRange() {
    return ageRange;
  }

  public void setAgeRange(ComboOption ageRange) {
    this.ageRange = ageRange;
  }

}
