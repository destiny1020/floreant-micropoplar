package com.micropoplar.pos.ui.util;

import java.awt.Font;

import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import com.floreantpos.POSConstants;

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

}
