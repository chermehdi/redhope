package com.mql.redhope.buisness.impl;

import com.mql.redhope.buisness.AdminService;
import com.mql.redhope.buisness.PasswordEncoder;
import com.mql.redhope.dao.RegionDao;
import com.mql.redhope.dao.RoleDao;
import com.mql.redhope.dao.UserDao;
import com.mql.redhope.models.Region;
import com.mql.redhope.models.Role;
import com.mql.redhope.models.User;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import org.slf4j.Logger;

/**
 * @author mehdithe
 */
public class AdminServiceImp implements AdminService {

  @Inject
  UserDao userDao;

  @Inject
  RegionDao regionDao;

  @Inject
  RoleDao roleDao;

  @Inject
  PasswordEncoder encoder;

  @Inject
  Logger log;

  @Override
  public User login(String email, String password) {
    try {
      User user = userDao.findByEmail(email);
      String encryptedPassword = encoder.encode(password);
      Role adminRole = roleDao.findByName("ADMIN");
      if (user.getActive() && user.getPassword().equals(encryptedPassword)
          && user.getRoles().contains(adminRole)) {
        return user;
      }
      return null;
    } catch (Exception e) {
      log.error(e.getMessage());
      return null;
    }
  }

  @Override
  public List<User> getUsersMatchingRegion(String regionStr) {
    Region region = regionDao.findByName(regionStr);
    if (region == null) {
      return Collections.emptyList();
    }
    List<User> users = region.getUsers();
    return users;
  }

  @Override
  public User getEmailInformations(String email) {
    User user = userDao.findByEmail(email);
    return user;
  }
}
