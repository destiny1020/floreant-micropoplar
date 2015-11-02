package com.micropoplar.pos.bo.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JTabbedPane;

import com.floreantpos.POSConstants;
import com.floreantpos.bo.ui.BOMessageDialog;
import com.floreantpos.bo.ui.BackOfficeWindow;
import com.micropoplar.pos.bo.ui.explorer.CompaignExplorer;

public class CompaignExplorerAction extends AbstractAction {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public CompaignExplorerAction() {
    super(POSConstants.COMPAIGN_EXPLORER_MENU);
  }

  public CompaignExplorerAction(String name) {
    super(name);
  }

  public CompaignExplorerAction(String name, Icon icon) {
    super(name, icon);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    try {
      BackOfficeWindow backOfficeWindow = BackOfficeWindow.getInstance();

      CompaignExplorer explorer = null;
      JTabbedPane tabbedPane = backOfficeWindow.getTabbedPane();
      int index = tabbedPane.indexOfTab(POSConstants.COMPAIGN_EXPLORER);
      if (index == -1) {
        explorer = new CompaignExplorer();
        tabbedPane.addTab(POSConstants.COMPAIGN_EXPLORER, explorer);
      } else {
        explorer = (CompaignExplorer) tabbedPane.getComponentAt(index);
      }
      tabbedPane.setSelectedComponent(explorer);
    } catch (Exception x) {
      BOMessageDialog.showError(POSConstants.ERROR_MESSAGE, x);
    }
  }

}
