package com.micropoplar.pos.model;

import com.floreantpos.model.TicketItem;
import com.micropoplar.pos.model.base.BaseMenuItemSet;
import com.micropoplar.pos.ui.IOrderViewItem;

/**
 * 套餐实体类
 * 
 * @author destiny1020
 *
 */
public class MenuItemSet extends BaseMenuItemSet implements IOrderViewItem {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public MenuItemSet() {
    super();
  }

  /**
   * Constructor for primary key
   */
  public MenuItemSet(java.lang.Integer id) {
    super(id);
  }

  /**
   * Constructor for required fields
   */
  public MenuItemSet(java.lang.Integer id, java.lang.String name, java.lang.Double price) {

    super(id, name, price);
  }

  //  @Override
  //  public String toString() {
  //    return getName();
  //  }

  public String getUniqueId() {
    return ("menu_item_set" + getName() + "_" + getId()).replaceAll("\\s+", "_");
  }

  public TicketItem convertToTicketItem() {
    TicketItem ticketItem = new TicketItem();
    ticketItem.setItemId(this.getId());
    ticketItem.setItemCount(1);
    ticketItem.setName(this.getName());
    ticketItem.setGroupName(this.getGroup().getName());
    ticketItem.setCategoryName(this.getCategory().getName());
    ticketItem.setUnitPrice(this.getPrice());
    ticketItem.setDiscountRate(this.getDiscountRate());
    ticketItem.setTaxRate(0.0);
    ticketItem.setHasModifiers(false);
    if (this.getCategory().isBeverage()) {
      ticketItem.setBeverage(true);
      ticketItem.setShouldPrintToKitchen(false);
    } else {
      ticketItem.setBeverage(false);
      ticketItem.setShouldPrintToKitchen(true);
    }
    ticketItem.setVirtualPrinter(this.getVirtualPrinter());
    ticketItem.setIsSet(true);

    return ticketItem;
  }

}
