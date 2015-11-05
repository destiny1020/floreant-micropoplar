package com.micropoplar.pos.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUtil {

  private static final Pattern VALID_MOBILE_NUMBER_REGEX =
      Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");

  private static final Pattern VALID_EMAIL_REGEX =
      Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

  /**
   * To validate a phone number is valid Chinese mainland phone.
   * 
   * @param mobileNumber
   * @return
   */
  public static boolean isMobileNO(String mobileNumber) {
    Matcher m = VALID_MOBILE_NUMBER_REGEX.matcher(mobileNumber);
    return m.matches();
  }

  /**
   * To validate a email address.
   * 
   * @param email
   * @return
   */
  public static boolean isEmail(String email) {
    Matcher m = VALID_EMAIL_REGEX.matcher(email);
    return m.matches();
  }

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

  public static final int DINE_IN_NUMBER_LOWER_BOUND = 1;
  public static final int DINE_IN_NUMBER_UPPER_BOUND = 999;

  /**
   * Check whether desigated dine in number is valid.
   * 
   * @param text
   * @return
   */
  public static boolean isDineInNumber(String text) {
    return isNumber(text, DINE_IN_NUMBER_LOWER_BOUND, DINE_IN_NUMBER_UPPER_BOUND);
  }

}
