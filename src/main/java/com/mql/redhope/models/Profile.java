package com.mql.redhope.models;

import java.io.Serializable;
import java.util.Optional;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Profile implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String firstName;

  private String lastName;

  @Enumerated
  BloodType type;

  private String phone;

  private String address;

  private boolean completed;

  private boolean receiveEmails;

  public Profile() {
    completed = false;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public BloodType getType() {
    return type;
  }

  public Optional<String> getOptionalAddress() {
    return Optional.ofNullable(address);
  }

  public Optional<BloodType> getOptionalBloodType() {
    return Optional.ofNullable(type);
  }

  public Optional<String> getOptionalPhone() {
    return Optional.ofNullable(phone);
  }

  public void setType(BloodType type) {
    this.type = type;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public boolean isCompleted() {
    return completed;
  }

  public void setCompleted(boolean completed) {
    this.completed = completed;
  }

  public boolean isReceiveEmails() {
    return receiveEmails;
  }

  public void setReceiveEmails(boolean receiveEmails) {
    this.receiveEmails = receiveEmails;
  }
}
