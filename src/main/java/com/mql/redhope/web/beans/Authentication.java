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
    // TODO: send email to validate user account
    User user = userService.signup(userDto);
  }

  public String login() {
    System.out.println("current user information are " + userDto);
    User user = userService.login(userDto.getEmail(), userDto.getPassword());
    if (user == null) {
      return "login-ind?faces-redirect=true";
    }
    FacesContext context = FacesContext.getCurrentInstance();
    return "user-dashboard";
  }

  public UserDto getUserDto() {
    return userDto;
  }

  public void setUserDto(UserDto userDto) {
    this.userDto = userDto;
  }

}
