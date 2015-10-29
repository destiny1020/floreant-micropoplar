package com.floreantpos.ui.views.order;

import org.apache.commons.lang3.StringUtils;

import com.floreantpos.POSConstants;
import com.floreantpos.actions.SettleTicketAction;
import com.floreantpos.main.Application;
import com.floreantpos.model.ActionHistory;
import com.floreantpos.model.MenuCategory;
import com.floreantpos.model.MenuGroup;
import com.floreantpos.model.MenuItem;
import com.floreantpos.model.MenuModifier;
import com.floreantpos.model.Ticket;
import com.floreantpos.model.TicketItem;
import com.floreantpos.model.TicketType;
import com.floreantpos.model.User;
import com.floreantpos.model.dao.ActionHistoryDAO;
import com.floreantpos.model.dao.MenuItemDAO;
import com.floreantpos.model.dao.TicketDAO;
import com.floreantpos.model.util.TicketUniqIdGenerator;
import com.floreantpos.ui.dialog.POSMessageDialog;
import com.floreantpos.ui.views.CustomerView;
import com.floreantpos.ui.views.SwitchboardView;
import com.floreantpos.ui.views.customer.CustomerTicketView;
import com.floreantpos.ui.views.order.actions.CategorySelectionListener;
import com.floreantpos.ui.views.order.actions.GroupSelectionListener;
import com.floreantpos.ui.views.order.actions.ItemSelectionListener;
import com.floreantpos.ui.views.order.actions.ModifierSelectionListener;
import com.floreantpos.ui.views.order.actions.OrderListener;
import com.micropoplar.pos.model.MenuItemSet;
import com.micropoplar.pos.model.dao.MenuItemSetDAO;
import com.micropoplar.pos.ui.IOrderViewItem;

public class OrderController implements OrderListener, CategorySelectionListener,
    GroupSelectionListener, ItemSelectionListener, ModifierSelectionListener {
  private OrderView orderView;

  public OrderController(OrderView orderView) {
    this.orderView = orderView;

    orderView.getCategoryView().addCategorySelectionListener(this);
    orderView.getGroupView().addGroupSelectionListener(this);
    orderView.getItemView().addItemSelectionListener(this);
    orderView.getOthersView().setItemSelectionListener(this);
    orderView.getModifierView().addModifierSelectionListener(this);
    orderView.getTicketView().addOrderListener(this);
  }

  public void categorySelected(MenuCategory foodCategory) {
    orderView.showView(GroupView.VIEW_NAME);
    orderView.getGroupView().setMenuCategory(foodCategory);
  }

  public void groupSelected(MenuGroup foodGroup) {
    orderView.showView(MenuItemAndMenuItemSetView.VIEW_NAME);
    orderView.getItemView().setMenuGroup(foodGroup);
  }

  public void itemSelected(IOrderViewItem menuItem) {
    if (menuItem instanceof MenuItem) {
      MenuItem item = (MenuItem) menuItem;
      MenuItemDAO dao = MenuItemDAO.getInstance();
      item = dao.initialize(item);

      TicketItem ticketItem = item.convertToTicketItem();
      orderView.getTicketView().addTicketItem(ticketItem);

      // sync ticket item to the customer view
      CustomerView.getInstance().getCustomerTicketView().updateAllView();

      if (item.hasModifiers()) {
        ModifierView modifierView = orderView.getModifierView();
        modifierView.setMenuItem(item, ticketItem);
        orderView.showView(ModifierView.VIEW_NAME);
      }
    } else if (menuItem instanceof MenuItemSet) {
      MenuItemSet item = (MenuItemSet) menuItem;
      MenuItemSetDAO dao = MenuItemSetDAO.getInstance();
      item = dao.initialize(item);

      TicketItem ticketItem = item.convertToTicketItem();
      orderView.getTicketView().addTicketItem(ticketItem);

      // sync ticket item to the customer view
      CustomerView.getInstance().getCustomerTicketView().updateAllView();
    } else {
      POSMessageDialog.showError(com.floreantpos.POSConstants.ERROR_ITEM_NOT_VALID);
      return;
    }
  }

  public void modifierSelected(MenuItem parent, MenuModifier modifier) {
    // TicketItemModifier itemModifier = new TicketItemModifier();
    // itemModifier.setItemId(modifier.getId());
    // itemModifier.setName(modifier.getName());
    // itemModifier.setPrice(modifier.getPrice());
    // itemModifier.setExtraPrice(modifier.getExtraPrice());
    // itemModifier.setMinQuantity(modifier.getMinQuantity());
    // itemModifier.setMaxQuantity(modifier.getMaxQuantity());
    // itemModifier.setTaxRate(modifier.getTax() == null ? 0 : modifier.getTax().getRate());
    //
    // orderView.getTicketView().addModifier(itemModifier);
  }

  public void itemSelectionFinished(MenuGroup parent) {
    MenuCategory menuCategory = parent.getParent();
    GroupView groupView = orderView.getGroupView();
    if (!menuCategory.equals(groupView.getMenuCategory())) {
      groupView.setMenuCategory(menuCategory);
    }
    orderView.showView(GroupView.VIEW_NAME);
  }

  public void modifierSelectionFiniched(MenuItem parent) {
    MenuGroup menuGroup = parent.getParent();
    MenuItemAndMenuItemSetView itemView = orderView.getItemView();
    if (!menuGroup.equals(itemView.getMenuGroup())) {
      itemView.setMenuGroup(menuGroup);
    }
    orderView.showView(MenuItemAndMenuItemSetView.VIEW_NAME);
  }

  public void payOrderSelected(Ticket ticket) {
    // not to return to the ticket list view after payment
    //    RootView.getInstance().showView(SwitchboardView.VIEW_NAME);
    new SettleTicketAction(ticket.getId()).execute();
    SwitchboardView.getInstance().updateTicketList();
  }

  // VERIFIED
  public synchronized static void saveOrder(Ticket ticket) {
    if (ticket == null)
      return;

    // set uniq id if necessary
    if (StringUtils.isBlank(ticket.getUniqId())) {
      ticket.setUniqId(TicketUniqIdGenerator.generate());
    }

    boolean newTicket = ticket.getId() == null;

    TicketDAO ticketDAO = new TicketDAO();
    ticketDAO.saveOrUpdate(ticket);

    // save the action
    ActionHistoryDAO actionHistoryDAO = ActionHistoryDAO.getInstance();
    User user = Application.getCurrentUser();

    if (newTicket) {
      actionHistoryDAO.saveHistory(user, ActionHistory.NEW_CHECK,
          POSConstants.RECEIPT_REPORT_TICKET_NO_LABEL + ":" + ticket.getId());
    } else {
      actionHistoryDAO.saveHistory(user, ActionHistory.EDIT_CHECK,
          POSConstants.RECEIPT_REPORT_TICKET_NO_LABEL + ":" + ticket.getId());
    }
  }
}
