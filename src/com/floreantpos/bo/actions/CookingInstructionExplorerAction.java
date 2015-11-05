package com.floreantpos.bo.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JTabbedPane;

import com.floreantpos.bo.ui.BackOfficeWindow;
import com.floreantpos.bo.ui.explorer.CookingInstructionExplorer;

public class CookingInstructionExplorerAction extends AbstractAction {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public CookingInstructionExplorerAction() {
    super(com.floreantpos.POSConstants.COOKING_INSTRUCTIONS);
  }

  public CookingInstructionExplorerAction(String name) {
    super(name);
  }

  public CookingInstructionExplorerAction(String name, Icon icon) {
    super(name, icon);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    BackOfficeWindow backOfficeWindow = BackOfficeWindow.getInstance();

    CookingInstructionExplorer explorer = null;
    JTabbedPane tabbedPane = backOfficeWindow.getTabbedPane();
    int index = tabbedPane.indexOfTab(com.floreantpos.POSConstants.COOKING_INSTRUCTIONS_EXPLORER);
    if (index == -1) {
      explorer = new CookingInstructionExplorer();
      tabbedPane.addTab(com.floreantpos.POSConstants.COOKING_INSTRUCTIONS_EXPLORER, explorer);
    } else {
      explorer = (CookingInstructionExplorer) tabbedPane.getComponentAt(index);
    }
    tabbedPane.setSelectedComponent(explorer);
  }

}
