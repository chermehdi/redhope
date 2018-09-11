package com.mql.redhope.buisness;

import com.mql.redhope.dao.DonationDao;
import com.mql.redhope.dao.UserDao;
import com.mql.redhope.domain.models.Donation;
import com.mql.redhope.domain.models.Schedule;
import java.time.LocalDateTime;
import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

/**
 * @author mehdithe
 */
@Stateless
public class DonationListener {

  @Inject
  DonationDao donationDao;

  @Inject
  UserDao userDao;

  public void createDonation(@Observes Schedule schedule) {
    Donation donation = new Donation();
    donation.setRegion(schedule.getRegion());
    donation.setCreatedAt(LocalDateTime.now());
    donation.setDonationId(schedule.getDonationId());
    donation.setType(schedule.getBloodType());
    donationDao.save(donation);
    schedule.getUser().getDonations().add(donation);
    userDao.update(schedule.getUser());
  }
}
