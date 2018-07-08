package com.mql.redhope.web.user;

import com.mql.redhope.buisness.UserService;
import com.mql.redhope.models.BloodType;
import com.mql.redhope.models.Profile;
import com.mql.redhope.web.admin.UserSecured;
import java.security.Principal;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

/**
 * @author mehdithe
 */
@Path("users")
public class UserResource {

  @Inject
  UserService userService;

  @UserSecured
  @GET
  @Path("/me")
  public Response getUserInfo(@Context SecurityContext context) {
    Principal principal = context.getUserPrincipal();
    Profile profile = userService.getUser(principal.getName());
    JsonObject jsonObject = getInfoObject(principal.getName(), profile);
    return Response.ok(jsonObject).build();
  }

  @POST
  @Path("/update")
  @UserSecured
  public Response updateProfile(@Context SecurityContext context, JsonObject body) {
    Principal principal = context.getUserPrincipal();
    Profile profile = userService.updateProfile(principal.getName(), body);
    return Response.ok(profile).build();
  }

  private JsonObject getInfoObject(String email, Profile profile) {
    return Json.createObjectBuilder().add("firstName", profile.getFirstName())
        .add("lastName", profile.getLastName())
        .add("address", profile.getOptionalAddress().orElse(""))
        .add("type", profile.getOptionalBloodType().orElse(BloodType.UNSET).toString())
        .add("phone", profile.getOptionalPhone().orElse(""))
        .add("completed", profile.isCompleted())
        .add("email", email)
        .build();
  }
}
