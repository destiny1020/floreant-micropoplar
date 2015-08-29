package com.floreantpos.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JDialog;

import com.floreantpos.model.Ticket;
import com.floreantpos.model.dao.TicketDAO;
import com.floreantpos.ui.dialog.POSMessageDialog;
import com.floreantpos.ui.views.payment.SettleTicketDialog;

public class SettleTicketAction extends AbstractAction {

	private String ticketUniqId;

	public SettleTicketAction(String ticketUniqId) {
		this.ticketUniqId = ticketUniqId;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		execute();
	}

	public boolean execute() {
		Ticket ticket = TicketDAO.getInstance().loadFullTicket(ticketUniqId);

		if (ticket.isPaid()) {
			POSMessageDialog.showError("订单已经支付完成");
			return false;
		}

		SettleTicketDialog posDialog = new SettleTicketDialog();
		posDialog.setTicket(ticket);
		posDialog.setSize(800, 700);
		posDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		posDialog.open();
		
		return !posDialog.isCanceled();
	}

}
