package com.floreantpos.ui.forms;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.commons.lang.StringUtils;
import org.hibernate.StaleObjectStateException;
import org.jdesktop.swingx.JXDatePicker;

import com.floreantpos.POSConstants;
import com.floreantpos.bo.ui.BOMessageDialog;
import com.floreantpos.model.Customer;
import com.floreantpos.model.dao.CustomerDAO;
import com.floreantpos.model.util.DateUtil;
import com.floreantpos.model.util.IllegalModelStateException;
import com.floreantpos.swing.FixedLengthTextField;
import com.floreantpos.swing.QwertyKeyPad;
import com.floreantpos.ui.BeanEditor;
import com.floreantpos.ui.dialog.POSMessageDialog;

import net.miginfocom.swing.MigLayout;

public class CustomerForm extends BeanEditor<Customer> {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private FixedLengthTextField tfPhone;
  private FixedLengthTextField tfName;
  private JXDatePicker dpDob;

  private FixedLengthTextField tfEmail;
  private FixedLengthTextField tfAddress;
  private JLabel lblDob;
  private JPanel panel;
  private QwertyKeyPad qwertyKeyPad;

  public CustomerForm() {
    setLayout(new MigLayout("", "[][grow][][][][grow]", "[19px][][][][][][][grow]"));

    // Phone
    JLabel lblPhone = new JLabel("电话");
    add(lblPhone, "cell 0 0,alignx trailing");

    tfPhone = new FixedLengthTextField(30);
    tfPhone.setLength(30);
    add(tfPhone, "cell 1 0 5 1,growx");

    // Name
    JLabel lblName = new JLabel("姓名");
    add(lblName, "cell 0 1,alignx trailing");

    tfName = new FixedLengthTextField(60);
    tfName.setLength(60);
    add(tfName, "cell 1 1 5 1,growx");

    // DoB
    lblDob = new JLabel("生日 (年-月-日，例如:1987-10-20)");
    add(lblDob, "cell 0 2,alignx trailing");

    dpDob = new JXDatePicker(getDefaultLocale());
    dpDob.setFormats(DateUtil.getDOBFormatter());
    add(dpDob, "cell 1 2 5 1,growx");

    // E-Mail
    JLabel lblEmail = new JLabel("电子邮箱");
    add(lblEmail, "cell 0 3,alignx trailing");

    tfEmail = new FixedLengthTextField(40);
    tfEmail.setLength(40);
    add(tfEmail, "cell 1 3 5 1,growx");

    // Address
    JLabel lblAddress = new JLabel("地址");
    add(lblAddress, "cell 0 4,alignx trailing");

    tfAddress = new FixedLengthTextField(120);
    tfAddress.setLength(120);
    add(tfAddress, "cell 1 4 5 1,growx");

    panel = new JPanel();
    add(panel, "cell 0 7 6 1,grow");

    qwertyKeyPad = new QwertyKeyPad();
    panel.add(qwertyKeyPad);
  }

  public void setFieldsEditable(boolean editable) {
    tfName.setEditable(editable);
    tfPhone.setEditable(editable);
    tfEmail.setEditable(editable);
    tfAddress.setEditable(editable);
    dpDob.setEditable(editable);
  }

  @Override
  public boolean save() {
    try {
      if (!updateModel())
        return false;

      Customer customer = getBean();

      // make sure there is no duplicates
      CustomerDAO dao = CustomerDAO.getInstance();
      Customer existedCustomer = dao.findByPhone(customer.getPhone());
      if (existedCustomer != null) {
        BOMessageDialog.showError(this, POSConstants.ERROR_DUPLICATE_CUSTOMER);
        return false;
      }

      dao.saveOrUpdate(customer);
      return true;
    } catch (IllegalModelStateException e) {
    } catch (StaleObjectStateException e) {
      BOMessageDialog.showError(this, "该会员信息似乎已经被另外的操作人员修改了, 保存失败.");
    } catch (Exception e) {
      e.printStackTrace();
      BOMessageDialog.showError(this, e.getMessage());
    }

    return false;
  }

  @Override
  protected void updateView() {
    Customer customer = getBean();

    if (customer != null) {
      tfName.setText(customer.getName());
      if (customer.getDob() != null) {
        dpDob.setDate(customer.getDob());
      }
      tfAddress.setText(customer.getAddress());
      tfEmail.setText(customer.getEmail());
      tfPhone.setText(customer.getPhone());
    } else {
      tfName.setText("");
      tfAddress.setText("");
      tfEmail.setText("");
      tfPhone.setText("");
    }
  }

  @Override
  protected boolean updateModel() throws IllegalModelStateException {
    String phoneString = tfPhone.getText();

    if (StringUtils.isEmpty(phoneString)) {
      POSMessageDialog.showError(POSConstants.ERROR_CUSTOMER_PHONE_NOT_PROVIDED);
      return false;
    }

    // check phone number
    if (!isMobileNO(phoneString)) {
      POSMessageDialog.showError(POSConstants.ERROR_CUSTOMER_PHONE_NOT_VALID);
      return false;
    }

    Customer customer = getBean();

    if (customer == null) {
      customer = new Customer();
      setBean(customer, false);
    }

    customer.setName(tfName.getText());

    Date dob = dpDob.getDate();
    customer.setDob(dob);

    customer.setAddress(tfAddress.getText());
    customer.setEmail(tfEmail.getText());
    customer.setPhone(phoneString);

    return true;
  }

  private boolean isMobileNO(String mobileNumber) {

    Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");

    Matcher m = p.matcher(mobileNumber);

    return m.matches();

  }

  @Override
  public String getDisplayText() {
    if (editMode) {
      return "编辑会员";
    }
    return "创建会员";
  }
}
