package com.micropoplar.pos.ui.model;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JLabel;

import com.floreantpos.POSConstants;
import com.floreantpos.model.Ticket;
import com.floreantpos.model.TicketType;
import com.floreantpos.model.util.DateUtil;
import com.floreantpos.model.util.IllegalModelStateException;
import com.floreantpos.swing.TransparentPanel;
import com.floreantpos.ui.BeanEditor;
import com.floreantpos.ui.ticket.TicketViewerTable;

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

  // Details Table / Discounts
  private TransparentPanel pnlDetails;
  private TicketViewerTable ticketItemTable;
  private JLabel lblDiscountDetails;

  // Money related
  private TransparentPanel pnlMoney;
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

    pnlBasics = new TransparentPanel(
        new MigLayout("", "[grow, left][grow, left][30px][grow, left][grow, left]", "[][][][][]"));

    lblTicketType = new JLabel(POSConstants.TICKET_FORM_TICKET_TYPE + POSConstants.COLON);
    lblTicketTypeContent = new JLabel(ticket.getType().getValue());
    pnlBasics.add(lblTicketType, "cell 0 0, growx");
    pnlBasics.add(lblTicketTypeContent, "cell 1 0");

    if (ticket.getType() == TicketType.DINE_IN && ticket.getDineInNumber() != null) {
      lblDineInNumber =
          new JLabel(POSConstants.TICKET_FORM_TICKET_DINE_IN_NUMBER + POSConstants.COLON);
      lblDineInNumberContent = new JLabel(String.valueOf(ticket.getDineInNumber()));
      pnlBasics.add(lblDineInNumber, "cell 3 0");
      pnlBasics.add(lblDineInNumberContent, "cell 4 0");
    }

    lblTicketStatus = new JLabel(POSConstants.TICKET_FORM_TICKET_STATUS + POSConstants.COLON);
    lblTicketStatusContent = new JLabel(ticket.getTicketStatus());
    pnlBasics.add(lblTicketStatus, "cell 0 1, growx");
    pnlBasics.add(lblTicketStatusContent, "cell 1 1");

    lblOperatorName = new JLabel(POSConstants.TICKET_FORM_TICKET_OPERATOR + POSConstants.COLON);
    lblOperatorNameContent = new JLabel(ticket.getOwner().getName());
    pnlBasics.add(lblOperatorName, "cell 0 2, growx");
    pnlBasics.add(lblOperatorNameContent, "cell 1 2");

    lblTicketCreationTime =
        new JLabel(POSConstants.TICKET_FORM_TICKET_CREATION_TIME + POSConstants.COLON);
    lblTicketCreationTimeContent = new JLabel(DateUtil.getDateString(ticket.getCreateDate()));
    pnlBasics.add(lblTicketCreationTime, "cell 0 3, growx");
    pnlBasics.add(lblTicketCreationTimeContent, "cell 1 3");

    lblMemberPhone = new JLabel(POSConstants.TICKET_FORM_TICKET_MEMBER_PHONE + POSConstants.COLON);
    if (ticket.getCustomer() != null) {
      lblMemberPhoneContent = new JLabel(ticket.getCustomerPhone());
    } else {
      lblMemberPhoneContent = new JLabel(POSConstants.TICKET_FORM_TICKET_NON_MEMBER);
    }
    pnlBasics.add(lblMemberPhone, "cell 0 4, growx");
    pnlBasics.add(lblMemberPhoneContent, "cell 1 4");

    add(pnlBasics, BorderLayout.NORTH);
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
