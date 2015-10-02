package com.mircopoplar.pos.ui.set;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import com.floreantpos.model.ITicketItem;
import com.floreantpos.ui.ticket.TicketItemRowCreator;
import com.micropoplar.pos.model.MenuItemSet;

public class MenuItemSetTableModel extends AbstractTableModel {

  private JTable table;
  protected MenuItemSet itemSet;
  protected final Map<String, ITicketItem> tableRows = new LinkedHashMap<>();

  //TODO: externalize
  protected String[] columnNames = {"商品", "单价", "数量", "折扣金额", "商品总价"};

  private boolean forReciptPrint;
  private boolean printCookingInstructions;

  public MenuItemSetTableModel(JTable table) {
    this(table, null);
  }

  public MenuItemSetTableModel(JTable table, MenuItemSet itemSet) {
    this.table = table;
    setMenuItemSet(itemSet);
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

      case 3:
        return ticketItem.getDiscountAmount();

      case 4:
        return ticketItem.getTotalAmount();
    }

    return null;
  }

  private void update() {
    calculateRows();
    fireTableDataChanged();
  }

  private void calculateRows() {
    SetItemRowCreator.calculateSetRows(itemSet, tableRows);
  }

}
