package com.mql.redhope.dao;

import com.mql.redhope.domain.models.Schedule;
import java.util.List;

/**
 * @author mehdithe
 */
public interface ScheduleDao extends AbstractDao<Schedule> {

  List<Schedule> getUserSchedules(String email);

  Schedule update(Schedule t);
}
