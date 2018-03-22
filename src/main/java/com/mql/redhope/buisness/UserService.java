package com.mql.redhope.buisness;

import com.mql.redhope.dto.UserDto;
import com.mql.redhope.models.User;
import javax.ejb.Stateless;

@Stateless
public interface UserService {

  /**
   * logs the user identified by email and password, returns null if cannot perform authentication
   * or returns the user object if login is successful
   */
  User login(String email, String password);

  /**
   * signup the user, creates account and returns the user object if everything went well else it
   * returns null
   */
  User signup(UserDto userDto);

  /**
   * activates the account of the user identified by the given token returns true if it succeeds
   * false if not
   */
  boolean activateAccount(String token);
}
