package com.micropoplar.pos.model;

import com.floreantpos.POSConstants;

public enum AgeRange {
  //@formatter:off
  AGE_UNKNOWN(-1, POSConstants.AGE_RANGE_OPTION_UNKNOWN),
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

  public static AgeRange fromType(int type) {
    switch (type) {
      case 1:
        return AGE_20_MINUS;

      case 2:
        return AGE_20_30;

      case 3:
        return AGE_30_40;

      case 4:
        return AGE_40_50;

      case 5:
        return AGE_50_PLUS;

      default:
        return AGE_UNKNOWN;
    }
  }

  public int getType() {
    return type;
  }

  public String getDisplayString() {
    return displayString;
  }

}
