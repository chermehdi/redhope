package com.mql.redhope.buisness;

import com.mql.redhope.domain.models.User;
import java.util.List;

/**
 * @author mehdithe
 */
public interface AdminService {

  User login(String email, String password);

  User getEmailInformations(String adminEmail);

  List<User> getUsersMatchingRegion(String regionName);
}
