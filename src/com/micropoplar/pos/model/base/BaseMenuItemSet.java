package com.micropoplar.pos.model.base;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.floreantpos.model.MenuCategory;
import com.floreantpos.util.NumberUtil;
import com.micropoplar.pos.model.MenuItemSet;
import com.micropoplar.pos.model.SetItem;
import com.micropoplar.pos.model.UnitName;

public class BaseMenuItemSet implements Comparable, Serializable {

  public static String REF = "MenuItemSet";
  public static String PROP_BARCODE = "barcode";
  public static String PROP_CATEGORY = "category";
  public static String PROP_GROUP = "group";
  public static String PROP_VISIBLE = "visible";
  public static String PROP_SHOW_IMAGE_ONLY = "showImageOnly";
  public static String PROP_DISCOUNT_RATE = "discountRate";
  public static String PROP_NAME = "name";
  public static String PROP_RECEPIE = "recepie";
  public static String PROP_PRICE = "price";
  public static String PROP_IMAGE = "image";
  public static String PROP_ID = "id";
  public static String PROP_VIRTUAL_PRINTER = "virtualPrinter";
  public static String PROP_CODE = "code";
  public static String PROP_UNIT_NAME = "unitName";
  public static String PROP_MEMBER_PRICE = "memberPrice";
  public static String PROP_INVENTORY_COUNT = "inventoryCount";

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

  // primary key
  private java.lang.Integer id;

  private java.util.Date modifiedTime;

  // fields
  protected java.lang.String barcode;
  protected java.lang.Double discountRate;
  protected byte[] image;
  protected java.lang.String name;
  protected java.lang.Double price;
  protected java.lang.Boolean showImageOnly;
  protected java.lang.Boolean visible;

  private UnitName unitName;
  private String code;
  private Double memberPrice;
  private Integer inventoryCount;

  // many to one
  private MenuCategory category;
  private com.floreantpos.model.MenuGroup group;
  private com.floreantpos.model.inventory.Recepie recepie;
  private com.floreantpos.model.VirtualPrinter virtualPrinter;

  // collections
  private List<SetItem> items;

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

  @Override
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

  public java.lang.Integer getId() {
    return id;
  }

  public void setId(java.lang.Integer id) {
    this.id = id;
  }

  public java.util.Date getModifiedTime() {
    return modifiedTime;
  }

  public void setModifiedTime(java.util.Date modifiedTime) {
    this.modifiedTime = modifiedTime;
  }

  public java.lang.String getBarcode() {
    return barcode;
  }

  public void setBarcode(java.lang.String barcode) {
    this.barcode = barcode;
  }

  public java.lang.Double getDiscountRate() {
    return discountRate;
  }

  public void setDiscountRate(java.lang.Double discountRate) {
    this.discountRate = discountRate;
  }

  public byte[] getImage() {
    return image;
  }

  public void setImage(byte[] image) {
    this.image = image;
  }

  public java.lang.String getName() {
    return name;
  }

  public void setName(java.lang.String name) {
    this.name = name;
  }

  public java.lang.Double getPrice() {
    if (price == null && items != null) {
      // calculate on demand
      double totalAmount = updatePriceCore();
      if (items.size() > 0 && new BigDecimal(totalAmount).compareTo(BigDecimal.ZERO) >= 0) {
        price = totalAmount;
      }
    }

    return price;
  }

  public void updatePrices() {
    // update the total price
    double totalAmount = updatePriceCore();
    if (items.size() > 0 && new BigDecimal(totalAmount).compareTo(BigDecimal.ZERO) >= 0) {
      price = totalAmount;
    }

    // update the member price if necessary
    if (discountRate != null && price != null) {
      memberPrice = NumberUtil.roundToTwoDigit(price * discountRate / 10);
    }
  }

  private Double updatePriceCore() {
    double totalAmount = 0.0;
    for (SetItem item : items) {
      Integer itemCount = item.getItemCount();
      Double unitPrice = item.getUnitPrice();
      if (itemCount != null && unitPrice != null) {
        totalAmount += itemCount * unitPrice;
      }
    }
    return totalAmount;
  }

  public void setPrice(java.lang.Double price) {
    this.price = price;
  }

  public java.lang.Boolean getShowImageOnly() {
    return showImageOnly == null ? Boolean.valueOf(true) : showImageOnly;
  }

  public void setShowImageOnly(java.lang.Boolean showImageOnly) {
    this.showImageOnly = showImageOnly;
  }

  public java.lang.Boolean getVisible() {
    return visible == null ? Boolean.valueOf(true) : visible;
  }

  public void setVisible(java.lang.Boolean visible) {
    this.visible = visible;
  }

  public UnitName getUnitName() {
    return unitName;
  }

  public void setUnitName(UnitName unitName) {
    this.unitName = unitName;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public Double getMemberPrice() {
    return memberPrice;
  }

  public void setMemberPrice(Double memberPrice) {
    this.memberPrice = memberPrice;
  }

  public Integer getInventoryCount() {
    return inventoryCount;
  }

  public void setInventoryCount(Integer inventoryCount) {
    this.inventoryCount = inventoryCount;
  }

  public MenuCategory getCategory() {
    return category;
  }

  public void setCategory(MenuCategory category) {
    this.category = category;
  }

  public com.floreantpos.model.MenuGroup getGroup() {
    return group;
  }

  public void setGroup(com.floreantpos.model.MenuGroup group) {
    this.group = group;
  }

  public com.floreantpos.model.inventory.Recepie getRecepie() {
    return recepie;
  }

  public void setRecepie(com.floreantpos.model.inventory.Recepie recepie) {
    this.recepie = recepie;
  }

  public com.floreantpos.model.VirtualPrinter getVirtualPrinter() {
    return virtualPrinter;
  }

  public void setVirtualPrinter(com.floreantpos.model.VirtualPrinter virtualPrinter) {
    this.virtualPrinter = virtualPrinter;
  }

  public List<SetItem> getItems() {
    return items;
  }

  public void setItems(List<SetItem> items) {
    this.items = items;
  }

}
