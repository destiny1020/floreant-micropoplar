package com.floreantpos.model;

import com.floreantpos.config.AppConfig;
import com.floreantpos.config.CardConfig;

public enum PaymentType {
	CASH("现金"), DEBIT_VISA("Visa", "visa_card.png"), DEBIT_MASTER_CARD("MasterCard", "master_card.png"), CREDIT_VISA(
			"Visa", "visa_card.png"), CREDIT_MASTER_CARD("MasterCard", "master_card.png"), CREDIT_AMEX("Amex",
					"am_ex_card.png"), CREDIT_DISCOVERY("Discover", "discover_card.png"), GIFT_CERTIFICATE("礼品卡"),

	WECHAT("微信支付", "wechat.png"), ALIPAY("支付宝", "alipay.png"), UNION_PAY("银联支付", "unionpay.png"),

	MEITUAN("美团", "meituan.png"), ELEME("饿了么", "eleme.png"), DAOJIA("到家美食会", "daojia.png"), LINEZERO("零号线",
			"linezero.png");

	private String displayString;
	private String imageFile;

	private PaymentType(String display) {
		this.displayString = display;
	}

	private PaymentType(String display, String image) {
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
		switch (this) {
		case CASH:
			return true;
		case UNION_PAY:
			return AppConfig.getBoolean(AppConfig.PAYMENT_UNION_PAY_SUPPORTED, true);
		default:
			// TODO: permission control for the payment
			return true;
		// return CardConfig.isSwipeCardSupported() ||
		// CardConfig.isManualEntrySupported() ||
		// CardConfig.isExtTerminalSupported();
		}
	}

	public PosTransaction createTransaction() {
		PosTransaction transaction = null;
		switch (this) {
		case CREDIT_VISA:
		case CREDIT_AMEX:
		case CREDIT_DISCOVERY:
		case CREDIT_MASTER_CARD:
			transaction = new CreditCardTransaction();
			transaction.setAuthorizable(true);
			break;

		case DEBIT_MASTER_CARD:
		case DEBIT_VISA:
			transaction = new DebitCardTransaction();
			transaction.setAuthorizable(true);
			break;

		case GIFT_CERTIFICATE:
			transaction = new GiftCertificateTransaction();
			break;

		case WECHAT:
			transaction = new WeChatTransaction();
			break;
		case ALIPAY:
			transaction = new AlipayTransaction();
			break;
		case UNION_PAY:
			transaction = new UnionPayTransaction();
			break;

		case MEITUAN:
		case DAOJIA:
		case ELEME:
		case LINEZERO:
			transaction = new TakeoutTransaction();
			break;

		default:
			transaction = new CashTransaction();
			break;
		}

		transaction.setPaymentType(name());
		return transaction;
	}
}
