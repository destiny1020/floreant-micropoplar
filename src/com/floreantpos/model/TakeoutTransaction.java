package com.floreantpos.model;

import com.floreantpos.model.base.BaseTakeoutTransaction;

public class TakeoutTransaction extends BaseTakeoutTransaction {

	private static final long serialVersionUID = 1L;

	public TakeoutTransaction() {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public TakeoutTransaction(java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public TakeoutTransaction(java.lang.Integer id, java.lang.String transactionType, java.lang.String paymentType) {

		super(id, transactionType, paymentType);
	}

}
