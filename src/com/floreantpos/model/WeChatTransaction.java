package com.floreantpos.model;

import java.io.Serializable;

public class WeChatTransaction extends PosTransaction implements Comparable, Serializable {

	public static String REF = "WeChatTransaction";
	public static String PROP_ID = "id";


	// constructors
	public WeChatTransaction () {
		initialize();
	}

	/**
	 * Constructor for primary key
	 */
	public WeChatTransaction (java.lang.Integer id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public WeChatTransaction (
		java.lang.Integer id,
		java.lang.String transactionType,
		java.lang.String paymentType) {

		super (
			id,
			transactionType,
			paymentType);
	}



	private int hashCode = Integer.MIN_VALUE;

	public boolean equals (Object obj) {
		if (null == obj) return false;
		if (!(obj instanceof com.floreantpos.model.WeChatTransaction)) return false;
		else {
			com.floreantpos.model.WeChatTransaction weChatTransaction = (com.floreantpos.model.WeChatTransaction) obj;
			if (null == this.getId() || null == weChatTransaction.getId()) return false;
			else return (this.getId().equals(weChatTransaction.getId()));
		}
	}

	public int hashCode () {
		if (Integer.MIN_VALUE == this.hashCode) {
			if (null == this.getId()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}

	public int compareTo (Object obj) {
		if (obj.hashCode() > hashCode()) return 1;
		else if (obj.hashCode() < hashCode()) return -1;
		else return 0;
	}

	public String toString () {
		return super.toString();
	}
	
}
