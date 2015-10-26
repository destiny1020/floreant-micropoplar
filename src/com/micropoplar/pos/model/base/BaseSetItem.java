package com.micropoplar.pos.model.base;

import java.io.Serializable;

import com.micropoplar.pos.model.MenuItemSet;

public class BaseSetItem implements Comparable, Serializable {

  private static final long serialVersionUID = 1L;

  public static String REF = "SetItem";
  public static String PROP_BEVERAGE = "beverage";
  public static String PROP_ITEM_ID = "itemId";
  public static String PROP_CATEGORY_NAME = "categoryName";
  public static String PROP_GROUP_NAME = "groupName";
  public static String PROP_DISCOUNT_RATE = "discountRate";
  public static String PROP_ITEM_COUNT = "itemCount";
  public static String PROP_UNIT_PRICE = "unitPrice";
  public static String PROP_DISCOUNT_AMOUNT = "discountAmount";
  public static String PROP_NAME = "name";
  public static String PROP_PRINTED_TO_KITCHEN = "printedToKitchen";
  public static String PROP_SHOULD_PRINT_TO_KITCHEN = "shouldPrintToKitchen";
  public static String PROP_SET = "set";
  public static String PROP_SUBTOTAL_AMOUNT = "subtotalAmount";
  public static String PROP_ID = "id";
  public static String PROP_TOTAL_AMOUNT = "totalAmount";
  public static String PROP_VIRTUAL_PRINTER = "virtualPrinter";
  public static String PROP_DISCOUNT_OFFSET_AMOUNT = "discountOffsetAmount";

  //constructors
  public BaseSetItem() {
    initialize();
  }

  /**
  * Constructor for primary key
  */
  public BaseSetItem(java.lang.Integer id) {
    this.setId(id);
    initialize();
  }

  /**
  * Constructor for required fields
  */
  public BaseSetItem(java.lang.Integer id, MenuItemSet itemSet) {

    this.setId(id);
    this.setMenuItemSet(itemSet);
    initialize();
  }

  protected void initialize() {}

  private int hashCode = Integer.MIN_VALUE;

  // primary key
  private java.lang.Integer id;

  java.util.Date modifiedTime;

  //fields
  protected java.lang.Integer itemId;
  protected java.lang.Integer itemCount;
  protected java.lang.String name;
  protected java.lang.String groupName;
  protected java.lang.String categoryName;
  protected java.lang.Double unitPrice;
  protected java.lang.Double discountRate = 1.0; // 1.0 表示没有打折
  protected java.lang.Double subtotalAmount;
  protected java.lang.Double discountAmount;
  protected java.lang.Double totalAmount;
  protected java.lang.Boolean beverage;
  protected java.lang.Boolean shouldPrintToKitchen;
  protected java.lang.Boolean printedToKitchen;
  protected java.lang.Double discountOffsetAmount;

  // many to one
  protected MenuItemSet itemSet;
  protected com.floreantpos.model.VirtualPrinter virtualPrinter;

  /**
   * Return the unique identifier of this class
   * 
   * @hibernate.id generator-class="identity" column="ID"
   */
  public java.lang.Integer getId() {
    return id;
  }

  /**
   * Set the unique identifier of this class
   * 
   * @param id the new ID
   */
  public void setId(java.lang.Integer id) {
    this.id = id;
    this.hashCode = Integer.MIN_VALUE;
  }

  /**
   * Return the value associated with the column: MODIFIED_TIME
   */
  public java.util.Date getModifiedTime() {
    return modifiedTime;
  }

  /**
   * Set the value related to the column: MODIFIED_TIME
   * 
   * @param modifiedTime the MODIFIED_TIME value
   */
  public void setModifiedTime(java.util.Date modifiedTime) {
    this.modifiedTime = modifiedTime;
  }

  /**
   * Return the value associated with the column: ITEM_ID
   */
  public java.lang.Integer getItemId() {
    return itemId == null ? Integer.valueOf(0) : itemId;
  }

  /**
   * Set the value related to the column: ITEM_ID
   * 
   * @param itemId the ITEM_ID value
   */
  public void setItemId(java.lang.Integer itemId) {
    this.itemId = itemId;
  }

  /**
   * Return the value associated with the column: ITEM_COUNT
   */
  public java.lang.Integer getItemCount() {
    return itemCount == null ? Integer.valueOf(0) : itemCount;
  }

  /**
   * Set the value related to the column: ITEM_COUNT
   * 
   * @param itemCount the ITEM_COUNT value
   */
  public void setItemCount(java.lang.Integer itemCount) {
    this.itemCount = itemCount;
  }

  /**
   * Return the value associated with the column: ITEM_NAME
   */
  public java.lang.String getName() {
    return name;
  }

  /**
   * Set the value related to the column: ITEM_NAME
   * 
   * @param name the ITEM_NAME value
   */
  public void setName(java.lang.String name) {
    this.name = name;
  }

  /**
   * Return the value associated with the column: GROUP_NAME
   */
  public java.lang.String getGroupName() {
    return groupName;
  }

  /**
   * Set the value related to the column: GROUP_NAME
   * 
   * @param groupName the GROUP_NAME value
   */
  public void setGroupName(java.lang.String groupName) {
    this.groupName = groupName;
  }

  /**
   * Return the value associated with the column: CATEGORY_NAME
   */
  public java.lang.String getCategoryName() {
    return categoryName;
  }

  /**
   * Set the value related to the column: CATEGORY_NAME
   * 
   * @param categoryName the CATEGORY_NAME value
   */
  public void setCategoryName(java.lang.String categoryName) {
    this.categoryName = categoryName;
  }

  /**
   * Return the value associated with the column: ITEM_PRICE
   */
  public java.lang.Double getUnitPrice() {
    return unitPrice == null ? Double.valueOf(0) : unitPrice;
  }

  /**
   * Set the value related to the column: ITEM_PRICE
   * 
   * @param unitPrice the ITEM_PRICE value
   */
  public void setUnitPrice(java.lang.Double unitPrice) {
    this.unitPrice = unitPrice;
  }

  /**
   * Return the value associated with the column: DISCOUNT_RATE
   */
  public java.lang.Double getDiscountRate() {
    return discountRate == null ? Double.valueOf(0) : discountRate;
  }

  /**
   * Set the value related to the column: DISCOUNT_RATE
   * 
   * @param discountRate the DISCOUNT_RATE value
   */
  public void setDiscountRate(java.lang.Double discountRate) {
    this.discountRate = discountRate;
  }

  /**
   * Return the value associated with the column: SUB_TOTAL
   */
  public java.lang.Double getSubtotalAmount() {
    return subtotalAmount == null ? Double.valueOf(0) : subtotalAmount;
  }

  /**
   * Set the value related to the column: SUB_TOTAL
   * 
   * @param subtotalAmount the SUB_TOTAL value
   */
  public void setSubtotalAmount(java.lang.Double subtotalAmount) {
    this.subtotalAmount = subtotalAmount;
  }

  /**
   * Return the value associated with the column: DISCOUNT
   */
  public java.lang.Double getDiscountAmount() {
    return discountAmount == null ? Double.valueOf(0) : discountAmount;
  }

  /**
   * Set the value related to the column: DISCOUNT
   * 
   * @param discountAmount the DISCOUNT value
   */
  public void setDiscountAmount(java.lang.Double discountAmount) {
    this.discountAmount = discountAmount;
  }

  /**
   * Return the value associated with the column: TOTAL_PRICE
   */
  public java.lang.Double getTotalAmount() {
    return totalAmount == null ? Double.valueOf(0) : totalAmount;
  }

  /**
   * Set the value related to the column: TOTAL_PRICE
   * 
   * @param totalAmount the TOTAL_PRICE value
   */
  public void setTotalAmount(java.lang.Double totalAmount) {
    this.totalAmount = totalAmount;
  }

  /**
   * Return the value associated with the column: BEVERAGE
   */
  public java.lang.Boolean isBeverage() {
    return beverage == null ? Boolean.FALSE : beverage;
  }

  /**
   * Set the value related to the column: BEVERAGE
   * 
   * @param beverage the BEVERAGE value
   */
  public void setBeverage(java.lang.Boolean beverage) {
    this.beverage = beverage;
  }



  /**
   * Return the value associated with the column: PRINT_TO_KITCHEN
   */
  public java.lang.Boolean isShouldPrintToKitchen() {
    return shouldPrintToKitchen == null ? Boolean.valueOf(true) : shouldPrintToKitchen;
  }

  /**
   * Set the value related to the column: PRINT_TO_KITCHEN
   * 
   * @param shouldPrintToKitchen the PRINT_TO_KITCHEN value
   */
  public void setShouldPrintToKitchen(java.lang.Boolean shouldPrintToKitchen) {
    this.shouldPrintToKitchen = shouldPrintToKitchen;
  }


  /**
   * Custom property
   */
  public static String getShouldPrintToKitchenDefaultValue() {
    return "true";
  }

  /**
   * Return the value associated with the column: PRINTED_TO_KITCHEN
   */
  public java.lang.Boolean isPrintedToKitchen() {
    return printedToKitchen == null ? Boolean.FALSE : printedToKitchen;
  }

  /**
   * Set the value related to the column: PRINTED_TO_KITCHEN
   * 
   * @param printedToKitchen the PRINTED_TO_KITCHEN value
   */
  public void setPrintedToKitchen(java.lang.Boolean printedToKitchen) {
    this.printedToKitchen = printedToKitchen;
  }

  /**
   * Return the value associated with the column: SET_ID
   */
  public MenuItemSet getMenuItemSet() {
    return itemSet;
  }

  /**
   * Set the value related to the column: SET_ID
   * 
   * @param menuItemSet the SET_ID value
   */
  public void setMenuItemSet(MenuItemSet itemSet) {
    this.itemSet = itemSet;
  }



  /**
   * Return the value associated with the column: VPRINTER_ID
   */
  public com.floreantpos.model.VirtualPrinter getVirtualPrinter() {
    return virtualPrinter;
  }

  /**
   * Set the value related to the column: VPRINTER_ID
   * 
   * @param virtualPrinter the VPRINTER_ID value
   */
  public void setVirtualPrinter(com.floreantpos.model.VirtualPrinter virtualPrinter) {
    this.virtualPrinter = virtualPrinter;
  }

  public boolean equals(Object obj) {
    if (null == obj)
      return false;
    if (!(obj instanceof com.floreantpos.model.TicketItem))
      return false;
    else {
      com.floreantpos.model.TicketItem ticketItem = (com.floreantpos.model.TicketItem) obj;
      if (null == this.getId() || null == ticketItem.getId())
        return false;
      else
        return (this.getId().equals(ticketItem.getId()));
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

  public int compareTo(Object obj) {
    if (obj.hashCode() > hashCode())
      return 1;
    else if (obj.hashCode() < hashCode())
      return -1;
    else
      return 0;
  }

  public String toString() {
    return super.toString();
  }

  public java.lang.Double getDiscountOffsetAmount() {
    return discountOffsetAmount;
  }

  public void setDiscountOffsetAmount(java.lang.Double discountOffsetAmount) {
    this.discountOffsetAmount = discountOffsetAmount;
  }

}
