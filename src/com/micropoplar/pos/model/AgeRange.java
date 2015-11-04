package com.micropoplar.pos.model;

import com.floreantpos.POSConstants;

public enum AgeRange {
  //@formatter:off
  AGE_20_MINUS(1, POSConstants.AGE_RANGE_OPTION_1), 
  AGE_20_30(2, POSConstants.AGE_RANGE_OPTION_2),
  AGE_30_40(3, POSConstants.AGE_RANGE_OPTION_3), 
  AGE_40_50(4, POSConstants.AGE_RANGE_OPTION_4), 
  AGE_50_PLUS(5, POSConstants.AGE_RANGE_OPTION_5); 
  //@formatter:on

  private int type;
  private String displayString;

  private AgeRange(int type, String displayString) {
    this.type = type;
    this.displayString = displayString;
  }

  public int getType() {
    return type;
  }

  public String getDisplayString() {
    return displayString;
  }

}
