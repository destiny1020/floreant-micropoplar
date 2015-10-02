package com.micropoplar.pos.bo.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JTabbedPane;

import com.floreantpos.POSConstants;
import com.floreantpos.bo.ui.BackOfficeWindow;
import com.floreantpos.bo.ui.explorer.MenuItemExplorer;
import com.micropoplar.pos.bo.ui.explorer.MenuItemSetExplorer;

public class MenuItemSetExplorerAction extends AbstractAction {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public MenuItemSetExplorerAction() {
    super(POSConstants.BO_MENU_ITEM_SET);
  }

  public MenuItemSetExplorerAction(String name) {
    super(name);
  }

  public MenuItemSetExplorerAction(String name, Icon icon) {
    super(name, icon);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    BackOfficeWindow backOfficeWindow = BackOfficeWindow.getInstance();
    JTabbedPane tabbedPane;
    MenuItemSetExplorer itemSetExplorer;
    tabbedPane = backOfficeWindow.getTabbedPane();
    int index = tabbedPane.indexOfTab(com.floreantpos.POSConstants.ITEM_SET_EXPLORER);
    if (index == -1) {
      itemSetExplorer = new MenuItemSetExplorer();
      tabbedPane.addTab(com.floreantpos.POSConstants.ITEM_SET_EXPLORER, itemSetExplorer);
    } else {
      itemSetExplorer = (MenuItemSetExplorer) tabbedPane.getComponentAt(index);
    }
    tabbedPane.setSelectedComponent(itemSetExplorer);
  }

}
