package com.floreantpos.actions;

import com.floreantpos.POSConstants;
import com.floreantpos.main.Application;
import com.floreantpos.model.UserPermission;

public class ShutDownAction extends PosAction {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public ShutDownAction() {
    super(POSConstants.CAPITAL_SHUTDOWN, UserPermission.SHUT_DOWN);
  }

  @Override
  public void execute() {
    Application.getInstance().shutdownPOS();
  }

}
