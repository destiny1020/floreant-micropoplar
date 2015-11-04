package com.micropoplar.pos.util;

import java.awt.Font;

import com.floreantpos.POSConstants;

public class FontUtil {

  private static final int FONT_SIZE_LARGER = 18;
  private static final int FONT_SIZE_LARGE = 16;
  private static final int FONT_SIZE_BIG = 14;
  private static final int FONT_SIZE_NORMAL = 12;

  public static final Font FONT_LARGER =
      new Font(POSConstants.DEFAULT_FONT_NAME, 1, FONT_SIZE_LARGER);
  public static final Font FONT_LARGE =
      new Font(POSConstants.DEFAULT_FONT_NAME, 1, FONT_SIZE_LARGE);
  public static final Font FONT_BIG = new Font(POSConstants.DEFAULT_FONT_NAME, 1, FONT_SIZE_BIG);
  public static final Font FONT_NORMAL =
      new Font(POSConstants.DEFAULT_FONT_NAME, 1, FONT_SIZE_NORMAL);

}
