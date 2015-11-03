package com.floreantpos.actions;

import org.apache.commons.lang3.StringUtils;

import com.floreantpos.ITicketList;
import com.floreantpos.POSConstants;
import com.floreantpos.main.Application;
import com.floreantpos.model.Ticket;
import com.floreantpos.model.UserPermission;
import com.floreantpos.model.dao.TicketDAO;
import com.floreantpos.services.PosTransactionService;
import com.floreantpos.services.TicketService;
import com.floreantpos.ui.dialog.NumberSelectionDialog2;
import com.floreantpos.ui.dialog.POSMessageDialog;

public class RefundAction extends PosAction {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private ITicketList ticketList;

  public RefundAction(ITicketList ticketList) {
    super("REFUND", UserPermission.REFUND_TICKET);

    this.ticketList = ticketList;
  }

  @Override
  public void execute() {
    try {
      Ticket ticket = ticketList.getSelectedTicket();

      if (ticket == null) {
        String ticketUniqId =
            NumberSelectionDialog2.takeStringInput(POSConstants.PLEASE_INPUT_OR_SCAN_TICKET);
        if (StringUtils.isBlank(ticketUniqId)) {
          // user clicked without input
          return;
        }
        ticket = TicketService.getTicketByUniqId(ticketUniqId);
      }

      if (!ticket.isPaid()) {
        POSMessageDialog.showError(POSConstants.TICKET_NOT_PAID_YET);
        return;
      }

      if (ticket.isRefunded()) {
        POSMessageDialog.showError(POSConstants.TICKET_REFUND_ALREADY);
        return;
      }

      Double paidAmount = ticket.getPaidAmount();

      String message = POSConstants.REFUND_AMOUNT + Application.getCurrencySymbol() + paidAmount;

      // int option = JOptionPane.showOptionDialog(Application.getPosWindow(), message,
      // POSConstants.CONFIRM,
      // JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
      // if (option != JOptionPane.OK_OPTION) {
      // return;
      // }

      ticket = TicketDAO.getInstance().loadFullTicket(ticket.getId());

      message = "<html>" + "订单 " + ticket.getUniqId() + "<br/>总共支付 " + ticket.getPaidAmount();
      message += "</html>";

      double refundAmount = NumberSelectionDialog2.takeDoubleInput(message, "请输入退款金额", paidAmount);
      if (Double.isNaN(refundAmount)) {
        return;
      }

      if (refundAmount > paidAmount) {
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
