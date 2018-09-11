package com.mql.redhope.web.user;

import com.mql.redhope.buisness.UserService;
import com.mql.redhope.domain.models.Donation;
import com.mql.redhope.web.admin.UserSecured;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
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
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

/**
 * @author mehdithe
 */
@Path("donations")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DonationResource {

  @Inject
  UserService userService;

  @Context
  UriInfo uriInfo;

  @GET
  @UserSecured
  public List<DonationDto> allDonations(@Context SecurityContext context) {
    List<DonationDto> donations = userService
        .getUserDonations(context.getUserPrincipal().getName())
        .stream()
        .map(DonationDto::new)
        .collect(Collectors.toList());
    return donations;
  }

  @POST
  @Path("create")
  @UserSecured
  public Response createDonation(@Context SecurityContext context) {
    Principal principal = context.getUserPrincipal();
    Donation donation = new Donation();
    userService.addDonationToUser(principal.getName(), donation);
    return Response.created(uriInfo.getAbsolutePath()).build();
  }

  private JsonObject convertDonationToDto(Donation donation) {
    JsonObject object = Json.createObjectBuilder()
        .add("createdAt", donation.getCreatedAt().toString())
        .add("region",
            Json.createObjectBuilder().add("name", donation.getRegion().getName()).build())
        .build();
    return object;
  }

  class DonationDto {

    private String createdAt;
    private String regionName;

    public DonationDto(Donation donation) {
      createdAt = donation.getCreatedAt().toString();
      regionName = donation.getRegion().getName();
    }

    public String getCreatedAt() {
      return createdAt;
    }

    public void setCreatedAt(String createdAt) {
      this.createdAt = createdAt;
    }

    public String getRegionName() {
      return regionName;
    }

    public void setRegionName(String regionName) {
      this.regionName = regionName;
    }
  }
}
