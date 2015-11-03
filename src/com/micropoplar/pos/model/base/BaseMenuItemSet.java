package com.micropoplar.pos.model.base;

import java.io.Serializable;
import java.math.BigDecimal;

import com.floreantpos.model.MenuItem;
import com.micropoplar.pos.model.MenuItemSet;
import com.micropoplar.pos.model.SetItem;

public class BaseMenuItemSet extends MenuItem implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public static String REF = "MenuItemSet";

  public BaseMenuItemSet() {
    initialize();
  }

  /**
   * Constructor for primary key
   */
  public BaseMenuItemSet(java.lang.Integer id) {
    this.setId(id);
    initialize();
  }

  /**
   * Constructor for required fields
   */
  public BaseMenuItemSet(java.lang.Integer id, java.lang.String name, java.lang.Double price) {

    this.setId(id);
    this.setName(name);
    this.setPrice(price);
    initialize();
  }

  protected void initialize() {}

  private int hashCode = Integer.MIN_VALUE;

  public boolean equals(Object obj) {
    if (null == obj)
      return false;
    if (!(obj instanceof MenuItemSet))
      return false;
    else {
      MenuItemSet menuItem = (MenuItemSet) obj;
      if (null == this.getId() || null == menuItem.getId())
        return false;
      else
        return (this.getId().equals(menuItem.getId()));
    }
  }

  public int hashCode() {
    if (Integer.MIN_VALUE == this.hashCode) {
      if (null == this.getId())
        return super.hashCode();
      else {
        String hashStr = this.getClass().getName() + ":" + this.getId().hashCode();
        this.hashCode = hashStr.hashCode();
      }
    }
    return this.hashCode;
  }

  public String toString() {
    return super.toString();
  }

  public Double getPrice() {
    if (getPrice() == null && getItems() != null) {
      // calculate on demand
      double totalAmount = updatePriceCore();
      if (getItems().size() > 0 && new BigDecimal(totalAmount).compareTo(BigDecimal.ZERO) >= 0) {
        setPrice(totalAmount);
      }
    }

    return getPrice();
  }

  public void updatePrices() {
    // update the total price
    double totalAmount = updatePriceCore();
    if (getItems().size() > 0 && new BigDecimal(totalAmount).compareTo(BigDecimal.ZERO) >= 0) {
      setPrice(totalAmount);
    }

    // TODO: discount calculate here
  }

  private Double updatePriceCore() {
    double totalAmount = 0.0;
    for (SetItem item : getItems()) {
      Integer itemCount = item.getItemCount();
      Double unitPrice = item.getUnitPrice();
      if (itemCount != null && unitPrice != null) {
        totalAmount += itemCount * unitPrice;
      }
    }
    return totalAmount;
  }

}
