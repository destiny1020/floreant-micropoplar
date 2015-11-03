package com.floreantpos.model.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.floreantpos.model.Coupon;


public class CouponDAO extends BaseCouponDAO {

  /**
   * Default constructor. Can be used in place of getInstance()
   */
  public CouponDAO() {}

  public List<Coupon> getValidCoupons() {
    Session session = null;

    Date currentDate = new Date();

    try {
      session = createNewSession();
      Criteria criteria = session.createCriteria(getReferenceClass());
      criteria.add(Restrictions.eq(Coupon.PROP_DISABLED, Boolean.FALSE));
      criteria
          .add(Restrictions.or(Restrictions.eq(Coupon.PROP_NEVER_EXPIRE, Boolean.TRUE),
              Restrictions.ge(Coupon.PROP_EXPIRY_DATE, currentDate)));
      return criteria.list();
    } finally {
      closeSession(session);
    }

  }


}
