package com.micropoplar.pos.bo.ui.model;

import com.floreantpos.bo.ui.explorer.ListTableModel;
import com.floreantpos.main.Application;
import com.floreantpos.model.MenuItem;

public class MenuItemSelectedTableModel extends ListTableModel {

  private String[] columnNames =
      {com.floreantpos.POSConstants.ID, com.floreantpos.POSConstants.NAME,
          com.floreantpos.POSConstants.PRICE + " (" + Application.getCurrencySymbol() + ")",
          com.floreantpos.POSConstants.CATEGORY, com.floreantpos.POSConstants.FOOD_GROUP};

  public MenuItemSelectedTableModel() {
    setColumnNames(columnNames);
  }

  @Override
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
        if (item.getParent() != null && item.getParent().getParent() != null) {
          return item.getParent().getParent().getName();
        }
        return "";

      case 4:
        if (item.getParent() != null) {
          return item.getParent().getName();
        }
        return "";

    }
    return null;
  }

}
