package com.micropoplar.pos.ui;

import com.floreantpos.model.TicketType;
import com.floreantpos.swing.POSToggleButton;
import com.floreantpos.ui.views.order.TicketView;

public class TicketTypeButton extends POSToggleButton {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private TicketType ticketType;

  public TicketTypeButton(TicketType type, TicketView view) {
    this.ticketType = type;
    setText(type.getValue());

    addActionListener(view);
  }

  public TicketType getTicketType() {
    return ticketType;
  }

}
