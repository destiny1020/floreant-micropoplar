package com.floreantpos.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;

public class NumberUtil {
  private final static NumberFormat numberFormat = NumberFormat.getNumberInstance();

  static {
    numberFormat.setMinimumFractionDigits(2);
    numberFormat.setMaximumFractionDigits(2);
  }

  public static double roundToTwoDigit(double value) {
    BigDecimal bd = new BigDecimal(value);
    bd = bd.setScale(2, RoundingMode.HALF_UP);
    return bd.doubleValue();
  }

  public static String formatNumber(Double number) {
    if (number == null) {
      return numberFormat.format(0);
    }

    String value = numberFormat.format(number);

    if (value.startsWith("-")) { //$NON-NLS-1$
      return numberFormat.format(0);
    }

    return value;
  }
}
