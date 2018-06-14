package com.mql.redhope.web.filters;

import com.mql.redhope.models.User;
import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Security filter to test the security level of the current user
 *
 * @author mehdithe
 */
@WebFilter(filterName = "SecurityFilter", urlPatterns = {"/*"})
public class SecurityFilter implements Filter {

  @Inject
  Logger log;

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {

  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    HttpSession session = httpRequest.getSession(false);
    User currentUser = null;
    if (session != null && (currentUser = (User) session.getAttribute("user")) != null) {
      log.info("user entered resource " + httpRequest.getRequestURL() + ". User " + currentUser);
    }
    chain.doFilter(request, response);
  }

  @Override
  public void destroy() {

  }
}
