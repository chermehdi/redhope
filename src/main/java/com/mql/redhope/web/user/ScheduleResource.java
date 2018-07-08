package com.mql.redhope.web.user;

import com.mql.redhope.buisness.ScheduleService;
import com.mql.redhope.dto.ScheduleDto;
import com.mql.redhope.models.Schedule;
import com.mql.redhope.models.ScheduleStatus;
import com.mql.redhope.models.User;
import com.mql.redhope.web.admin.UserSecured;
import java.security.Principal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import org.slf4j.Logger;

/**
 * @author mehdithe
 */
@Path("schedules")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Stateless
public class ScheduleResource {

  @Inject
  private ScheduleService scheduleService;

  @Inject
  Logger logger;

  @Context
  private UriInfo uri;

  @Inject
  Event<Schedule> donationEvent;

  @GET
  @UserSecured
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
  @UserSecured
  public Response createSchedule(@Valid ScheduleDto body) {
    Schedule schedule = scheduleService.createSchedule(body);
    if (schedule == null) {
      return Response.status(Status.BAD_REQUEST).build();
    }
    return Response.created(uri.getAbsolutePath())
        .build(); // todo: should return the url to visit the created resource
  }

  @GET
  @Path("all")
  @UserSecured
  public Response getAllSchedules(@Context SecurityContext context) {
    Principal principal = context.getUserPrincipal();
    Optional<List<Schedule>> schedules = scheduleService.getAllSchedules(principal.getName());
    if (schedules.isPresent()) {
      List<Schedule> scheduleList = schedules.get();
      List<ScheduleInternalDto> list = scheduleList.stream()
          .sorted(Comparator.comparing(e -> e.getTime()))
          .map(ScheduleInternalDto::new).collect(Collectors.toList());
      return Response.ok(list).build();
    }
    return Response.status(Status.UNAUTHORIZED).build();
  }

  @UserSecured
  @GET
  @Path("{date}")
  public Response getSchedulesForDay(@PathParam("date") String date,
      @Context SecurityContext context) {
    Principal principal = context.getUserPrincipal();
    Optional<List<Schedule>> allSchedules = scheduleService.getAllSchedules(principal.getName());
    if (allSchedules.isPresent()) {
      List<Schedule> schedules = allSchedules.get();
      LocalDate query;
      try {
        query = LocalDate.parse(date);
      } catch (Exception e) {
        logger.error("could not parse date query");
        return Response.status(Status.BAD_REQUEST).build();
      }
      List<ScheduleInternalDto> result = schedules.stream()
          .filter(schedule -> schedule.getTime().equals(query))
          .sorted((a, b) -> -a.getTime().compareTo(b.getTime()))
          .map(ScheduleInternalDto::new)
          .collect(Collectors.toList());
      return Response.ok(result).build();
    }
    return Response.status(Status.UNAUTHORIZED).build();
  }

  @Path("mark/{id}")
  @GET
  @UserSecured
  public Response markScheduleAsDone(@PathParam("id") Long id, @Context SecurityContext context) {
    logger.info("trying to mark schedule with id " + id);
    Principal principal = context.getUserPrincipal();
    Optional<Schedule> schedule = scheduleService.markSchedule(id, principal.getName());
    if(schedule.isPresent()) {
      donationEvent.fire(schedule.get());
      return Response.ok(schedule).build();
    }
    return Response.status(Status.BAD_REQUEST).build();
  }


  class ScheduleInternalDto {

    private Long id;
    private String region;
    private User user;
    private String date;
    private ScheduleStatus status;

    public ScheduleInternalDto(Schedule schedule) {
      id = schedule.getId();
      region = schedule.getRegion().getName();
      user = schedule.getUser();
      date = schedule.getTime().toString();
      status = schedule.getStatus();
    }

    public Long getId() {
      return id;
    }

    public void setId(Long id) {
      this.id = id;
    }

    public String getRegion() {
      return region;
    }

    public void setRegion(String region) {
      this.region = region;
    }

    public User getUser() {
      return user;
    }

    public void setUser(User user) {
      this.user = user;
    }

    public String getDate() {
      return date;
    }

    public void setDate(String date) {
      this.date = date;
    }

    public ScheduleStatus getStatus() {
      return status;
    }

    public void setStatus(ScheduleStatus status) {
      this.status = status;
    }
  }

}
