/*
 * OrderView.java
 *
 * Created on August 4, 2006, 6:58 PM
 */

package com.floreantpos.ui.views.order;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;

import javax.swing.JComponent;

import com.floreantpos.model.Ticket;
import com.floreantpos.swing.TransparentPanel;
import com.floreantpos.ui.dialog.POSMessageDialog;

/**
 *
 * @author MShahriar
 */
public class OrderView extends TransparentPanel {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private HashMap<String, JComponent> views = new HashMap<String, JComponent>();

  public final static String VIEW_NAME = "ORDER_VIEW";
  private static OrderView instance;

  private Ticket currentTicket;

  // whether scan is enabled
  private boolean enableScan;

  /** Creates new form OrderView */
  private OrderView() {
    initComponents();
    initActions();
  }

  private void initActions() {
    addKeyListener(new KeyAdapter() {
      @Override
      public void keyTyped(KeyEvent e) {
        if (enableScan) {
          System.out.println(e.getKeyChar());
        }
      }
    });

    addFocusListener(new FocusListener() {
      @Override
      public void focusLost(FocusEvent e) {
        othersView.unselectScan();
      }

      @Override
      public void focusGained(FocusEvent e) {}
    });
  }

  public void addView(final String viewName, final JComponent view) {
    JComponent oldView = views.get(viewName);
    if (oldView != null) {
      return;
    }

    midContainer.add(view, viewName);
  }

  public void init() {
    setOpaque(false);

    cardLayout = new CardLayout();
    midContainer.setOpaque(false);
    midContainer.setLayout(cardLayout);

    groupView = new GroupView();
    itemView = new MenuItemAndMenuItemSetView();

    addView(GroupView.VIEW_NAME, groupView);
    addView(MenuItemAndMenuItemSetView.VIEW_NAME, itemView);
    addView("VIEW_EMPTY", new com.floreantpos.swing.TransparentPanel());

    showView("VIEW_EMPTY");

    orderController = new OrderController(this);
  }

  /**
   * This method is called from within the constructor to initialize the form. WARNING: Do NOT
   * modify this code. The content of this method is always regenerated by the Form Editor.
   */
  // <editor-fold defaultstate="collapsed" desc=" Generated Code
  // ">//GEN-BEGIN:initComponents
  private void initComponents() {
    categoryView = new com.floreantpos.ui.views.order.CategoryView();
    ticketView = new com.floreantpos.ui.views.order.TicketView();
    ticketView.setPreferredSize(new Dimension(380, 463));
    jPanel1 = new com.floreantpos.swing.TransparentPanel();
    midContainer = new com.floreantpos.swing.TransparentPanel();
    othersView = new com.floreantpos.ui.views.order.OthersView();

    setLayout(new java.awt.BorderLayout());

    add(categoryView, java.awt.BorderLayout.WEST);

    add(ticketView, java.awt.BorderLayout.EAST);

    jPanel1.setLayout(new java.awt.BorderLayout());

    jPanel1.setBackground(new java.awt.Color(51, 153, 0));
    jPanel1.add(midContainer, java.awt.BorderLayout.CENTER);

    jPanel1.add(othersView, java.awt.BorderLayout.SOUTH);

    add(jPanel1, java.awt.BorderLayout.CENTER);

  }// </editor-fold>//GEN-END:initComponents

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private com.floreantpos.ui.views.order.CategoryView categoryView;
  private com.floreantpos.swing.TransparentPanel jPanel1;
  private com.floreantpos.swing.TransparentPanel midContainer;
  private com.floreantpos.ui.views.order.OthersView othersView;
  private com.floreantpos.ui.views.order.TicketView ticketView;
  // End of variables declaration//GEN-END:variables

  private CardLayout cardLayout;
  private GroupView groupView;
  private MenuItemAndMenuItemSetView itemView;
  private OrderController orderController;

  private boolean hasClosedSearchDialog = false;

  public void showView(final String viewName) {
    cardLayout.show(midContainer, viewName);

    // show the dialog let server scan the items
    if (!hasClosedSearchDialog && currentTicket != null && currentTicket.getTicketItems() != null
        && currentTicket.getTicketItems().size() == 0) {
      EventQueue.invokeLater(new Runnable() {
        @Override
        public void run() {
          othersView.searchItem(false);
          hasClosedSearchDialog = true;
        }
      });
    }
  }

  public com.floreantpos.ui.views.order.CategoryView getCategoryView() {
    return categoryView;
  }

  public void setCategoryView(com.floreantpos.ui.views.order.CategoryView categoryView) {
    this.categoryView = categoryView;
  }

  public GroupView getGroupView() {
    return groupView;
  }

  public void setGroupView(GroupView groupView) {
    this.groupView = groupView;
  }

  public MenuItemAndMenuItemSetView getItemView() {
    return itemView;
  }

  public void setItemView(MenuItemAndMenuItemSetView itemView) {
    this.itemView = itemView;
  }

  public com.floreantpos.ui.views.order.TicketView getTicketView() {
    return ticketView;
  }

  public void setTicketView(com.floreantpos.ui.views.order.TicketView ticketView) {
    this.ticketView = ticketView;
  }

  public OrderController getOrderController() {
    return orderController;
  }

  public Ticket getCurrentTicket() {
    return currentTicket;
  }

  public void setCurrentTicket(Ticket currentTicket) {
    this.currentTicket = currentTicket;

    ticketView.setTicket(currentTicket);
    othersView.setCurrentTicket(currentTicket);

    // set ticket to the customer view
    CustomerRootView.getInstance().getCustomerView().setCurrentTicket(currentTicket);

    resetView();
  }

  public synchronized static OrderView getInstance() {
    if (instance == null) {
      instance = new OrderView();
    }
    return instance;
  }

  public void resetView() {}

  public com.floreantpos.ui.views.order.OthersView getOthersView() {
    return othersView;
  }

  @Override
  public void setVisible(boolean aFlag) {
    if (aFlag) {
      try {
        categoryView.initialize();
      } catch (Throwable t) {
        POSMessageDialog.showError(com.floreantpos.POSConstants.ERROR_MESSAGE, t);
      }
    } else {
      categoryView.cleanup();
    }
    super.setVisible(aFlag);
  }

  public boolean isHasClosedSearchDialog() {
    return hasClosedSearchDialog;
  }

  public void setHasClosedSearchDialog(boolean hasClosedSearchDialog) {
    this.hasClosedSearchDialog = hasClosedSearchDialog;
  }

  public void setScanEnabled(boolean selected) {
    enableScan = selected;
    requestFocusInWindow();
  }
}
