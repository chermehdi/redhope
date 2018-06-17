package com.mql.redhope.buisness.impl;

import com.mql.redhope.buisness.MailService;
import com.mql.redhope.buisness.PasswordEncoder;
import com.mql.redhope.buisness.UserService;
import com.mql.redhope.dao.ProfileDao;
import com.mql.redhope.dao.RoleDao;
import com.mql.redhope.dao.TokenDao;
import com.mql.redhope.dao.UserDao;
import com.mql.redhope.dto.UserDto;
import com.mql.redhope.models.Profile;
import com.mql.redhope.models.Token;
import com.mql.redhope.models.User;
import java.util.Date;
import java.util.UUID;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;

public class UserServiceImp implements UserService {

  @Inject
  UserDao userDao;

  @Inject
  RoleDao roleDao;

  @Inject
  PasswordEncoder encoder;

  @Inject
  ProfileDao profileDao;

  @Inject
  TokenDao tokenDao;

  @Inject
  MailService mailService;

  @Inject
  Logger log;

  @Override
  public User login(String email, String password) {
    User user = userDao.findByEmail(email);
    if (user != null) {
      String encodedPassword = encoder.encode(password);
      if (encodedPassword.equals(user.getPassword()) && user.getActive()) {
        return user;
      }
    }
    return null;
  }

  @Override
  public User signup(UserDto userDto) {
    User user = userDao.findByEmail(userDto.getEmail());
    if (user == null) {
      user = new User();
      user.setCreatedAt(new Date());
      user.setPassword(userDto.getPassword());
      user.setActive(
          true); // todo: FOR THE TIME BEING ALL THE USERS ARE TREATED TO HAVE AN ACTIVE ACCOUNT
      user.setProfile(createProfileForUser(userDto));
      return user;
    }
    return null;
  }

  private Profile createProfileForUser(UserDto userDto) {
    Profile profile = new Profile();
    profile.setLastName(userDto.getLastName());
    profile.setFirstName(userDto.getFirstName());
    profileDao.save(profile);
    return profile;
  }

  @Override
  public boolean activateAccount(String token) {
    try {
      User user = userDao.findByToken(token);
      user.setActive(true);
      user = userDao.update(user);
      log.info("The user {0}", user);
      return true;
    } catch (Exception e) {
      log.error("could not activate the user account " + e.getMessage());
      return false;
    }
  }

  private Token createNewToken() {
    Token token = new Token(UUID.randomUUID().toString());
    tokenDao.save(token);
    return token;
  }

  private String createHtmlMail(String token) {
    String msg = "Welcome ! click the following <a href='%s'>link</a> to activate your account !";
    HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance()
        .getExternalContext().getRequest();
    return String.format(msg,
        request.getRequestURL().substring(0, request.getRequestURL().indexOf("faces"))
            + "validate?token=" + token);
  }

}