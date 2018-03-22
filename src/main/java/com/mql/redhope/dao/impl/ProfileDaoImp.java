package com.mql.redhope.dao.impl;

import com.mql.redhope.dao.ProfileDao;
import com.mql.redhope.models.Profile;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

public class ProfileDaoImp implements ProfileDao {

  @PersistenceContext
  private EntityManager em;

  @Override
  public Profile findById(Long id) {
    try {
      return em.find(Profile.class, id);
    } catch (Exception e) {
      return null;
    }
  }

  @Override
  public List<Profile> findAll() {
    TypedQuery<Profile> query = em
        .createQuery("SELECT p from Profile p", Profile.class);
    try {
      return query.getResultList();
    } catch (Exception e) {
      return Collections.emptyList();
    }
  }

  @Override
  public void save(Profile value) {
    try {
      Objects.requireNonNull(value);
      em.persist(value);
    } catch (Exception e) {
      // TODO: add logging
      System.out.println("could not save the object");
    }
  }

  @Override
  public Profile delete(Profile value) {
    try {
      Objects.requireNonNull(value);
      em.remove(value);
      return value;
    } catch (Exception e) {
      return null;
    }
  }
}
