package com.floreantpos.bo.ui.explorer;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import com.floreantpos.bo.ui.BOMessageDialog;
import com.floreantpos.bo.ui.BackOfficeWindow;
import com.floreantpos.model.Coupon;
import com.floreantpos.model.dao.CouponDAO;
import com.floreantpos.swing.TransparentPanel;
import com.floreantpos.ui.PosTableRenderer;
import com.floreantpos.ui.dialog.BeanEditorDialog;
import com.floreantpos.ui.dialog.ConfirmDeleteDialog;
import com.floreantpos.ui.model.CouponForm;

public class CouponExplorer extends TransparentPanel implements ActionListener {
  private JTable explorerView;
  private CouponExplorerTableModel explorerModel;

  public CouponExplorer() {

    explorerView = new JTable();
    explorerView.setDefaultRenderer(Object.class, new PosTableRenderer());

    setLayout(new BorderLayout(5, 5));
    add(new JScrollPane(explorerView));

    JButton addButton = new JButton(com.floreantpos.POSConstants.NEW);
    addButton.setActionCommand(com.floreantpos.POSConstants.ADD);
    addButton.addActionListener(this);

    final JButton editButton = new JButton(com.floreantpos.POSConstants.EDIT);
    editButton.setActionCommand(com.floreantpos.POSConstants.EDIT);
    editButton.addActionListener(this);

    explorerView.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent evt) {
        if (evt.getClickCount() == 2) {
          editButton.doClick();
        }
      }
    });

    JButton deleteButton = new JButton(com.floreantpos.POSConstants.DELETE);
    deleteButton.setActionCommand(com.floreantpos.POSConstants.DELETE);
    deleteButton.addActionListener(this);

    TransparentPanel panel = new TransparentPanel();
    panel.add(addButton);
    panel.add(editButton);
    panel.add(deleteButton);
    add(panel, BorderLayout.SOUTH);
  }

  public void initData() throws Exception {
    CouponDAO dao = new CouponDAO();
    List<Coupon> couponList = dao.findAll();
    explorerModel = new CouponExplorerTableModel(couponList);
    explorerView.setModel(explorerModel);
  }

  private void addNewCoupon() {
    try {
      CouponForm editor = new CouponForm();
      BeanEditorDialog dialog = new BeanEditorDialog(editor, BackOfficeWindow.getInstance(), true);
      dialog.open();

      if (dialog.isCanceled())
        return;
      Coupon coupon = (Coupon) editor.getBean();
      explorerModel.addCoupon(coupon);
    } catch (Exception x) {
      BOMessageDialog.showError(com.floreantpos.POSConstants.ERROR_MESSAGE, x);
    }
  }

  private void editCoupon(Coupon coupon) {
    try {
      CouponForm editor = new CouponForm(coupon);
      BeanEditorDialog dialog = new BeanEditorDialog(editor, BackOfficeWindow.getInstance(), true);
      dialog.open();
      if (dialog.isCanceled())
        return;

      explorerView.repaint();
    } catch (Throwable x) {
      BOMessageDialog.showError(com.floreantpos.POSConstants.ERROR_MESSAGE, x);
    }
  }

  private void deleteCoupon(int index, Coupon coupon) {
    try {
      if (ConfirmDeleteDialog.showMessage(this, com.floreantpos.POSConstants.CONFIRM_DELETE,
          com.floreantpos.POSConstants.DELETE) == ConfirmDeleteDialog.YES) {
        CouponDAO dao = new CouponDAO();
        dao.delete(coupon);
        explorerModel.deleteCoupon(coupon, index);
      }
    } catch (Exception x) {
      BOMessageDialog.showError(com.floreantpos.POSConstants.ERROR_MESSAGE, x);
    }
  }

  private class CouponExplorerTableModel extends AbstractTableModel {
    String[] columnNames =
        {com.floreantpos.POSConstants.NAME, com.floreantpos.POSConstants.COUPON_TYPE,
            com.floreantpos.POSConstants.COUPON_VALUE, com.floreantpos.POSConstants.EXPIRY_DATE,
            com.floreantpos.POSConstants.DISABLED, com.floreantpos.POSConstants.NEVER_EXPIRE};
    List<Coupon> couponList;

    CouponExplorerTableModel(List<Coupon> list) {
      this.couponList = list;
    }

    public int getRowCount() {
      if (couponList == null)
        return 0;

      return couponList.size();
    }

    public int getColumnCount() {
      return columnNames.length;
    }

    @Override
    public String getColumnName(int index) {
      return columnNames[index];
    }

    public Object getValueAt(int row, int column) {
      if (couponList == null)
        return ""; //$NON-NLS-1$

      Coupon coupon = couponList.get(row);
      switch (column) {
        case 0:
          return coupon.getName();

        case 1:
          return Coupon.COUPON_TYPE_NAMES[coupon.getType()];

        case 2:
          return Double.valueOf(coupon.getValue());

        case 3:
          return coupon.getExpiryDate();

        case 4:
          return Boolean.valueOf(coupon.isDisabled());

        case 5:
          return Boolean.valueOf(coupon.isNeverExpire());
      }

      return null;
    }

    public void addCoupon(Coupon coupon) {
      int size = couponList.size();
      couponList.add(coupon);
      fireTableRowsInserted(size, size);
    }

    public void deleteCoupon(Coupon coupon, int index) {
      couponList.remove(coupon);
      fireTableRowsDeleted(index, index);
    }

    public Coupon getCoupon(int index) {
      return couponList.get(index);
    }
  }

  public void actionPerformed(ActionEvent e) {
    String actionCommand = e.getActionCommand();
    if (com.floreantpos.POSConstants.ADD.equals(actionCommand)) {
      addNewCoupon();
    } else if (com.floreantpos.POSConstants.EDIT.equals(actionCommand)) {
      int index = explorerView.getSelectedRow();
      if (index < 0) {
        BOMessageDialog.showError(com.floreantpos.POSConstants.SELECT_COUPON_TO_EDIT);
        return;
      }
      Coupon coupon = explorerModel.getCoupon(index);
      editCoupon(coupon);
    } else if (com.floreantpos.POSConstants.DELETE.equals(actionCommand)) {
      int index = explorerView.getSelectedRow();
      if (index < 0) {
        BOMessageDialog.showError(com.floreantpos.POSConstants.SELECT_COUPON_TO_DELETE);
        return;
      }
      Coupon coupon = explorerModel.getCoupon(index);
      deleteCoupon(index, coupon);
    }
  }
}
