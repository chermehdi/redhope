package com.mql.redhope.dao.impl;

import com.mql.redhope.dao.ScheduleDao;
import com.mql.redhope.models.Schedule;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * @author mehdithe
 */
@Stateless
public class ScheduleDaoImp implements ScheduleDao {

  @PersistenceContext
  private EntityManager em;

  @Override
  public Schedule findById(Long id) {
    return em.find(Schedule.class, id);
  }

  @Override
  public List<Schedule> findAll() {
    TypedQuery<Schedule> query = em
        .createQuery("SELECT s FROM Schedule s", Schedule.class);
    return query.getResultList();
  }

  @Override
  public void save(Schedule value) {
    try {
      em.persist(value);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public Schedule delete(Schedule value) {
    if (!em.contains(value)) {
      em.merge(value);
    }
    em.remove(value);
    return value;
  }

  @Override
  public List<Schedule> getUserSchedules(String email) {
    TypedQuery<Schedule> allSchedules = em
        .createQuery("SELECT s FROM Schedule sc WHERE sc.user.email=:email", Schedule.class);
    allSchedules.setParameter("email", email);
    return allSchedules.getResultList();
  }

  @Override
  public Schedule update(Schedule t) {
    try {
      em.merge(t);
    }catch (Exception e) {
      e.printStackTrace();
    }
    return t;
  }
}
