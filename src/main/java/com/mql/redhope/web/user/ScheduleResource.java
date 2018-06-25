package com.mql.redhope.web.user;

import com.mql.redhope.buisness.ScheduleService;
import com.mql.redhope.dto.ScheduleDto;
import com.mql.redhope.models.Schedule;
import com.mql.redhope.web.admin.Secured;
import java.security.Principal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

/**
 * @author mehdithe
 */
@Path("schedules")
public class ScheduleResource {

  @Inject
  private ScheduleService scheduleService;

  @Context
  private UriInfo uri;

  @GET
  @Secured
  public Response getAllSchedulesForUser(@Context SecurityContext context) {
    Principal principal = context.getUserPrincipal();
    String email = principal.getName();
    List<Schedule> schedules = scheduleService.getUserSchedules(email)
        .stream()
        .sorted(Comparator.comparing(e -> e.getTime()))
        .collect(Collectors.toList());
    return Response.ok(schedules).build();
  }


  @POST
  @Secured
  public Response createSchedule(@Valid ScheduleDto body) {
    Schedule schedule = scheduleService.createSchedule(body);
    if (schedule == null) {
      return Response.status(Status.BAD_REQUEST).build();
    }
    return Response.created(uri.getAbsolutePath()).build();
  }
}
