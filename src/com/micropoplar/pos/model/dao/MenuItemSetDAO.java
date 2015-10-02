package com.micropoplar.pos.model.dao;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Session;

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
      menuItem = (MenuItemSet) session.merge(menuItem);

      Hibernate.initialize(menuItem.getShifts());
      Hibernate.initialize(menuItem.getItems());

      return menuItem;
    } finally {
      closeSession(session);
    }
  }

}
