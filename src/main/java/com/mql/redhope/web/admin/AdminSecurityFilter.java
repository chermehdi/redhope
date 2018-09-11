package com.mql.redhope.web.admin;

import com.mql.redhope.dao.UserDao;
import com.mql.redhope.domain.models.Role;
import com.mql.redhope.domain.models.User;
import com.mql.redhope.web.admin.jwt.KeyGenerator;
import com.mql.redhope.web.security.UserSecurityContext;
import io.jsonwebtoken.Jwts;
import java.io.IOException;
import java.security.Key;
import javax.annotation.Priority;
import javax.inject.Inject;
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
@AdminSecured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AdminSecurityFilter implements ContainerRequestFilter {

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

      User currentUser = userDao.findByEmail(email);
      boolean isAdmin = currentUser.getRoles().stream()
          .map(Role::getRoleName)
          .filter(this::isAdminRole)
          .findAny()
          .isPresent();
      logger.info("the current user " + currentUser);
      // if the current user is not an admin throw a runtime exception, so the execution is aborted
      if (!isAdmin) {
        throw new RuntimeException("the user is not an administrator");
      }
      // create the request context object
      requestContext.setSecurityContext(new UserSecurityContext(currentUser));
      logger.info("#### valid token : " + authorizationHeader);
    } catch (Exception e) {
      logger.info("#### invalid token : " + authorizationHeader);
      requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
    }
  }

  private boolean isAdminRole(String s) {
    return s.toLowerCase().equals("admin");
  }
}
