package com.floreantpos.model;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.StringUtils;

import com.floreantpos.POSConstants;
import com.floreantpos.model.base.BaseRestaurant;


@XmlRootElement(name = "restaurant")
public class Restaurant extends BaseRestaurant {
  private static final long serialVersionUID = 1L;

  /* [CONSTRUCTOR MARKER BEGIN] */
  public Restaurant() {
    super();
  }

  /**
   * Constructor for primary key
   */
  public Restaurant(java.lang.Integer id) {
    super(id);
  }

  /* [CONSTRUCTOR MARKER END] */

  @Override
  public String getCurrencyName() {
    String currencyName = super.getCurrencyName();
    if (StringUtils.isEmpty(currencyName)) {
      return POSConstants.DEFAULT_CURRENCY_NAME;
    }
    return currencyName;
  }

  @Override
  public String getCurrencySymbol() {
    String currencySymbol = super.getCurrencySymbol();
    if (StringUtils.isEmpty(currencySymbol)) {
      currencySymbol = "￥";
    }
    return currencySymbol;
  }
}
