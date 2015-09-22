package com.floreantpos.ui.views.customer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import com.floreantpos.POSConstants;
import com.floreantpos.model.Ticket;
import com.floreantpos.model.TicketItem;
import com.floreantpos.swing.TransparentPanel;
import com.floreantpos.ui.ticket.TicketViewerTable;
import com.floreantpos.util.NumberUtil;

import net.miginfocom.swing.MigLayout;

/**
 * The ticket view for the customers. Contains ticket items' information, just for showing.
 * 
 * @author destiny1020
 *
 */
public class CustomerTicketView extends JPanel {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private JScrollPane ticketViewerPane;
  private TicketViewerTable ticketViewerTable;

  private TransparentPanel overallPanel;
  private TransparentPanel ticketAmountPanel;

  private JLabel lblDiscount;
  private JLabel lblSubtotal;
  private JLabel lblTotal;
  private JTextField tfDiscount;
  private JTextField tfSubtotal;
  private JTextField tfTotal;

  /**
   * current ticket
   */
  private Ticket ticket;

  public CustomerTicketView() {
    initComponents();

    // ticket view table related
    ticketViewerTable.setRowHeight(35);
    ticketViewerTable.getRenderer().setInTicketScreen(true);
  }

  private void initComponents() {
    setLayout(new BorderLayout(5, 5));

    overallPanel = new TransparentPanel();
    overallPanel.setLayout(new BorderLayout(5, 5));

    ticketAmountPanel = new TransparentPanel();
    ticketAmountPanel
        .setLayout(new MigLayout("alignx trailing,fill", "[grow][]", "[][][][][][][][]"));

    ticketViewerTable = new TicketViewerTable();
    ticketViewerPane = new JScrollPane(ticketViewerTable);

    // subtotal related
    lblSubtotal = new JLabel();
    lblSubtotal.setFont(new Font(POSConstants.DEFAULT_FONT_NAME, 1, 12));
    lblSubtotal.setHorizontalAlignment(SwingConstants.RIGHT);
    lblSubtotal.setText(POSConstants.SUBTOTAL + POSConstants.COLON);
    ticketAmountPanel.add(lblSubtotal, "cell 0 1,growx,aligny center");

    tfSubtotal = new JTextField();
    tfSubtotal.setHorizontalAlignment(SwingConstants.TRAILING);
    tfSubtotal.setEditable(false);
    tfSubtotal.setFont(new Font(POSConstants.DEFAULT_FONT_NAME, 1, 12));
    tfSubtotal.setPreferredSize(new Dimension(200, 25));
    ticketAmountPanel.add(tfSubtotal, "cell 1 1,growx,aligny center");

    // discount related
    lblDiscount = new JLabel();
    lblDiscount.setFont(new Font(POSConstants.DEFAULT_FONT_NAME, 1, 12));
    lblDiscount.setHorizontalAlignment(SwingConstants.RIGHT);
    lblDiscount.setText(POSConstants.DISCOUNT + POSConstants.COLON);
    ticketAmountPanel.add(lblDiscount, "cell 0 2,growx,aligny center");

    tfDiscount = new JTextField();
    tfDiscount.setHorizontalAlignment(SwingConstants.TRAILING);
    tfDiscount.setEditable(false);
    tfDiscount.setFont(new Font(POSConstants.DEFAULT_FONT_NAME, 1, 12));
    tfDiscount.setPreferredSize(new Dimension(200, 25));
    ticketAmountPanel.add(tfDiscount, "cell 1 2,growx,aligny center");

    // total related
    lblTotal = new JLabel();
    lblTotal.setFont(new Font(POSConstants.DEFAULT_FONT_NAME, 1, 12));
    lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
    lblTotal.setText(POSConstants.TOTAL + POSConstants.COLON);
    ticketAmountPanel.add(lblTotal, "cell 0 3,growx,aligny center");

    tfTotal = new JTextField();
    tfTotal.setHorizontalAlignment(SwingConstants.TRAILING);
    tfTotal.setEditable(false);
    tfTotal.setFont(new Font(POSConstants.DEFAULT_FONT_NAME, 1, 12));
    tfTotal.setPreferredSize(new Dimension(200, 25));
    ticketAmountPanel.add(tfTotal, "cell 1 3,growx,aligny center");

    // layout
    JPanel ticketTableAndAmountPanel = new JPanel(new BorderLayout(5, 5));
    ticketTableAndAmountPanel.add(ticketViewerPane, BorderLayout.CENTER);
    ticketTableAndAmountPanel.add(ticketAmountPanel, BorderLayout.SOUTH);

    overallPanel.add(ticketTableAndAmountPanel, BorderLayout.CENTER);

    add(overallPanel, BorderLayout.CENTER);
  }

  public void updateView() {
    if (ticket == null) {
      tfSubtotal.setText("");
      tfDiscount.setText("");
      tfTotal.setText("");

      setBorder(BorderFactory.createTitledBorder(null, "还未点单", TitledBorder.CENTER,
          TitledBorder.DEFAULT_POSITION));

      return;
    }

    ticket.calculatePrice();

    tfSubtotal.setText(NumberUtil.formatNumber(ticket.getSubtotalAmount()));
    tfDiscount.setText(NumberUtil.formatNumber(ticket.getDiscountAmount()));
    tfTotal.setText(NumberUtil.formatNumber(ticket.getTotalAmount()));

    if (ticket.getId() == null) {
      setBorder(BorderFactory.createTitledBorder(null, "尊敬的顾客, 您的点单如下:", TitledBorder.CENTER,
          TitledBorder.DEFAULT_POSITION));
    } else {
      setBorder(BorderFactory.createTitledBorder(null, "尊敬的顾客, 您的点单如下:", TitledBorder.CENTER,
          TitledBorder.DEFAULT_POSITION));
    }
  }

  public void addTicketItem(TicketItem ticketItem) {
    ticketViewerTable.addTicketItem(ticketItem);
    updateView();
  }

  public void updateAllView() {
    ticketViewerTable.updateView();
    updateView();
  }

  public Ticket getTicket() {
    return ticket;
  }

  public void setTicket(Ticket ticket) {
    this.ticket = ticket;
    ticketViewerTable.setTicket(ticket);
    updateView();
  }

}
