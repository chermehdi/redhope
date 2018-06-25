package com.mql.redhope.dto;

import java.util.Date;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author mehdithe
 */
public class ScheduleDto {

  @NotNull
  private Date date;

  @NotNull
  @Size(min = 1)
  private String region;

  @NotNull
  @Size(min = 1)
  private String userEmail;

  public ScheduleDto() {}

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public String getRegion() {
    return region;
  }

  public void setRegion(String region) {
    this.region = region;
  }

  public String getUserEmail() {
    return userEmail;
  }

  public void setUserEmail(String userEmail) {
    this.userEmail = userEmail;
  }
}
