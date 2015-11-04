package com.micropoplar.pos.ui.dialog;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

import com.floreantpos.POSConstants;
import com.floreantpos.main.Application;
import com.floreantpos.swing.FixedLengthTextField;
import com.floreantpos.swing.POSToggleButton;
import com.floreantpos.ui.dialog.POSDialog;
import com.floreantpos.ui.dialog.POSMessageDialog;
import com.micropoplar.pos.model.AgeRange;

import net.miginfocom.swing.MigLayout;

public class CustomerQuickInputDialog extends POSDialog implements ActionListener {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private FixedLengthTextField tfPhone;

  private JRadioButton rdbMale;
  private JRadioButton rdbFemale;

  private ButtonGroup btgGender;
  private ButtonGroup btgAgeRange;

  private String phone;

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

    getContentPane().setLayout(new MigLayout("", "[60px][100px,grow][][100px]", "[][][][][][][]"));

    JLabel lblPhone = new JLabel(POSConstants.CUSTOMER_QUICK_DLG_PHONE);
    getContentPane().add(lblPhone, "cell 0 1,alignx trailing");

    tfPhone = new FixedLengthTextField(11);
    tfPhone.setText(phone);
    tfPhone.setFocusable(false);
    getContentPane().add(tfPhone, "cell 1 1 2 1,growx");

    JLabel lblGender = new JLabel(POSConstants.CUSTOMER_QUICK_DLG_GENDER);
    getContentPane().add(lblGender, "cell 0 2");

    btgGender = new ButtonGroup();

    rdbMale = new JRadioButton(POSConstants.CUSTOMER_QUICK_DLG_GENDER_MALE);
    btgGender.add(rdbMale);
    getContentPane().add(rdbMale, "flowx,cell 1 2");

    rdbFemale = new JRadioButton(POSConstants.CUSTOMER_QUICK_DLG_GENDER_FEMALE);
    btgGender.add(rdbFemale);
    getContentPane().add(rdbFemale, "cell 1 2");

    JLabel lblAgeRange = new JLabel(POSConstants.CUSTOMER_QUICK_DLG_AGE_RANGE);
    getContentPane().add(lblAgeRange, "cell 0 3");

    btgAgeRange = new ButtonGroup();

    POSToggleButton btnAge20Minus = new POSToggleButton(AgeRange.AGE_20_MINUS.getDisplayString());
    btgAgeRange.add(btnAge20Minus);
    getContentPane().add(btnAge20Minus, "flowx,cell 1 3");

    POSToggleButton btnAge2030 = new POSToggleButton(AgeRange.AGE_20_30.getDisplayString());
    btgAgeRange.add(btnAge2030);
    getContentPane().add(btnAge2030, "cell 1 3");

    POSToggleButton btnAge3040 = new POSToggleButton(AgeRange.AGE_30_40.getDisplayString());
    btgAgeRange.add(btnAge3040);
    getContentPane().add(btnAge3040, "flowx,cell 1 4");

    POSToggleButton btnAge4050 = new POSToggleButton(AgeRange.AGE_40_50.getDisplayString());
    btgAgeRange.add(btnAge4050);
    getContentPane().add(btnAge4050, "cell 1 4");

    POSToggleButton btnAge50Plus = new POSToggleButton(AgeRange.AGE_50_PLUS.getDisplayString());
    btgAgeRange.add(btnAge50Plus);
    getContentPane().add(btnAge50Plus, "cell 1 5");

    JButton btnOK = new JButton(POSConstants.OK);
    btnOK.addActionListener(this);
    getContentPane().add(btnOK, "flowx,cell 1 6");

    JButton btnCancel = new JButton(POSConstants.CANCEL);
    btnCancel.addActionListener(this);
    getContentPane().add(btnCancel, "cell 1 6");
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String actionCommand = e.getActionCommand();

    if (POSConstants.CANCEL.equalsIgnoreCase(actionCommand)) {
      doCancel();
    } else if (POSConstants.OK.equalsIgnoreCase(actionCommand)) {
      doOk();
    } else {
      POSMessageDialog.showError(this, POSConstants.ERROR_UNKNOWN_COMMAND);
    }
  }

  private void doOk() {
    // create new customer
    setCanceled(false);
    dispose();
  }

  private void doCancel() {
    setCanceled(true);
    dispose();
  }
}
