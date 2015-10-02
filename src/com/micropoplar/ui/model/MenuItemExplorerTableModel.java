package com.micropoplar.ui.model;

import java.util.List;

import com.floreantpos.bo.ui.explorer.ListTableModel;
import com.floreantpos.main.Application;
import com.floreantpos.model.MenuItem;
import com.micropoplar.pos.model.SetItem;

public class MenuItemExplorerTableModel extends ListTableModel {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private List<SetItem> items;
  private String[] columnNames =
      {com.floreantpos.POSConstants.ID, com.floreantpos.POSConstants.NAME,
          com.floreantpos.POSConstants.PRICE + " (" + Application.getCurrencySymbol() + ")",
          com.floreantpos.POSConstants.VISIBLE, com.floreantpos.POSConstants.DISCOUNT + "(%)",
          com.floreantpos.POSConstants.FOOD_GROUP};

  public MenuItemExplorerTableModel() {
    setColumnNames(columnNames);
  }

  public Object getValueAt(int rowIndex, int columnIndex) {
    MenuItem item = (MenuItem) rows.get(rowIndex);

    switch (columnIndex) {
      case 0:
        return String.valueOf(item.getId());

      case 1:
        return item.getName();

      case 2:
        return Double.valueOf(item.getPrice());

      case 3:
        return item.isVisible();

      case 4:
        return Double.valueOf(item.getDiscountRate());

      case 5:
        if (item.getParent() != null) {
          return item.getParent().getName();
        }
        return "";

    }
    return null;
  }

  public void addMenuItem(MenuItem menuItem) {
    super.addItem(menuItem);

  }

  public void deleteMenuItem(MenuItem category, int index) {
    super.deleteItem(index);
  }

  public List<SetItem> getItems() {
    return items;
  }
}
