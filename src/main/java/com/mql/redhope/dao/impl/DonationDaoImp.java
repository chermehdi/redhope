package com.mql.redhope.dao.impl;

import com.mql.redhope.dao.DonationDao;
import com.mql.redhope.domain.models.Donation;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * @author mehdithe
 */
@Stateless
public class DonationDaoImp implements DonationDao {

  @PersistenceContext
  private EntityManager em;

  @Override
  public Donation findById(Long id) {
    return em.find(Donation.class, id);
  }

  @Override
  public List<Donation> findAll() {
    TypedQuery<Donation> query = em
        .createQuery("SELECT o FROM Donation o", Donation.class);
    return query.getResultList();
  }

  @Override
  public void save(Donation value) {
    if (em.contains(value)) {
      em.merge(value);
    } else {
      em.persist(value);
    }
  }

  @Override
  public Donation delete(Donation value) {
    return null;
  }

  @Override
  public Donation findByDonationId(String donationId) {
    TypedQuery<Donation> query = em
        .createQuery("SELECT o FROM Donation o WHERE o.donationId = :id", Donation.class);
    query.setParameter("id", donationId);
    return query.getSingleResult();
  }

  @Override
  public List<Donation> findByRegionName(String regionName) {
    TypedQuery<Donation> query = em
        .createQuery("SELECT o FROM Donation o WHERE o.region.name = :regionName", Donation.class);
    query.setParameter("regionName", regionName);
    return query.getResultList();
  }
}
