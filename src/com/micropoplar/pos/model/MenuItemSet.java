package com.micropoplar.pos.model;

import com.micropoplar.pos.model.base.BaseMenuItemSet;

/**
 * 套餐实体类
 * 
 * @author destiny1020
 *
 */
public class MenuItemSet extends BaseMenuItemSet {

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

}
