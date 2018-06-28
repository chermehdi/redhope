package com.mql.redhope;

import com.github.javafaker.Faker;
import com.mql.redhope.buisness.PasswordEncoder;
import com.mql.redhope.dao.DonationDao;
import com.mql.redhope.dao.ProfileDao;
import com.mql.redhope.dao.RegionDao;
import com.mql.redhope.dao.RoleDao;
import com.mql.redhope.dao.UserDao;
import com.mql.redhope.models.BloodType;
import com.mql.redhope.models.Donation;
import com.mql.redhope.models.Profile;
import com.mql.redhope.models.Region;
import com.mql.redhope.models.Role;
import com.mql.redhope.models.User;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import org.slf4j.Logger;

@Startup
@Singleton
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
  DonationDao donationDao;

  @Inject
  Logger logger;

  Faker faker = new Faker();

  Random rnd = new Random();

  @PostConstruct
  public void init() {
    Set<Region> r = Stream.of("Fes-Meknes", "Rabat").map(Region::new)
        .collect(Collectors.toSet());
    List<Region> regions  = new ArrayList<>();
    for(Region region: r) {
      regionDao.save(region);
      regions.add(region);
    }
    logger.info("the regions " + regions);
    User admin = new User();
    User donor = new User();
    admin.setEmail("mehdi.cheracher@gmail.com");
    donor.setEmail("mehdi@gmail.com");
    admin.setActive(true);
    donor.setActive(true);
    admin.setPassword(encoder.encode("mehdi"));
    donor.setPassword(encoder.encode("mehdi"));
    Profile adminProfile = new Profile();
    Profile donorProfile = new Profile();
    adminProfile.setFirstName("mehdi");
    donorProfile.setFirstName("mehdi");
    adminProfile.setLastName("cheracher");
    donorProfile.setLastName("cheracher");
    adminProfile.setReceiveEmails(true);
    adminProfile.setAddress("221B Baker street");
    adminProfile.setType(BloodType.A);
    adminProfile.setPhone("0634411218");
    admin.setProfile(adminProfile);
    donor.setProfile(donorProfile);
    admin.setRoles(Stream.of("ADMIN", "USER").map(Role::new).collect(Collectors.toSet()));
    donor.setRoles(Stream.of("USER").map(Role::new).collect(Collectors.toSet()));
    System.out.println(regions);
    admin.setRegion(regions.get(0));
    donor.setRegion(regions.get(1));
    userDao.save(admin);
    userDao.save(donor);
//    logger.info("Database Seeder invoked ");
//    Faker faker = new Faker();
//    List<User> fakeUsers = createFakeUsers(faker);
//    List<User> fakeRabatUsers = createFakeUsers(faker);
//    System.out.println("Database Seeder called !");
//    User u = new User("mehdi.cheracher@gmail.com", encoder.encode("mehdi"));
//    u.setActive(true);
//    Role userRole = new Role("USER");
//    Role adminRole = new Role("ADMIN");
//    roleDao.save(adminRole);
//    roleDao.save(userRole);
//    u.setRoles(new HashSet<>(Arrays.asList(adminRole, userRole)));
//    Profile profile = new Profile();
//    profile.setFirstName("mehdi");
//    profile.setLastName("cheracher");
//    profile.setType(BloodType.A);
//    profile.setAddress("221B baker street");
//    profile.setPhone("+212-634411218");
//    profileDao.save(profile);
//    u.setProfile(profile);
//    Region r = new Region();
//    r.setName("Fes-Meknes");
//    r.getUsers().add(u);
//    Region rabat = new Region();
//    rabat.setName("Rabat");
//    rabat.getUsers().add(u);
//    u.setRegion(r);
//    Donation donation = new Donation(LocalDateTime.now().minusMonths(5));
//    regionDao.save(r);
//    donation.setRegion(r);
//    donationDao.save(donation);
//    u.getDonations().add(donation);
//    userDao.save(u);
//    logger.info("saving user " + u);
//    fakeUsers.forEach(System.out::println);
//    for (User user : fakeUsers) {
//      r.getUsers().add(user);
//      user.setRegion(r);
//    }
//    for (User user : fakeRabatUsers) {
//      rabat.getUsers().add(user);
//      user.setRegion(rabat);
//    }
//    regionDao.save(rabat);
    createFakeUsers(faker);
  }

  private List<Region> createRegions() {
    Set<Region> regions = Stream.of("Fes-Meknes", "Rabat").map(Region::new)
        .collect(Collectors.toSet());
    List<Region> ret  = new ArrayList<>();
    for(Region region: regions) {
      regionDao.save(region);
      ret.add(region);
    }
    logger.info("the regions " + ret);
    return ret;
  }

  private List<User> createFakeUsers(Faker faker) {
    List<User> users = new ArrayList<>();
    for (int i = 0; i < 100; i++) {
      User cur = new User();
      cur.setActive(true);
      cur.setEmail(faker.internet().emailAddress());
      cur.setCreatedAt(faker.date().past(90, TimeUnit.DAYS));
      cur.setPassword(faker.name().fullName());
      cur.setRegion(getRandomRegion());
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

  private Region getRandomRegion() {
    List<Region> all = regionDao.findAll();
    int i = rnd.nextInt(all.size());
    return all.get(i);
  }

  private BloodType randomBloodType() {
    BloodType[] r = new BloodType[4];
    r[0] = BloodType.A;
    r[1] = BloodType.B;
    r[2] = BloodType.B;
    r[3] = BloodType.O;
    return r[rnd.nextInt(4)];
  }

}
