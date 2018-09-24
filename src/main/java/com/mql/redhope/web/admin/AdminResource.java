package com.mql.redhope.web.admin;

import com.mql.redhope.buisness.AdminService;
import com.mql.redhope.buisness.TokenIssuer;
import com.mql.redhope.domain.models.User;
import com.mql.redhope.web.admin.dto.RegionRequest;
import java.util.List;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
@Path("admin")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdminResource {

  @Inject
  TokenIssuer tokenIssuer;

  @Inject
  AdminService adminService;

  @Inject
  Logger logger;

  @Context
  UriInfo uriInfo;

  @POST
  @Path("login")
  public Response token(JsonObject requestBody) {
    String email = requestBody.getString("email");
    String password = requestBody.getString("password");
    User admin = adminService.login(email, password);
    if (admin == null) {
      Response.status(Status.BAD_REQUEST).build();
    }
    String token = tokenIssuer.issueToken(email, uriInfo);
    JsonObject object = Json.createObjectBuilder()
        .add("token", token)
        .add("email", email)
        .build();
    return Response.ok(object).build();
  }

  @GET
  @UserSecured
  @Path("info")
  public Response adminInfo(@Context SecurityContext context) {
    if (context == null || context.getUserPrincipal() == null) {
      return Response.status(Status.UNAUTHORIZED).build();
    }
    String adminEmail = context.getUserPrincipal().getName();
    logger.info("the user admin " + adminEmail);
    if (adminEmail == null) {
      return Response.status(Status.UNAUTHORIZED).build();
    }
    User admin = adminService.getEmailInformations(adminEmail);
    return Response.ok(admin).build();
  }

  @POST
  @UserSecured
  @Path("by-region")
  public Response getUsersByRegion(RegionRequest request) {
    List<User> usersMatchingRegion = adminService.getUsersMatchingRegion(request.getRegionName());
    return Response.ok(usersMatchingRegion).build();
  }
}