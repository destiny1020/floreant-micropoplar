package com.floreantpos.bo.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JTabbedPane;

import com.floreantpos.bo.ui.BOMessageDialog;
import com.floreantpos.bo.ui.BackOfficeWindow;
import com.floreantpos.bo.ui.explorer.CouponExplorer;

public class CouponExplorerAction extends AbstractAction {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public CouponExplorerAction() {
    super(com.floreantpos.POSConstants.COUPONS);
  }

  public CouponExplorerAction(String name) {
    super(name);
  }

  public CouponExplorerAction(String name, Icon icon) {
    super(name, icon);
  }

  public void actionPerformed(ActionEvent e) {
    try {
      BackOfficeWindow backOfficeWindow = BackOfficeWindow.getInstance();

      CouponExplorer explorer = null;
      JTabbedPane tabbedPane = backOfficeWindow.getTabbedPane();
      int index = tabbedPane.indexOfTab(com.floreantpos.POSConstants.COUPON_EXPLORER);
      if (index == -1) {
        explorer = new CouponExplorer();
        explorer.initData();
        tabbedPane.addTab(com.floreantpos.POSConstants.COUPON_EXPLORER, explorer);
      } else {
        explorer = (CouponExplorer) tabbedPane.getComponentAt(index);
      }
      tabbedPane.setSelectedComponent(explorer);
    } catch (Exception x) {
      BOMessageDialog.showError(com.floreantpos.POSConstants.ERROR_MESSAGE, x);
    }
  }

}
