package com.mql.redhope;

import java.util.Arrays;
import java.util.HashSet;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import com.mql.redhope.dao.RoleDao;
import com.mql.redhope.dao.UserDao;
import com.mql.redhope.models.Role;
import com.mql.redhope.models.User;

@Singleton
@Startup
public class DataBaseSeeder {

  @Inject RoleDao roleDao;
  
  @Inject UserDao userDao;
  
  @PostConstruct
  public void init() {
    System.out.println("Database Seeder called !");
    User u = new User("mehdi", "mehdi");
    Role role = new Role("USER");
    roleDao.save(role);
    u.setRoles(new HashSet<>(Arrays.asList(role)));
    userDao.save(u);
  }
}
