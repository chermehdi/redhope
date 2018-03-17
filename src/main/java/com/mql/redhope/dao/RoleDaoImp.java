package com.mql.redhope.dao;

import java.util.List;
import java.util.function.Predicate;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.mql.redhope.models.Role;

public class RoleDaoImp implements RoleDao {
  
  @PersistenceContext
  private EntityManager em;

  @Override
  public Role findById(Long id) {
    return em.find(Role.class, id);
  }

  @Override
  public List<Role> findAll() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void save(Role value) {
   em.persist(value);
  }

  @Override
  public Role delete(Role value) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Role> findByCriteria(Predicate<Role> p) {
    
    return null;
  }

}
