package com.mql.redhope.buisness.impl;

import com.mql.redhope.buisness.ScheduleService;
import com.mql.redhope.dao.RegionDao;
import com.mql.redhope.dao.ScheduleDao;
import com.mql.redhope.dao.UserDao;
import com.mql.redhope.dto.ScheduleDto;
import com.mql.redhope.models.Region;
import com.mql.redhope.models.Schedule;
import com.mql.redhope.models.User;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import javax.inject.Inject;

/**
 * @author mehdithe
 */
public class ScheduleServiceImp implements ScheduleService {

  @Inject
  private ScheduleDao scheduleDao;

  @Inject
  private RegionDao regionDao;

  @Inject
  private UserDao userDao;

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
    LocalDateTime localDateTime = body.getDate().toInstant().atZone(ZoneId.systemDefault())
        .toLocalDateTime();
    Schedule schedule = new Schedule(region, localDateTime, user);
    scheduleDao.save(schedule);
    return schedule;
  }
}
