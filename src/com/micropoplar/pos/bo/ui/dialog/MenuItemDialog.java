package com.micropoplar.pos.bo.ui.dialog;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import com.floreantpos.POSConstants;
import com.floreantpos.model.MenuItem;
import com.floreantpos.model.dao.MenuItemDAO;
import com.floreantpos.swing.PosButton;
import com.floreantpos.ui.PosTableRenderer;
import com.floreantpos.ui.dialog.POSDialog;
import com.micropoplar.pos.bo.ui.model.MenuItemDialogTableModel;
import com.micropoplar.pos.bo.ui.model.MenuItemSelectedTableModel;
import com.micropoplar.pos.model.MenuItemSet;

import net.miginfocom.swing.MigLayout;

public class MenuItemDialog extends POSDialog {

  private static List<MenuItem> candidateMenuItemList;
  private List<MenuItem> selectedMenuItemList;

  static {
    MenuItemDAO dao = new MenuItemDAO();
    candidateMenuItemList = dao.findAll();
  }

  private JTable tableMenuItems;
  private MenuItemDialogTableModel candidateTableModel;

  private JTable tableSelectedMenuItems;
  private MenuItemSelectedTableModel selectedTableModel;

  private JButton btnOK;
  private JButton btnCancel;

  /**
   * @wbp.parser.constructor
   */
  public MenuItemDialog(Dialog parent) {
    this(parent, null);
    this.setMinimumSize(new Dimension(1024, 768));
    setTitle(POSConstants.MENU_ITEM_SET_EDITOR_EDIT_DLG);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
  }

  @SuppressWarnings("unchecked")
  public MenuItemDialog(Dialog parent, MenuItemSet menuItemSet) {
    super(parent, true);

    // init the selected menu items
    selectedMenuItemList = new ArrayList<>();

    getContentPane().setLayout(new MigLayout("", "[470px][60px][470px]", "[15px][720px][20px]"));

    JLabel lblTitle = new JLabel();
    lblTitle.setText(POSConstants.MENU_ITEM_SET_EDITOR_AVAILABLE_ITEMS);
    getContentPane().add(lblTitle, "cell 0 0 3 1,alignx center,aligny top");

    candidateTableModel = new MenuItemDialogTableModel();
    candidateTableModel.setRows(candidateMenuItemList);
    tableMenuItems = new JTable(candidateTableModel);
    tableMenuItems.setDefaultRenderer(Object.class, new PosTableRenderer());
    tableMenuItems.getSelectionModel()
        .setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

    getContentPane().add(new JScrollPane(tableMenuItems), "cell 0 1,grow");

    JPanel pnlButtons = new JPanel(new BorderLayout(5, 5));

    PosButton btnRight = new PosButton();
    btnRight.setIcon(new ImageIcon(getClass().getResource("/images/next_32.png")));
    btnRight.setPreferredSize(new Dimension(76, 45));
    btnRight.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        doSelect(evt);
      }
    });
    pnlButtons.add(btnRight, BorderLayout.NORTH);

    PosButton btnLeft = new PosButton();
    btnLeft.setIcon(new ImageIcon(getClass().getResource("/images/previous_32.png")));
    btnLeft.setPreferredSize(new Dimension(76, 45));
    btnLeft.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        doUnselect(evt);
      }
    });
    pnlButtons.add(btnLeft, BorderLayout.SOUTH);

    getContentPane().add(pnlButtons, "cell 1 1");

    selectedTableModel = new MenuItemSelectedTableModel();
    selectedTableModel.setRows(selectedMenuItemList);
    tableSelectedMenuItems = new JTable(selectedTableModel);
    tableSelectedMenuItems.getSelectionModel()
        .setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

    getContentPane().add(new JScrollPane(tableSelectedMenuItems), "cell 2 1,grow");

    JPanel pnlOperationButtons = new JPanel(new GridLayout(1, 2, 5, 5));

    btnOK = new JButton(POSConstants.CONFIRM);
    btnOK.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        // TODO
      }
    });

    btnCancel = new JButton(POSConstants.CANCEL);
    btnCancel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        setCanceled(true);
        dispose();
      }
    });

    pnlOperationButtons.add(btnOK);
    pnlOperationButtons.add(btnCancel);

    getContentPane().add(pnlOperationButtons, "cell 0 2 3 1,grow");
  }

  @SuppressWarnings("unchecked")
  private void doSelect(ActionEvent evt) {
    int[] selectedRows = tableMenuItems.getSelectedRows();
    for (int selectedRow : selectedRows) {
      MenuItem menuItem = candidateMenuItemList.get(selectedRow);
      if (!alreadySelected(menuItem)) {
        selectedTableModel.addItem(menuItem);
      }
    }
  }

  private void doUnselect(ActionEvent evt) {
    int[] selectedRows = tableSelectedMenuItems.getSelectedRows();
    for (int idx = selectedRows.length; idx > 0; idx--) {
      selectedTableModel.deleteItem(selectedRows[idx - 1]);
    }
  }

  private boolean alreadySelected(MenuItem menuItem) {
    for (MenuItem item : selectedMenuItemList) {
      if (item.getId().intValue() == menuItem.getId().intValue()) {
        return true;
      }
    }

    return false;
  }
}
