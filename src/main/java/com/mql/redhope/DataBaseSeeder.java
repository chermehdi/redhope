package com.mql.redhope;

import com.github.javafaker.Faker;
import com.mql.redhope.buisness.PasswordEncoder;
import com.mql.redhope.dao.ProfileDao;
import com.mql.redhope.dao.RegionDao;
import com.mql.redhope.dao.RoleDao;
import com.mql.redhope.dao.UserDao;
import com.mql.redhope.models.BloodType;
import com.mql.redhope.models.Profile;
import com.mql.redhope.models.Region;
import com.mql.redhope.models.Role;
import com.mql.redhope.models.User;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import org.slf4j.Logger;

@Singleton
@Startup
public class DataBaseSeeder {

  @Inject
  RoleDao roleDao;

  @Inject
  UserDao userDao;

  @Inject
  ProfileDao profileDao;

  @Inject
  PasswordEncoder encoder;

  @Inject
  RegionDao regionDao;

  @Inject
  Logger logger;

  @PostConstruct
  public void init() {
    logger.info("Database Seeder invoked ");
    Faker faker = new Faker();
    List<User> fakeUsers = createFakeUsers(faker);
    List<User> fakeRabatUsers = createFakeUsers(faker);
    System.out.println("Database Seeder called !");
    User u = new User("mehdi.cheracher@gmail.com", encoder.encode("mehdi"));
    u.setActive(true);
    Role userRole = new Role("USER");
    Role adminRole = new Role("ADMIN");
    roleDao.save(adminRole);
    roleDao.save(userRole);
    u.setRoles(new HashSet<>(Arrays.asList(adminRole, userRole)));
    Profile profile = new Profile();
    profile.setFirstName("mehdi");
    profile.setLastName("cheracher");
    profile.setType(BloodType.A);
    profile.setAddress("221B baker street");
    profile.setPhone("+212-634411218");
    profileDao.save(profile);
    u.setProfile(profile);
    Region r = new Region();
    r.setName("Fes-Meknes");
    r.getUsers().add(u);
    Region rabat = new Region();
    rabat.setName("Rabat");
    rabat.getUsers().add(u);
    u.setRegion(r);
    userDao.save(u);
    logger.info("saving user " + u);
    fakeUsers.forEach(System.out::println);
    for (User user : fakeUsers) {
      r.getUsers().add(user);
      user.setRegion(r);
    }
    for (User user : fakeRabatUsers) {
      rabat.getUsers().add(user);
      user.setRegion(rabat);
    }
    regionDao.save(r);
    regionDao.save(rabat);
  }

  private List<User> createFakeUsers(Faker faker) {
    List<User> users = new ArrayList<>();
    for (int i = 0; i < 100; i++) {
      User cur = new User();
      cur.setActive(true);
      cur.setEmail(faker.internet().emailAddress());
      cur.setCreatedAt(faker.date().past(90, TimeUnit.DAYS));
      cur.setPassword(faker.name().fullName());
      Profile p = new Profile();
      p.setFirstName(faker.name().firstName());
      p.setLastName(faker.name().lastName());
      p.setAddress(faker.address().fullAddress());
      p.setType(randomBloodType());
      p.setPhone(faker.phoneNumber().phoneNumber());
      profileDao.save(p);
      cur.setProfile(p);
      userDao.save(cur);
      users.add(cur);
    }
    return users;
  }

  private BloodType randomBloodType() {
    BloodType[] r = new BloodType[4];
    r[0] = BloodType.A;
    r[1] = BloodType.B;
    r[2] = BloodType.B;
    r[3] = BloodType.O;
    Random rnd = new Random();
    return r[rnd.nextInt(4)];
  }
}
