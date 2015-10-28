package com.mircopoplar.pos.ui.set;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableColumn;

import com.floreantpos.bo.ui.explorer.ListTableModel;
import com.floreantpos.model.ITicketItem;
import com.floreantpos.util.NumberUtil;
import com.micropoplar.pos.model.MenuItemSet;
import com.micropoplar.pos.model.SetItem;
import com.micropoplar.pos.ui.util.ControllerGenerator;

public class MenuItemSetTableModel extends ListTableModel {

  private JTable table;
  protected MenuItemSet itemSet;
  protected final Map<String, ITicketItem> tableRows = new LinkedHashMap<>();

  // MenuItemSet ID ---> ROW IDX ---> SetItem entity
  private static final Map<Integer, Map<Integer, SetItem>> setItemCache;

  static {
    setItemCache = new HashMap<>();
  }

  //TODO: externalize
  protected String[] columnNames = {"商品", "单价", "数量", "小计"};

  private boolean forReciptPrint;
  private boolean printCookingInstructions;

  public MenuItemSetTableModel(JTable table) {
    this(table, null);
  }

  public MenuItemSetTableModel(JTable table, MenuItemSet itemSet) {
    this.table = table;
    setMenuItemSet(itemSet);
  }

  /**
   * Used to create custom editor for the count column
   */
  public void setItemCountEditor() {
    TableColumn countColumn = table.getColumnModel().getColumn(2);
    countColumn.setCellEditor(new DefaultCellEditor(ControllerGenerator.getCountComboBox()));
  }

  public void setMenuItemSet(MenuItemSet itemSet) {
    this.itemSet = itemSet;
    update();
  }

  @Override
  public int getRowCount() {
    return tableRows.size();
  }

  @Override
  public int getColumnCount() {
    return columnNames.length;
  }

  @Override
  public String getColumnName(int column) {
    return columnNames[column];
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    ITicketItem ticketItem = tableRows.get(String.valueOf(rowIndex));

    if (ticketItem == null) {
      return null;
    }

    switch (columnIndex) {
      case 0:
        return ticketItem.getNameDisplay();

      case 1:
        return ticketItem.getUnitPriceDisplay();

      case 2:
        return ticketItem.getItemCountDisplay();

      //      case 3:
      //        return ticketItem.getDiscountAmount();

      case 3:
        return ticketItem.getTotalAmount();
    }

    return null;
  }

  @Override
  public void setValueAt(Object value, int row, int col) {
    if (col == 2) {
      Integer newCount = (Integer) value;
      Integer originalCount = itemSet.getItems().get(row).getItemCount();
      if (originalCount.intValue() != newCount.intValue()) {
        SetItem item = itemSet.getItems().get(row);
        item.setItemCount(newCount);

        // update the total price
        item.setTotalAmount(NumberUtil.roundToTwoDigit(item.getItemCount() * item.getUnitPrice()));

        // update the count cache
        Map<Integer, SetItem> map = setItemCache.get(itemSet.getId());
        if (map == null) {
          map = new HashMap<>();
        }
        setItemCache.put(itemSet.getId(), map);
        map.put(row, item);

        fireTableDataChanged();
      }
    }
  }

  @Override
  public boolean isCellEditable(int row, int col) {
    //Note that the data/cell address is constant,
    //no matter where the cell appears onscreen.
    if (col != 2) {
      return false;
    } else {
      return true;
    }
  }

  public void update() {
    calculateRows();
    fireTableDataChanged();
  }

  private void calculateRows() {
    SetItemRowCreator.calculateSetRows(itemSet, tableRows);
  }

  public MenuItemSet getItemSet() {
    return itemSet;
  }

  public static SetItem getCacheCount(Integer menuItemSetId, Integer rowIndex) {
    Map<Integer, SetItem> map = setItemCache.get(menuItemSetId);
    if (map == null) {
      // means no need to adjust
      return null;
    }

    SetItem latestSetItem = map.get(rowIndex);
    return latestSetItem;
  }

}
