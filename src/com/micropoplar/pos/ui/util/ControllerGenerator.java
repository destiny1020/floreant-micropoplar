package com.micropoplar.pos.ui.util;

import java.awt.Font;

import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.floreantpos.POSConstants;
import com.floreantpos.bo.ui.ComboOption;
import com.floreantpos.model.PaymentType;
import com.micropoplar.pos.model.AgeRange;

/**
 * To generate common controller.
 * 
 * @author destiny1020
 *
 */
public class ControllerGenerator {

  /**
   * After retrieving the controller, you should define the listener for it. 
   * Say, the addChangeListener:
   * 
   * spinCurrentDiscount.addChangeListener(new ChangeListener() {
   * public void stateChanged(ChangeEvent e) {
   *    JSpinner target = (JSpinner) e.getSource();
   *    Double discount = (Double) target.getValue();
   *    ......
   * 
   * @param defaultDiscount
   * @return
   */
  public static JSpinner getDiscountSpinner(double defaultDiscount) {
    JSpinner spinCurrentDiscount =
        new JSpinner(new SpinnerNumberModel(defaultDiscount, 0.0, 10.0, 0.1));

    // 设置输入格式以及校验
    spinCurrentDiscount.setInputVerifier(new DiscountVerifier());
    JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinCurrentDiscount, "0.0");
    spinCurrentDiscount.setEditor(editor);
    JFormattedTextField textField =
        ((JSpinner.NumberEditor) spinCurrentDiscount.getEditor()).getTextField();
    textField.setEditable(true);
    DefaultFormatterFactory factory = (DefaultFormatterFactory) textField.getFormatterFactory();
    NumberFormatter formatter = (NumberFormatter) factory.getDefaultFormatter();
    formatter.setAllowsInvalid(false);

    spinCurrentDiscount.setFont(new Font(POSConstants.DEFAULT_FONT_NAME, 1, 20));
    spinCurrentDiscount.setEnabled(true);

    return spinCurrentDiscount;
  }

  /**
   * After retrieving the controller, you should define the listener for it. 
   * 
   * @return
   */
  public static JSpinner getCountSpinner() {
    JSpinner spinCurrentDiscount = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));

    // 设置输入格式以及校验
    spinCurrentDiscount.setInputVerifier(new CountVerifier());
    JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinCurrentDiscount, "0");
    spinCurrentDiscount.setEditor(editor);
    JFormattedTextField textField =
        ((JSpinner.NumberEditor) spinCurrentDiscount.getEditor()).getTextField();
    textField.setEditable(true);
    DefaultFormatterFactory factory = (DefaultFormatterFactory) textField.getFormatterFactory();
    NumberFormatter formatter = (NumberFormatter) factory.getDefaultFormatter();
    formatter.setAllowsInvalid(false);

    spinCurrentDiscount.setFont(new Font(POSConstants.DEFAULT_FONT_NAME, 1, 20));
    spinCurrentDiscount.setEnabled(true);

    return spinCurrentDiscount;
  }

  /**
   * Return the count range.
   * 
   * @return
   */
  public static JComboBox<Integer> getCountComboBox() {
    JComboBox<Integer> comboBox = new JComboBox<>();

    for (int i = 1; i <= 20; i++) {
      comboBox.addItem(i);
    }

    return comboBox;
  }

  private static JComboBox<ComboOption> cbPaymentType;

  /**
   * Return the combo box for the payment type.
   * 
   * @return
   */
  public static JComboBox<ComboOption> getPaymentTypeComboBox() {
    if (cbPaymentType != null) {
      return cbPaymentType;
    }

    cbPaymentType = new JComboBox<>();

    // All Option
    cbPaymentType.addItem(new ComboOption(0, POSConstants.TICKET_EXPLORER_CB_OPTION_ALL));

    PaymentType[] paymentTypes = PaymentType.values();

    for (PaymentType pt : paymentTypes) {
      cbPaymentType.addItem(new ComboOption(pt.getType(), pt.getDisplayString()));
    }

    return cbPaymentType;
  }

  private static JComboBox<ComboOption> cbAgeRnge;

  /**
   * Return the combo box for the age range.
   * 
   * @return
   */
  public static JComboBox<ComboOption> getAgeRangeComboBox() {
    if (cbAgeRnge != null) {
      return cbAgeRnge;
    }

    cbAgeRnge = new JComboBox<>();

    // All Option
    cbAgeRnge.addItem(new ComboOption(0, POSConstants.AGE_RANGE_OPTION_ALL));

    AgeRange[] ageRanges = AgeRange.values();

    for (AgeRange ar : ageRanges) {
      cbAgeRnge.addItem(new ComboOption(ar.getType(), ar.getDisplayString()));
    }

    return cbAgeRnge;
  }

}
