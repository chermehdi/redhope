package com.mql.redhope.dao.impl;

import com.mql.redhope.dao.RegionDao;
import com.mql.redhope.models.Region;
import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.slf4j.Logger;

/**
 * @author mehdithe
 */
@Stateless
public class RegionDaoImp implements RegionDao {

  @PersistenceContext
  EntityManager em;

  @Inject
  Logger log;

  @Override
  public Region findById(Long id) {
    try {
      return em.find(Region.class, id);
    } catch (Exception e) {
      log.error("could not find region by id: " + id + " " + e.getMessage());
    }
    return null;
  }

  @Override
  public List<Region> findAll() {
    try {
      TypedQuery<Region> query = em
          .createQuery("SELECT r from Region r", Region.class);
      return query.getResultList();
    } catch (Exception e) {
      log.error("could not find regions " + e.getMessage());
    }
    return Collections.emptyList();
  }

  @Override
  public void save(Region value) {
    try {
      em.persist(value);
    } catch (Exception e) {
      log.error("could save region " + value);
    }
  }

  @Override
  public Region delete(Region value) {
    try {
      em.remove(value);
      return value;
    } catch (Exception e) {
      log.error("could remove region " + value);
    }
    return null;
  }

  @Override
  public Region findByName(String regionName) {
    try {
      regionName = regionName.toLowerCase();
      TypedQuery<Region> regionQuery = em
          .createQuery("SELECT r from Region r where r.name = :name", Region.class);
      regionQuery.setParameter("name", regionName);
      return regionQuery.getSingleResult();
    } catch (Exception e) {
      log.error("could not find region with name " + regionName + " with error " + e.getMessage());
    }
    return null;

  }
}
