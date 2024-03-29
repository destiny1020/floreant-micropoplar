package com.floreantpos.model;

import com.floreantpos.model.base.BaseCashTransaction;

public class CashTransaction extends BaseCashTransaction {
  private static final long serialVersionUID = 1L;

  /* [CONSTRUCTOR MARKER BEGIN] */
  public CashTransaction() {
    super();
  }

  /**
   * Constructor for primary key
   */
  public CashTransaction(java.lang.Integer id) {
    super(id);
  }

  /**
   * Constructor for required fields
   */
  public CashTransaction(java.lang.Integer id, java.lang.String transactionType,
      java.lang.Integer paymentType) {

    super(id, transactionType, paymentType);
  }

  /* [CONSTRUCTOR MARKER END] */
}
