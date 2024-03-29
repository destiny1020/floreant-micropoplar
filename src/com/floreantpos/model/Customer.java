package com.floreantpos.model;

import java.util.HashMap;

import com.floreantpos.model.base.BaseCustomer;
import com.floreantpos.util.IDEntity;



public class Customer extends BaseCustomer implements IDEntity {
  private static final long serialVersionUID = 1L;

  /* [CONSTRUCTOR MARKER BEGIN] */
  public Customer() {
    super();
  }

  /**
   * Constructor for primary key
   */
  public Customer(java.lang.Integer id) {
    super(id);
  }

  /* [CONSTRUCTOR MARKER END] */

  public void addProperty(String name, String value) {
    if (getProperties() == null) {
      setProperties(new HashMap<String, String>());
    }

    getProperties().put(name, value);
  }

  public boolean hasProperty(String key) {
    return getProperty(key) != null;
  }

  public String getProperty(String key) {
    if (getProperties() == null) {
      return null;
    }

    return getProperties().get(key);
  }

  @Override
  public String toString() {
    String name = getName();
    return name;
  }

}
