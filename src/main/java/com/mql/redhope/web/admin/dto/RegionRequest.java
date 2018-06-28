package com.mql.redhope.web.admin.dto;

/**
 * @author mehdithe
 */
public class RegionRequest {

  private String regionName;

  public RegionRequest() {}

  public String getRegionName() {

    return regionName;
  }

  public void setRegionName(String regionName) {
    this.regionName = regionName;
  }

  @Override
  public String toString() {
    return "RegionRequest{" +
        "regionName='" + regionName + '\'' +
        '}';
  }
}
