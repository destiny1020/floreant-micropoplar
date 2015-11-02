package com.micropoplar.pos.bo.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JTabbedPane;

import com.floreantpos.POSConstants;
import com.floreantpos.bo.ui.BOMessageDialog;
import com.floreantpos.bo.ui.BackOfficeWindow;
import com.floreantpos.bo.ui.explorer.TicketExplorer;

public class TicketExplorerAction extends AbstractAction {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public TicketExplorerAction() {
    super(POSConstants.TICKET_MENU);
  }

  public TicketExplorerAction(String name) {
    super(name);
  }

  public TicketExplorerAction(String name, Icon icon) {
    super(name, icon);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    try {
      BackOfficeWindow backOfficeWindow = BackOfficeWindow.getInstance();

      TicketExplorer explorer = null;
      JTabbedPane tabbedPane = backOfficeWindow.getTabbedPane();
      int index = tabbedPane.indexOfTab(com.floreantpos.POSConstants.TICKET_EXPLORER);
      if (index == -1) {
        explorer = new TicketExplorer();
        tabbedPane.addTab(com.floreantpos.POSConstants.TICKET_EXPLORER, explorer);
      } else {
        explorer = (TicketExplorer) tabbedPane.getComponentAt(index);
      }
      tabbedPane.setSelectedComponent(explorer);
    } catch (Exception x) {
      BOMessageDialog.showError(com.floreantpos.POSConstants.ERROR_MESSAGE, x);
    }
  }

}
