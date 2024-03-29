package com.floreantpos.model.dao;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.floreantpos.PosException;
import com.floreantpos.model.AttendenceHistory;
import com.floreantpos.model.Shift;
import com.floreantpos.model.Terminal;
import com.floreantpos.model.Ticket;
import com.floreantpos.model.User;
import com.floreantpos.util.UserNotFoundException;

public class UserDAO extends BaseUserDAO {
  public final static UserDAO instance = new UserDAO();

  /**
   * Default constructor. Can be used in place of getInstance()
   */
  public UserDAO() {}

  public List<User> findDrivers() {
    Session session = null;

    try {
      session = getSession();
      Criteria criteria = session.createCriteria(getReferenceClass());
      criteria.add(Restrictions.eq(User.PROP_DRIVER, Boolean.TRUE));

      return criteria.list();
    } finally {
      if (session != null) {
        closeSession(session);
      }
    }
  }

  public User findUser(String id) {
    Session session = null;

    try {
      session = getSession();
      Criteria criteria = session.createCriteria(getReferenceClass());
      criteria.add(Restrictions.eq(User.PROP_USER_ID, id));

      Object result = criteria.uniqueResult();
      if (result != null) {
        return (User) result;
      } else {
        // TODO: externalize string
        throw new UserNotFoundException("用户ID: " + id + " 不存在");
      }
    } finally {
      if (session != null) {
        closeSession(session);
      }
    }
  }

  public User findUserBySecretKey(String username, String secretKey) {
    Session session = null;

    try {

      session = getSession();
      Criteria criteria = session.createCriteria(getReferenceClass());
      criteria.add(Restrictions.eq(User.PROP_USER_ID, username));
      criteria.add(Restrictions.eq(User.PROP_PASSWORD, secretKey));

      Object result = criteria.uniqueResult();
      return (User) result;
    } finally {
      if (session != null) {
        closeSession(session);
      }
    }
  }

  public boolean isUserExist(String id) {
    try {
      User user = findUser(id);

      return user != null;

    } catch (UserNotFoundException x) {
      return false;
    }
  }

  public Integer findUserWithMaxId() {
    Session session = null;

    try {
      session = getSession();
      Criteria criteria = session.createCriteria(getReferenceClass());
      criteria.setProjection(Projections.max(User.PROP_USER_ID));

      List list = criteria.list();
      if (list != null && list.size() > 0) {
        return (Integer) list.get(0);
      }

      return null;
    } finally {
      if (session != null) {
        closeSession(session);
      }
    }
  }

  public List<User> getClockedInUser(Terminal terminal) {
    Session session = null;

    try {
      session = getSession();
      Criteria criteria = session.createCriteria(getReferenceClass());
      criteria.add(Restrictions.eq(User.PROP_CLOCKED_IN, Boolean.TRUE));
      criteria.add(Restrictions.eq(User.PROP_CURRENT_TERMINAL, terminal));

      return criteria.list();
    } finally {
      if (session != null) {
        closeSession(session);
      }
    }

  }

  public void saveClockIn(User user, AttendenceHistory attendenceHistory, Shift shift,
      Calendar currentTime) {
    Session session = null;
    Transaction tx = null;

    try {
      session = getSession();
      tx = session.beginTransaction();

      session.saveOrUpdate(user);
      session.saveOrUpdate(attendenceHistory);

      tx.commit();
    } catch (Exception e) {
      e.printStackTrace();

      if (tx != null) {
        try {
          tx.rollback();
        } catch (Exception x) {
        }
      }
      // TODO: find why such exception throws when enters a new day
      throw new PosException("Unable to store clock in information", e);

    } finally {
      if (session != null) {
        closeSession(session);
      }
    }
  }

  public User saveClockOut(User user, AttendenceHistory attendenceHistory, Shift shift,
      Calendar currentTime) {
    Session session = null;
    Transaction tx = null;

    try {
      session = getSession();
      tx = session.beginTransaction();

      session.saveOrUpdate(user);
      session.saveOrUpdate(attendenceHistory);

      tx.commit();
      session.refresh(user);

      return user;
    } catch (Exception e) {
      if (tx != null) {
        try {
          tx.rollback();
        } catch (Exception x) {
        }
      }
      throw new PosException("Unable to store clock out information", e);

    } finally {
      if (session != null) {
        closeSession(session);
      }
    }
  }

  private boolean validate(User user, boolean editMode) throws PosException {
    String hql = "from User u where u.userId=:userId and u.type=:userType";

    Session session = getSession();
    Query query = session.createQuery(hql);
    query = query.setParameter("userId", user.getUserId());
    query = query.setParameter("userType", user.getType());

    if (query.list().size() > 0) {
      throw new PosException("该用户ID已经被使用了");
    }

    return true;
  }

  public void saveOrUpdate(User user, boolean editMode) {
    Session session = null;

    try {
      if (!editMode) {
        validate(user, editMode);
      }
      super.saveOrUpdate(user);
    } catch (Exception x) {
      throw new PosException("无法保存用户", x);
    } finally {
      closeSession(session);
    }

  }

  public int findNumberOfOpenTickets(User user) throws PosException {
    Session session = null;
    Transaction tx = null;

    String hql = "select count(*) from Ticket ticket where ticket.owner=:owner and ticket."
        + Ticket.PROP_CLOSED + "settled=false";
    int count = 0;
    try {
      session = getSession();
      tx = session.beginTransaction();
      Query query = session.createQuery(hql);
      query = query.setEntity("owner", user);
      Iterator iterator = query.iterate();
      if (iterator.hasNext()) {
        count = ((Integer) iterator.next()).intValue();
      }
      tx.commit();
      return count;
    } catch (Exception e) {
      try {
        if (tx != null) {
          tx.rollback();
        }
      } catch (Exception e2) {
      }
      throw new PosException("无法找到用户", e);
    } finally {
      if (session != null) {
        session.close();
      }
    }
  }

}
