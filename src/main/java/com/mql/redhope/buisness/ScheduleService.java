package com.mql.redhope.buisness;

import com.mql.redhope.domain.dto.ScheduleDto;
import com.mql.redhope.domain.models.BloodType;
import com.mql.redhope.domain.models.Schedule;
import java.util.List;
import java.util.Optional;

/**
 * @author mehdithe
 */
public interface ScheduleService {

  List<Schedule> getUserSchedules(String email);

  Schedule createSchedule(ScheduleDto body);

  Optional<List<Schedule>> getAllSchedules(String email);

  Optional<Schedule> markSchedule(Long id, String name, String donationId, BloodType bloodType);
}
