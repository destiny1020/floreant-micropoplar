package com.micropoplar.pos.model;

import com.floreantpos.POSConstants;

public enum CompaignType {

  //@formatter:off
  N_DISCOUNT(1, POSConstants.COMPAIGN_TYPE_N_DISCOUNT), 
  N_OFF(2, POSConstants.COMPAIGN_TYPE_N_OFF), 
  A_DISCOUNT(3, POSConstants.COMPAIGN_TYPE_A_DISCOUNT), 
  A_OFF(4, POSConstants.COMPAIGN_TYPE_A_OFF), 
  TIME_DISCOUNT(5, POSConstants.COMPAIGN_TYPE_TIME_DISCOUNT), 
  TIME_OFF(6, POSConstants.COMPAIGN_TYPE_TIME_OFF);
  //@formatter:on

  private int type;
  private String description;

  private CompaignType(int type, String description) {
    this.type = type;
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

  public int getType() {
    return type;
  }

}
