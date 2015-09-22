package com.floreantpos.model.dao;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import com.floreantpos.model.Generator;

public abstract class BaseGenDAO extends _RootDAO {

  public static GenDAO instance;

  public static GenDAO getInstance() {
    if (null == instance)
      instance = new GenDAO();
    return instance;
  }

  @Override
  protected Class getReferenceClass() {
    return Generator.class;
  }

  public Order getDefaultOrder() {
    return Order.asc("gen_name");
  }

  public Generator cast(Object object) {
    return (Generator) object;
  }

  public Generator get(java.lang.String key) {
    return (Generator) get(getReferenceClass(), key);
  }

  public Generator get(java.lang.String key, Session s) {
    return (Generator) get(getReferenceClass(), key, s);
  }

  public Generator load(java.lang.String key) {
    return (Generator) load(getReferenceClass(), key);
  }

  public Generator load(java.lang.String key, Session s) {
    return (Generator) load(getReferenceClass(), key, s);
  }

  public Generator loadInitialize(java.lang.String key, Session s) {
    Generator obj = load(key, s);
    if (!Hibernate.isInitialized(obj)) {
      Hibernate.initialize(obj);
    }
    return obj;
  }

  public java.lang.String save(Generator gen) {
    return (java.lang.String) super.save(gen);
  }

  public java.lang.String save(Generator gen, Session s) {
    return (java.lang.String) save((Object) gen, s);
  }

  public void saveOrUpdate(Generator gen) {
    saveOrUpdate((Object) gen);
  }

  public void saveOrUpdate(Generator gen, Session s) {
    saveOrUpdate((Object) gen, s);
  }

  public void update(Generator gen) {
    update((Object) gen);
  }

  public void update(Generator gen, Session s) {
    update((Object) gen, s);
  }

  public void refresh(Generator gen, Session s) {
    refresh((Object) gen, s);
  }

}
