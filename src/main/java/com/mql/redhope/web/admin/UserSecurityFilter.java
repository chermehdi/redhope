package com.mql.redhope.web.admin;

import com.mql.redhope.dao.UserDao;
import com.mql.redhope.domain.models.User;
import com.mql.redhope.web.admin.jwt.KeyGenerator;
import com.mql.redhope.web.security.UserSecurityContext;
import io.jsonwebtoken.Jwts;
import java.io.IOException;
import java.security.Key;
import javax.annotation.Priority;
import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import org.slf4j.Logger;

/**
 * @author mehdithe
 */
@UserSecured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class UserSecurityFilter implements ContainerRequestFilter {

  @Inject
  Logger logger;

  @Inject
  KeyGenerator keyGenerator;

  @Inject
  UserDao userDao;

  @Override
  public void filter(ContainerRequestContext requestContext) throws IOException {
    MultivaluedMap<String, String> headers = requestContext.getHeaders();
    headers.forEach((key, value) -> logger.info(key + "    " + value));
    String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
    logger.info("this is the authorization Header " + authorizationHeader);
    if (authorizationHeader == null) {
      throw new NotAuthorizedException("Authorization header must be provided");
    }
    try {
      // Validate the token
      Key key = keyGenerator.generateKey();
      // get the subject from the token
      String email = Jwts.parser()
          .setSigningKey(key)
          .parseClaimsJws(authorizationHeader)
          .getBody()
          .getSubject();

      User user = userDao.findByEmail(email);
      // any regular user is allowed to view the resource
      if (user == null) {
        throw new EntityNotFoundException("Could not find user for given token");
      }
      // create the request context object
      requestContext.setSecurityContext(new UserSecurityContext(user));
      logger.info("#### valid token : " + authorizationHeader);
    } catch (Exception e) {
      logger.info("#### invalid token : " + authorizationHeader);
      requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
    }
  }
}
