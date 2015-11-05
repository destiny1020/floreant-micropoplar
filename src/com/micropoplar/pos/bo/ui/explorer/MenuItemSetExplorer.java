package com.micropoplar.pos.bo.ui.explorer;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.floreantpos.POSConstants;
import com.floreantpos.bo.ui.BOMessageDialog;
import com.floreantpos.bo.ui.BackOfficeWindow;
import com.floreantpos.bo.ui.explorer.ExplorerButtonPanel;
import com.floreantpos.bo.ui.explorer.ListTableModel;
import com.floreantpos.swing.TransparentPanel;
import com.floreantpos.ui.PosTableRenderer;
import com.floreantpos.ui.dialog.BeanEditorDialog;
import com.floreantpos.ui.dialog.ConfirmDeleteDialog;
import com.micropoplar.pos.model.MenuItemSet;
import com.micropoplar.pos.model.SetItem;
import com.micropoplar.pos.model.dao.MenuItemSetDAO;
import com.micropoplar.pos.model.dao.SetItemDAO;
import com.micropoplar.pos.ui.model.MenuItemSetForm;

public class MenuItemSetExplorer extends TransparentPanel {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private List<MenuItemSet> itemSetList;

  private JTable table;
  private MenuItemSetExplorerTableModel tableModel;

  public MenuItemSetExplorer() {
    MenuItemSetDAO dao = new MenuItemSetDAO();
    itemSetList = dao.findAll();

    tableModel = new MenuItemSetExplorerTableModel();
    tableModel.setRows(itemSetList);
    table = new JTable(tableModel);
    table.setDefaultRenderer(Object.class, new PosTableRenderer());

    setLayout(new BorderLayout(5, 5));
    add(new JScrollPane(table));
    ExplorerButtonPanel explorerButton = new ExplorerButtonPanel();
    final JButton editButton = explorerButton.getEditButton();
    JButton addButton = explorerButton.getAddButton();
    JButton deleteButton = explorerButton.getDeleteButton();

    // bind actions
    editButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          int index = table.getSelectedRow();
          if (index < 0)
            return;

          MenuItemSet menuItem = itemSetList.get(index);
          menuItem = MenuItemSetDAO.getInstance().initialize(menuItem);
          itemSetList.set(index, menuItem);

          MenuItemSetForm editor = new MenuItemSetForm(menuItem);
          BeanEditorDialog dialog =
              new BeanEditorDialog(editor, BackOfficeWindow.getInstance(), true);
          dialog.open();
          if (dialog.isCanceled())
            return;

          table.repaint();
        } catch (Throwable x) {
          BOMessageDialog.showError(com.floreantpos.POSConstants.ERROR_MESSAGE, x);
        }
      }
    });
    table.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent evt) {
        if (evt.getClickCount() == 2) {
          editButton.doClick();
        }
      }
    });

    addButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          MenuItemSetForm editor = new MenuItemSetForm();
          BeanEditorDialog dialog =
              new BeanEditorDialog(editor, BackOfficeWindow.getInstance(), true);
          dialog.open();
          if (dialog.isCanceled()) {
            return;
          }
          MenuItemSet setItem = editor.getBean();
          tableModel.addItem(setItem);
        } catch (Throwable x) {
          BOMessageDialog.showError(com.floreantpos.POSConstants.ERROR_MESSAGE, x);
        }
      }
    });

    deleteButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          int index = table.getSelectedRow();
          if (index < 0) {
            return;
          }

          if (ConfirmDeleteDialog.showMessage(MenuItemSetExplorer.this, POSConstants.CONFIRM_DELETE,
              POSConstants.DELETE) != ConfirmDeleteDialog.NO) {
            MenuItemSet menuitemSet = itemSetList.get(index);
            MenuItemSetDAO menuItemSetDAO = MenuItemSetDAO.getInstance();
            menuitemSet = menuItemSetDAO.initialize(menuitemSet);

            // delete set items first
            if (menuitemSet.getItems() != null) {
              for (SetItem item : menuitemSet.getItems()) {
                SetItemDAO setItemDAO = SetItemDAO.getInstance();
                setItemDAO.delete(item);
              }
            }

            menuitemSet = menuItemSetDAO.initialize(menuitemSet);
            menuItemSetDAO.delete(menuitemSet);
            tableModel.deleteMenuItemSet(menuitemSet, index);
          }
        } catch (Throwable x) {
          BOMessageDialog.showError(com.floreantpos.POSConstants.ERROR_MESSAGE, x);
        }
      }
    });

    TransparentPanel panel = new TransparentPanel();
    panel.add(addButton);
    panel.add(editButton);
    panel.add(deleteButton);
    add(panel, BorderLayout.SOUTH);
  }

  class MenuItemSetExplorerTableModel extends ListTableModel<MenuItemSet> {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    // @formatter:off
    private String[] columnNames =  {
        POSConstants.MENU_ITEM_SET_EXPLORER_CODE,
        POSConstants.MENU_ITEM_SET_EXPLORER_NAME,
        POSConstants.MENU_ITEM_SET_EXPLORER_BARCODE, 
        POSConstants.MENU_ITEM_SET_EXPLORER_PRICE,
        POSConstants.MENU_ITEM_SET_EXPLORER_GROUP,
        POSConstants.MENU_ITEM_SET_EXPLORER_CATEGORY,
        POSConstants.MENU_ITEM_SET_EXPLORER_VISIBILITY
    };
    // @formatter:on

    MenuItemSetExplorerTableModel() {
      setColumnNames(columnNames);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
      MenuItemSet item = rows.get(rowIndex);

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

      return null;
    }

    public void addMenuItemSet(MenuItemSet menuItemSet) {
      super.addItem(menuItemSet);

    }

    public void deleteMenuItemSet(MenuItemSet category, int index) {
      super.deleteItem(index);
    }

  }

}
