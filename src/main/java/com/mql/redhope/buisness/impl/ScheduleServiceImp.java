package com.mql.redhope.buisness.impl;

import com.mql.redhope.buisness.ScheduleService;
import com.mql.redhope.dao.RegionDao;
import com.mql.redhope.dao.ScheduleDao;
import com.mql.redhope.dao.UserDao;
import com.mql.redhope.domain.dto.ScheduleDto;
import com.mql.redhope.domain.models.BloodType;
import com.mql.redhope.domain.models.Region;
import com.mql.redhope.domain.models.Role;
import com.mql.redhope.domain.models.Schedule;
import com.mql.redhope.domain.models.ScheduleStatus;
import com.mql.redhope.domain.models.User;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.slf4j.Logger;

/**
 * @author mehdithe
 */
@Stateless
public class ScheduleServiceImp implements ScheduleService {

  @Inject
  private ScheduleDao scheduleDao;

  @Inject
  private RegionDao regionDao;

  @Inject
  private UserDao userDao;

  @Inject
  Logger logger;

  @Override
  public List<Schedule> getUserSchedules(String email) {
    return scheduleDao.getUserSchedules(email);
  }

  @Override
  public Schedule createSchedule(ScheduleDto body) {
    User user = userDao.findByEmail(body.getUserEmail());
    Region region = regionDao.findByName(body.getRegion());
    if (user == null || region == null) {
      return null;
    }
    boolean isUserAllowedToDonate = user.getDonations().stream()
        .sorted((a, b) -> -a.getCreatedAt().compareTo(b.getCreatedAt()))
        .findFirst()
        .filter(donation -> {
          LocalDateTime currentTime = LocalDateTime.now().minus(Period.ofMonths(3));
          return currentTime.isAfter(donation.getCreatedAt());
        }).isPresent();
    //
    if (isUserAllowedToDonate) {
      return null;
    }
    LocalDate currentDate = body.getDate().toInstant().atZone(ZoneId.systemDefault())
        .toLocalDate();
    Schedule schedule = new Schedule(region, currentDate, user);
    scheduleDao.save(schedule);
    return schedule;
  }

  @Override
  public Optional<List<Schedule>> getAllSchedules(String email) {
    User user = userDao.findByEmail(email);
    logger.info("found user " + user);
    Optional<Role> isAdmin = user.getRoles().stream().filter(this::isAdmin).findAny();
    if (!isAdmin.isPresent()) {
      return null;
    }
    return Optional.ofNullable(scheduleDao.findAll());
  }

  @Override
  public Optional<Schedule> markSchedule(Long id, String name, String donationId, BloodType bloodType) {
    Schedule schedule = scheduleDao.findById(id);
    if(schedule == null) {
      return Optional.ofNullable(null);
    }
    schedule.setDonationId(donationId);
    schedule.setBloodType(bloodType);
    schedule.setStatus(ScheduleStatus.DONE);
    scheduleDao.update(schedule);
    return Optional.ofNullable(schedule);
  }

  private boolean isAdmin(Role role) {
    return role.getRoleName().toLowerCase().equals("admin");
  }
}
