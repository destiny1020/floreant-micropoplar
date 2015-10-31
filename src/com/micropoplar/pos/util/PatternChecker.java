package com.micropoplar.pos.util;

public class PatternChecker {

  /**
   * Check whether designated text is within certain range.
   * 
   * @param text
   * @param lowerBound
   * @param upperBound
   * @return
   */
  public static boolean isNumber(String text, int lowerBound, int upperBound) {
    try {
      Integer number = Integer.parseInt(text);

      if (number < lowerBound || number > upperBound) {
        return false;
      }

      return true;
    } catch (NumberFormatException nfe) {
      nfe.printStackTrace();
      return false;
    }
  }

}
