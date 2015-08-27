package com.floreantpos.actions;

import com.floreantpos.ITicketList;
import com.floreantpos.main.Application;
import com.floreantpos.model.Ticket;
import com.floreantpos.model.UserPermission;
import com.floreantpos.model.dao.TicketDAO;
import com.floreantpos.services.PosTransactionService;
import com.floreantpos.services.TicketService;
import com.floreantpos.ui.dialog.NumberSelectionDialog2;
import com.floreantpos.ui.dialog.POSMessageDialog;

public class RefundAction extends PosAction {
	private ITicketList ticketList;

	public RefundAction(ITicketList ticketList) {
		super("REFUND", UserPermission.REFUND);

		this.ticketList = ticketList;
	}

	@Override
	public void execute() {
		try {
			Ticket ticket = ticketList.getSelectedTicket();

			if (ticket == null) {
				int ticketId = NumberSelectionDialog2.takeIntInput("请输入或者扫描订单ID");
				if(ticketId == -1) {
					// user clicked without input
					return;
				}
				ticket = TicketService.getTicket(ticketId);
			}
			
			if(!ticket.isPaid()) {
				POSMessageDialog.showError("订单还未被支付.");
				return;
			}
			
			if(ticket.isRefunded()) {
				POSMessageDialog.showError("订单已经退款.");
				return;
			}
			
			Double paidAmount = ticket.getPaidAmount();
			
			String message = "需要退款的金额: " + Application.getCurrencySymbol() + paidAmount;
			
//			int option = JOptionPane.showOptionDialog(Application.getPosWindow(), message, POSConstants.CONFIRM,
//					JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
//			if (option != JOptionPane.OK_OPTION) {
//				return;
//			}

			ticket = TicketDAO.getInstance().loadFullTicket(ticket.getId());
			
			message = "<html>" +
					"订单 #" + ticket.getUniqId() + "<br/>总共支付 " + ticket.getPaidAmount();
			
			if(ticket.getGratuity() != null) {
				message += ", including tips " + ticket.getGratuity().getAmount();
			}
			
			message += "</html>";
			
			double refundAmount = NumberSelectionDialog2.takeDoubleInput(message, "请输入退款金额", paidAmount);
			if(Double.isNaN(refundAmount)) {
				return;
			}
			
			if(refundAmount > paidAmount) {
				POSMessageDialog.showError("退款金额不能大于实际支付金额");
				return;
			}

			PosTransactionService.getInstance().refundTicket(ticket, refundAmount);
			
			POSMessageDialog.showMessage("退款: " + Application.getCurrencySymbol() + refundAmount);
			
			ticketList.updateTicketList();
			
		} catch (Exception e) {
			POSMessageDialog.showError(e.getMessage(), e);
		}
	}

}
