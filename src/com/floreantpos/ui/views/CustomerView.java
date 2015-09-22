package com.floreantpos.ui.views;

import java.awt.Dimension;

import com.floreantpos.model.Ticket;
import com.floreantpos.swing.TransparentPanel;
import com.floreantpos.ui.views.customer.AdvertisementView;
import com.floreantpos.ui.views.customer.CustomerTicketView;
import com.floreantpos.ui.views.customer.MultipleUsageView;

/**
 * The view that customer can see: 1. ticket items information 2. payment interactions: wechat and
 * alipay 3. advertisement provided by the restaurant
 * 
 * @author destiny1020
 *
 */
public class CustomerView extends TransparentPanel {
  public final static String VIEW_NAME = "CUSTOMER_VIEW";

  private static CustomerView instance;

  private MultipleUsageView customerMultipleUsageView;
  private CustomerTicketView customerTicketView;

  private Ticket currentTicket;

  private CustomerView() {
    initComponents();
  }

  private void initComponents() {
    setLayout(new java.awt.BorderLayout());

    customerTicketView = new CustomerTicketView();
    customerTicketView.setPreferredSize(new Dimension(380, 463));

    add(customerTicketView, java.awt.BorderLayout.EAST);

    customerMultipleUsageView = new MultipleUsageView();
    add(customerMultipleUsageView, java.awt.BorderLayout.CENTER);
  }

  public synchronized static CustomerView getInstance() {
    if (instance == null) {
      instance = new CustomerView();
    }
    return instance;
  }

  public CustomerTicketView getCustomerTicketView() {
    return customerTicketView;
  }

  public void setCustomerTicketView(CustomerTicketView customerTicketView) {
    this.customerTicketView = customerTicketView;
  }

  public Ticket getCurrentTicket() {
    return currentTicket;
  }

  public void setCurrentTicket(Ticket currentTicket) {
    this.currentTicket = currentTicket;

    customerTicketView.setTicket(currentTicket);
  }

  public void transactionCompleted() {
    setCurrentTicket(null);
    customerMultipleUsageView.showView(AdvertisementView.VIEW_NAME);
  }

  public MultipleUsageView getCustomerMultipleUsageView() {
    return customerMultipleUsageView;
  }
}
