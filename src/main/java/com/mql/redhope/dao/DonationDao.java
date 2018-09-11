package com.mql.redhope.dao;

import com.mql.redhope.domain.models.Donation;
import java.util.List;

/**
 * @author mehdithe
 */
public interface DonationDao extends AbstractDao<Donation> {

  Donation findByDonationId(String donationId);

  List<Donation> findByRegionName(String regionName);
}
