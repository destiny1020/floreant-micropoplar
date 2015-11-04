package com.floreantpos.model.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;

import com.floreantpos.bo.ui.ComboOption;
import com.floreantpos.model.Customer;
import com.micropoplar.pos.bo.ui.explorer.search.CustomerSearchDto;

public class CustomerDAO extends BaseCustomerDAO {

  /**
   * Default constructor. Can be used in place of getInstance()
   */
  public CustomerDAO() {}

  public Customer findByPhone(String phone) {
    Session session = null;

    try {
      session = getSession();
      Criteria criteria = session.createCriteria(getReferenceClass());
      Disjunction disjunction = Restrictions.disjunction();

      if (StringUtils.isNotEmpty(phone))
        disjunction.add(Restrictions.eq(Customer.PROP_PHONE, phone));

      criteria.add(disjunction);

      return (Customer) criteria.uniqueResult();

    } finally {
      if (session != null) {
        closeSession(session);
      }
    }
  }

  @SuppressWarnings("unchecked")
  public List<Customer> findBy(String phone, String name) {
    Session session = null;

    try {
      session = getSession();
      Criteria criteria = session.createCriteria(getReferenceClass());
      Disjunction disjunction = Restrictions.disjunction();

      if (StringUtils.isNotEmpty(phone))
        disjunction.add(Restrictions.like(Customer.PROP_PHONE, "%" + phone + "%"));

      if (StringUtils.isNotEmpty(name))
        disjunction.add(Restrictions.like(Customer.PROP_NAME, "%" + name + "%"));

      criteria.add(disjunction);

      return criteria.list();

    } finally {
      if (session != null) {
        closeSession(session);
      }
    }

  }

  @SuppressWarnings("unchecked")
  public List<Customer> findCustomers(CustomerSearchDto searchDto) {
    Session session = null;
    try {
      session = getSession();
      Criteria criteria = session.createCriteria(getReferenceClass());

      String phone = searchDto.getPhone();
      if (StringUtils.isNotBlank(phone)) {
        criteria.add(Restrictions.like(Customer.PROP_PHONE, "%" + phone + "%"));
      }

      if (searchDto.getCreateTimeStart() != null) {
        criteria.add(Restrictions.ge(Customer.PROP_CREATE_TIME, searchDto.getCreateTimeStart()));
      }

      if (searchDto.getCreateTimeEnd() != null) {
        criteria.add(Restrictions.le(Customer.PROP_CREATE_TIME, searchDto.getCreateTimeEnd()));
      }

      if (searchDto.getLastActiveTimeStart() != null) {
        criteria.add(
            Restrictions.ge(Customer.PROP_LAST_ACTIVE_TIME, searchDto.getLastActiveTimeStart()));
      }

      if (searchDto.getLastActiveTimeEnd() != null) {
        criteria
            .add(Restrictions.le(Customer.PROP_LAST_ACTIVE_TIME, searchDto.getLastActiveTimeEnd()));
      }

      ComboOption gender = searchDto.getGender();
      if (gender.getValue() != CustomerSearchDto.CUSTOMER_GENDER_ALL) {
        criteria.add(Restrictions.eq(Customer.PROP_GENDER, gender.getValue()));
      }

      ComboOption ageRange = searchDto.getAgeRange();
      if (ageRange.getValue() != CustomerSearchDto.CUSTOMER_AGE_RANGE_ALL) {
        criteria.add(Restrictions.eq(Customer.PROP_AGE_RANGE, ageRange.getValue()));
      }

      return criteria.list();
    } finally {
      closeSession(session);
    }
  }

}
