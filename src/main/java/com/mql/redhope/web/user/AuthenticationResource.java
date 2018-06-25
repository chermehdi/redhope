package com.mql.redhope.web.user;

import com.mql.redhope.buisness.TokenIssuer;
import com.mql.redhope.buisness.UserService;
import com.mql.redhope.dto.UserDto;
import com.mql.redhope.models.User;
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
import javax.ws.rs.core.UriInfo;
import org.slf4j.Logger;

/**
 * @author mehdithe
 */
@Path("user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthenticationResource {

  @Inject
  UserService userService;

  @Inject
  Logger logger;

  @Inject
  TokenIssuer issuer;

  @Context
  UriInfo uriInfo;

  @GET
  public String test() {
    return "{me: hello people}";
  }

  @POST
  @Path("login")
  public Response login(JsonObject body) {
    String email = body.getString("email");
    String password = body.getString("password");
    logger.info("Trying to login : {} {}", email, password);
    User user = userService.login(email, password);
    logger.info("the found user is " + user);
    if (user == null) {
      return Response.status(Status.BAD_REQUEST).build();
    }
    return Response.ok(buildTokenResponse(user)).build();
  }

  private JsonObject buildTokenResponse(User user) {
    return Json.createObjectBuilder()
        .add("success", true)
        .add("token", issuer.issueToken(user.getEmail(), uriInfo))
        .add("firstName", user.getProfile().getFirstName())
        .add("lastName", user.getProfile().getLastName())
        .add("email", user.getEmail())
        .build();
  }

  @POST
  @Path("signup")
  public Response signup(JsonObject body) {
    User created = userService.signup(UserDto.from(body));
    if (created == null) {
      return Response.status(Status.BAD_REQUEST).build();
    }
    logger.info("User sign up successful : {}", created.toString());
    return Response.ok(created).build();
  }
}
