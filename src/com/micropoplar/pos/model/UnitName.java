package com.micropoplar.pos.model;

import com.floreantpos.POSConstants;

/**
 * 计量单位枚举类型
 * 
 * @author destiny1020
 *
 */
public enum UnitName {

  UNIT(POSConstants.UNITNAME_UNIT), CUP(POSConstants.UNITNAME_CUP), BOWL(
      POSConstants.UNITNAME_BOWL), PLATE(POSConstants.UNITNAME_PLATE), BOTTLE(
          POSConstants.UNITNAME_BOTTLE);

  private String unitName;

  private UnitName(String unitName) {
    this.unitName = unitName;
  }

  public String getUnitName() {
    return unitName;
  }

}
