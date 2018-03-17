package com.mql.redhope.buisness;

import java.util.Arrays;
import java.util.HashSet;
import javax.inject.Inject;
import com.mql.redhope.dao.RoleDao;
import com.mql.redhope.dao.UserDao;
import com.mql.redhope.dto.UserDto;
import com.mql.redhope.models.User;

public class UserServiceImp implements UserService {

  @Inject UserDao userDao;
  
  @Inject RoleDao roleDao;
  
  @Override
  public User login(String email, String password) {
    
    return null;
  }

  @Override
  public User signup(UserDto userDto) {
    // TODO: fill profile information
    // TODO: check if email already exist
    // TODO: add password encryption
    User user = new User(userDto.getEmail(), userDto.getPassword());
    user.setRoles(new HashSet<>(Arrays.asList(roleDao.findById(1L))));
    userDao.save(user);
    return user;
  }

}
