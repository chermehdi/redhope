package com.mql.redhope.web.beans;

import com.mql.redhope.buisness.UserService;
import com.mql.redhope.dto.UserDto;
import com.mql.redhope.models.User;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

@Named
@RequestScoped
@Transactional
public class Authentication implements Serializable {

  private UserDto userDto;

  @Inject
  private UserService userService;

  public Authentication() {
    userDto = new UserDto();
  }

  public void signup() {
    System.out.println("current suer information are " + userDto);
    User user = userService.signup(userDto);
    System.out.println("the saved user is " + user);
  }

  public void login() {
    System.out.println("current suer information are " + userDto);
    User user = userService.login(userDto.getEmail(), userDto.getPassword());
    FacesContext context = FacesContext.getCurrentInstance();
    System.out.println(context.getExternalContext().getSessionMap());
    System.out.println("the saved user is " + user);
  }

  public UserDto getUserDto() {
    return userDto;
  }

  public void setUserDto(UserDto userDto) {
    this.userDto = userDto;
  }

}
