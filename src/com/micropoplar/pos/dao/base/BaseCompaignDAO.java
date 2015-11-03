package com.micropoplar.pos.dao.base;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import com.floreantpos.model.dao._RootDAO;
import com.micropoplar.pos.dao.CompaignDAO;
import com.micropoplar.pos.model.Compaign;

public class BaseCompaignDAO extends _RootDAO {

  //query name references

  public static CompaignDAO instance;

  /**
  * Return a singleton of the DAO
  */
  public static CompaignDAO getInstance() {
    if (null == instance)
      instance = new CompaignDAO();
    return instance;
  }

  @Override
  public Class getReferenceClass() {
    return Compaign.class;
  }

  @Override
  public Order getDefaultOrder() {
    return Order.asc("name");
  }

  /**
  * Cast the object as a com.micropoplar.pos.model.Compaign
  */
  public com.micropoplar.pos.model.Compaign cast(Object object) {
    return (com.micropoplar.pos.model.Compaign) object;
  }

  public com.micropoplar.pos.model.Compaign get(java.lang.Integer key)
      throws org.hibernate.HibernateException {
    return (com.micropoplar.pos.model.Compaign) get(getReferenceClass(), key);
  }

  public com.micropoplar.pos.model.Compaign get(java.lang.Integer key, Session s)
      throws org.hibernate.HibernateException {
    return (com.micropoplar.pos.model.Compaign) get(getReferenceClass(), key, s);
  }

  public com.micropoplar.pos.model.Compaign load(java.lang.Integer key)
      throws org.hibernate.HibernateException {
    return (com.micropoplar.pos.model.Compaign) load(getReferenceClass(), key);
  }

  public com.micropoplar.pos.model.Compaign load(java.lang.Integer key, Session s)
      throws org.hibernate.HibernateException {
    return (com.micropoplar.pos.model.Compaign) load(getReferenceClass(), key, s);
  }

  public com.micropoplar.pos.model.Compaign loadInitialize(java.lang.Integer key, Session s)
      throws org.hibernate.HibernateException {
    com.micropoplar.pos.model.Compaign obj = load(key, s);
    if (!Hibernate.isInitialized(obj)) {
      Hibernate.initialize(obj);
    }
    return obj;
  }

  /* Generic methods */

  /**
  * Return all objects related to the implementation of this DAO with no filter.
  */
  @Override
  public java.util.List<com.micropoplar.pos.model.Compaign> findAll() {
    return super.findAll();
  }

  /**
  * Return all objects related to the implementation of this DAO with no filter.
  */
  @Override
  public java.util.List<com.micropoplar.pos.model.Compaign> findAll(Order defaultOrder) {
    return super.findAll(defaultOrder);
  }

  /**
  * Return all objects related to the implementation of this DAO with no filter. Use the session
  * given.
  * 
  * @param s the Session
  */
  @Override
  public java.util.List<com.micropoplar.pos.model.Compaign> findAll(Session s, Order defaultOrder) {
    return super.findAll(s, defaultOrder);
  }

  /**
  * Persist the given transient instance, first assigning a generated identifier. (Or using the
  * current value of the identifier property if the assigned generator is used.)
  * 
  * @param compaign a transient instance of a persistent class
  * @return the class identifier
  */
  public java.lang.Integer save(com.micropoplar.pos.model.Compaign compaign)
      throws org.hibernate.HibernateException {
    return (java.lang.Integer) super.save(compaign);
  }

  /**
  * Persist the given transient instance, first assigning a generated identifier. (Or using the
  * current value of the identifier property if the assigned generator is used.) Use the Session
  * given.
  * 
  * @param compaign a transient instance of a persistent class
  * @param s the Session
  * @return the class identifier
  */
  public java.lang.Integer save(com.micropoplar.pos.model.Compaign compaign, Session s)
      throws org.hibernate.HibernateException {
    return (java.lang.Integer) save((Object) compaign, s);
  }

  /**
  * Either save() or update() the given instance, depending upon the value of its identifier
  * property. By default the instance is always saved. This behaviour may be adjusted by specifying
  * an unsaved-value attribute of the identifier property mapping.
  * 
  * @param compaign a transient instance containing new or updated state
  */
  public void saveOrUpdate(com.micropoplar.pos.model.Compaign compaign)
      throws org.hibernate.HibernateException {
    saveOrUpdate((Object) compaign);
  }

  /**
  * Either save() or update() the given instance, depending upon the value of its identifier
  * property. By default the instance is always saved. This behaviour may be adjusted by specifying
  * an unsaved-value attribute of the identifier property mapping. Use the Session given.
  * 
  * @param compaign a transient instance containing new or updated state.
  * @param s the Session.
  */
  public void saveOrUpdate(com.micropoplar.pos.model.Compaign compaign, Session s)
      throws org.hibernate.HibernateException {
    saveOrUpdate((Object) compaign, s);
  }

  /**
  * Update the persistent state associated with the given identifier. An exception is thrown if
  * there is a persistent instance with the same identifier in the current session.
  * 
  * @param compaign a transient instance containing updated state
  */
  public void update(com.micropoplar.pos.model.Compaign compaign)
      throws org.hibernate.HibernateException {
    update((Object) compaign);
  }

  /**
  * Update the persistent state associated with the given identifier. An exception is thrown if
  * there is a persistent instance with the same identifier in the current session. Use the Session
  * given.
  * 
  * @param compaign a transient instance containing updated state
  * @param the Session
  */
  public void update(com.micropoplar.pos.model.Compaign compaign, Session s)
      throws org.hibernate.HibernateException {
    update((Object) compaign, s);
  }

  /**
  * Remove a persistent instance from the datastore. The argument may be an instance associated
  * with the receiving Session or a transient instance with an identifier associated with existing
  * persistent state.
  * 
  * @param id the instance ID to be removed
  */
  public void delete(java.lang.Integer id) throws org.hibernate.HibernateException {
    delete((Object) load(id));
  }

  /**
  * Remove a persistent instance from the datastore. The argument may be an instance associated
  * with the receiving Session or a transient instance with an identifier associated with existing
  * persistent state. Use the Session given.
  * 
  * @param id the instance ID to be removed
  * @param s the Session
  */
  public void delete(java.lang.Integer id, Session s) throws org.hibernate.HibernateException {
    delete((Object) load(id, s), s);
  }

  /**
  * Remove a persistent instance from the datastore. The argument may be an instance associated
  * with the receiving Session or a transient instance with an identifier associated with existing
  * persistent state.
  * 
  * @param compaign the instance to be removed
  */
  public void delete(com.micropoplar.pos.model.Compaign compaign)
      throws org.hibernate.HibernateException {
    delete((Object) compaign);
  }

  /**
  * Remove a persistent instance from the datastore. The argument may be an instance associated
  * with the receiving Session or a transient instance with an identifier associated with existing
  * persistent state. Use the Session given.
  * 
  * @param compaign the instance to be removed
  * @param s the Session
  */
  public void delete(com.micropoplar.pos.model.Compaign compaign, Session s)
      throws org.hibernate.HibernateException {
    delete((Object) compaign, s);
  }

  /**
  * Re-read the state of the given instance from the underlying database. It is inadvisable to use
  * this to implement long-running sessions that span many business tasks. This method is, however,
  * useful in certain special circumstances. For example
  * <ul>
  * <li>where a database trigger alters the object state upon insert or update</li>
  * <li>after executing direct SQL (eg. a mass update) in the same session</li>
  * <li>after inserting a Blob or Clob</li>
  * </ul>
  */
  public void refresh(com.micropoplar.pos.model.Compaign compaign, Session s)
      throws org.hibernate.HibernateException {
    refresh((Object) compaign, s);
  }

}
