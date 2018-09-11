package com.mql.redhope.domain.models;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * @author mehdithe
 */
@Entity
public class Schedule {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(fetch = FetchType.EAGER)
  private Region region;

  private LocalDate time;

  @OneToOne(fetch = FetchType.EAGER)
  private User user;

  @Enumerated
  private ScheduleStatus status = ScheduleStatus.PENDING;

  private String donationId;

  @Enumerated
  private BloodType bloodType;

  public Schedule() {}

  public Schedule(Region region, LocalDate time, User user) {
    this.user = user;
    this.time = time;
    this.region = region;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Region getRegion() {
    return region;
  }

  public void setRegion(Region region) {
    this.region = region;
  }

  public LocalDate getTime() {
    return time;
  }

  public void setTime(LocalDate time) {
    this.time = time;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public ScheduleStatus getStatus() {
    return status;
  }

  public void setStatus(ScheduleStatus status) {
    this.status = status;
  }

  public String getDonationId() {
    return donationId;
  }

  public void setDonationId(String donationId) {
    this.donationId = donationId;
  }

  public void setBloodType(BloodType bloodType) {
    this.bloodType = bloodType;
  }

  public BloodType getBloodType() {
    return bloodType;
  }
}
