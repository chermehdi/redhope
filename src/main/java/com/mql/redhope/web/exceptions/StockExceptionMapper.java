package com.mql.redhope.web.exceptions;

import com.mql.redhope.domain.exceptions.StockException;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * @author mehdithe
 */
@Provider
public class StockExceptionMapper implements ExceptionMapper<StockException> {

  @Override
  public Response toResponse(StockException exception) {
    String message = exception.getMessage();
    JsonObject response = Json.createObjectBuilder()
        .add("error", true)
        .add("message", message)
        .build();
    return Response.status(Status.BAD_REQUEST).entity(response).build();
  }
}
