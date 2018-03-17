package com.mql.redhope.models;

import java.util.Date;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class User {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  private String email;
  
  private String password;
  
  private Date createdAt;

  @OneToMany
  private Set<Role> roles;
  
  public User() {
    
  }
  
  public User(String email, String password) {
    this.email = email;
    this.password = password;
    this.createdAt = new Date();
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
    this.password  = password;
  }
  
  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }
  
}

