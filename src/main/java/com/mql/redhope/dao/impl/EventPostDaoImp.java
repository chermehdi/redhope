package com.mql.redhope.dao.impl;

import com.mql.redhope.dao.EventPostDao;
import com.mql.redhope.domain.models.EventPost;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * @author mehdithe
 */
@Stateless
public class EventPostDaoImp implements EventPostDao {

  @PersistenceContext
  EntityManager em;

  @Override
  public EventPost findById(Long id) {
    return em.find(EventPost.class, id);
  }

  @Override
  public List<EventPost> findAll() {
    TypedQuery<EventPost> allPosts = em
        .createQuery("SELECT post from EventPost post order by post.createdAt desc  ", EventPost.class);
    return allPosts.getResultList();
  }

  @Override
  public void save(EventPost value) {
    em.persist(value);
  }

  @Override
  public EventPost delete(EventPost value) {
    if (!em.contains(value)) {
      em.merge(value);
    }
    em.remove(value);
    return value;
  }
}
