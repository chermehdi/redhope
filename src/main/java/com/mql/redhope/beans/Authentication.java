package com.mql.redhope.beans;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import com.mql.redhope.buisness.UserService;
import com.mql.redhope.dto.UserDto;

@Named
@SessionScoped
public class Authentication implements Serializable {

  private UserDto userDto;

  @Inject
  private transient UserService userService;

  public Authentication() {
    userDto = new UserDto();
  }

  public void signup() {
    System.out.println("current suer information are " + userDto);
  }
  
  public UserDto getUserDto() {
    return userDto;
  }

  public void setUserDto(UserDto userDto) {
    this.userDto = userDto;
  }
  
}
