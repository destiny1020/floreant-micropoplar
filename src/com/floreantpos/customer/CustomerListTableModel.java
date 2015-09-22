package com.floreantpos.customer;

import java.util.List;

import com.floreantpos.bo.ui.explorer.ListTableModel;
import com.floreantpos.model.Customer;
import com.floreantpos.ui.util.UiUtil;

public class CustomerListTableModel extends ListTableModel<Customer> {
  private final static String[] columns = {"电话号码", "姓名", "生日", "电子邮件", "地址"};

  public CustomerListTableModel() {
    super(columns);
  }

  public CustomerListTableModel(List<Customer> customers) {
    super(columns, customers);
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {

    Customer customer = getRowData(rowIndex);

    switch (columnIndex) {
      case 0:
        return customer.getTelephoneNo();
      case 1:
        return customer.getName();

      case 2:
        return UiUtil.getDobStr(customer.getDob());

      case 3:
        return customer.getEmail();

      case 4:
        return customer.getAddress();

    }
    return null;
  }
}
