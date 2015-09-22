package com.floreantpos.util;

import java.util.Comparator;

/**
 * To hold various kind of comparators.
 * 
 * @author destiny1020
 *
 */
public class POSComparators {

  /**
   * Every entity that want to order by ID's natural order, should implement the IDEntity interface.
   */
  public static final Comparator<? super IDEntity> COMPARATOR_ID = new Comparator<IDEntity>() {

    @Override
    public int compare(IDEntity o1, IDEntity o2) {
      return o1.getId() - o2.getId();
    }
  };

}
