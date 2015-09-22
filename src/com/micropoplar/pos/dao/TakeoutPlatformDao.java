package com.micropoplar.pos.dao;

import org.hibernate.Hibernate;
import org.hibernate.Session;

import com.floreantpos.model.dao._RootDAO;
import com.micropoplar.pos.model.TakeoutPlatform;

public class TakeoutPlatformDao extends _RootDAO {

  public static TakeoutPlatformDao instance;

  public static TakeoutPlatformDao getInstance() {
    if (null == instance)
      instance = new TakeoutPlatformDao();
    return instance;
  }

  @Override
  protected Class getReferenceClass() {
    return TakeoutPlatform.class;
  }

  public TakeoutPlatform cast(Object object) {
    return (TakeoutPlatform) object;
  }

  public TakeoutPlatform get(java.lang.String key) {
    return (TakeoutPlatform) get(getReferenceClass(), key);
  }

  public TakeoutPlatform get(java.lang.String key, Session s) {
    return (TakeoutPlatform) get(getReferenceClass(), key, s);
  }

  public TakeoutPlatform load(java.lang.String key) {
    return (TakeoutPlatform) load(getReferenceClass(), key);
  }

  public TakeoutPlatform load(java.lang.String key, Session s) {
    return (TakeoutPlatform) load(getReferenceClass(), key, s);
  }

  public TakeoutPlatform loadInitialize(java.lang.String key, Session s) {
    TakeoutPlatform obj = load(key, s);
    if (!Hibernate.isInitialized(obj)) {
      Hibernate.initialize(obj);
    }
    return obj;
  }

  public java.lang.String save(TakeoutPlatform gen) {
    return (java.lang.String) super.save(gen);
  }

  public java.lang.String save(TakeoutPlatform gen, Session s) {
    return (java.lang.String) save((Object) gen, s);
  }

  public void saveOrUpdate(TakeoutPlatform gen) {
    saveOrUpdate((Object) gen);
  }

  public void saveOrUpdate(TakeoutPlatform gen, Session s) {
    saveOrUpdate((Object) gen, s);
  }

  public void update(TakeoutPlatform gen) {
    update((Object) gen);
  }

  public void update(TakeoutPlatform gen, Session s) {
    update((Object) gen, s);
  }

  public void refresh(TakeoutPlatform gen, Session s) {
    refresh((Object) gen, s);
  }

}
