package com.mql.redhope.web.security;

import com.mql.redhope.models.User;
import java.security.Principal;
import javax.ws.rs.core.SecurityContext;

/**
 * @author mehdithe
 */
public class UserSecurityContext implements SecurityContext {

  private User currentUser;

  public UserSecurityContext(User currentUser) {
    this.currentUser = currentUser;
  }

  @Override
  public Principal getUserPrincipal() {
    return () -> currentUser.getEmail();
  }

  @Override
  public boolean isUserInRole(String role) {
    return currentUser.getRoles().stream()
        .filter(e -> e.getRoleName().compareToIgnoreCase(role) == 0)
        .findAny()
        .isPresent();
  }

  @Override
  public boolean isSecure() {
    return false;
  }

  @Override
  public String getAuthenticationScheme() {
    return "JWT";
  }

  @Override
  public String toString() {
    return "UserSecurityContext{" +
        "currentUser=" + currentUser +
        '}';
  }
}
