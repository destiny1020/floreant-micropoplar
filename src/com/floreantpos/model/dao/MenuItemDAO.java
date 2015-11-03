package com.floreantpos.model.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.floreantpos.POSConstants;
import com.floreantpos.PosException;
import com.floreantpos.model.MenuGroup;
import com.floreantpos.model.MenuItem;

public class MenuItemDAO extends BaseMenuItemDAO {

  /**
   * Default constructor. Can be used in place of getInstance()
   */
  public MenuItemDAO() {}

  public MenuItem initialize(MenuItem menuItem) {
    if (menuItem.getId() == null)
      return menuItem;

    Session session = null;

    try {
      session = createNewSession();
      menuItem = (MenuItem) session.get(MenuItem.class, menuItem.getId());

      return menuItem;
    } finally {
      closeSession(session);
    }
  }

  /**
   * Search menu item by its barcode. Used by the scanner.
   * 
   * @param barcode
   * @return
   */
  public MenuItem findByBarcode(String barcode) {
    Session session = null;

    try {
      session = getSession();
      Criteria criteria = session.createCriteria(getReferenceClass());
      criteria.add(Restrictions.eq(MenuItem.PROP_BARCODE, barcode));
      MenuItem newItem = (MenuItem) criteria.uniqueResult();

      return newItem;
    } catch (Exception e) {
      throw new PosException("搜索商品条形码时发生了错误: " + barcode);
    } finally {
      if (session != null) {
        session.close();
      }
    }
  }

  @SuppressWarnings("unchecked")
  public List<MenuItem> findByParent(MenuGroup group, boolean includeInvisibleItems)
      throws PosException {
    Session session = null;

    try {
      session = getSession();
      Criteria criteria = session.createCriteria(getReferenceClass());
      criteria.add(Restrictions.eq(MenuItem.PROP_GROUP, group));

      if (!includeInvisibleItems) {
        criteria.add(Restrictions.eq(MenuItem.PROP_VISIBLE, Boolean.TRUE));
      }

      return criteria.list();
    } catch (Exception e) {
      e.printStackTrace();
      throw new PosException(POSConstants.ERROR_WHEN_QUERY_MENU_ITEM);
    } finally {
      if (session != null) {
        session.close();
      }
    }
  }

}
