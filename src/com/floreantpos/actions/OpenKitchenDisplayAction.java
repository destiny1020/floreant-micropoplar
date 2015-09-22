package com.floreantpos.actions;

import com.floreantpos.POSConstants;
import com.floreantpos.demo.KitchenDisplay;

public class OpenKitchenDisplayAction extends PosAction {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public OpenKitchenDisplayAction() {
    super(POSConstants.KITCHEN_ORDER);
  }

  @Override
  public void execute() {
    KitchenDisplay.instance.setVisible(true);
  }

}
