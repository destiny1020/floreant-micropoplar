package com.micropoplar.pos.ui.set;

import java.awt.Color;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import com.micropoplar.pos.model.MenuItemSet;

/**
 * Viewer for the items inside one certain set.
 * 
 * @author Destiny
 *
 */
public class MenuItemSetViewerTable extends JTable {

  private static final long serialVersionUID = 1L;
  private MenuItemSetTableModel model;
  private MenuItemSetTableCellRenderer cellRenderer;

  public MenuItemSetViewerTable() {
    this(null);
  }

  public MenuItemSetViewerTable(MenuItemSet itemSet) {
    model = new MenuItemSetTableModel(this);
    setModel(model);

    selectionModel = new DefaultListSelectionModel();
    selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    cellRenderer = new MenuItemSetTableCellRenderer();

    setGridColor(Color.LIGHT_GRAY);
    setSelectionModel(selectionModel);
    //    setAutoscrolls(true);
    setShowGrid(true);
    setBorder(null);

    setRowHeight(20);
    resizeTableColumns();

    setMenuItemSet(itemSet);
  }

  @Override
  public TableCellRenderer getCellRenderer(int row, int column) {
    return cellRenderer;
  }

  public MenuItemSetTableCellRenderer getRenderer() {
    return cellRenderer;
  }

  public void setMenuItemSet(MenuItemSet itemSet) {
    model.setMenuItemSet(itemSet);
  }

  public MenuItemSet getMenuItemSet() {
    return model.getItemSet();
  }

  private void resizeTableColumns() {
    setAutoResizeMode(AUTO_RESIZE_ALL_COLUMNS);
    // setColumnWidth(0, 180);
    setColumnWidth(1, 70);
    setColumnWidth(2, 50);
    setColumnWidth(3, 70);
    // setColumnWidth(4, 60);
  }

  private void setColumnWidth(int columnNumber, int width) {
    TableColumn column = getColumnModel().getColumn(columnNumber);

    column.setPreferredWidth(width);
    column.setMaxWidth(width);
    column.setMinWidth(width);
  }

}
