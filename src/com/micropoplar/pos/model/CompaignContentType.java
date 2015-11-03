package com.micropoplar.pos.model;

import com.floreantpos.POSConstants;

public enum CompaignContentType {

  N_DISCOUNT(POSConstants.COMPAIGN_TYPE_N_DISCOUNT), N_OFF(
      POSConstants.COMPAIGN_TYPE_N_OFF), A_DISCOUNT(POSConstants.COMPAIGN_TYPE_A_DISCOUNT), A_OFF(
          POSConstants.COMPAIGN_TYPE_A_OFF), TIME_DISCOUNT(
              POSConstants.COMPAIGN_TYPE_TIME_DISCOUNT), TIME_OFF(
                  POSConstants.COMPAIGN_TYPE_TIME_OFF);

  private String description;

  private CompaignContentType(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

}
