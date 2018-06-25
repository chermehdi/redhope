package com.mql.redhope.buisness;

import com.mql.redhope.dto.UserDto;
import com.mql.redhope.models.Donation;
import com.mql.redhope.models.Profile;
import com.mql.redhope.models.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.json.JsonObject;

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

  /**
   * gets the list of the a user's donations
   *
   * @param email the email of the current user
   */
  List<Donation> getUserDonations(String email);

  /**
   * gets the user identified by it's email
   *
   * @param email the user's email
   */
  Profile getUser(String email);

  /**
   * updates the user profile information based on the given json object
   *
   * @param email Email of the user
   */
  Profile updateProfile(String email, JsonObject body);

  void addDonationToUser(String name, Donation donation);
}
