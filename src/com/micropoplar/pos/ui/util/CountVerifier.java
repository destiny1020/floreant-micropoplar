package com.micropoplar.pos.ui.util;

import java.math.BigDecimal;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JSpinner;

public class CountVerifier extends InputVerifier {

  @Override
  public boolean verify(JComponent input) {
    JSpinner spinner = (JSpinner) input;

    try {
      Double countValue = Double.valueOf((String) spinner.getValue());
      int countValueInt = new BigDecimal(countValue).intValue();

      if (countValueInt > 100 || countValueInt < 1) {
        return false;
      }

      return true;
    } catch (NumberFormatException e) {
      e.printStackTrace();
      return false;
    }
  }

}
