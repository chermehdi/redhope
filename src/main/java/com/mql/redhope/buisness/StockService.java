package com.mql.redhope.buisness;

import com.mql.redhope.domain.models.Donation;
import javax.json.JsonObject;

/**
 * @author mehdithe
 */
public interface StockService {

  /**
   * Update the information about the quantities of plasma, platelet and red cells, or throws an
   * <code>StockException</code> in case of error
   *
   * @param donationId target donation id
   * @param plasma plasma quantity
   * @param platelet platelet quantity
   * @param redCell redCell quantity
   * @return the updated donation entity
   */
  Donation updateDonation(String donationId, long plasma, long platelet, long redCell);

  /**
   * Removes the chosen values from the donation, if the i'th bit is set, than the value is chosen
   * first bit => plasma second bit => platelet third bit => red cells
   *
   * @param donationId the chosen donation id
   * @param mask of the chosen values
   */
  Donation removeFromStock(String donationId, long mask);

  /**
   * returns a json representation of the stock state
   *
   * @param regionName name of the target region
   */
  JsonObject getStockInfo(String regionName);
}
