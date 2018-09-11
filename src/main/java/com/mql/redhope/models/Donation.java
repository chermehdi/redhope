package com.mql.redhope.models;

import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * @author mehdithe
 */
@Entity
public class Donation {

  @Id
  @GeneratedValue
  private Long id;

  private LocalDateTime createdAt;

  @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private Region region;

  @OneToOne(fetch = FetchType.EAGER)
  private Plasma plasma;

  @OneToOne(fetch = FetchType.EAGER)
  private RedCell redCells;

  @OneToOne(fetch = FetchType.EAGER)
  private Platelet platelet;

  private String donationId;

  public Donation() {
    createdAt = LocalDateTime.now();
  }

  public Donation(LocalDateTime time) {
    createdAt = time;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public Region getRegion() {
    return region;
  }

  public void setRegion(Region region) {
    this.region = region;
  }

  public Plasma getPlasma() {
    return plasma;
  }

  public void setPlasma(Plasma plasma) {
    this.plasma = plasma;
  }

  public RedCell getRedCells() {
    return redCells;
  }

  public void setRedCells(RedCell redCells) {
    this.redCells = redCells;
  }

  public Platelet getPlatelet() {
    return platelet;
  }

  public void setPlatelet(Platelet platelet) {
    this.platelet = platelet;
  }

  public String getDonationId() {
    return donationId;
  }

  public void setDonationId(String donationId) {
    this.donationId = donationId;
  }
}
