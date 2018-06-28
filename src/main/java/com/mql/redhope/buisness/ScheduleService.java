package com.mql.redhope.buisness;

import com.mql.redhope.dto.ScheduleDto;
import com.mql.redhope.models.Schedule;
import java.util.List;
import java.util.Optional;

/**
 * @author mehdithe
 */
public interface ScheduleService {

  List<Schedule> getUserSchedules(String email);

  Schedule createSchedule(ScheduleDto body);

  Optional<List<Schedule>> getAllSchedules(String email);

  Optional<Schedule> markSchedule(Long id, String name);
}
