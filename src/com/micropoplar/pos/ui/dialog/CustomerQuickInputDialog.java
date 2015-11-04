package com.micropoplar.pos.ui.dialog;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;

import com.floreantpos.POSConstants;
import com.floreantpos.main.Application;
import com.floreantpos.model.Customer;
import com.floreantpos.model.dao.CustomerDAO;
import com.floreantpos.swing.FixedLengthTextField;
import com.floreantpos.swing.POSToggleButton;
import com.floreantpos.swing.PosButton;
import com.floreantpos.ui.TitlePanel;
import com.floreantpos.ui.dialog.POSDialog;
import com.floreantpos.ui.dialog.POSMessageDialog;
import com.micropoplar.pos.model.AgeRange;
import com.micropoplar.pos.util.ValidateUtil;

import net.miginfocom.swing.MigLayout;

public class CustomerQuickInputDialog extends POSDialog implements ActionListener {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private TitlePanel titlePanel;

  private FixedLengthTextField tfPhone;

  private ButtonGroup btgGender;
  private ButtonGroup btgAgeRange;

  private String phone;

  private Integer gender;
  private Integer ageRange;

  public CustomerQuickInputDialog(String phone) {
    this(Application.getPosWindow(), phone);
  }

  public CustomerQuickInputDialog(Frame parent, String phone) {
    super(parent, true);
    this.phone = phone;

    initComponents();
  }

  private void initComponents() {
    setTitle(POSConstants.CUSTOMER_QUICK_DLG_TITLE);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    addWindowListener(new WindowAdapter() {

      @Override
      public void windowClosing(WindowEvent e) {

      }

    });

    getContentPane().setLayout(new MigLayout("fillx",
        "[60px,fill][60px,fill][60px,fill][60px,fill][60px,fill][60px,fill]", "[][][][]push[]"));

    titlePanel = new TitlePanel();
    titlePanel.setTitle(POSConstants.CUSTOMER_QUICK_DLG_TITLE_PANEL);
    getContentPane().add(titlePanel, "spanx, growy, height 60,wrap");

    JLabel lblPhone = new JLabel(POSConstants.CUSTOMER_QUICK_DLG_PHONE + POSConstants.COLON);
    getContentPane().add(lblPhone, "span 1, aligny center");

    tfPhone = new FixedLengthTextField(11);
    tfPhone.setText(phone);
    tfPhone.setFont(tfPhone.getFont().deriveFont(Font.BOLD, 24));
    tfPhone.setBackground(Color.WHITE);
    getContentPane().add(tfPhone, "span 4, grow");

    PosButton btnEditPhone = new PosButton(POSConstants.CUSTOMER_QUICK_DLG_MODIFY_PHONE);
    btnEditPhone.setFocusable(false);
    btnEditPhone.setMinimumSize(new Dimension(25, 23));
    getContentPane().add(btnEditPhone, "growy, wrap");

    JLabel lblGender = new JLabel(POSConstants.CUSTOMER_QUICK_DLG_GENDER + POSConstants.COLON);
    getContentPane().add(lblGender, "span 1, aligny center");

    Dimension minDimension = new Dimension(90, 60);

    btgGender = new ButtonGroup();

    POSToggleButton btnMale = new POSToggleButton(POSConstants.CUSTOMER_QUICK_DLG_GENDER_MALE);
    btnMale.setMinimumSize(minDimension);
    btnMale.addActionListener(this);
    btgGender.add(btnMale);
    getContentPane().add(btnMale, "grow");

    POSToggleButton btnFemale = new POSToggleButton(POSConstants.CUSTOMER_QUICK_DLG_GENDER_FEMALE);
    btnFemale.setMinimumSize(minDimension);
    btnFemale.addActionListener(this);
    btgGender.add(btnFemale);
    getContentPane().add(btnFemale, "grow, wrap");

    JLabel lblAgeRange = new JLabel(POSConstants.CUSTOMER_QUICK_DLG_AGE_RANGE + POSConstants.COLON);
    getContentPane().add(lblAgeRange, "span 1, aligny center");

    btgAgeRange = new ButtonGroup();

    POSToggleButton btnAge20Minus = new POSToggleButton(AgeRange.AGE_20_MINUS.getDisplayString());
    btnAge20Minus.setMinimumSize(minDimension);
    btnAge20Minus.addActionListener(this);
    btgAgeRange.add(btnAge20Minus);
    getContentPane().add(btnAge20Minus, "grow");

    POSToggleButton btnAge2030 = new POSToggleButton(AgeRange.AGE_20_30.getDisplayString());
    btnAge2030.setMinimumSize(minDimension);
    btnAge2030.addActionListener(this);
    btgAgeRange.add(btnAge2030);
    getContentPane().add(btnAge2030, "grow");

    POSToggleButton btnAge3040 = new POSToggleButton(AgeRange.AGE_30_40.getDisplayString());
    btnAge3040.setMinimumSize(minDimension);
    btnAge3040.addActionListener(this);
    btgAgeRange.add(btnAge3040);
    getContentPane().add(btnAge3040, "grow");

    POSToggleButton btnAge4050 = new POSToggleButton(AgeRange.AGE_40_50.getDisplayString());
    btnAge4050.setMinimumSize(minDimension);
    btnAge4050.addActionListener(this);
    btgAgeRange.add(btnAge4050);
    getContentPane().add(btnAge4050, "grow");

    POSToggleButton btnAge50Plus = new POSToggleButton(AgeRange.AGE_50_PLUS.getDisplayString());
    btnAge50Plus.setMinimumSize(minDimension);
    btnAge50Plus.addActionListener(this);
    btgAgeRange.add(btnAge50Plus);
    getContentPane().add(btnAge50Plus, "grow, wrap");

    JButton btnOK = new JButton(POSConstants.OK);
    btnOK.setMinimumSize(minDimension);
    btnOK.addActionListener(this);
    getContentPane().add(btnOK, "skip 1, grow");

    JButton btnCancel = new JButton(POSConstants.CANCEL);
    btnCancel.setMinimumSize(minDimension);
    btnCancel.addActionListener(this);
    getContentPane().add(btnCancel, "grow");
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String actionCommand = e.getActionCommand();

    if (POSConstants.CUSTOMER_QUICK_DLG_GENDER_MALE.equalsIgnoreCase(actionCommand)) {
      gender = 1;
    } else if (POSConstants.CUSTOMER_QUICK_DLG_GENDER_FEMALE.equalsIgnoreCase(actionCommand)) {
      gender = 0;
    } else if (AgeRange.AGE_20_MINUS.getDisplayString().equalsIgnoreCase(actionCommand)) {
      ageRange = 1;
    } else if (AgeRange.AGE_20_30.getDisplayString().equalsIgnoreCase(actionCommand)) {
      ageRange = 2;
    } else if (AgeRange.AGE_30_40.getDisplayString().equalsIgnoreCase(actionCommand)) {
      ageRange = 3;
    } else if (AgeRange.AGE_40_50.getDisplayString().equalsIgnoreCase(actionCommand)) {
      ageRange = 4;
    } else if (AgeRange.AGE_50_PLUS.getDisplayString().equalsIgnoreCase(actionCommand)) {
      ageRange = 5;
    } else if (POSConstants.CANCEL.equalsIgnoreCase(actionCommand)) {
      doCancel();
    } else if (POSConstants.OK.equalsIgnoreCase(actionCommand)) {
      doOk();
    } else {
      POSMessageDialog.showError(this, POSConstants.ERROR_UNKNOWN_COMMAND);
    }
  }

  private void doOk() {
    // TODO: create new customer
    // check validity again
    String phone = tfPhone.getText().trim();
    if (!ValidateUtil.isMobileNO(phone)) {
      POSMessageDialog.showError(this, POSConstants.ERROR_CUSTOMER_PHONE_NOT_VALID);
      return;
    }

    // check whether customer exists again in case the user has modified the phone
    Customer customer = CustomerDAO.getInstance().findByPhone(phone);
    if (customer != null) {
      POSMessageDialog.showError(this, POSConstants.ERROR_CUSTOMER_ALREADY_EXISTED);
      return;
    }

    Customer newCustomer = new Customer();
    newCustomer.setPhone(phone);
    newCustomer.setGender(gender);
    newCustomer.setAgeRange(ageRange);
    CustomerDAO.getInstance().save(newCustomer);

    setCanceled(false);
    dispose();
  }

  private void doCancel() {
    setCanceled(true);
    dispose();
  }
}
