package com.floreantpos.customer;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.floreantpos.bo.ui.BOMessageDialog;
import com.floreantpos.bo.ui.BackOfficeWindow;
import com.floreantpos.model.Customer;
import com.floreantpos.model.dao.CustomerDAO;
import com.floreantpos.swing.BeanTableModel;
import com.floreantpos.swing.TransparentPanel;
import com.floreantpos.ui.PosTableRenderer;
import com.floreantpos.ui.dialog.BeanEditorDialog;
import com.floreantpos.ui.dialog.ConfirmDeleteDialog;
import com.floreantpos.ui.forms.CustomerForm;
import com.floreantpos.util.POSComparators;
import com.floreantpos.util.PosGuiUtil;

public class CustomerExplorer extends TransparentPanel {
  private List<Customer> customerList;

  private JTable table;

  private BeanTableModel<Customer> tableModel;

  public CustomerExplorer() {
    CustomerDAO dao = new CustomerDAO();
    customerList = dao.findAll();

    // sort customers by ID asc default:
    Collections.sort(customerList, POSComparators.COMPARATOR_ID);

    tableModel = new BeanTableModel<Customer>(Customer.class);
    tableModel.addColumn("ID", "autoId");
    tableModel.addColumn("电话", "telephoneNo");
    tableModel.addColumn("邮箱", "email");
    tableModel.addColumn("姓名", "name");
    tableModel.addColumn("生日", "dob");
    // tableModel.addColumn("LOYALTY", "loyaltyNo");
    // tableModel.addColumn("SSN", "ssn");
    tableModel.addColumn("地址", "address");
    tableModel.addColumn("城市", "city");
    // tableModel.addColumn("STATE", "state");
    // tableModel.addColumn("ZIP", "zipCode");
    // tableModel.addColumn("COUNTRY", "country");
    // tableModel.addColumn("CREDIT LIMIT", "creditLimit");
    // tableModel.addColumn("CREDIT SPENT", "creditSpent");
    tableModel.addColumn("备注", "note");
    tableModel.addRows(customerList);

    table = new JTable(tableModel);
    // table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    table.setDefaultRenderer(Object.class, new PosTableRenderer());
    PosGuiUtil.setColumnWidth(table, 0, 40);

    setLayout(new BorderLayout(5, 5));
    add(new JScrollPane(table));

    JButton addButton = new JButton(com.floreantpos.POSConstants.ADD);
    addButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          CustomerForm editor = new CustomerForm();
          BeanEditorDialog dialog =
              new BeanEditorDialog(editor, BackOfficeWindow.getInstance(), true);
          dialog.open();
          if (dialog.isCanceled())
            return;
          Customer customer = (Customer) editor.getBean();
          tableModel.addRow(customer);
        } catch (Exception x) {
          BOMessageDialog.showError(com.floreantpos.POSConstants.ERROR_MESSAGE, x);
        }
      }

    });

    JButton editButton = new JButton(com.floreantpos.POSConstants.EDIT);
    editButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          int index = table.getSelectedRow();
          if (index < 0)
            return;

          Customer customer = customerList.get(index);

          CustomerForm editor = new CustomerForm();
          editor.setBean(customer);
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
    JButton deleteButton = new JButton(com.floreantpos.POSConstants.DELETE);
    deleteButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          int index = table.getSelectedRow();
          if (index < 0)
            return;

          if (ConfirmDeleteDialog.showMessage(CustomerExplorer.this,
              com.floreantpos.POSConstants.CONFIRM_DELETE,
              com.floreantpos.POSConstants.DELETE) == ConfirmDeleteDialog.YES) {
            Customer customer = customerList.get(index);
            CustomerDAO dao = new CustomerDAO();
            dao.delete(customer);
            tableModel.removeRow(customer);
          }
        } catch (Exception x) {
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
}
