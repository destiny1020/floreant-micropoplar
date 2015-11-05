package com.floreantpos.model.base;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.floreantpos.model.MenuGroup;
import com.floreantpos.model.VirtualPrinter;
import com.micropoplar.pos.model.SetItem;
import com.micropoplar.pos.model.UnitName;


/**
 * This is an object that contains data related to the MENU_ITEM table. Do not modify this class
 * because it will be overwritten if the configuration file related to this class is modified.
 *
 * @hibernate.class table="MENU_ITEM"
 */

public abstract class BaseMenuItem implements Comparable<BaseMenuItem>, Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public static String REF = "MenuItem";
  public static String PROP_ID = "id";
  public static String PROP_CODE = "code";
  public static String PROP_NAME = "name";
  public static String PROP_BARCODE = "barcode";
  public static String PROP_GROUP = "group";
  public static String PROP_UNIT_NAME = "unitName";
  public static String PROP_VISIBLE = "visible";
  public static String PROP_PRICE = "price";
  public static String PROP_IMAGE = "image";
  public static String PROP_SHOW_IMAGE_ONLY = "showImageOnly";
  public static String PROP_VIRTUAL_PRINTER = "virtualPrinter";
  public static String PROP_IS_SET = "isSet";
  public static String PROP_SET_ITEMS = "setItems";

  // constructors
  public BaseMenuItem() {
    initialize();
  }

  /**
   * Constructor for primary key
   */
  public BaseMenuItem(java.lang.Integer id) {
    this.setId(id);
    initialize();
  }

  /**
   * Constructor for required fields
   */
  public BaseMenuItem(java.lang.Integer id, java.lang.String name, java.lang.Double price) {
    this.setId(id);
    this.setName(name);
    this.setPrice(price);
    initialize();
  }

  protected void initialize() {}

  private int hashCode = Integer.MIN_VALUE;

  // primary key
  private Integer id;
  private Date modifiedTime;

  // fields
  private MenuGroup group;
  private String code;
  private String barcode;
  private String name;
  private UnitName unitName;
  private Boolean visible = true;
  private Double price;
  private byte[] image;
  private Boolean showImageOnly = false;
  private VirtualPrinter virtualPrinter;
  private Boolean isSet = false;
  private List<SetItem> items;

  @Override
  public boolean equals(Object obj) {
    if (null == obj)
      return false;
    if (!(obj instanceof com.floreantpos.model.MenuItem))
      return false;
    else {
      com.floreantpos.model.MenuItem menuItem = (com.floreantpos.model.MenuItem) obj;
      if (null == this.getId() || null == menuItem.getId())
        return false;
      else
        return (this.getId().equals(menuItem.getId()));
    }
  }

  @Override
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
  public int compareTo(BaseMenuItem obj) {
    if (obj.hashCode() > hashCode())
      return 1;
    else if (obj.hashCode() < hashCode())
      return -1;
    else
      return 0;
  }

  @Override
  public String toString() {
    return super.toString();
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Date getModifiedTime() {
    return modifiedTime;
  }

  public void setModifiedTime(Date modifiedTime) {
    this.modifiedTime = modifiedTime;
  }

  public MenuGroup getGroup() {
    return group;
  }

  public void setGroup(MenuGroup group) {
    this.group = group;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getBarcode() {
    return barcode;
  }

  public void setBarcode(String barcode) {
    this.barcode = barcode;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public UnitName getUnitName() {
    return unitName;
  }

  public void setUnitName(UnitName unitName) {
    this.unitName = unitName;
  }

  public Boolean isVisible() {
    return visible;
  }

  public void setVisible(Boolean visible) {
    this.visible = visible;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public byte[] getImage() {
    return image;
  }

  public void setImage(byte[] image) {
    this.image = image;
  }

  public Boolean isShowImageOnly() {
    return showImageOnly;
  }

  public void setShowImageOnly(Boolean showImageOnly) {
    this.showImageOnly = showImageOnly;
  }

  public VirtualPrinter getVirtualPrinter() {
    return virtualPrinter;
  }

  public void setVirtualPrinter(VirtualPrinter virtualPrinter) {
    this.virtualPrinter = virtualPrinter;
  }

  public List<SetItem> getItems() {
    return items;
  }

  public void setItems(List<SetItem> items) {
    this.items = items;
  }

  public Boolean getIsSet() {
    return isSet;
  }

  public void setIsSet(Boolean isSet) {
    this.isSet = isSet;
  }

}
