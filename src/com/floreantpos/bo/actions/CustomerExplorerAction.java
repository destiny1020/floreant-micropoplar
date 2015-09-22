package com.floreantpos.bo.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JTabbedPane;

import com.floreantpos.bo.ui.BOMessageDialog;
import com.floreantpos.bo.ui.BackOfficeWindow;
import com.floreantpos.customer.CustomerExplorer;

public class CustomerExplorerAction extends AbstractAction {

  public CustomerExplorerAction() {
    super(com.floreantpos.POSConstants.CUSTOMER_EXPLORER);
  }

  public CustomerExplorerAction(String name) {
    super(name);
  }

  public CustomerExplorerAction(String name, Icon icon) {
    super(name, icon);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    try {
      BackOfficeWindow backOfficeWindow = BackOfficeWindow.getInstance();

      CustomerExplorer explorer = null;
      JTabbedPane tabbedPane = backOfficeWindow.getTabbedPane();
      int index = tabbedPane.indexOfTab(com.floreantpos.POSConstants.CUSTOMER_EXPLORER);
      if (index == -1) {
        explorer = new CustomerExplorer();
        tabbedPane.addTab(com.floreantpos.POSConstants.CUSTOMER_EXPLORER, explorer);
      } else {
        explorer = (CustomerExplorer) tabbedPane.getComponentAt(index);
      }
      tabbedPane.setSelectedComponent(explorer);
    } catch (Exception x) {
      BOMessageDialog.showError(com.floreantpos.POSConstants.ERROR_MESSAGE, x);
    }
  }

}
