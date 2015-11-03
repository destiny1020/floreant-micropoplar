package com.floreantpos.model.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.floreantpos.bo.ui.ComboOption;
import com.floreantpos.bo.ui.explorer.search.TicketSearchDto;
import com.floreantpos.main.Application;
import com.floreantpos.model.PaymentType;
import com.floreantpos.model.Shift;
import com.floreantpos.model.Terminal;
import com.floreantpos.model.Ticket;
import com.floreantpos.model.TicketType;
import com.floreantpos.model.TransactionType;
import com.floreantpos.model.User;
import com.floreantpos.model.VoidTransaction;
import com.floreantpos.model.util.TicketSummary;
import com.floreantpos.services.PosTransactionService;

public class TicketDAO extends BaseTicketDAO {
  private final static TicketDAO instance = new TicketDAO();

  /**
   * Default constructor. Can be used in place of getInstance()
   */
  public TicketDAO() {}

  @Override
  public void saveOrUpdate(Ticket ticket) {
    // TODO: move refresh to invokers
    // if(ticket.getId() != null &&
    // StringUtils.isNotBlank(ticket.getUniqId())) {
    // refresh(ticket);
    // }

    ticket.setActiveDate(Calendar.getInstance().getTime());

    super.saveOrUpdate(ticket);
  }

  @Override
  public void saveOrUpdate(Ticket ticket, Session s) {
    ticket.setActiveDate(Calendar.getInstance().getTime());

    super.saveOrUpdate(ticket, s);
  }

  /**
   * simple wrapper over loadFullTicket(int id) overload
   * 
   * @param uniqId
   * @return
   */
  public Ticket loadFullTicket(String uniqId) {
    return loadFullTicket(findByUniqId(uniqId).getId());
  }

  public Ticket loadFullTicket(int id) {
    Session session = createNewSession();

    Ticket ticket = (Ticket) session.get(getReferenceClass(), id);

    if (ticket == null)
      return null;

    // to prevent: [org.hibernate.HibernateException: collection is not associated with any session]
    session.refresh(ticket);

    Hibernate.initialize(ticket.getTicketItems());
    Hibernate.initialize(ticket.getCoupons());
    Hibernate.initialize(ticket.getTransactions());

    session.close();

    return ticket;
  }

  public Ticket loadCouponsAndTransactions(int id) {
    Session session = createNewSession();

    Ticket ticket = (Ticket) session.get(getReferenceClass(), id);

    Hibernate.initialize(ticket.getCoupons());
    Hibernate.initialize(ticket.getTransactions());

    session.close();

    return ticket;
  }

  public void voidTicket(Ticket ticket) throws Exception {
    Session session = null;
    Transaction tx = null;

    try {
      session = createNewSession();
      tx = session.beginTransaction();

      Terminal terminal = Application.getInstance().getTerminal();

      ticket.setVoided(true);
      ticket.setClosed(true);
      ticket.setClosingDate(new Date());
      ticket.setTerminal(terminal);

      if (ticket.isPaid()) {
        VoidTransaction transaction = new VoidTransaction();
        transaction.setTicket(ticket);
        transaction.setTerminal(terminal);
        transaction.setTransactionTime(new Date());
        transaction.setTransactionType(TransactionType.DEBIT.name());
        transaction.setPaymentType(PaymentType.CASH.getType());
        transaction.setAmount(ticket.getPaidAmount());
        transaction.setTerminal(Application.getInstance().getTerminal());
        transaction.setCaptured(true);

        PosTransactionService.adjustTerminalBalance(transaction);

        ticket.addTotransactions(transaction);
      }

      session.update(ticket);
      session.update(terminal);

      session.flush();
      tx.commit();
    } catch (Exception x) {
      try {
        tx.rollback();
      } catch (Exception e) {
      }
      throw x;
    }

    finally {
      closeSession(session);
    }
  }

  public Ticket findByUniqId(String uniqId) {
    Session session = null;

    try {
      session = getSession();
      Criteria criteria = session.createCriteria(getReferenceClass());
      criteria.add(Restrictions.eq(Ticket.PROP_UNIQ_ID, uniqId));
      List list = criteria.list();
      if (list != null && list.size() == 1) {
        return (Ticket) list.get(0);
      } else {
        if (list != null && list.size() == 0) {
          return null;
        } else if (list != null && list.size() > 1) {
          throw new RuntimeException("订单唯一标识符: " + uniqId + " 对应着不止一条订单 !");
        }
      }
    } finally {
      closeSession(session);
    }

    return null;
  }

  public List<Ticket> findOpenTickets() {
    Session session = null;

    try {
      session = getSession();
      Criteria criteria = session.createCriteria(getReferenceClass());
      criteria.add(Restrictions.eq(Ticket.PROP_CLOSED, Boolean.FALSE));
      List list = criteria.list();
      return list;
    } finally {
      closeSession(session);
    }
  }

  public List<Ticket> findOpenTicketsForUser(User user) {
    Session session = null;

    try {
      session = getSession();
      Criteria criteria = session.createCriteria(getReferenceClass());
      criteria.add(Restrictions.eq(Ticket.PROP_CLOSED, Boolean.FALSE));
      // criteria.add(Restrictions.eq(Ticket.PROP_VOIDED, Boolean.FALSE));
      // criteria.add(Restrictions.eq(Ticket.PROP_REFUNDED, Boolean.FALSE));
      criteria.add(Restrictions.eq(Ticket.PROP_OWNER, user));
      List list = criteria.list();
      return list;
    } finally {
      closeSession(session);
    }
  }

  public List<Ticket> findOpenTickets(Date startDate, Date endDate) {
    Session session = null;

    try {
      session = getSession();
      Criteria criteria = session.createCriteria(getReferenceClass());
      criteria.add(Restrictions.eq(Ticket.PROP_CLOSED, Boolean.FALSE));
      criteria.add(Restrictions.ge(Ticket.PROP_CREATE_DATE, startDate));
      criteria.add(Restrictions.le(Ticket.PROP_CREATE_DATE, endDate));
      List list = criteria.list();
      return list;
    } finally {
      closeSession(session);
    }
  }

  // public Ticket findTicketByTableNumber(int tableNumber) {
  // Session session = null;
  //
  // try {
  // session = getSession();
  // Criteria criteria = session.createCriteria(getReferenceClass());
  // criteria.add(Restrictions.eq(Ticket.PROP_CLOSED, Boolean.FALSE));
  // criteria.add(Restrictions.eq(Ticket.PROP_TABLE_NUMBER, Integer.valueOf(tableNumber)));
  //
  // List list = criteria.list();
  // if(list.size() <= 0) {
  // return null;
  // }
  //
  // return (Ticket) list.get(0);
  // } finally {
  // closeSession(session);
  // }
  // }

  // public boolean hasTicketByTableNumber(int tableNumber) {
  // return findTicketByTableNumber(tableNumber) != null;
  // }

  public TicketSummary getOpenTicketSummary() {
    Session session = null;
    TicketSummary ticketSummary = new TicketSummary();
    try {
      session = getSession();
      Criteria criteria = session.createCriteria(Ticket.class);
      criteria.add(Restrictions.eq(Ticket.PROP_CLOSED, Boolean.FALSE));
      criteria.add(Restrictions.eq(Ticket.PROP_VOIDED, Boolean.FALSE));
      criteria.add(Restrictions.eq(Ticket.PROP_REFUNDED, Boolean.FALSE));

      ProjectionList projectionList = Projections.projectionList();
      projectionList.add(Projections.count(Ticket.PROP_ID));
      projectionList.add(Projections.sum(Ticket.PROP_TOTAL_AMOUNT));
      criteria.setProjection(projectionList);

      List list = criteria.list();
      if (list.size() > 0) {
        Object[] o = (Object[]) list.get(0);
        ticketSummary.setTotalTicket(((Integer) o[0]).intValue());
        ticketSummary.setTotalPrice(o[1] == null ? 0 : ((Double) o[1]).doubleValue());
      }
      return ticketSummary;
    } finally {
      closeSession(session);
    }
  }

  public TicketSummary getClosedTicketSummary(Terminal terminal) {

    Session session = null;
    TicketSummary ticketSummary = new TicketSummary();
    try {
      session = getSession();
      Criteria criteria = session.createCriteria(Ticket.class);
      criteria.add(Restrictions.eq(Ticket.PROP_CLOSED, Boolean.TRUE));
      criteria.add(Restrictions.eq(Ticket.PROP_VOIDED, Boolean.FALSE));
      criteria.add(Restrictions.eq(Ticket.PROP_REFUNDED, Boolean.FALSE));
      criteria.add(Restrictions.eq(Ticket.PROP_TERMINAL, terminal));

      ProjectionList projectionList = Projections.projectionList();
      projectionList.add(Projections.count(Ticket.PROP_ID));
      projectionList.add(Projections.sum(Ticket.PROP_TOTAL_AMOUNT));
      criteria.setProjection(projectionList);

      List list = criteria.list();
      if (list.size() > 0) {
        Object[] o = (Object[]) list.get(0);
        ticketSummary.setTotalTicket(((Integer) o[0]).intValue());
        ticketSummary.setTotalPrice(o[1] == null ? 0 : ((Double) o[1]).doubleValue());
      }
      return ticketSummary;
    } finally {
      closeSession(session);
    }
  }

  public List<Ticket> findTickets(Date startDate, Date endDate) {
    Session session = null;
    try {
      session = getSession();
      Criteria criteria = session.createCriteria(getReferenceClass());
      criteria.add(Restrictions.ge(Ticket.PROP_CREATE_DATE, startDate));
      criteria.add(Restrictions.le(Ticket.PROP_CREATE_DATE, endDate));
      criteria.add(Restrictions.eq(Ticket.PROP_VOIDED, Boolean.FALSE));
      criteria.add(Restrictions.eq(Ticket.PROP_REFUNDED, Boolean.FALSE));

      return criteria.list();
    } finally {
      closeSession(session);
    }
  }

  public List<Ticket> findTickets(TicketSearchDto searchDto) {
    Session session = null;
    try {
      session = getSession();
      Criteria criteria = session.createCriteria(getReferenceClass());

      String uniqIdOrPhone = searchDto.getUniqIdOrPhone();
      if (StringUtils.isNotBlank(uniqIdOrPhone)) {
        criteria
            .add(Restrictions.or(Restrictions.like(Ticket.PROP_UNIQ_ID, "%" + uniqIdOrPhone + "%"),
                Restrictions.like(Ticket.PROP_CUSTOMER_PHONE, "%" + uniqIdOrPhone + "%")));
      }

      if (searchDto.getStartDate() != null) {
        criteria.add(Restrictions.ge(Ticket.PROP_CREATE_DATE, searchDto.getStartDate()));
      }
      if (searchDto.getEndDate() != null) {
        criteria.add(Restrictions.le(Ticket.PROP_CREATE_DATE, searchDto.getEndDate()));
      }


      if (!searchDto.isAllTickets()) {
        Disjunction disjunction = Restrictions.disjunction();
        disjunction.add(Restrictions.eq(Ticket.PROP_PAID, searchDto.isPaidTickets()));
        if (searchDto.isVoidedTickets()) {
          disjunction.add(Restrictions.eq(Ticket.PROP_VOIDED, searchDto.isVoidedTickets()));
        }
        if (searchDto.isRefundedTickets()) {
          disjunction.add(Restrictions.eq(Ticket.PROP_REFUNDED, searchDto.isRefundedTickets()));
        }
        criteria.add(disjunction);
      }

      ComboOption ticketType = searchDto.getTicketType();
      if (ticketType.getValue() != TicketSearchDto.TICKET_TYPE_ALL) {
        TicketType type = TicketType.fromDisplayName(ticketType.getLabel());
        criteria.add(Restrictions.eq(Ticket.PROP_TICKET_TYPE, type.name()));
      }

      ComboOption membershipType = searchDto.getMembershipType();
      if (membershipType.getValue() != TicketSearchDto.MEMBERSHIP_ALL) {
        if (membershipType.getValue() == TicketSearchDto.MEMBERSHIP_MEMBER) {
          criteria.add(Restrictions.isNotNull(Ticket.PROP_CUSTOMER));
        } else if (membershipType.getValue() == TicketSearchDto.MEMBERSHIP_NON_MEMBER) {
          criteria.add(Restrictions.isNull(Ticket.PROP_CUSTOMER));
        }
      }

      ComboOption paymentType = searchDto.getPaymentType();
      if (paymentType.getValue() != TicketSearchDto.PAYMENT_TYPE_ALL) {
        criteria
            .add(Restrictions.like(Ticket.PROP_PAYMENT_TYPE, "%" + paymentType.getLabel() + "%"));
      }

      return criteria.list();
    } finally {
      closeSession(session);
    }
  }

  public List<Ticket> findTicketsForLaborHour(Date startDate, Date endDate, int hour,
      String userType, Terminal terminal) {
    Session session = null;
    try {
      session = getSession();
      Criteria criteria = session.createCriteria(getReferenceClass());
      criteria.add(Restrictions.ge(Ticket.PROP_ACTIVE_DATE, startDate));
      criteria.add(Restrictions.le(Ticket.PROP_ACTIVE_DATE, endDate));
      criteria.add(Restrictions.eq(Ticket.PROP_CREATION_HOUR, Integer.valueOf(hour)));
      // criteria.add(Restrictions.eq(Ticket.PROP_CLOSED, Boolean.TRUE));
      // criteria.add(Restrictions.eq(Ticket.PROP_VOIDED, Boolean.FALSE));

      if (userType != null) {
        criteria.createAlias(Ticket.PROP_OWNER, "u");
        criteria.add(Restrictions.eq("u.type", userType));
      }
      if (terminal != null) {
        criteria.add(Restrictions.eq(Ticket.PROP_TERMINAL, terminal));
      }

      return criteria.list();
    } finally {
      closeSession(session);
    }
  }

  public List<Ticket> findTicketsForShift(Date startDate, Date endDate, Shift shit, String userType,
      Terminal terminal) {
    Session session = null;
    try {
      session = getSession();
      Criteria criteria = session.createCriteria(getReferenceClass());
      criteria.add(Restrictions.ge(Ticket.PROP_CREATE_DATE, startDate));
      criteria.add(Restrictions.le(Ticket.PROP_CREATE_DATE, endDate));
      criteria.add(Restrictions.eq(Ticket.PROP_CLOSED, Boolean.TRUE));
      criteria.add(Restrictions.eq(Ticket.PROP_VOIDED, Boolean.FALSE));
      criteria.add(Restrictions.eq(Ticket.PROP_REFUNDED, Boolean.FALSE));

      if (userType != null) {
        criteria.createAlias(Ticket.PROP_OWNER, "u");
        criteria.add(Restrictions.eq("u.type", userType));
      }
      if (terminal != null) {
        criteria.add(Restrictions.eq(Ticket.PROP_TERMINAL, terminal));
      }

      return criteria.list();
    } finally {
      closeSession(session);
    }
  }

  public static TicketDAO getInstance() {
    return instance;
  }

}
