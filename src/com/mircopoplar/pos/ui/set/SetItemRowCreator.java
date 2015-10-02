package com.mircopoplar.pos.ui.set;

import java.util.Map;

import com.floreantpos.model.ITicketItem;
import com.micropoplar.pos.model.MenuItemSet;

import antlr.collections.List;

public class SetItemRowCreator {

  public static void calculateSetRows(MenuItemSet itemSet, Map<String, ITicketItem> tableRows) {
    calculateSetRows(itemSet, tableRows, true, true);
  }

  private static void calculateSetRows(MenuItemSet itemSet, Map<String, ITicketItem> tableRows,
      boolean includeModifiers, boolean includeCookingInstructions) {
    tableRows.clear();

    int rowNum = 0;

    if (itemSet == null || itemSet.getItems() == null) {
      return;
    }

    itemSet.getItems();
  }


}
