package com.mql.redhope.web.beans;

import com.mql.redhope.buisness.UserService;
import com.mql.redhope.domain.dto.UserDto;
import com.mql.redhope.domain.models.User;
import java.io.Serializable;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@RequestScoped
@Transactional
public class UserAuthentication implements Serializable {

  private UserDto userDto;

  @Inject
  private UserService userService;

  Logger log = LoggerFactory.getLogger(getClass());

  public UserAuthentication() {
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
    log.info("User Logged in " + user);
    FacesContext context = FacesContext.getCurrentInstance();
    Map<String, Object> session = context.getExternalContext().getSessionMap();
    session.put("user", user);
    return "user-dashboard?faces-redirect=true";
  }

  public UserDto getUserDto() {
    return userDto;
  }

  public void setUserDto(UserDto userDto) {
    this.userDto = userDto;
  }

}
