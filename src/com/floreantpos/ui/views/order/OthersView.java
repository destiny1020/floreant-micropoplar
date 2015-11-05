package com.floreantpos.ui.views.order;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JPanel;

import org.apache.commons.lang3.StringUtils;

import com.floreantpos.POSConstants;
import com.floreantpos.customer.CustomerSelectionDialog;
import com.floreantpos.main.Application;
import com.floreantpos.model.MenuItem;
import com.floreantpos.model.Ticket;
import com.floreantpos.model.TicketItem;
import com.floreantpos.model.dao.MenuItemDAO;
import com.floreantpos.swing.POSToggleButton;
import com.floreantpos.swing.PosButton;
import com.floreantpos.ui.dialog.MiscTicketItemDialog;
import com.floreantpos.ui.dialog.NumberSelectionDialog2;
import com.floreantpos.ui.dialog.POSMessageDialog;
import com.floreantpos.ui.views.OrderInfoDialog;
import com.floreantpos.ui.views.OrderInfoView;
import com.floreantpos.ui.views.order.actions.ItemSelectionListener;

import net.miginfocom.swing.MigLayout;

public class OthersView extends JPanel {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private Ticket currentTicket;
  private ItemSelectionListener itemSelectionListener;

  private POSToggleButton btnEnableScan;
  private com.floreantpos.swing.PosButton btnQuickAddItem;
  private com.floreantpos.swing.PosButton btnOrderInfo;
  private com.floreantpos.swing.PosButton btnSearchItem;

  /** Creates new form OthersView */
  public OthersView() {
    initComponents();
  }

  public OthersView(ItemSelectionListener itemSelectionListener) {
    initComponents();

    setItemSelectionListener(itemSelectionListener);
  }

  private void initComponents() {
    setBorder(javax.swing.BorderFactory.createTitledBorder(null,
        POSConstants.OTHERS_VIEW_BORDER_TITLE, javax.swing.border.TitledBorder.CENTER,
        javax.swing.border.TitledBorder.DEFAULT_POSITION));
    setLayout(
        new MigLayout("", "[0:0, grow 33, fill][0:0, grow 33, fill][0:0, grow 33, fill]", "[][]"));

    btnEnableScan = new POSToggleButton(POSConstants.TICKET_OTHERS_VIEW_ENABLE_SCAN);
    btnEnableScan.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        boolean enableScan = btnEnableScan.isSelected();
        if (enableScan) {
          btnEnableScan.setText(POSConstants.TICKET_OTHERS_VIEW_ENABLED_SCAN);
        } else {
          btnEnableScan.setText(POSConstants.TICKET_OTHERS_VIEW_ENABLE_SCAN);
        }
        OrderView.getInstance().setScanEnabled(enableScan);
      }
    });
    add(btnEnableScan, "span, growx, wrap");

    btnOrderInfo = new com.floreantpos.swing.PosButton();
    btnQuickAddItem = new com.floreantpos.swing.PosButton();
    btnSearchItem = new PosButton(POSConstants.OTHERS_VIEW_SEARCH_ITEM_BY_BARCODE);
    btnSearchItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        // direct click
        searchItem(true);
      }
    });
    add(btnSearchItem, "");

    btnOrderInfo.setText(com.floreantpos.POSConstants.ORDER_INFO);
    btnOrderInfo.addActionListener(new java.awt.event.ActionListener() {
      @Override
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        doViewOrderInfo();
      }
    });
    add(btnOrderInfo, "");

    btnQuickAddItem.setText(com.floreantpos.POSConstants.MISC);
    btnQuickAddItem.addActionListener(new java.awt.event.ActionListener() {
      @Override
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        doInsertMisc(evt);
      }
    });
    add(btnQuickAddItem, "wrap");
  }

  protected void doAddEditCustomer() {
    CustomerSelectionDialog dialog = new CustomerSelectionDialog(getCurrentTicket());
    dialog.setSize(800, 650);
    dialog.open();
  }

  private void doInsertMisc(java.awt.event.ActionEvent evt) {
    MiscTicketItemDialog dialog = new MiscTicketItemDialog(Application.getPosWindow(), true);
    dialog.open();
    if (!dialog.isCanceled()) {
      TicketItem ticketItem = dialog.getTicketItem();
      RootView.getInstance().getOrderView().getTicketView().addTicketItem(ticketItem);
    }
  }

  private void doViewOrderInfo() {
    try {
      Ticket ticket = getCurrentTicket();

      List<Ticket> tickets = new ArrayList<Ticket>();
      tickets.add(ticket);

      OrderInfoView view = new OrderInfoView(tickets);
      OrderInfoDialog dialog = new OrderInfoDialog(view);
      dialog.setSize(400, 600);
      dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
      dialog.setLocationRelativeTo(Application.getPosWindow());
      dialog.setVisible(true);

    } catch (Exception e) {
      // TODO: handle exception
    }
  }

  public void updateView() {}

  public Ticket getCurrentTicket() {
    return currentTicket;
  }

  public void setCurrentTicket(Ticket currentTicket) {
    this.currentTicket = currentTicket;
    updateView();
  }

  public ItemSelectionListener getItemSelectionListener() {
    return itemSelectionListener;
  }

  public void setItemSelectionListener(ItemSelectionListener itemSelectionListener) {
    this.itemSelectionListener = itemSelectionListener;
  }

  /**
   * Two situations: 1. direct click by the operator 2. automatic show-up for the first time
   * 
   * @param isDirectClick
   */
  public void searchItem(boolean isDirectClick) {
    boolean hasClosedAlready = OrderView.getInstance().isHasClosedSearchDialog();
    if (hasClosedAlready && !isDirectClick) {
      // not to show up for two times
      return;
    }

    String barcode = NumberSelectionDialog2.takeStringInput(POSConstants.OTHERS_VIEW_INPUT_BARCODE);

    if (StringUtils.isBlank(barcode)) {
      return;
    }

    MenuItem menuItem = MenuItemDAO.getInstance().findByBarcode(barcode);
    if (menuItem == null) {
      POSMessageDialog.showError(POSConstants.OTHERS_VIEW_BARCODE_NO_ITEM);
      return;
    }
    itemSelectionListener.itemSelected(menuItem);
  }

  public void unselectScan() {
    btnEnableScan.setSelected(false);
    btnEnableScan.setText(POSConstants.TICKET_OTHERS_VIEW_ENABLE_SCAN);
  }

}
