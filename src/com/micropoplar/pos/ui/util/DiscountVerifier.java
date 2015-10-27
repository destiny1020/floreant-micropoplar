package com.micropoplar.pos.ui.util;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JSpinner;

public class DiscountVerifier extends InputVerifier {

  @Override
  public boolean verify(JComponent input) {
    JSpinner spinner = (JSpinner) input;

    try {
      Double discountValue = Double.valueOf((String) spinner.getValue());

      if (discountValue > 10.0 || discountValue < 0.0) {
        return false;
      }

      return true;
    } catch (NumberFormatException e) {
      e.printStackTrace();
      return false;
    }
  }

}
