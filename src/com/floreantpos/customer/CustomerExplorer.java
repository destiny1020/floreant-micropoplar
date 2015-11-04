package com.floreantpos.customer;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import org.jdesktop.swingx.JXDatePicker;
import org.jdesktop.swingx.JXTable;

import com.floreantpos.POSConstants;
import com.floreantpos.bo.ui.BOMessageDialog;
import com.floreantpos.bo.ui.BackOfficeWindow;
import com.floreantpos.bo.ui.ComboOption;
import com.floreantpos.bo.ui.explorer.search.CustomerSearchDto;
import com.floreantpos.model.Customer;
import com.floreantpos.model.dao.CustomerDAO;
import com.floreantpos.swing.TransparentPanel;
import com.floreantpos.ui.PosTableRenderer;
import com.floreantpos.ui.dialog.BeanEditorDialog;
import com.floreantpos.ui.dialog.ConfirmDeleteDialog;
import com.floreantpos.ui.dialog.POSMessageDialog;
import com.floreantpos.ui.forms.CustomerForm;
import com.floreantpos.ui.util.UiUtil;
import com.micropoplar.pos.ui.TextFieldWithPrompt;
import com.micropoplar.pos.ui.util.ControllerGenerator;

import net.miginfocom.swing.MigLayout;

public class CustomerExplorer extends TransparentPanel {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private JXTable explorerTable;
  private CustomerListTableModel tableModel;
  private List<Customer> customers;

  private JLabel lblSearch;
  private TextFieldWithPrompt tfSearch;

  private JLabel lblCreateTimeStart;
  private JLabel lblCreateTimeEnd;
  private JXDatePicker dpCreateTimeStart;
  private JXDatePicker dpCreateTimeEnd;

  private JLabel lblLastActiveTimeStart;
  private JLabel lblLastActiveTimeEnd;
  private JXDatePicker dpLastActiveTimeStart;
  private JXDatePicker dpLastActiveTimeEnd;

  private JLabel lblGender;
  private JComboBox<ComboOption> cbGender;

  private JLabel lblAgeRange;
  private JComboBox<ComboOption> cbAgeRange;

  private JButton btnLoad;
  private TransparentPanel pnlFilters;

  public CustomerExplorer() {
    setLayout(new BorderLayout());

    initTableGroup();
    initFilterGroup();
    initControlGroup();
  }

  private void initTableGroup() {
    tableModel = new CustomerListTableModel(customers);
    explorerTable = new JXTable(tableModel);
    explorerTable.setDefaultRenderer(Object.class, new PosTableRenderer());
    explorerTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    explorerTable.setColumnControlVisible(true);
    explorerTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    explorerTable.setHorizontalScrollEnabled(false);

    add(new JScrollPane(explorerTable), BorderLayout.CENTER);
  }

  private void initFilterGroup() {
    pnlFilters = new TransparentPanel();
    pnlFilters.setLayout(new MigLayout("", "[][][][][][][][][grow]", "[][][][]"));

    lblSearch = new JLabel(POSConstants.CUSTOMER_EXPLORER_SEARCH + POSConstants.COLON);
    pnlFilters.add(lblSearch, "cell 0 0, alignx left, aligny center");
    tfSearch = new TextFieldWithPrompt(POSConstants.CUSTOMER_EXPLORER_SEARCH_PROMPT);
    pnlFilters.add(tfSearch, "cell 1 0 5 1, grow");

    lblCreateTimeStart =
        new JLabel(POSConstants.CUSTOMER_EXPLORER_CREATE_TIME_START + POSConstants.COLON);
    pnlFilters.add(lblCreateTimeStart, "cell 0 1, alignx left, aligny center");

    dpCreateTimeStart = UiUtil.getCurrentMonthStart();
    pnlFilters.add(dpCreateTimeStart, "cell 1 1, alignx left, aligny center");

    lblCreateTimeEnd =
        new JLabel(POSConstants.CUSTOMER_EXPLORER_CREATE_TIME_END + POSConstants.COLON);
    pnlFilters.add(lblCreateTimeEnd, "cell 2 1, alignx left, aligny center");

    dpCreateTimeEnd = UiUtil.getCurrentMonthEnd();
    pnlFilters.add(dpCreateTimeEnd, "cell 3 1, alignx left, aligny center");

    lblLastActiveTimeStart =
        new JLabel(POSConstants.CUSTOMER_EXPLORER_LAST_ACTIVE_TIME_START + POSConstants.COLON);
    pnlFilters.add(lblLastActiveTimeStart, "cell 4 1, alignx left, aligny center");

    dpLastActiveTimeStart = UiUtil.getCurrentMonthStart();
    pnlFilters.add(dpLastActiveTimeStart, "cell 5 1, alignx left, aligny center");

    lblLastActiveTimeEnd =
        new JLabel(POSConstants.CUSTOMER_EXPLORER_LAST_ACTIVE_TIME_END + POSConstants.COLON);
    pnlFilters.add(lblLastActiveTimeEnd, "cell 6 1, alignx left, aligny center");

    dpLastActiveTimeEnd = UiUtil.getCurrentMonthEnd();
    pnlFilters.add(dpLastActiveTimeEnd, "cell 7 1, alignx left, aligny center");

    lblGender = new JLabel(POSConstants.CUSTOMER_EXPLORER_GENDER + POSConstants.COLON);
    pnlFilters.add(lblGender, "cell 0 2, alignx left, aligny center");

    cbGender = ControllerGenerator.getGenderComboBox();
    pnlFilters.add(cbGender, "cell 1 2, alignx left, aligny center");

    lblAgeRange = new JLabel(POSConstants.CUSTOMER_EXPLORER_AGE_RANGE + POSConstants.COLON);
    pnlFilters.add(lblAgeRange, "cell 2 2, alignx left, aligny center");

    cbAgeRange = ControllerGenerator.getAgeRangeComboBox();
    pnlFilters.add(cbAgeRange, "cell 3 2, alignx left, aligny center");

    btnLoad = new JButton(POSConstants.LOAD);
    btnLoad.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent evt) {
        doLoadCustomers(evt);
      }
    });

    pnlFilters.add(btnLoad, "cell 2 3 2 1, growx");

    add(pnlFilters, BorderLayout.NORTH);
  }

  private void doLoadCustomers(ActionEvent evt) {
    Date createTimeStart = dpCreateTimeStart.getDate();
    Date createTimeEnd = dpCreateTimeEnd.getDate();

    Date lastActiveTimeStart = dpLastActiveTimeStart.getDate();
    Date lastActiveTimeEnd = dpLastActiveTimeEnd.getDate();

    if (createTimeStart.after(createTimeEnd) || lastActiveTimeStart.after(lastActiveTimeEnd)) {
      POSMessageDialog.showError(BackOfficeWindow.getInstance(),
          com.floreantpos.POSConstants.FROM_DATE_CANNOT_BE_GREATER_THAN_TO_DATE_);
      return;
    }

    CustomerSearchDto searchDto = prepareSearchDto();

    CustomerDAO dao = CustomerDAO.getInstance();
    customers = dao.findCustomers(searchDto);

    tableModel.setRows(customers);
  }

  private CustomerSearchDto prepareSearchDto() {
    CustomerSearchDto searchDto = new CustomerSearchDto();

    searchDto.setPhone(tfSearch.getText().trim());
    searchDto.setCreateTimeStart(dpCreateTimeStart.getDate());
    searchDto.setCreateTimeEnd(dpCreateTimeEnd.getDate());
    searchDto.setLastActiveTimeStart(dpLastActiveTimeStart.getDate());
    searchDto.setLastActiveTimeEnd(dpLastActiveTimeEnd.getDate());
    searchDto.setGender((ComboOption) cbGender.getSelectedItem());
    searchDto.setAgeRange((ComboOption) cbAgeRange.getSelectedItem());

    return searchDto;
  }

  private void initControlGroup() {
    JButton addButton = new JButton(com.floreantpos.POSConstants.ADD);
    addButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          CustomerForm editor = new CustomerForm();
          BeanEditorDialog dialog =
              new BeanEditorDialog(editor, BackOfficeWindow.getInstance(), true);
          dialog.open();
          if (dialog.isCanceled())
            return;
          Customer customer = editor.getBean();
          tableModel.addItem(customer);
        } catch (Exception x) {
          BOMessageDialog.showError(com.floreantpos.POSConstants.ERROR_MESSAGE, x);
        }
      }

    });

    final JButton editButton = new JButton(com.floreantpos.POSConstants.EDIT);
    editButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          int index = explorerTable.getSelectedRow();
          if (index < 0)
            return;

          Customer customer = customers.get(index);

          CustomerForm editor = new CustomerForm();
          editor.setBean(customer);
          BeanEditorDialog dialog =
              new BeanEditorDialog(editor, BackOfficeWindow.getInstance(), true);
          dialog.open();
          if (dialog.isCanceled())
            return;

          explorerTable.repaint();
        } catch (Throwable x) {
          BOMessageDialog.showError(com.floreantpos.POSConstants.ERROR_MESSAGE, x);
        }
      }
    });
    explorerTable.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent evt) {
        if (evt.getClickCount() == 2) {
          editButton.doClick();
        }
      }
    });

    JButton deleteButton = new JButton(com.floreantpos.POSConstants.DELETE);
    deleteButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          int index = explorerTable.getSelectedRow();
          if (index < 0)
            return;

          if (ConfirmDeleteDialog.showMessage(CustomerExplorer.this,
              com.floreantpos.POSConstants.CONFIRM_DELETE,
              com.floreantpos.POSConstants.DELETE) == ConfirmDeleteDialog.YES) {
            Customer customer = customers.get(index);
            CustomerDAO dao = new CustomerDAO();
            dao.delete(customer);
            tableModel.deleteItem(index);
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
