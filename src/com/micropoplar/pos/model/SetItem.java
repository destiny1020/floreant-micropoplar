package com.micropoplar.pos.model;

import com.floreantpos.main.Application;
import com.floreantpos.model.ITicketItem;
import com.floreantpos.model.PosPrinters;
import com.floreantpos.model.Printer;
import com.floreantpos.model.VirtualPrinter;
import com.floreantpos.util.NumberUtil;
import com.micropoplar.pos.model.base.BaseSetItem;

public class SetItem extends BaseSetItem implements ITicketItem {
  private static final long serialVersionUID = 1L;

  /* [CONSTRUCTOR MARKER BEGIN] */
  public SetItem() {
    super();
  }

  /**
   * Constructor for primary key
   */
  public SetItem(java.lang.Integer id) {
    super(id);
  }

  /**
   * Constructor for required fields
   */
  public SetItem(java.lang.Integer id, MenuItemSet itemSet) {

    super(id, itemSet);
  }

  /* [CONSTRUCTOR MARKER END] */
  private int tableRowNum;

  public int getTableRowNum() {
    return tableRowNum;
  }

  public void setTableRowNum(int tableRowNum) {
    this.tableRowNum = tableRowNum;
  }

  @Override
  public String toString() {
    return getName();
  }

  public void calculatePrice(boolean needDiscount) {
    setSubtotalAmount(NumberUtil.roundToTwoDigit(calculateSubtotal(true)));
    if (needDiscount) {
      setDiscountAmount(NumberUtil.roundToTwoDigit(calculateDiscount()));
    }
    setTotalAmount(NumberUtil.roundToTwoDigit(calculateTotal(true)));
  }

  private double calculateSubtotal(boolean includeModifierPrice) {
    double subTotalAmount = NumberUtil.roundToTwoDigit(getUnitPrice() * getItemCount());

    return subTotalAmount;
  }

  private double calculateDiscount() {
    double subtotal = getSubtotalAmount();
    double discountRate = getDiscountRate();

    double discount = 0;
    if (discountRate > 0) {
      discount = NumberUtil.roundToTwoDigit(subtotal * (100 - discountRate * 10) / 100.0);
    }

    return discount;
  }

  private double calculateTotal(boolean includeModifiers) {
    double total = 0;
    total = getSubtotalAmount() - getDiscountAmount();
    return total;
  }

  @Override
  public String getNameDisplay() {
    return getName();
  }

  @Override
  public Double getUnitPriceDisplay() {
    return getUnitPrice();
  }

  @Override
  public Integer getItemCountDisplay() {
    return getItemCount();
  }

  @Override
  public Double getTotalAmount() {
    return getTotalAmount();
  }

  @Override
  public String getItemCode() {
    return String.valueOf(getId());
  }

  public Printer getPrinter() {
    PosPrinters printers = Application.getPrinters();
    VirtualPrinter virtualPrinter = getVirtualPrinter();

    if (virtualPrinter == null) {
      return printers.getDefaultKitchenPrinter();
    }

    return printers.getKitchenPrinterFor(virtualPrinter);
  }

  @Override
  public boolean canAddCookingInstruction() {
    return false;
  }

}
