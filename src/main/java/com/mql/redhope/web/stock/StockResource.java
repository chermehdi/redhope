package com.mql.redhope.web.stock;

import com.mql.redhope.buisness.AdminService;
import com.mql.redhope.buisness.StockService;
import com.mql.redhope.domain.models.Donation;
import com.mql.redhope.domain.models.User;
import com.mql.redhope.web.admin.AdminSecured;
import java.security.Principal;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

/**
 * @author mehdithe
 */
@Path("stock")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StockResource {

  @Inject
  StockService stockService;

  @Inject
  AdminService adminService;

  @POST
  @AdminSecured
  public Response updateStock(JsonObject body) {
    String donationId = body.getString("donationId");
    long plasma = extractValue(body, "plasma");
    long platelet = extractValue(body, "platelet");
    long redCell = extractValue(body, "redCell");
    Donation donation = stockService.updateDonation(donationId, plasma, platelet, redCell);
    if (donation == null) {
      return Response.status(Status.BAD_REQUEST).build();
    }
    return Response.ok(donation).build();
  }

  @PUT
  @Path("remove")
  @AdminSecured
  public Response removeFromStock(JsonObject body) {
    String donationId = body.getString("donationId");
    // here 0 denotes that it's not chosen otherwise it is
    long plasma = extractValue(body, "plasma");
    long platelet = extractValue(body, "platelet");
    long redCell = extractValue(body, "redCell");
    long mask = 1 << plasma;
    mask |= (platelet == 1 ? 1 << 2 : 0);
    mask |= (redCell == 1 ? 1 << 3 : 0);
    // here the first bit is for plasma, second is for platelet and third for red cells
    Donation donation = stockService.removeFromStock(donationId, mask);
    return Response.ok(donation).build();
  }

  @GET
  @AdminSecured
  public Response getStockData(@Context SecurityContext context) {
    String adminEmail = context.getUserPrincipal().getName();
    User user = adminService.getEmailInformations(adminEmail);
    String regionName = user.getRegion().getName();
    JsonObject stockInfo = stockService.getStockInfo(regionName);
    return Response.ok(stockInfo).build();
  }

  private long extractValue(JsonObject body, String key) {
    String ret = body.getString(key);
    if (ret == null || ret.isEmpty()) {
      return 0L;
    }
    try {
      return Long.parseLong(ret);
    } catch (Exception e) {
      return 0L;
    }
  }
}
