package com.micropoplar.pos.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUtil {

  /**
   * To validate a phone number is valid Chinese mainland phone.
   * 
   * @param mobileNumber
   * @return
   */
  public static boolean isMobileNO(String mobileNumber) {
    Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
    Matcher m = p.matcher(mobileNumber);

    return m.matches();

  }

}
