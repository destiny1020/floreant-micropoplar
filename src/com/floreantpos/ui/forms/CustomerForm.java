package com.floreantpos.ui.forms;

import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.commons.lang.StringUtils;
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
import com.floreantpos.ui.dialog.POSMessageDialog;
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
    JRadioButton rdbMale = new JRadioButton(POSConstants.GENDER_MALE);
    JRadioButton rdbFemale = new JRadioButton(POSConstants.GENDER_FEMALE);
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
    if (!ValidateUtil.isMobileNO(phoneString)) {
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

  @Override
  public String getDisplayText() {
    if (editMode) {
      return "编辑会员";
    }
    return "创建会员";
  }
}
