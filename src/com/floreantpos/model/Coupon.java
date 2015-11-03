package com.floreantpos.model;

import com.floreantpos.model.base.BaseCoupon;



public class Coupon extends BaseCoupon {
  private static final long serialVersionUID = 1L;

  /* [CONSTRUCTOR MARKER BEGIN] */
  public Coupon() {
    super();
  }

  /**
   * Constructor for primary key
   */
  public Coupon(java.lang.Integer id) {
    super(id);
  }

  /* [CONSTRUCTOR MARKER END] */

  public final static int FREE_AMOUNT = 0;
  public final static int FIXED_PER_CATEGORY = 1;
  public final static int FIXED_PER_ITEM = 2;
  public final static int FIXED_PER_ORDER = 3;
  public final static int PERCENTAGE_PER_CATEGORY = 4;
  public final static int PERCENTAGE_PER_ITEM = 5;
  public final static int PERCENTAGE_PER_ORDER = 6;

  public final static String[] COUPON_TYPE_NAMES = {"无限制抵扣券", "一级类目抵扣券", "商品抵扣券", "Fixed Per Order",
      "Percentage Per Category", "Percentage Per Item", "Percentage Per Order"};

  @Override
  public String toString() {
    return COUPON_TYPE_NAMES[getType()];
  }

}
