package com.floreantpos.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.floreantpos.POSConstants;
import com.floreantpos.ui.dialog.AboutDialog;

public class AboutAction extends AbstractAction {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public AboutAction() {
    super(POSConstants.ABOUT_THIS_SYSTEM);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    AboutDialog dialog = new AboutDialog();
    dialog.pack();
    dialog.open();
  }

}
