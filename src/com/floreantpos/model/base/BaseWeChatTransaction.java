package com.floreantpos.model.base;

import java.io.Serializable;

public class BaseWeChatTransaction extends com.floreantpos.model.PosTransaction
    implements Comparable, Serializable {

  public static String REF = "WeChatTransaction";
  public static String PROP_ID = "id";

  // constructors
  public BaseWeChatTransaction() {
    initialize();
  }

  /**
   * Constructor for primary key
   */
  public BaseWeChatTransaction(java.lang.Integer id) {
    super(id);
  }

  /**
   * Constructor for required fields
   */
  public BaseWeChatTransaction(java.lang.Integer id, java.lang.String transactionType,
      java.lang.Integer paymentType) {

    super(id, transactionType, paymentType);
  }

  private int hashCode = Integer.MIN_VALUE;

  @Override
  public boolean equals(Object obj) {
    if (null == obj)
      return false;
    if (!(obj instanceof com.floreantpos.model.WeChatTransaction))
      return false;
    else {
      com.floreantpos.model.WeChatTransaction weChatTransaction =
          (com.floreantpos.model.WeChatTransaction) obj;
      if (null == this.getId() || null == weChatTransaction.getId())
        return false;
      else
        return (this.getId().equals(weChatTransaction.getId()));
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
  public int compareTo(Object obj) {
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

}
