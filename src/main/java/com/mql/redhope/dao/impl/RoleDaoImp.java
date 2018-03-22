package com.mql.redhope.dao.impl;

import com.mql.redhope.dao.RoleDao;
import com.mql.redhope.models.Role;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

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
  public Role findByName(String name) {
    TypedQuery<Role> query = em
        .createQuery("SELECT r FROM Role r WHERE r.roleName=:name", Role.class);
    query.setParameter("name", name);
    return query.getSingleResult();
  }

}
