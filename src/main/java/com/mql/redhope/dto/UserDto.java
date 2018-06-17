package com.mql.redhope.dto;

import javax.json.JsonObject;

public class UserDto {

  private String email;

  private String password;

  private String firstName;

  private String lastName;

  public UserDto() {

  }

  public static UserDto from(JsonObject object) {
    return new UserDto(object.getString("email"),
        object.getString("password"),
        object.getString("firstName"),
        object.getString("lastName"));
  }

  public UserDto(String email, String password, String firstName, String lastName) {
    this.email = email;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
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

  @Override
  public String toString() {
    return "first Name : " + firstName + " last Name : " + lastName + " email" + email
        + " password " + password;
  }


}
