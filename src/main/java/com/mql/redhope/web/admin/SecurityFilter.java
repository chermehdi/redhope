package com.mql.redhope.web.admin;

import com.mql.redhope.web.admin.jwt.KeyGenerator;
import io.jsonwebtoken.Jwts;
import java.io.IOException;
import java.security.Key;
import java.security.Principal;
import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import org.slf4j.Logger;

/**
 * @author mehdithe
 */
@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class SecurityFilter implements ContainerRequestFilter {

  @Inject
  Logger logger;

  @Inject
  KeyGenerator keyGenerator;

  @Override
  public void filter(ContainerRequestContext requestContext) throws IOException {
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
      Jwts.parser().setSigningKey(key).parseClaimsJws(authorizationHeader);

      // create the request context object
      requestContext.setSecurityContext(new SecurityContext() {
        @Override
        public Principal getUserPrincipal() {
          return () -> email;
        }

        @Override
        public boolean isUserInRole(String role) {
          return false;
        }

        @Override
        public boolean isSecure() {
          return true;
        }

        @Override
        public String getAuthenticationScheme() {
          return "JWT";
        }
      });
      logger.info("#### valid token : " + authorizationHeader);

    } catch (Exception e) {
      logger.info("#### invalid token : " + authorizationHeader);
      requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
    }
  }
}
