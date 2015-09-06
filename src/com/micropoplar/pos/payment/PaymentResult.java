package com.micropoplar.pos.payment;

/**
 * 第三方支付业务返回对象
 * 
 * @author destiny1020
 *
 */
public class PaymentResult {

	public static final PaymentResult FAIL_RESULT = new PaymentResult(false, "");

	private boolean successful;
	private String transactionId;

	public PaymentResult(boolean successful, String transactionId) {
		super();
		this.successful = successful;
		this.transactionId = transactionId;
	}

	public boolean isSuccessful() {
		return successful;
	}

	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

}
