package com.mql.redhope.buisness.impl;

import com.mql.redhope.buisness.StockService;
import com.mql.redhope.buisness.qualifiers.UserProperties;
import com.mql.redhope.dao.DonationDao;
import com.mql.redhope.domain.exceptions.StockException;
import com.mql.redhope.domain.models.BloodType;
import com.mql.redhope.domain.models.Donation;
import com.mql.redhope.domain.models.Plasma;
import com.mql.redhope.domain.models.Platelet;
import com.mql.redhope.domain.models.RedCell;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 * @author mehdithe
 */
@Stateless
public class StockServiceImpl implements StockService {

  @Inject
  DonationDao donationDao;

  @Inject
  @UserProperties("stock-config.properties")
  Properties stockProperties;

  @Override
  public Donation updateDonation(String donationId, long plasma, long platelet, long redCell) {
    Donation donation = donationDao.findByDonationId(donationId);
    // TODO: refactor
    Plasma plasmaEntity = donation.getPlasma();
    if (plasmaEntity == null) {
      plasmaEntity = new Plasma();
      plasmaEntity.setQuantity(plasma);
    } else {
      throw new StockException("plasma already been set");
    }
    Platelet plateletEntity = donation.getPlatelet();
    if (plateletEntity == null) {
      plateletEntity = new Platelet();
      plateletEntity.setQuantity(platelet);
    } else {
      throw new StockException("platelet already been set");
    }
    RedCell redCellEntity = donation.getRedCells();
    if (redCellEntity == null) {
      redCellEntity = new RedCell();
      redCellEntity.setQuantity(platelet);
    } else {
      throw new StockException("red cells already been set");
    }
    donation.setPlasma(plasmaEntity);
    donation.setRedCells(redCellEntity);
    donation.setPlatelet(plateletEntity);
    return donation;
  }

  @Override
  public Donation removeFromStock(String donationId, long mask) {
    Donation donation = donationDao.findByDonationId(donationId);
    if (isBitSet(mask, 1L)) {
      donation.getPlasma().setRemoved(true);
    }
    if ((isBitSet(mask, 2L))) {
      donation.getPlatelet().setRemoved(true);
    }
    if ((isBitSet(mask, 3L))) {
      donation.getRedCells().setRemoved(true);
    }
    return donation;
  }

  @Override
  public JsonObject getStockInfo(String regionName) {
    List<Donation> donations = donationDao.findByRegionName(regionName);
    long maxSize = Long.parseLong(stockProperties.getProperty("stock.max.size", "1000000"));
    JsonObjectBuilder builder = Json.createObjectBuilder();

    Map<BloodType, List<Donation>> byBloodType = donations.stream()
        .filter(donation -> donation.getType() != BloodType.UNSET)
        .collect(Collectors.groupingBy(e -> e.getType()));

    getBloodTypes().forEach(type -> byBloodType.putIfAbsent(type, new ArrayList<>()));

    byBloodType.keySet().forEach(type -> {
      builder.add("maxSize", maxSize);
      JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
      long sumPlasma = byBloodType.get(type).stream().map(donation -> donation.getPlasma())
          .filter(plasma -> plasma != null)
          .mapToLong(plasma -> plasma.getQuantity()).sum();
      objectBuilder.add("plasma", sumPlasma);
      long sumPlatelet = byBloodType.get(type).stream().map(donation -> donation.getPlatelet())
          .filter(platelet -> platelet != null)
          .mapToLong(platelet -> platelet.getQuantity()).sum();
      objectBuilder.add("platelet", sumPlatelet);
      long redCellSum = byBloodType.get(type).stream().map(donation -> donation.getRedCells())
          .filter(redCell -> redCell != null)
          .mapToLong(redCell -> redCell.getQuantity()).sum();
      objectBuilder.add("redCell", redCellSum);
      builder.add(type.name(), objectBuilder);
    });
    return builder.build();
  }

  private boolean isBitSet(long mask, long l) {
    return (mask & (1L << l)) > 0;
  }

  private List<BloodType> getBloodTypes() {
    return Arrays.asList(BloodType.A_PLUS, BloodType.A_MINUS,
        BloodType.B_PLUS, BloodType.B_MINUS,
        BloodType.O_PLUS, BloodType.O_PLUS,
        BloodType.AB_PLUS, BloodType.AB_MINUS);
  }
}
