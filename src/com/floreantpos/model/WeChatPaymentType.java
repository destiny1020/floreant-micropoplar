package com.floreantpos.model;

/**
 * WeChat supported payments.
 * 
 * @author destiny1020
 *
 */
public enum WeChatPaymentType {

	PAYMENT_BAR("刷卡支付", "wechat_1.png"),
	QR_CODE("扫码支付", "wechat_2.png");
	
	private String displayString;
	private String imageFile;
	
	private WeChatPaymentType(String display) {
		this.displayString = display;
	}

	private WeChatPaymentType(String display, String image) {
		this.displayString = display;
		this.imageFile = image;
	}

	@Override
	public String toString() {
		return displayString;
	}

	public String getDisplayString() {
		return displayString;
	}

	public void setDisplayString(String displayString) {
		this.displayString = displayString;
	}

	public String getImageFile() {
		return imageFile;
	}

	public void setImageFile(String imageFile) {
		this.imageFile = imageFile;
	};
	
	public boolean isSupported() {
		// TODO: make it configurable
		return true;
	}
	
}
