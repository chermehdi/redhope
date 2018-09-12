package com.mql.redhope.web.exceptions;

import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * @author mehdithe
 */
@Provider
public class SecurityExceptionMapper implements ExceptionMapper<SecurityException> {

  @Override
  public Response toResponse(SecurityException exception) {
    String message = exception.getMessage();
    JsonObject payload = Json.createObjectBuilder()
        .add("message", message)
        .add("error", true)
        .build();
    return Response.status(Status.UNAUTHORIZED)
        .entity(payload)
        .build();
  }
}
