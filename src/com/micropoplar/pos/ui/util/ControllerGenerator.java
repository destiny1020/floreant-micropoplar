package com.micropoplar.pos.ui.util;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
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

  /**
   * Return the combo box for the age range.
   * 
   * @return
   */
  public static JComboBox<ComboOption> getAgeRangeComboBox(boolean generateAllOption) {
    JComboBox<ComboOption> cbAgeRange = new JComboBox<>();

    // All Option
    if (generateAllOption) {
      cbAgeRange.addItem(new ComboOption(0, POSConstants.AGE_RANGE_OPTION_ALL));
    }

    AgeRange[] ageRanges = AgeRange.values();

    for (AgeRange ar : ageRanges) {
      cbAgeRange.addItem(new ComboOption(ar.getType(), ar.getDisplayString()));
    }

    return cbAgeRange;
  }

  private static JComboBox<ComboOption> cbGender;

  /**
   * Return the combo box for the age range.
   * 
   * @return
   */
  public static JComboBox<ComboOption> getGenderComboBox() {
    if (cbGender != null) {
      return cbGender;
    }

    cbGender = new JComboBox<>();

    cbGender.addItem(new ComboOption(-1, POSConstants.GENDER_ALL));
    cbGender.addItem(new ComboOption(0, POSConstants.GENDER_FEMALE));
    cbGender.addItem(new ComboOption(1, POSConstants.GENDER_MALE));

    return cbGender;
  }

  private static final Color LABEL_COLOR = new Color(0, 70, 213);

  public static void addSeparator(JPanel panel, String text) {
    addSeparator(panel, text, "gapbottom 1, span, split 2, aligny center", "gapleft rel, growx");
  }

  public static void addSeparator(JPanel panel, String text, String constraintLabel,
      String constraintSeparator) {
    JLabel l = createLabel(text);
    l.setForeground(LABEL_COLOR);

    panel.add(l, constraintLabel);
    panel.add(new JSeparator(), constraintSeparator);
  }

  // support methods for addSeparator
  private static JLabel createLabel(String text) {
    return createLabel(text, SwingConstants.LEADING);
  }

  private static JLabel createLabel(String text, int align) {
    return new JLabel(text, align);
  }

}
