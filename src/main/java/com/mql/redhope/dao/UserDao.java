package com.mql.redhope.dao;

import com.mql.redhope.models.User;

public interface UserDao extends AbstractDao<User> {

  User findByEmail(String email);

  User findByToken(String token);

  User update(User user);

  User findAndUpdateByToken(String token);
}
