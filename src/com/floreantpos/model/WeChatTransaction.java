package com.floreantpos.model;

import com.floreantpos.model.base.BaseWeChatTransaction;

public class WeChatTransaction extends BaseWeChatTransaction {

  private static final long serialVersionUID = 1L;

  public WeChatTransaction() {
    super();
  }

  /**
   * Constructor for primary key
   */
  public WeChatTransaction(java.lang.Integer id) {
    super(id);
  }

  /**
   * Constructor for required fields
   */
  public WeChatTransaction(java.lang.Integer id, java.lang.String transactionType,
      java.lang.Integer paymentType) {

    super(id, transactionType, paymentType);
  }

}
