package com.mql.redhope;

import com.github.javafaker.Faker;
import com.mql.redhope.buisness.PasswordEncoder;
import com.mql.redhope.dao.DonationDao;
import com.mql.redhope.dao.EventPostDao;
import com.mql.redhope.dao.ProfileDao;
import com.mql.redhope.dao.RegionDao;
import com.mql.redhope.dao.RoleDao;
import com.mql.redhope.dao.ScheduleDao;
import com.mql.redhope.dao.UserDao;
import com.mql.redhope.domain.models.BloodType;
import com.mql.redhope.domain.models.Donation;
import com.mql.redhope.domain.models.EventPost;
import com.mql.redhope.domain.models.Profile;
import com.mql.redhope.domain.models.Region;
import com.mql.redhope.domain.models.Role;
import com.mql.redhope.domain.models.Schedule;
import com.mql.redhope.domain.models.ScheduleStatus;
import com.mql.redhope.domain.models.User;
import java.time.LocalDate;
import java.util.ArrayList;
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
  ScheduleDao scheduleDao;

  @Inject
  DonationDao donationDao;

  @Inject
  EventPostDao eventPostDao;

  @Inject
  Logger logger;

  Faker faker = new Faker();

  Random rnd = new Random();

  static int counter = 1;

  @PostConstruct
  public void init() {
    Role userRole = new Role("USER");
    Role adminRole = new Role("ADMIN");
    roleDao.save(userRole);
    roleDao.save(adminRole);

    Set<Region> r = Stream.of("Fes-Meknes", "Rabat").map(Region::new)
        .collect(Collectors.toSet());
    List<Region> regions = new ArrayList<>();
    for (Region region : r) {
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
    adminProfile.setType(BloodType.A_PLUS);
    adminProfile.setPhone("0634411218");
    admin.setProfile(adminProfile);
    donor.setProfile(donorProfile);
    admin.setRoles(Stream.of(adminRole, userRole).collect(Collectors.toSet()));
    donor.setRoles(Stream.of(userRole).collect(Collectors.toSet()));
    System.out.println(regions);
    admin.setRegion(regions.get(0));
    donor.setRegion(regions.get(1));
    userDao.save(admin);
    userDao.save(donor);
    createFakeUsers(faker);
    for (int i = 0; i < 5; i++) {
      EventPost newPost = EventPost.of(faker.lorem().characters(20));
      eventPostDao.save(newPost);
    }
  }

  private List<Region> createRegions() {
    Set<Region> regions = Stream.of("Fes-Meknes", "Rabat").map(Region::new)
        .collect(Collectors.toSet());
    List<Region> ret = new ArrayList<>();
    for (Region region : regions) {
      regionDao.save(region);
      ret.add(region);
    }
    logger.info("the regions " + ret);
    return ret;
  }

  private List<User> createFakeUsers(Faker faker) {
    List<User> users = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
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
      generateScheduleForUser(cur);
      generateDonationsForUser(cur);
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
    r[0] = BloodType.A_PLUS;
    r[1] = BloodType.B_PLUS;
    r[2] = BloodType.B_PLUS;
    r[3] = BloodType.O_PLUS;
    return r[rnd.nextInt(4)];
  }

  private void generateScheduleForUser(User user) {
    for (int i = 0; i < rnd.nextInt(10); i++) {
      Schedule schedule = new Schedule();
      schedule.setStatus(getRandomStatus());
      schedule.setRegion(user.getRegion());
      schedule.setTime(LocalDate.now().plusDays(rnd.nextInt(10)));
      schedule.setUser(user);
      scheduleDao.save(schedule);
    }
  }

  private void generateDonationsForUser(User user) {
    List<Schedule> schedules = scheduleDao.findAll();
    for (Schedule schedule : schedules) {
      if (schedule.getStatus() == ScheduleStatus.DONE) {
        Donation donation = new Donation();
        donation.setDonationId("abcde" + counter++);
        donation.setCreatedAt(schedule.getTime().atStartOfDay());
        donation.setRegion(schedule.getRegion());
        donationDao.save(donation);
        user.getDonations().add(donation);
      }
    }
    userDao.update(user);
  }

  private ScheduleStatus getRandomStatus() {
    if (rnd.nextBoolean()) {
      return ScheduleStatus.PENDING;
    }
    return ScheduleStatus.DONE;
  }
}
