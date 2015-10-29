package com.floreantpos.ui.views.order.actions;

import com.floreantpos.model.MenuGroup;
import com.micropoplar.pos.ui.IOrderViewItem;

public interface ItemSelectionListener {
  void itemSelected(IOrderViewItem menuItem);

  void itemSelectionFinished(MenuGroup parent);
}
