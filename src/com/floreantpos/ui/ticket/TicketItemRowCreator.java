package com.floreantpos.ui.ticket;

import java.util.List;
import java.util.Map;

import com.floreantpos.model.ITicketItem;
import com.floreantpos.model.Ticket;
import com.floreantpos.model.TicketItem;
import com.floreantpos.model.TicketItemCookingInstruction;

public class TicketItemRowCreator {

  public static void calculateTicketRows(Ticket ticket, Map<String, ITicketItem> tableRows) {
    calculateTicketRows(ticket, tableRows, true, true);
  }

  private static void calculateTicketRows(Ticket ticket, Map<String, ITicketItem> tableRows,
      boolean includeModifiers, boolean includeCookingInstructions) {
    tableRows.clear();

    int rowNum = 0;

    if (ticket == null || ticket.getTicketItems() == null)
      return;

    List<TicketItem> ticketItems = ticket.getTicketItems();
    for (TicketItem ticketItem : ticketItems) {

      ticketItem.setTableRowNum(rowNum);
      tableRows.put(String.valueOf(rowNum), ticketItem);
      rowNum++;

      if (includeCookingInstructions) {
        rowNum = includeCookintInstructions(ticketItem, tableRows, rowNum);
      }
    }
  }

  private static int includeCookintInstructions(TicketItem ticketItem,
      Map<String, ITicketItem> tableRows, int rowNum) {
    List<TicketItemCookingInstruction> cookingInstructions = ticketItem.getCookingInstructions();
    if (cookingInstructions != null) {
      for (TicketItemCookingInstruction ticketItemCookingInstruction : cookingInstructions) {
        ticketItemCookingInstruction.setTableRowNum(rowNum);
        tableRows.put(String.valueOf(rowNum), ticketItemCookingInstruction);
        rowNum++;
      }
    }
    return rowNum;
  }

}
