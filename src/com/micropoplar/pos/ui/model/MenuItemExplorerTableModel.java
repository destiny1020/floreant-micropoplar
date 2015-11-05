package com.micropoplar.pos.ui.model;

import java.util.List;

import com.floreantpos.POSConstants;
import com.floreantpos.bo.ui.explorer.ListTableModel;
import com.floreantpos.model.MenuItem;
import com.micropoplar.pos.model.SetItem;

public class MenuItemExplorerTableModel extends ListTableModel<MenuItem> {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private List<SetItem> items;

  // @formatter:off
  private String[] columnNames =  {
      POSConstants.MENU_ITEM_EXPLORER_CODE,
      POSConstants.MENU_ITEM_EXPLORER_NAME,
      POSConstants.MENU_ITEM_EXPLORER_BARCODE, 
      POSConstants.MENU_ITEM_EXPLORER_PRICE,
      POSConstants.MENU_ITEM_EXPLORER_GROUP,
      POSConstants.MENU_ITEM_EXPLORER_CATEGORY,
      POSConstants.MENU_ITEM_EXPLORER_VISIBILITY
  };
  // @formatter:on

  public MenuItemExplorerTableModel() {
    setColumnNames(columnNames);
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    MenuItem item = rows.get(rowIndex);

    switch (columnIndex) {
      case 0:
        return item.getCode();

      case 1:
        return item.getName();

      case 2:
        return item.getBarcode();

      case 3:
        return String.valueOf(item.getPrice());

      case 4:
        if (item.getGroup() != null) {
          return item.getGroup().getName();
        }
        return "";

      case 5:
        if (item.getGroup() != null && item.getGroup().getCategory() != null) {
          return item.getGroup().getCategory().getName();
        }
        return "";

      case 6:
        return item.isVisible();
    }

    return "";
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
