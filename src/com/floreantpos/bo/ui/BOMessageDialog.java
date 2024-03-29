package com.floreantpos.bo.ui;

import java.awt.Component;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import com.floreantpos.POSConstants;
import com.floreantpos.main.Application;

public class BOMessageDialog {
  private static Logger logger = Logger.getLogger(Application.class);

  public static void showError(Component parent, String message) {
    JOptionPane.showMessageDialog(parent, message, com.floreantpos.POSConstants.MDS_POS,
        JOptionPane.ERROR_MESSAGE, null);
  }

  public static void showError(Component parent, String message, Throwable x) {
    x.printStackTrace();
    logger.error(message, x);
    JOptionPane.showMessageDialog(parent, message, com.floreantpos.POSConstants.MDS_POS,
        JOptionPane.ERROR_MESSAGE, null);
  }

  public static void showError(String errorMessage) {
    JOptionPane.showMessageDialog(BackOfficeWindow.getInstance(), errorMessage,
        POSConstants.COMMON_ERROR, JOptionPane.ERROR_MESSAGE);
  }

  public static void showError(String errorMessage, Throwable t) {
    logger.error(errorMessage, t);
    JOptionPane.showMessageDialog(BackOfficeWindow.getInstance(), errorMessage,
        POSConstants.COMMON_ERROR, JOptionPane.ERROR_MESSAGE);
  }

  public static void showError(Throwable t) {
    logger.error("Error", t);
    JOptionPane.showMessageDialog(BackOfficeWindow.getInstance(), "发生了一个异常，请重启应用",
        POSConstants.COMMON_ERROR, JOptionPane.ERROR_MESSAGE);
  }
}
