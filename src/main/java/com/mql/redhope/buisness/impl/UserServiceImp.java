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
import com.mql.redhope.models.Role;
import com.mql.redhope.models.Token;
import com.mql.redhope.models.User;
import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

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

  @Override
  public User login(String email, String password) {
    User user = userDao.findByEmail(email);
    FacesContext context = FacesContext.getCurrentInstance();
    if (user != null && !user.getActive()) {
      context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
          "account not active, please activate your email account", null));
      return null;
    }
    // TODO: add error messaging
    if (user != null) {
      if (user.getPassword().equals(encoder.encode(password))) {
        // TODO: set session values and direct to dashboard
        context.getExternalContext().getSessionMap().put("user", user);
        context.addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_ERROR, "logging successful", null));
        return user;
      }
    }
    FacesContext.getCurrentInstance()
        .addMessage(null, new FacesMessage("email or password not working"));
    return null;
  }

  @Override
  public User signup(UserDto userDto) {
    User user = userDao.findByEmail(userDto.getEmail());
    if (user != null) {
      FacesContext.getCurrentInstance()
          .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
              "email already exists in the database", null));
      return null;
    }
    user = new User(userDto.getEmail(), encoder.encode(userDto.getPassword()));
    Role userRole = roleDao.findByName("USER");
    user.setRoles(new HashSet<>(Arrays.asList(userRole)));
    setProfile(userDto, user);
    Token token = createNewToken();
    mailService.sendMail(userDto.getEmail(), createHtmlMail(token.getValue()));
    user.setToken(token);
    userDao.save(user);
    return user;
  }

  @Override
  public boolean activateAccount(String token) {
    try {
      User user = userDao.findByToken(token);
      user.setActive(true);
      userDao.update(user);
      return true;
    } catch (Exception e) {
      System.out.println("could not activate the user account");
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

  private void setProfile(UserDto userDto, User user) {
    Profile profile = new Profile();
    profile.setFirstName(userDto.getFirstName());
    profile.setLastName(userDto.getLastName());
    profileDao.save(profile);
    user.setProfile(profile);
  }
}
