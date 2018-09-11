package com.mql.redhope.web.stock;

import com.mql.redhope.web.admin.AdminSecured;
import java.util.Optional;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * @author mehdithe
 */
@Path("stock")
public class StockResource {

  @POST
  @AdminSecured
  public Response updateStock(JsonObject body) {
    String donationId = body.getString("donationId");
    long plasma = extractValue(body, "plasma");
    long platelet = extractValue(body, "platelet");
    long redCell = extractValue(body, "redCell");

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
