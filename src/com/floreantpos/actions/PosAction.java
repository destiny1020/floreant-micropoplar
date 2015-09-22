package com.floreantpos.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.floreantpos.POSConstants;
import com.floreantpos.main.Application;
import com.floreantpos.model.User;
import com.floreantpos.model.UserPermission;
import com.floreantpos.ui.dialog.POSMessageDialog;

public abstract class PosAction extends AbstractAction {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  protected UserPermission requiredPermission;
  // protected boolean allowAdministrator = true;

  public PosAction(String name) {
    super(name);
  }

  public PosAction(String name, UserPermission requiredPermission) {
    super(name);

    this.requiredPermission = requiredPermission;
  }

  public UserPermission getRequiredPermission() {
    return requiredPermission;
  }

  public void setRequiredPermission(UserPermission requiredPermission) {
    this.requiredPermission = requiredPermission;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    User user = Application.getCurrentUser();

    if (requiredPermission == null) {
      execute();
      return;
    }

    if (!user.hasPermission(requiredPermission)) {
      POSMessageDialog.showError(POSConstants.NOT_ENOUGH_PERMISSION);
      return;
    }

    execute();
  }

  public abstract void execute();

}
