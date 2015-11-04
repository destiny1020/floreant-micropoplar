package com.floreantpos.customer;

import java.util.List;

import com.floreantpos.POSConstants;
import com.floreantpos.bo.ui.explorer.ListTableModel;
import com.floreantpos.model.Customer;
import com.floreantpos.model.util.DateUtil;
import com.micropoplar.pos.model.AgeRange;

public class CustomerListTableModel extends ListTableModel<Customer> {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  // @formatter:off
  private final static String[] columnNames = {
    POSConstants.CUSTOMER_EXPLORER_TABLE_PHONE,
    POSConstants.CUSTOMER_EXPLORER_TABLE_GENDER,
    POSConstants.CUSTOMER_EXPLORER_TABLE_AGE_RANGE,
    POSConstants.CUSTOMER_EXPLORER_TABLE_CREATE_TIME,
    POSConstants.CUSTOMER_EXPLORER_TABLE_LAST_ACTIVE_TIME,
    POSConstants.CUSTOMER_EXPLORER_TABLE_TOTAL_AMOUNT_BEFORE_DISCOUNT,
    POSConstants.CUSTOMER_EXPLORER_TABLE_TOTAL_DISCOUNT,
    POSConstants.CUSTOMER_EXPLORER_TABLE_TOTAL_AMOUNT_AFTER_DISCOUNT
  };
  // @formatter:on

  public CustomerListTableModel() {
    super(columnNames);
  }

  public CustomerListTableModel(List<Customer> customers) {
    super(columnNames, customers);
  }

  @Override
  public int getRowCount() {
    if (rows == null) {
      return 0;
    }
    return rows.size();
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
  public boolean isCellEditable(int rowIndex, int columnIndex) {
    return false;
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {

    Customer customer = getRowData(rowIndex);

    switch (columnIndex) {
      case 0:
        return customer.getPhone();

      case 1:
        Integer gender = customer.getGender();
        if (gender == null) {
          return POSConstants.GENDER_UNKNOWN;
        }

        return gender == Customer.GENDER_FEMALE ? POSConstants.GENDER_FEMALE
            : POSConstants.GENDER_MALE;

      case 2:
        return AgeRange.fromType(customer.getAgeRange()).getDisplayString();

      case 3:
        return DateUtil.getReceiptDateTime(customer.getCreateTime());

      case 4:
        return DateUtil.getReceiptDateTime(customer.getLastActiveTime());

      case 5:
        return customer.getTotalAmountBeforeDiscount();

      case 6:
        return customer.getTotalDiscount();

      case 7:
        return customer.getTotalAmountAfterDiscount();

    }
    return null;
  }
}
