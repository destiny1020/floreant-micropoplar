package com.floreantpos.bo.ui.explorer;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.hibernate.exception.ConstraintViolationException;

import com.floreantpos.bo.ui.BOMessageDialog;
import com.floreantpos.bo.ui.BackOfficeWindow;
import com.floreantpos.model.User;
import com.floreantpos.model.dao.UserDAO;
import com.floreantpos.swing.TransparentPanel;
import com.floreantpos.ui.PosTableRenderer;
import com.floreantpos.ui.dialog.BeanEditorDialog;
import com.floreantpos.ui.dialog.ConfirmDeleteDialog;
import com.floreantpos.ui.forms.UserForm;

public class UserExplorer extends TransparentPanel {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private JTable table;
  private UserTableModel tableModel;

  public UserExplorer() {
    List<User> users = UserDAO.getInstance().findAll();

    tableModel = new UserTableModel(users);
    table = new JTable(tableModel);
    table.setDefaultRenderer(Object.class, new PosTableRenderer());

    setLayout(new BorderLayout(5, 5));
    add(new JScrollPane(table));

    JButton addButton = new JButton(com.floreantpos.POSConstants.ADD);
    addButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          UserForm editor = new UserForm();
          BeanEditorDialog dialog =
              new BeanEditorDialog(editor, BackOfficeWindow.getInstance(), true);
          dialog.open();
          if (dialog.isCanceled())
            return;
          User user = editor.getBean();
          tableModel.addItem(user);
        } catch (Exception x) {
          x.printStackTrace();
          BOMessageDialog.showError(com.floreantpos.POSConstants.ERROR_MESSAGE, x);
        }
      }

    });
    JButton copyButton = new JButton(com.floreantpos.POSConstants.COPY);
    copyButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          int index = table.getSelectedRow();
          if (index < 0)
            return;

          User user = (User) tableModel.getRowData(index);

          User user2 = new User();
          user2.setUserId(user.getUserId());
          user2.setType(user.getType());
          user2.setName(user.getName());
          user2.setPassword(user.getPassword());
          user2.setPhoneNo(user.getPhoneNo());

          UserForm editor = new UserForm();
          editor.setEditMode(false);
          editor.setBean(user2);
          BeanEditorDialog dialog =
              new BeanEditorDialog(editor, BackOfficeWindow.getInstance(), true);
          dialog.open();
          if (dialog.isCanceled())
            return;

          User newUser = editor.getBean();
          tableModel.addItem(newUser);
        } catch (Exception x) {
          BOMessageDialog.showError(com.floreantpos.POSConstants.ERROR_MESSAGE, x);
        }
      }

    });

    JButton editButton = new JButton(com.floreantpos.POSConstants.EDIT);
    editButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          int index = table.getSelectedRow();
          if (index < 0)
            return;

          User user = (User) tableModel.getRowData(index);
          UserForm editor = new UserForm();
          editor.setEditMode(true);
          editor.setBean(user);
          BeanEditorDialog dialog =
              new BeanEditorDialog(editor, BackOfficeWindow.getInstance(), true);
          dialog.open();
          if (dialog.isCanceled())
            return;

          tableModel.updateItem(index);
        } catch (Throwable x) {
          BOMessageDialog.showError(com.floreantpos.POSConstants.ERROR_MESSAGE, x);
        }
      }

    });
    JButton deleteButton = new JButton(com.floreantpos.POSConstants.DELETE);
    deleteButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        int index = table.getSelectedRow();
        if (index < 0)
          return;

        User user = (User) tableModel.getRowData(index);
        if (user == null) {
          return;
        }

        try {
          if (ConfirmDeleteDialog.showMessage(UserExplorer.this,
              com.floreantpos.POSConstants.CONFIRM_DELETE,
              com.floreantpos.POSConstants.DELETE) == ConfirmDeleteDialog.YES) {
            UserDAO.getInstance().delete(user);
            tableModel.deleteItem(index);
          }
        } catch (ConstraintViolationException x) {
          String message = com.floreantpos.POSConstants.USER + " " + user.getName() + " (" //$NON-NLS-1$//$NON-NLS-2$
              + user.getType() + ") " + com.floreantpos.POSConstants.ERROR_MESSAGE;
          BOMessageDialog.showError(message, x);
        } catch (Exception x) {
          BOMessageDialog.showError(com.floreantpos.POSConstants.ERROR_MESSAGE, x);
        }
      }

    });

    TransparentPanel panel = new TransparentPanel();
    panel.add(addButton);
    panel.add(copyButton);
    panel.add(editButton);
    panel.add(deleteButton);
    add(panel, BorderLayout.SOUTH);
  }

  class UserTableModel extends ListTableModel {

    UserTableModel(List list) {
      super(new String[] {com.floreantpos.POSConstants.USER_ID,
          com.floreantpos.POSConstants.USER_NAME, com.floreantpos.POSConstants.TYPE}, list);
    }


    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
      User user = (User) rows.get(rowIndex);

      switch (columnIndex) {
        case 0:
          return String.valueOf(user.getUserId());

        case 1:
          return user.toString();

        case 2:
          return user.getType();
      }
      return null;
    }
  }
}
