package com.mql.redhope.dao;

import java.util.List;
import java.util.function.Predicate;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.mql.redhope.models.User;

public class UserDaoImp implements UserDao {

  @PersistenceContext
  private EntityManager em;

  @Override
  public User findById(Long id) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<User> findAll() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void save(User value) {
     em.persist(value);
  }

  @Override
  public User delete(User value) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<User> findByCriteria(Predicate<User> p) {
    // TODO Auto-generated method stub
    return null;
  }

}
