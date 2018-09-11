package com.mql.redhope.buisness.impl;

import com.mql.redhope.buisness.MailService;
import com.mql.redhope.buisness.PasswordEncoder;
import com.mql.redhope.buisness.UserService;
import com.mql.redhope.dao.DonationDao;
import com.mql.redhope.dao.ProfileDao;
import com.mql.redhope.dao.RegionDao;
import com.mql.redhope.dao.RoleDao;
import com.mql.redhope.dao.TokenDao;
import com.mql.redhope.dao.UserDao;
import com.mql.redhope.dto.UserDto;
import com.mql.redhope.models.BloodType;
import com.mql.redhope.models.Donation;
import com.mql.redhope.models.Profile;
import com.mql.redhope.models.Token;
import com.mql.redhope.models.User;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;

@Stateless
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
  DonationDao donationDao;

  @Inject
  RegionDao regionDao;

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
      user.setPassword(encoder.encode(userDto.getPassword()));
      user.setEmail(userDto.getEmail());
      user.setRoles(new HashSet<>(Arrays.asList(roleDao.findByName("USER"))));
      user.setActive(
          true); // todo: FOR THE TIME BEING ALL THE USERS ARE TREATED TO HAVE AN ACTIVE ACCOUNT
      user.setProfile(createProfileForUser(userDto));
      userDao.save(user);
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

  @Override
  public List<Donation> getUserDonations(String email) {
    User currentUser = userDao.findByEmail(email);
    return new ArrayList<>(currentUser.getDonations());
  }

  @Override
  public Profile getUser(String email) {
    return userDao.findByEmail(email).getProfile();
  }

  @Override
  public Profile updateProfile(String email, JsonObject body) {
    User user = userDao.findByEmail(email);
    Profile profile = user.getProfile();
    JsonObject personal = body.getJsonObject("personal");
    JsonObject address = body.getJsonObject("address");
    JsonObject general = body.getJsonObject("general");
    profile.setFirstName(personal.getString("firstName"));
    profile.setLastName(personal.getString("lastName"));
    profile.setPhone(personal.getString("phone"));
    profile.setAddress(address.getString("address"));
    profile.setType(BloodType.from(general.getString("bloodType")));
    profile.setReceiveEmails(general.getBoolean("receiveEmails"));
    profile.setCompleted(true);
    profileDao.update(profile);
    return profile;
  }

  @Override
  public void addDonationToUser(String name, Donation donation) {
    User user = userDao.findByEmail(name);
    donation.setRegion(regionDao.findByName(user.getRegion().getName()));
    user.getDonations().add(donation);
    userDao.update(user);
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