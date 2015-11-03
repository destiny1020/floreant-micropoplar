package com.floreantpos.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.floreantpos.model.base.BaseMenuItem;
import com.micropoplar.pos.ui.IOrderViewItem;

@XmlRootElement(name = "menu-item")
public class MenuItem extends BaseMenuItem implements IOrderViewItem {
  private static final long serialVersionUID = 1L;

  /* [CONSTRUCTOR MARKER BEGIN] */
  public MenuItem() {
    super();
  }

  /**
   * Constructor for primary key
   */
  public MenuItem(java.lang.Integer id) {
    super(id);
  }

  /**
   * Constructor for required fields
   */
  public MenuItem(java.lang.Integer id, java.lang.String name, java.lang.Double price) {

    super(id, name, price);
  }

  /* [CONSTRUCTOR MARKER END] */

  @Override
  @XmlTransient
  public Date getModifiedTime() {
    return super.getModifiedTime();
  }

  @Override
  public Double getPrice() {
    double price = super.getPrice();

    // TODO: calculate discount here ?

    return price;
  }

  @Override
  public String toString() {
    return getName();
  }

  public String getUniqueId() {
    return ("menu_item_" + getName() + "_" + getId()).replaceAll("\\s+", "_");
  }

  public TicketItem convertToTicketItem() {
    TicketItem ticketItem = new TicketItem();
    ticketItem.setItemId(this.getId());
    ticketItem.setItemCount(1);
    ticketItem.setName(this.getName());
    ticketItem.setGroupName(this.getGroup().getName());
    ticketItem.setCategoryName(this.getGroup().getCategory().getName());
    ticketItem.setUnitPrice(this.getPrice());
    if (this.getGroup().getCategory().isBeverage()) {
      ticketItem.setBeverage(true);
      ticketItem.setShouldPrintToKitchen(false);
    } else {
      ticketItem.setBeverage(false);
      ticketItem.setShouldPrintToKitchen(true);
    }
    ticketItem.setVirtualPrinter(this.getVirtualPrinter());
    ticketItem.setIsSet(false);

    return ticketItem;
  }

}
