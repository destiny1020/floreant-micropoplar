package com.floreantpos.model;

import com.floreantpos.config.AppConfig;

public enum PaymentType {
  // @formatter:off
  CASH(1, "现金"), 
  UNION_PAY(2, "银联支付", "unionpay.png"),
  WECHAT(3, "微信支付", "wechat.png"), 
  ALIPAY(4, "支付宝", "alipay.png"), 
  MEITUAN(5, "美团", "meituan.png"), 
  ELEME(6, "饿了么", "eleme.png"), 
  DAOJIA(7, "到家美食会", "daojia.png"), 
  LINEZERO(8, "零号线", "linezero.png");
  // @formatter:on

  private int type;
  private String displayString;
  private String imageFile;

  private PaymentType(int type, String display) {
    this.type = type;
    this.displayString = display;
  }

  private PaymentType(int type, String display, String image) {
    this(type, display);
    this.imageFile = image;
  }

  @Override
  public String toString() {
    return displayString;
  }

  public int getType() {
    return type;
  }

  public String getDisplayString() {
    return displayString;
  }

  public String getImageFile() {
    return imageFile;
  }

  public boolean isSupported() {
    switch (this) {
      case CASH:
        return true;
      case UNION_PAY:
        return AppConfig.getBoolean(AppConfig.PAYMENT_UNION_PAY_SUPPORTED, true);
      default:
        // TODO: permission control for the payment
        return true;
    }
  }

  public PosTransaction createTransaction() {
    PosTransaction transaction = null;
    switch (this) {
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

    transaction.setPaymentType(getType());
    return transaction;
  }

  public static PaymentType getPaymentTypeFromType(int type) {
    switch (type) {
      case 1:
        return PaymentType.CASH;
      case 2:
        return PaymentType.UNION_PAY;
      case 3:
        return PaymentType.WECHAT;
      case 4:
        return PaymentType.ALIPAY;
      case 5:
        return PaymentType.MEITUAN;
      case 6:
        return PaymentType.ELEME;
      case 7:
        return PaymentType.DAOJIA;
      case 8:
        return PaymentType.LINEZERO;
      default:
        return null;

    }
  }

}
