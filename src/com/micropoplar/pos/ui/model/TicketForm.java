package com.micropoplar.pos.ui.model;

import com.floreantpos.model.Ticket;
import com.floreantpos.model.util.IllegalModelStateException;
import com.floreantpos.ui.BeanEditor;

public class TicketForm extends BeanEditor<Ticket> {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private Ticket ticket;

  public TicketForm(Ticket ticket) {
    this.ticket = ticket;
  }

  @Override
  public boolean save() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  protected void updateView() {
    // TODO Auto-generated method stub

  }

  @Override
  protected boolean updateModel() throws IllegalModelStateException {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public String getDisplayText() {
    // TODO Auto-generated method stub
    return null;
  }

  public Ticket getTicket() {
    return ticket;
  }

}
