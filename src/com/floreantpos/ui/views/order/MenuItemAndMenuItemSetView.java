/*
 * GroupView.java
 *
 * Created on August 5, 2006, 9:29 PM
 */

package com.floreantpos.ui.views.order;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractButton;
import javax.swing.SwingConstants;

import com.floreantpos.PosException;
import com.floreantpos.model.MenuGroup;
import com.floreantpos.model.MenuItem;
import com.floreantpos.model.dao.MenuItemDAO;
import com.floreantpos.swing.ImageIcon;
import com.floreantpos.swing.PosButton;
import com.floreantpos.ui.views.order.actions.ItemSelectionListener;
import com.micropoplar.pos.ui.IOrderViewItem;

/**
 * 
 * @author MShahriar
 */
public class MenuItemAndMenuItemSetView extends SelectionView {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public final static String VIEW_NAME = "ITEM_VIEW";

  private Vector<ItemSelectionListener> listenerList = new Vector<ItemSelectionListener>();

  private MenuGroup menuGroup;

  /** Creates new form GroupView */
  public MenuItemAndMenuItemSetView() {
    super(com.floreantpos.POSConstants.ITEMS);

    setBackEnable(false);
  }

  public MenuGroup getMenuGroup() {
    return menuGroup;
  }

  public void setMenuGroup(MenuGroup menuGroup) {
    this.menuGroup = menuGroup;

    reset();

    if (menuGroup == null) {
      return;
    }

    MenuItemDAO dao = MenuItemDAO.getInstance();
    try {
      List<MenuItem> menuItems = dao.findByParent(menuGroup, false);

      // put MenuItem and MenuItemSet together
      List<IOrderViewItem> items = new ArrayList<>(menuItems.size());
      items.addAll(menuItems);

      setBackEnable(items.size() > 0);
      setItems(items);

      // for (int i = 0; i < items.size(); i++) {
      // MenuItem menuItem = items.get(i);
      // ItemButton itemButton = new ItemButton(menuItem);
      // addButton(itemButton);
      // }
      // revalidate();
      // repaint();
    } catch (PosException e) {
      e.printStackTrace();
    }
  }

  @Override
  protected AbstractButton createItemButton(Object item) {
    ItemButton itemButton = new ItemButton((IOrderViewItem) item);

    return itemButton;
  }

  public void addItemSelectionListener(ItemSelectionListener listener) {
    listenerList.add(listener);
  }

  public void removeItemSelectionListener(ItemSelectionListener listener) {
    listenerList.remove(listener);
  }

  private void fireItemSelected(IOrderViewItem foodItem) {
    for (ItemSelectionListener listener : listenerList) {
      listener.itemSelected(foodItem);
    }
  }

  private void fireBackFromItemSelected() {
    for (ItemSelectionListener listener : listenerList) {
      listener.itemSelectionFinished(menuGroup);
    }
  }

  private class ItemButton extends PosButton implements ActionListener {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private static final int BUTTON_SIZE = 100;
    IOrderViewItem foodItem;

    ItemButton(IOrderViewItem foodItem) {
      this.foodItem = foodItem;
      init(foodItem);
    }

    private void init(IOrderViewItem foodItem) {
      setVerticalTextPosition(SwingConstants.BOTTOM);
      setHorizontalTextPosition(SwingConstants.CENTER);

      if (foodItem.getImage() != null) {
        int w = BUTTON_SIZE - 10;
        int h = BUTTON_SIZE - 10;

        if (foodItem.isShowImageOnly()) {
          ImageIcon imageIcon = new ImageIcon(new ImageIcon(foodItem.getImage()).getImage()
              .getScaledInstance(w, h, Image.SCALE_SMOOTH));
          setIcon(imageIcon);
        } else {
          w = 80;
          h = 80;

          ImageIcon imageIcon = new ImageIcon(new ImageIcon(foodItem.getImage()).getImage()
              .getScaledInstance(w, h, Image.SCALE_SMOOTH));
          setIcon(imageIcon);
          setText("<html><body><center>" + foodItem.getName() + "</center></body></html>");
        }

      } else {
        setText("<html><body><center>" + foodItem.getName() + "</center></body></html>");
      }

      setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));
      addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      fireItemSelected(foodItem);
    }
  }

  @Override
  public void doGoBack() {
    fireBackFromItemSelected();
  }
}
