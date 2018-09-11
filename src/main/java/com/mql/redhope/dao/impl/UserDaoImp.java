package com.mql.redhope.dao.impl;

import com.mql.redhope.dao.UserDao;
import com.mql.redhope.domain.models.User;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class UserDaoImp implements UserDao {

  @PersistenceContext
  private EntityManager em;

  Logger log = LoggerFactory.getLogger(getClass());

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
      TypedQuery<User> query = em.createQuery(queryString, User.class);
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
      log.info("Could not update the user object " + e.getMessage());
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

  @Override
  public User findByToken(String token) {
    try {
      TypedQuery<User> query = em
          .createQuery("SELECT u from User u WHERE u.token.value=:token", User.class);
      query.setParameter("token", token);
      User user = query.getSingleResult();
      return user;
    } catch (Exception e) {
      System.out.println("could not find the user identified by token " + token);
      return null;
    }
  }

  @Override
  public User update(User user) {
    try {
      log.info("before user update " + user);
      em.merge(user);
      log.info("after user update " + user);
      return user;
    } catch (Exception e) {
      log.info("could not update the user " + user + " "  + e.getMessage());
      return null;
    }
  }

  @Override
  public User findAndUpdateByToken(String token) {
    User user = findByToken(token);
    user.setActive(true);
    em.merge(user);
    return user;
  }

}
