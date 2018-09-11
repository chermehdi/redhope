package com.mql.redhope.web.beans;

import com.mql.redhope.buisness.AdminService;
import com.mql.redhope.domain.dto.UserDto;
import com.mql.redhope.domain.models.User;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.slf4j.Logger;

/**
 * @author mehdithe
 */
@Named
@RequestScoped
@Transactional
public class AdminAuthentication {

  private UserDto userDto;

  @Inject
  Logger logger;

  @Inject
  AdminService adminService;

  public AdminAuthentication() {
    userDto = new UserDto();
  }

  public UserDto getUserDto() {
    return userDto;
  }

  public void setUserDto(UserDto userDto) {
    this.userDto = userDto;
  }

  public String login() {
    logger.info("in the admin login " + userDto);
    User admin = adminService.login(userDto.getEmail(), userDto.getPassword());
    if (admin == null) {
      // TODO: add error message
      return null;
    }
    HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext()
        .getSession(true);
    session.setAttribute("admin", admin);
    logger.info("the found admin is " + admin);
    return "admin-dashboard?faces-redirect=true";
  }
}
