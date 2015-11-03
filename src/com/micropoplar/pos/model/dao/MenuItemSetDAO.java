package com.micropoplar.pos.model.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.floreantpos.POSConstants;
import com.floreantpos.PosException;
import com.floreantpos.model.MenuGroup;
import com.micropoplar.pos.model.MenuItemSet;

public class MenuItemSetDAO extends BaseMenuItemSetDAO {

  /**
   * Default constructor. Can be used in place of getInstance()
   */
  public MenuItemSetDAO() {}

  public MenuItemSet initialize(MenuItemSet menuItem) {
    if (menuItem.getId() == null)
      return menuItem;

    Session session = null;

    try {
      session = createNewSession();
      menuItem = (MenuItemSet) session.get(MenuItemSet.class, menuItem.getId());

      Hibernate.initialize(menuItem.getItems());

      return menuItem;
    } finally {
      closeSession(session);
    }
  }

  @SuppressWarnings("unchecked")
  public List<MenuItemSet> findByParent(MenuGroup group, boolean includeInvisibleItems)
      throws PosException {
    Session session = null;

    try {
      session = getSession();
      Criteria criteria = session.createCriteria(getReferenceClass());
      criteria.add(Restrictions.eq(MenuItemSet.PROP_GROUP, group));

      if (!includeInvisibleItems) {
        criteria.add(Restrictions.eq(MenuItemSet.PROP_VISIBLE, Boolean.TRUE));
      }

      return criteria.list();
    } catch (Exception e) {
      e.printStackTrace();
      throw new PosException(POSConstants.ERROR_WHEN_QUERY_MENU_ITEM_SET);
    } finally {
      if (session != null) {
        session.close();
      }
    }
  }

}
