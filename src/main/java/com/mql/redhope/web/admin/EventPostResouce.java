package com.mql.redhope.web.admin;

import com.mql.redhope.buisness.EventPostService;
import com.mql.redhope.models.EventPost;
import com.mql.redhope.web.security.UserSecurityContext;
import java.util.List;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import org.slf4j.Logger;

/**
 * @author mehdithe
 */
@Path("events")
@Produces(MediaType.APPLICATION_JSON)
public class EventPostResouce {

  @Inject
  EventPostService eventPostService;

  @Inject
  Logger logger;

  private final int DEFAULT_PAGE_SIZE = 5;

  @GET
  @UserSecured
  public List<EventPost> getPosts(@Context SecurityContext context) {
    return eventPostService.getAllPosts();
  }

  @GET
  @UserSecured
  @Path("page/{id}")
  public List<EventPost> getPaginatedPosts(@PathParam("id") int id) {
    int offset = DEFAULT_PAGE_SIZE * (id - 1);
    return eventPostService.getPaginated(offset, DEFAULT_PAGE_SIZE);
  }

  @POST
  @AdminSecured
  @Path("create")
  public EventPost createPost(JsonObject json) {
    return eventPostService.createEventPost(json);
  }
}
