package com.floreantpos.ui.dialog;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import com.floreantpos.POSConstants;
import com.floreantpos.main.Application;
import com.floreantpos.model.PosTransaction;
import com.floreantpos.model.Ticket;
import com.floreantpos.model.TicketType;
import com.floreantpos.report.JReportPrintService;
import com.floreantpos.swing.PosButton;
import com.floreantpos.ui.views.SwitchboardView;
import com.floreantpos.ui.views.order.OrderView;
import com.floreantpos.ui.views.order.RootView;
import com.floreantpos.util.NumberUtil;

import net.miginfocom.swing.MigLayout;

public class TransactionCompletionDialog extends POSDialog {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  // private List<Ticket> tickets;
  private double tenderedAmount;
  private double totalAmount;
  private double paidAmount;
  private double dueAmount;
  private double gratuityAmount;
  private double changeAmount;

  private JLabel lblTenderedAmount;
  private JLabel lblTotalAmount;
  private JLabel lblChangeDue;

  private PosTransaction completedTransaction;

  public TransactionCompletionDialog(Frame parent, PosTransaction transaction) {
    super(parent, true);

    this.completedTransaction = transaction;

    setTitle(com.floreantpos.POSConstants.TRANSACTION_COMPLETED);

    setLayout(new MigLayout("align 50% 0%, ins 20", "[]20[]", ""));

    add(createLabel(POSConstants.TRANS_COMPLETE_TOTAL_AMOUNT + POSConstants.COLON, JLabel.LEFT),
        "grow");
    lblTotalAmount = createLabel("0.0", JLabel.RIGHT);
    add(lblTotalAmount, "span, grow");

    add(createLabel(POSConstants.TRANS_COMPLETE_ACTUAL_AMOUNT + POSConstants.COLON, JLabel.LEFT),
        "newline,grow");
    lblTenderedAmount = createLabel("0.0", JLabel.RIGHT);
    add(lblTenderedAmount, "span, grow");

    add(new JSeparator(), "newline,span, grow");

    add(createLabel(POSConstants.TRANS_COMPLETE_RETURN_AMOUNT + POSConstants.COLON, JLabel.LEFT),
        "grow");
    lblChangeDue = createLabel("0.0", JLabel.RIGHT);
    add(lblChangeDue, "span, grow");

    add(new JSeparator(), "sg mygroup,newline,span,grow");
    PosButton btnClose = new PosButton(POSConstants.CLOSE);
    btnClose.addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {
        dispose();

        genereateNewTicket();
      }
    });

    PosButton btnPrintStoreCopy = new PosButton(POSConstants.TRANS_COMPLETE_PRINT);
    btnPrintStoreCopy.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {

          JReportPrintService.printTransaction(completedTransaction, false);

        } catch (Exception ee) {
          POSMessageDialog.showError(Application.getPosWindow(), POSConstants.PRINT_ERROR, ee);
        }
        dispose();

        genereateNewTicket();
      }
    });

    PosButton btnPrintAllCopy = new PosButton("PRINT STORE & MERCHANT COPY");
    btnPrintAllCopy.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {

          JReportPrintService.printTransaction(completedTransaction, true);

        } catch (Exception ee) {
          POSMessageDialog.showError(Application.getPosWindow(), "打印时发生了错误.", ee);
        }
        dispose();
      }
    });

    JPanel p = new JPanel();

    if (completedTransaction.isCard()) {
      // TODO: merchant copy
      //      p.add(btnPrintAllCopy, "newline,skip, h 50");
      p.add(btnPrintStoreCopy, "skip, h 50");
      p.add(btnClose, "skip, h 50");
    } else {
      btnPrintStoreCopy.setText(POSConstants.PRINT);
      p.add(btnPrintStoreCopy, "skip, h 50");
      p.add(btnClose, "skip, h 50");
    }

    add(p, "newline, span 2, grow, gaptop 15px");
    // setResizable(false);
  }

  protected JLabel createLabel(String text, int alignment) {
    JLabel label = new JLabel(text);
    label.setFont(new java.awt.Font("微软雅黑", 1, 24));
    // label.setForeground(new java.awt.Color(255, 102, 0));
    label.setHorizontalAlignment(alignment);
    label.setText(text);
    return label;
  }

  public double getTenderedAmount() {
    return tenderedAmount;
  }

  public void setTenderedAmount(double amountTendered) {
    this.tenderedAmount = amountTendered;
  }

  public void updateView() {
    lblTotalAmount.setText(NumberUtil.formatNumber(totalAmount));
    lblTenderedAmount.setText(NumberUtil.formatNumber(tenderedAmount));
    // lblPaidAmount.setText(NumberUtil.formatNumber(paidAmount));
    // lblDueAmount.setText(NumberUtil.formatNumber(dueAmount));
    // lblGratuityAmount.setText(NumberUtil.formatNumber(gratuityAmount));
    lblChangeDue.setText(NumberUtil.formatNumber(changeAmount));
  }


  public double getDueAmount() {
    return dueAmount;
  }

  public void setDueAmount(double dueAmount) {
    this.dueAmount = dueAmount;
  }

  public double getPaidAmount() {
    return paidAmount;
  }

  public void setPaidAmount(double paidAmount) {
    this.paidAmount = paidAmount;
  }

  public double getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(double totalAmount) {
    this.totalAmount = totalAmount;
  }

  public double getGratuityAmount() {
    return gratuityAmount;
  }

  public void setGratuityAmount(double gratuityAmount) {
    this.gratuityAmount = gratuityAmount;
  }

  public double getChangeAmount() {
    return changeAmount;
  }

  public void setChangeAmount(double changeAmount) {
    this.changeAmount = changeAmount;
  }

  public void setCompletedTransaction(PosTransaction completedTransaction) {
    this.completedTransaction = completedTransaction;
  }

  private void genereateNewTicket() {
    // generate new ticket after current payment is completed
    Ticket ticket = SwitchboardView.getInstance().prepareNewTicket(TicketType.TAKE_OUT);
    OrderView.getInstance().setCurrentTicket(ticket);
    RootView.getInstance().showView(OrderView.VIEW_NAME);
  }
}
