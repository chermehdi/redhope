package com.mql.redhope.dao.impl;

import com.mql.redhope.dao.UserDao;
import com.mql.redhope.models.User;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

public class UserDaoImp implements UserDao {

  @PersistenceContext
  private EntityManager em;

  @Override
  public User findById(Long id) {
    String queryString = "SELECT u FROM User u WHERE u.id=:id";
    try {
      TypedQuery<User> query = em.createQuery(queryString, User.class);
      query.setParameter("id", id);
      return query.getSingleResult();
    } catch (Exception e) {
      return null;
    }
  }

  @Override
  public List<User> findAll() {
    String queryString = "SELECT u FROM User u";
    try {
      TypedQuery<User> query = (TypedQuery<User>) em.createQuery(queryString, User.class);
      return query.getResultList();
    } catch (Exception e) {
      return Collections.emptyList();
    }
  }

  @Override
  public void save(User value) {
    try {
      em.persist(value);
    } catch (Exception e) {
      // TODO: refactor this, add logging
      System.out.println("could save the object");
    }
  }

  @Override
  public User delete(User value) {
    Objects.requireNonNull(value);
    try {
      em.remove(value);
      return value;
    } catch (Exception e) {
      return null;
    }
  }

  @Override
  public User findByEmail(String email) {
    TypedQuery<User> query =
        em.createQuery("SELECT u FROM User u WHERE u.email=:email", User.class);
    query.setParameter("email", email);
    try {
      return query.getSingleResult();
    } catch (Exception e) {
      return null;
    }
  }

}
