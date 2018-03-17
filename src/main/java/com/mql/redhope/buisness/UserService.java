package com.mql.redhope.buisness;

import com.mql.redhope.dto.UserDto;
import com.mql.redhope.models.User;

public interface UserService {
  
  User login(String email, String password);
  
  User signup(UserDto userDto);
}
