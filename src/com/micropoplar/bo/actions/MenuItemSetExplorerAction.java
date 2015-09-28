package com.micropoplar.bo.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JTabbedPane;

import com.floreantpos.POSConstants;
import com.floreantpos.bo.ui.BackOfficeWindow;
import com.floreantpos.bo.ui.explorer.MenuItemExplorer;

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
    MenuItemExplorer item;
    tabbedPane = backOfficeWindow.getTabbedPane();
    int index = tabbedPane.indexOfTab(com.floreantpos.POSConstants.ITEM_EXPLORER);
    if (index == -1) {
      item = new MenuItemExplorer();
      tabbedPane.addTab(com.floreantpos.POSConstants.ITEM_EXPLORER, item);
    } else {
      item = (MenuItemExplorer) tabbedPane.getComponentAt(index);
    }
    tabbedPane.setSelectedComponent(item);
  }

}
