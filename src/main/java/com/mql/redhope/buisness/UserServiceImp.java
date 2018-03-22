package com.mql.redhope.buisness;

import com.mql.redhope.dao.ProfileDao;
import com.mql.redhope.dao.RoleDao;
import com.mql.redhope.dao.UserDao;
import com.mql.redhope.dto.UserDto;
import com.mql.redhope.models.Profile;
import com.mql.redhope.models.Role;
import com.mql.redhope.models.User;
import java.util.Arrays;
import java.util.HashSet;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

public class UserServiceImp implements UserService {

  @Inject
  UserDao userDao;

  @Inject
  RoleDao roleDao;

  @Inject
  PasswordEncoder encoder;

  @Inject
  ProfileDao profileDao;

  @Override
  public User login(String email, String password) {
    User user = userDao.findByEmail(email);
    // TODO: add error messaging
    if (user != null) {
      if (user.getPassword().equals(encoder.encode(password))) {
        // TODO: set session values and direct to dashboard
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().put("user", user);
        return user;
      }
    }
    return null;
  }

  @Override
  public User signup(UserDto userDto) {
    User user = userDao.findByEmail(userDto.getEmail());
    if (user != null) {
      return null;
    }
    user = new User(userDto.getEmail(), encoder.encode(userDto.getPassword()));
    Role userRole = roleDao.findByName("USER");
    user.setRoles(new HashSet<>(Arrays.asList(userRole)));
    setProfile(userDto, user);
    userDao.save(user);
    return user;
  }

  private void setProfile(UserDto userDto, User user) {
    Profile profile = new Profile();
    profile.setFirstName(userDto.getFirstName());
    profile.setLastName(userDto.getLastName());
    profileDao.save(profile);
    user.setProfile(profile);
  }
}
