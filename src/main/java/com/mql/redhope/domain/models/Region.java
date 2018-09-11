package com.mql.redhope.domain.models;

import java.util.List;
import java.util.Vector;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author mehdithe
 */
@Entity
public class Region {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;


  @XmlTransient
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "region")
  List<User> users;

  public Region() {
    users = new Vector<>();
  }

  public Region(String name) {
    this();
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<User> getUsers() {
    return users;
  }

  public void setUsers(List<User> users) {
    this.users = users;
  }

  @Override
  public String toString() {
    return "Region{" +
        "id=" + id +
        ", name='" + name + '\'' +
        '}';
  }
}
