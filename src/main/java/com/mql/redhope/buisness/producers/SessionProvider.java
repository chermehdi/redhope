package com.mql.redhope.buisness.producers;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 * @author mehdithe
 */
public class SessionProvider {

  @Produces
  HttpSession getSession(InjectionPoint ip) {
    return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
  }
}
