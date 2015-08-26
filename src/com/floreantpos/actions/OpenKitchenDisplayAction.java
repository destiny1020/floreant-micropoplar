package com.floreantpos.actions;

import com.floreantpos.demo.KitchenDisplay;

public class OpenKitchenDisplayAction extends PosAction {

	public OpenKitchenDisplayAction() {
		super("厨房订单");
	}
	
	@Override
	public void execute() {
		KitchenDisplay.instance.setVisible(true);
	}

}
