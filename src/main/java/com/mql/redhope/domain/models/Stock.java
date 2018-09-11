package com.mql.redhope.domain.models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKeyEnumerated;
import javax.persistence.OneToOne;

/**
 * @author mehdithe
 */
@Entity
public class Stock implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne
  private Region region;

  @ElementCollection
  @MapKeyEnumerated(EnumType.STRING)
  private Map<BloodType, Integer> quantity = new HashMap<>();

  public Stock(){}

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

  public Map<BloodType, Integer> getQuantity() {
    return quantity;
  }

  public void setQuantity(Map<BloodType, Integer> quantity) {
    this.quantity = quantity;
  }
}
