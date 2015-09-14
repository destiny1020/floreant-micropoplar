package com.micropoplar.pos.payment;

/**
 * 第三方支付业务返回对象
 * 
 * @author destiny1020
 *
 */
public class PaymentResult {

	public static final PaymentResult FAIL_RESULT = new PaymentResult(false, "", 0);

	private boolean successful;
	private String transactionId;
	private int totalFee;

	public PaymentResult(boolean successful, String transactionId, int totalFee) {
		super();
		this.successful = successful;
		this.transactionId = transactionId;
		this.totalFee = totalFee;
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

	public int getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(int totalFee) {
		this.totalFee = totalFee;
	}

}
