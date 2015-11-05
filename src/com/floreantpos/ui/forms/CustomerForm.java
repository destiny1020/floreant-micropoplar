package com.floreantpos.ui.forms;

import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.StaleObjectStateException;
import org.jdesktop.swingx.JXDatePicker;

import com.floreantpos.POSConstants;
import com.floreantpos.bo.ui.BOMessageDialog;
import com.floreantpos.bo.ui.ComboOption;
import com.floreantpos.model.Customer;
import com.floreantpos.model.dao.CustomerDAO;
import com.floreantpos.model.util.IllegalModelStateException;
import com.floreantpos.swing.FixedLengthTextField;
import com.floreantpos.ui.BeanEditor;
import com.micropoplar.pos.ui.util.ControllerGenerator;
import com.micropoplar.pos.util.ValidateUtil;

import net.miginfocom.swing.MigLayout;

public class CustomerForm extends BeanEditor<Customer> {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private JLabel lblPhone;
  private FixedLengthTextField tfPhone;

  private JLabel lblGender;
  private JRadioButton rdbMale;
  private JRadioButton rdbFemale;
  private ButtonGroup btgGender;

  private JLabel lblAgeRange;
  private JComboBox<ComboOption> cbAgeRange;

  private JLabel lblName;
  private JTextField tfName;

  private JLabel lblDob;
  private JXDatePicker dpDob;

  private JLabel lblEmail;
  private JTextField tfEmail;

  private JLabel lblAddress;
  private JTextField tfAddress;

  private JLabel lblNote;
  private JTextArea taNote;

  public CustomerForm() {
    this(new Customer());
  }

  public CustomerForm(Customer customer) {
    initComponents();
  }

  private void initComponents() {
    setLayout(new MigLayout());

    ControllerGenerator.addSeparator(this, POSConstants.COMMON_REQUIRED);

    lblPhone = new JLabel(POSConstants.CUSTOMER_FORM_PHONE + POSConstants.COLON);
    tfPhone = new FixedLengthTextField(11);
    add(lblPhone, "gap para");
    add(tfPhone, "span, growx, wrap");

    lblGender = new JLabel(POSConstants.CUSTOMER_FORM_GENDER + POSConstants.COLON);
    rdbMale = new JRadioButton(POSConstants.GENDER_MALE);
    rdbFemale = new JRadioButton(POSConstants.GENDER_FEMALE);
    btgGender = new ButtonGroup();
    btgGender.add(rdbMale);
    btgGender.add(rdbFemale);
    rdbFemale.setSelected(true);
    add(lblGender, "gap para");
    add(rdbMale, "split 2");
    add(rdbFemale, "wrap");

    lblAgeRange = new JLabel(POSConstants.CUSTOMER_FORM_AGE_RANGE + POSConstants.COLON);
    cbAgeRange = ControllerGenerator.getAgeRangeComboBox(false);
    add(lblAgeRange, "gap para");
    add(cbAgeRange, "span, wrap");

    ControllerGenerator.addSeparator(this, POSConstants.COMMON_OPTIONAL);

    lblName = new JLabel(POSConstants.CUSTOMER_FORM_NAME + POSConstants.COLON);
    tfName = new JTextField();
    add(lblName, "gap para");
    add(tfName, "span, growx, wrap");

    lblDob = new JLabel(POSConstants.CUSTOMER_FORM_DOB + POSConstants.COLON);
    dpDob = new JXDatePicker();
    dpDob.getMonthView().setZoomable(true);
    dpDob.setFormats(new String[] {"yyyy-MM-dd"});
    add(lblDob, "gap para");
    add(dpDob, "span, wrap");

    lblEmail = new JLabel(POSConstants.CUSTOMER_FORM_EMAIL + POSConstants.COLON);
    tfEmail = new JTextField();
    add(lblEmail, "gap para");
    add(tfEmail, "span, growx, wrap");

    lblAddress = new JLabel(POSConstants.CUSTOMER_FORM_ADDRESS + POSConstants.COLON);
    tfAddress = new JTextField();
    add(lblAddress, "gap para");
    add(tfAddress, "span, growx, wrap");

    lblNote = new JLabel(POSConstants.CUSTOMER_FORM_NOTE + POSConstants.COLON);
    taNote = new JTextArea();
    taNote.setRows(10);
    taNote.setColumns(40);
    add(lblNote, "gap para");
    add(taNote, "height pref");
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
      tfPhone.setText(customer.getPhone());
      Integer gender = customer.getGender();
      if (gender == 1) {
        rdbMale.setSelected(true);
      } else if (gender == 0) {
        rdbFemale.setSelected(true);
      }
      Integer ageRange = customer.getAgeRange();
      if (ageRange == -1) {
        cbAgeRange.setSelectedIndex(0);
      } else {
        cbAgeRange.setSelectedIndex(ageRange);
      }
      tfName.setText(customer.getName());
      if (customer.getDob() != null) {
        dpDob.setDate(customer.getDob());
      }
      tfEmail.setText(customer.getEmail());
      tfAddress.setText(customer.getAddress());
      taNote.setText(customer.getNote());
    } else {
      tfPhone.setText("");
      rdbFemale.setSelected(true);
      tfName.setText("");
      tfEmail.setText("");
      tfAddress.setText("");
      taNote.setText("");
    }
  }

  @Override
  protected boolean updateModel() throws IllegalModelStateException {
    String phoneString = tfPhone.getText().trim();

    if (StringUtils.isEmpty(phoneString)) {
      BOMessageDialog.showError(POSConstants.ERROR_CUSTOMER_PHONE_NOT_PROVIDED);
      return false;
    }

    // check phone number
    if (!ValidateUtil.isMobileNO(phoneString)) {
      BOMessageDialog.showError(POSConstants.ERROR_CUSTOMER_PHONE_NOT_VALID);
      return false;
    }

    // check email if any
    String email = tfEmail.getText();
    if (StringUtils.isNotBlank(email) && !ValidateUtil.isEmail(email)) {
      BOMessageDialog.showError(POSConstants.ERROR_CUSTOMER_PHONE_NOT_VALID);
      return false;
    }

    // check fields length
    String name = tfName.getText();
    if (StringUtils.isNotBlank(name) && name.length() > 20) {
      BOMessageDialog.showError(String.format(POSConstants.ERROR_TEMPLATE_FIELD_TOO_LONG,
          POSConstants.CUSTOMER_FORM_NAME, 20));
      return false;
    }

    if (StringUtils.isNotBlank(email) && email.length() > 40) {
      BOMessageDialog.showError(String.format(POSConstants.ERROR_TEMPLATE_FIELD_TOO_LONG,
          POSConstants.CUSTOMER_FORM_EMAIL, 40));
      return false;
    }

    String address = tfAddress.getText();
    if (StringUtils.isNotBlank(address) && address.length() > 120) {
      BOMessageDialog.showError(String.format(POSConstants.ERROR_TEMPLATE_FIELD_TOO_LONG,
          POSConstants.CUSTOMER_FORM_ADDRESS, 120));
      return false;
    }

    String note = taNote.getText();
    if (StringUtils.isNotBlank(note) && note.length() > 255) {
      BOMessageDialog.showError(String.format(POSConstants.ERROR_TEMPLATE_FIELD_TOO_LONG,
          POSConstants.CUSTOMER_FORM_NOTE, 255));
      return false;
    }

    Customer customer = getBean();

    if (customer == null) {
      customer = new Customer();
      customer.setCreateTime(new Date());
      setBean(customer, false);
    }

    customer.setPhone(phoneString);
    if (rdbMale.isSelected()) {
      customer.setGender(Customer.GENDER_MALE);
    } else if (rdbFemale.isSelected()) {
      customer.setGender(Customer.GENDER_FEMALE);
    }

    ComboOption selectedAgeRange = (ComboOption) cbAgeRange.getSelectedItem();
    customer.setAgeRange(selectedAgeRange.getValue());

    customer.setName(name);
    Date dob = dpDob.getDate();
    customer.setDob(dob);
    customer.setEmail(email);
    customer.setAddress(address);
    customer.setNote(note);

    return true;
  }

  @Override
  public String getDisplayText() {
    if (editMode) {
      return POSConstants.CUSTOMER_FORM_EDIT_MODE;
    }
    return POSConstants.CUSTOMER_FORM_ADD_MODE;
  }
}
