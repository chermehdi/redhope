package com.mql.redhope.buisness;

import com.mql.redhope.models.Donation;

/**
 * @author mehdithe
 */
public interface StockService {
  Donation updateDonation(String donationId, long plasma, long platelet, long redCell);
}
