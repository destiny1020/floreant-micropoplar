package com.floreantpos.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.floreantpos.main.Application;
import com.floreantpos.model.base.BaseTicketItem;
import com.floreantpos.util.NumberUtil;

public class TicketItem extends BaseTicketItem implements ITicketItem {
  private static final long serialVersionUID = 1L;

  /* [CONSTRUCTOR MARKER BEGIN] */
  public TicketItem() {
    super();
  }

  /**
   * Constructor for primary key
   */
  public TicketItem(java.lang.Integer id) {
    super(id);
  }

  /**
   * Constructor for required fields
   */
  public TicketItem(java.lang.Integer id, com.floreantpos.model.Ticket ticket) {

    super(id, ticket);
  }

  /* [CONSTRUCTOR MARKER END] */
  private int tableRowNum;

  public int getTableRowNum() {
    return tableRowNum;
  }

  public void setTableRowNum(int tableRowNum) {
    this.tableRowNum = tableRowNum;
  }

  public boolean canAddCookingInstruction() {
    if (isPrintedToKitchen())
      return false;

    return true;
  }

  @Override
  public String toString() {
    return getName();
  }

  public void addCookingInstruction(TicketItemCookingInstruction cookingInstruction) {
    List<TicketItemCookingInstruction> cookingInstructions = getCookingInstructions();

    if (cookingInstructions == null) {
      cookingInstructions = new ArrayList<TicketItemCookingInstruction>(2);
      setCookingInstructions(cookingInstructions);
    }

    cookingInstructions.add(cookingInstruction);
  }

  public void addCookingInstructions(List<TicketItemCookingInstruction> instructions) {
    List<TicketItemCookingInstruction> cookingInstructions = getCookingInstructions();

    if (cookingInstructions == null) {
      cookingInstructions = new ArrayList<TicketItemCookingInstruction>(2);
      setCookingInstructions(cookingInstructions);
    }

    cookingInstructions.addAll(instructions);
  }

  public void removeCookingInstruction(TicketItemCookingInstruction itemCookingInstruction) {
    List<TicketItemCookingInstruction> cookingInstructions2 = getCookingInstructions();
    if (cookingInstructions2 == null) {
      return;
    }

    for (Iterator<TicketItemCookingInstruction> iterator = cookingInstructions2.iterator(); iterator
        .hasNext();) {
      TicketItemCookingInstruction ticketItemCookingInstruction =
          (TicketItemCookingInstruction) iterator.next();
      if (ticketItemCookingInstruction.getTableRowNum() == itemCookingInstruction
          .getTableRowNum()) {
        iterator.remove();
        return;
      }
    }
  }

  public void calculatePrice(boolean needDiscount) {
    setSubtotalAmount(NumberUtil.roundToTwoDigit(calculateSubtotal(true)));
    if (needDiscount) {
      setDiscountAmount(NumberUtil.roundToTwoDigit(calculateDiscount()));
    }
    setTotalAmount(NumberUtil.roundToTwoDigit(calculateTotal(true)));
  }

  // public double calculateSubtotal() {
  // double subtotal = NumberUtil.roundToTwoDigit(calculateSubtotal(true));
  //
  // return subtotal;
  // }
  //
  // public double calculateSubtotalWithoutModifiers() {
  // double subtotalWithoutModifiers = NumberUtil.roundToTwoDigit(calculateSubtotal(false));
  //
  // return subtotalWithoutModifiers;
  // }

  private double calculateSubtotal(boolean includeModifierPrice) {
    double subTotalAmount = NumberUtil.roundToTwoDigit(getUnitPrice() * getItemCount());

    return subTotalAmount;
  }

  private double calculateDiscount() {
    double discount = 0;
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
}
