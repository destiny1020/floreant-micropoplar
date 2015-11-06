package com.micropoplar.pos.ui.model;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import org.apache.commons.lang3.StringUtils;

import com.floreantpos.POSConstants;
import com.floreantpos.model.PaymentType;
import com.floreantpos.model.Ticket;
import com.floreantpos.model.TicketType;
import com.floreantpos.model.util.DateUtil;
import com.floreantpos.model.util.IllegalModelStateException;
import com.floreantpos.swing.TransparentPanel;
import com.floreantpos.ui.BeanEditor;
import com.floreantpos.ui.ticket.TicketViewerTable;
import com.micropoplar.pos.util.FontUtil;

import net.miginfocom.swing.MigLayout;

public class TicketForm extends BeanEditor<Ticket> {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  // data
  private Ticket ticket;

  // Basics
  private TransparentPanel pnlBasics;
  private JLabel lblTicketStatus;
  private JLabel lblTicketStatusContent;
  private JLabel lblTicketStatusNote;
  private JLabel lblTicketStatusNoteContent;
  private JLabel lblTicketType;
  private JLabel lblTicketTypeContent;
  private JLabel lblDineInNumber;
  private JLabel lblDineInNumberContent;
  private JLabel lblOperatorName;
  private JLabel lblOperatorNameContent;
  private JLabel lblTicketCreationTime;
  private JLabel lblTicketCreationTimeContent;
  private JLabel lblMemberPhone;
  private JLabel lblMemberPhoneContent;

  // Details Table
  private TicketViewerTable ticketItemTable;
  private JScrollPane ticketItemPane;

  // Discounts / Money related
  private TransparentPanel pnlCompaignAndPayment;
  private TransparentPanel pnlCompaign;
  private TransparentPanel pnlPayment;

  private JLabel lblTotalBeforeDiscount;
  private JLabel lblTotalBeforeDiscountContent;
  private JLabel lblTotalDiscount;
  private JLabel lblTotalDiscountContent;
  private JLabel lblTotalAfterDiscount;
  private JLabel lblTotalAfterDiscountContent;
  private JLabel lblPaymentType;
  private JLabel lblPaymentTypeContent;

  // lblActualReceived and lblChangeAmount will be hidden when the payment is not CASH
  private JLabel lblActualReceived;
  private JLabel lblActualReceivedContent;
  private JLabel lblChangeAmount;
  private JLabel lblChangeAmountContent;
  private JLabel lblIgnoredAmount;
  private JLabel lblIgnoredAmountContent;

  private JLabel lblRefundedAmount;
  private JLabel lblRefundedAmountContent;

  // Operations
  private JButton btnRefund;

  public TicketForm(Ticket ticket) {
    this.ticket = ticket;
    setBean(ticket);

    initComponents();
  }

  private void initComponents() {
    setLayout(new BorderLayout());

    // Basics Part
    pnlBasics = new TransparentPanel(
        new MigLayout("", "[grow, left][grow, left][30px][grow, left][grow, left]", "[][][][][]"));

    JLabel lblAreaBasic = new JLabel(POSConstants.TICKET_FORM_AREA_BASIC);
    lblAreaBasic.setFont(FontUtil.FONT_BIG);
    lblAreaBasic.setForeground(new Color(0, 70, 213));
    pnlBasics.add(lblAreaBasic, "cell 0 0");

    lblTicketType = new JLabel(POSConstants.TICKET_FORM_TICKET_TYPE + POSConstants.COLON);
    lblTicketTypeContent = new JLabel(ticket.getType().getValue());
    pnlBasics.add(lblTicketType, "cell 0 1, growx");
    pnlBasics.add(lblTicketTypeContent, "cell 1 1");

    if (ticket.getType() == TicketType.DINE_IN && ticket.getDineInNumber() != null) {
      lblDineInNumber =
          new JLabel(POSConstants.TICKET_FORM_TICKET_DINE_IN_NUMBER + POSConstants.COLON);
      lblDineInNumberContent = new JLabel(String.valueOf(ticket.getDineInNumber()));
      pnlBasics.add(lblDineInNumber, "cell 3 1");
      pnlBasics.add(lblDineInNumberContent, "cell 4 1");
    }

    lblTicketStatus = new JLabel(POSConstants.TICKET_FORM_TICKET_STATUS + POSConstants.COLON);
    lblTicketStatusContent = new JLabel(ticket.getTicketStatus());
    pnlBasics.add(lblTicketStatus, "cell 0 2, growx");
    pnlBasics.add(lblTicketStatusContent, "cell 1 2");

    if (ticket.isVoided()) {
      String voidReason = ticket.getVoidReason();
      if (StringUtils.isBlank(voidReason)) {
        voidReason = POSConstants.TICKET_FORM_TICKET_STATUS_VOID_NOTE_UNKNOWN;
      }
      lblTicketStatusNote =
          new JLabel(POSConstants.TICKET_FORM_TICKET_STATUS_VOID_NOTE + POSConstants.COLON);
      lblTicketStatusNoteContent = new JLabel(voidReason);
      pnlBasics.add(lblTicketStatusNote, "cell 3 2");
      pnlBasics.add(lblTicketStatusNoteContent, "cell 4 2");
    }

    lblOperatorName = new JLabel(POSConstants.TICKET_FORM_TICKET_OPERATOR + POSConstants.COLON);
    lblOperatorNameContent = new JLabel(ticket.getOwner().getName());
    pnlBasics.add(lblOperatorName, "cell 0 3, growx");
    pnlBasics.add(lblOperatorNameContent, "cell 1 3");

    lblTicketCreationTime =
        new JLabel(POSConstants.TICKET_FORM_TICKET_CREATION_TIME + POSConstants.COLON);
    lblTicketCreationTimeContent = new JLabel(DateUtil.getDateString(ticket.getCreateDate()));
    pnlBasics.add(lblTicketCreationTime, "cell 0 4, growx");
    pnlBasics.add(lblTicketCreationTimeContent, "cell 1 4");

    lblMemberPhone = new JLabel(POSConstants.TICKET_FORM_TICKET_MEMBER_PHONE + POSConstants.COLON);
    if (ticket.getCustomer() != null) {
      lblMemberPhoneContent = new JLabel(ticket.getCustomerPhone());
    } else {
      lblMemberPhoneContent = new JLabel(POSConstants.TICKET_FORM_TICKET_NON_MEMBER);
    }
    pnlBasics.add(lblMemberPhone, "cell 0 5, growx");
    pnlBasics.add(lblMemberPhoneContent, "cell 1 5");

    add(pnlBasics, BorderLayout.NORTH);

    // Details / Discount Part
    ticketItemTable = new TicketViewerTable(ticket);
    ticketItemTable.setRowHeight(25);
    ticketItemTable.getRenderer().setInTicketScreen(true);
    ticketItemPane = new JScrollPane(ticketItemTable);
    ticketItemPane
        .setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    ticketItemPane
        .setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    ticketItemPane.setPreferredSize(new Dimension(180, 200));
    ticketItemPane.setViewportView(ticketItemTable);

    add(ticketItemPane, BorderLayout.CENTER);

    // Payment
    pnlCompaignAndPayment = new TransparentPanel(new BorderLayout());

    pnlCompaign = new TransparentPanel(new MigLayout());

    JLabel lblAreaCompaign = new JLabel(POSConstants.TICKET_FORM_AREA_COMPAIGN);
    lblAreaCompaign.setFont(FontUtil.FONT_BIG);
    lblAreaCompaign.setForeground(new Color(0, 70, 213));
    pnlCompaign.add(lblAreaCompaign, "gapbottom 1, span, split 2, aligny center, wrap");

    JLabel lblTemp = new JLabel("暂无活动信息");
    pnlCompaign.add(lblTemp, "gap para, wrap");

    pnlCompaignAndPayment.add(pnlCompaign, BorderLayout.NORTH);

    pnlPayment = new TransparentPanel(new MigLayout());

    JLabel lblAreaPayment = new JLabel(POSConstants.TICKET_FORM_AREA_PAYMENT);
    lblAreaPayment.setFont(FontUtil.FONT_BIG);
    lblAreaPayment.setForeground(new Color(0, 70, 213));
    pnlPayment.add(lblAreaPayment, "gapbottom 1, span, split 2, aligny center, wrap");

    lblTotalBeforeDiscount =
        new JLabel(POSConstants.TICKET_FORM_MONEY_TOTAL_BEFORE_DISCOUNT + POSConstants.COLON);
    lblTotalBeforeDiscountContent = new JLabel(String.valueOf(ticket.getSubtotalAmount()));
    pnlPayment.add(lblTotalBeforeDiscount, "gap para");
    pnlPayment.add(lblTotalBeforeDiscountContent, "gap 20px");

    lblTotalDiscount =
        new JLabel(POSConstants.TICKET_FORM_MONEY_TOTAL_DISCOUNT + POSConstants.COLON);
    lblTotalDiscountContent = new JLabel(String.valueOf(ticket.getDiscountAmount()));
    pnlPayment.add(lblTotalDiscount, "gap 40px");
    pnlPayment.add(lblTotalDiscountContent, "gap 20px, wrap");

    lblTotalAfterDiscount =
        new JLabel(POSConstants.TICKET_FORM_MONEY_TOTAL_AFTER_DISCOUNT + POSConstants.COLON);
    lblTotalAfterDiscountContent = new JLabel(String.valueOf(ticket.getTotalAmount()));
    pnlPayment.add(lblTotalAfterDiscount, "gap para");
    pnlPayment.add(lblTotalAfterDiscountContent, "gap 20px");

    lblPaymentType = new JLabel(POSConstants.TICKET_FORM_MONEY_PAYMENT_TYPE + POSConstants.COLON);
    lblPaymentTypeContent = new JLabel(ticket.getPaymentType());
    pnlPayment.add(lblPaymentType, "gap 40px");
    pnlPayment.add(lblPaymentTypeContent, "gap 20px, wrap");

    // only for the CASH payment
    if (ticket.getPaymentType().equals(PaymentType.CASH.getDisplayString())) {
      lblActualReceived =
          new JLabel(POSConstants.TICKET_FORM_MONEY_ACTUAL_RECEIVED + POSConstants.COLON);
      lblActualReceivedContent = new JLabel(String.valueOf(ticket.getReceivedAmount()));
      pnlPayment.add(lblActualReceived, "gap para");
      pnlPayment.add(lblActualReceivedContent, "gap 20px");

      lblChangeAmount = new JLabel(POSConstants.TICKET_FORM_MONEY_CHANGE + POSConstants.COLON);
      lblChangeAmountContent = new JLabel(String.valueOf(ticket.getChangeAmount()));
      pnlPayment.add(lblChangeAmount, "gap 40px");
      pnlPayment.add(lblChangeAmountContent, "gap 20px, wrap");
    }

    pnlCompaignAndPayment.add(pnlPayment, BorderLayout.SOUTH);

    add(pnlCompaignAndPayment, BorderLayout.SOUTH);
  }

  @Override
  public boolean save() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  protected void updateView() {

  }

  @Override
  protected boolean updateModel() throws IllegalModelStateException {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public String getDisplayText() {
    return String.format(POSConstants.TICKET_FORM_TITLE, ticket.getUniqId());
  }

  public Ticket getTicket() {
    return ticket;
  }

}
