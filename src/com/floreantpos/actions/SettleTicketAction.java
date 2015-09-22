package com.floreantpos.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JDialog;

import com.floreantpos.POSConstants;
import com.floreantpos.model.PaymentType;
import com.floreantpos.model.PosTransaction;
import com.floreantpos.model.Ticket;
import com.floreantpos.model.dao.TicketDAO;
import com.floreantpos.ui.dialog.POSMessageDialog;
import com.floreantpos.ui.views.payment.SettleTicketDialog;
import com.floreantpos.util.NumberUtil;
import com.micropoplar.pos.payment.PaymentResult;
import com.micropoplar.pos.payment.QueryBusiness;

public class SettleTicketAction extends AbstractAction {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private int ticketId;

  public SettleTicketAction(int ticketId) {
    this.ticketId = ticketId;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    execute();
  }

  public boolean execute() {
    Ticket ticket = TicketDAO.getInstance().loadFullTicket(ticketId);

    if (ticket.isPaid()) {
      POSMessageDialog.showError(POSConstants.TICKET_ALREADY_PAID);
      return false;
    }

    // check whether wechat payment is finished if necessary
    if (ticket.isPaidBy(PaymentType.WECHAT)) {
      try {
        // make sure the WeChat related variables have been initialized
        Class.forName("com.micropoplar.pos.payment.config.WeChatConfig");
        PaymentResult result = QueryBusiness.queryQrCodePayOnce(ticket);
        if (result.isSuccessful()) {
          double totalFee =
              NumberUtil.roundToTwoDigit(Double.valueOf(result.getTotalFee() / 100.0));
          PosTransaction transaction = PaymentType.WECHAT.createTransaction();
          transaction.setTicket(ticket);
          transaction.setAmount(totalFee);
          transaction.setTenderAmount(totalFee);
          transaction.setOuterTransactionId(result.getTransactionId());
          // FIXME: make settleTicket dialog independent
          SettleTicketDialog posDialog = new SettleTicketDialog();
          posDialog.setTicket(ticket);
          posDialog.settleTicket(transaction);

          return true;
        }
      } catch (Exception e) {
        e.printStackTrace();
        POSMessageDialog.showError(POSConstants.WECHAT_PAY_STATUS_ERROR);
        return false;
      }
    }

    SettleTicketDialog posDialog = new SettleTicketDialog();
    posDialog.setTicket(ticket);
    posDialog.setSize(800, 700);
    posDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    posDialog.open();

    return !posDialog.isCanceled();
  }

}
