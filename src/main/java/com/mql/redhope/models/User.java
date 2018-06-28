package com.mql.redhope.models;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class User implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String email;

  private String password;

  private Date createdAt;

  private Boolean isActive;

  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private Set<Role> roles = new HashSet<>();

  @OneToOne(cascade = CascadeType.ALL)
  private Profile profile;

  @OneToOne
  @XmlTransient
  private Token token;

  @ManyToOne
  @XmlTransient
  private Region region;

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private Set<Donation> donations = new HashSet<>();


  public User() {
    isActive = false;
  }

  public User(String email, String password) {
    this.email = email;
    this.password = password;
    this.createdAt = new Date();
    isActive = false;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getPassword() {
    return password;
  }

  public String getEmail() {
    return email;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date date) {
    this.createdAt = date;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

  public Profile getProfile() {
    return profile;
  }

  public void setProfile(Profile profile) {
    this.profile = profile;
  }

  public Token getToken() {
    return token;
  }

  public void setToken(Token token) {
    this.token = token;
  }

  public Boolean getActive() {
    return isActive;
  }

  public void setActive(Boolean active) {
    isActive = active;
  }

  public Region getRegion() {
    return region;
  }

  public void setRegion(Region region) {
    this.region = region;
  }

  public Set<Donation> getDonations() {
    return donations;
  }

  public void setDonations(Set<Donation> donations) {
    this.donations = donations;
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", email='" + email + '\'' +
        ", password='" + password + '\'' +
        ", createdAt=" + createdAt +
        ", roles=" + roles +
        ", profile=" + profile +
        ", isActive=" + isActive +
        ", role=" + roles +
        ", region=" + region +
        '}';
  }


}

