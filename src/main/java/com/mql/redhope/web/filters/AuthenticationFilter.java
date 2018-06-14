package com.mql.redhope.web.filters;


import java.io.IOException;
import javax.faces.application.ResourceHandler;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthenticationFilter implements Filter {

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {

  }

  // TODO: refactor this
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    HttpServletResponse httpResponse = (HttpServletResponse) response;
    HttpSession session = httpRequest.getSession(false);

    String individualLoginRoute = httpRequest.getContextPath() + "/faces/login-ind.xhtml";
    String hospitalLoginRoute = httpRequest.getContextPath() + "/faces/login-hos.xhtml";
    String individualSingnupRoute = httpRequest.getContextPath() + "/faces/signup-ind.xhtml";
    String hospitalSingnupRoute = httpRequest.getContextPath() + "/faces/signup-hos.xhtml";

    String homePage = httpRequest.getContextPath() + "/faces/index.xhtml";
    String redirectUrl = httpRequest.getContextPath() + "/faces/signup-general.xhtml";

    String requestUrl = httpRequest.getRequestURI();

    boolean isResourceRequest = httpRequest.getRequestURI()
        .startsWith(httpRequest.getContextPath() + ResourceHandler.RESOURCE_IDENTIFIER);
    boolean isLoginRequest =
        requestUrl.equals(hospitalLoginRoute) || requestUrl.equals(individualLoginRoute);
    boolean isSessionSet = session != null && session.getAttribute("user") != null;
    boolean isHomePage = requestUrl.equals(homePage);
    boolean isSignupRequest = requestUrl.equals(redirectUrl) ||
        requestUrl.equals(hospitalSingnupRoute) || requestUrl.equals(individualSingnupRoute);
    if (isResourceRequest || isLoginRequest || isSessionSet || isHomePage || isSignupRequest) {
      chain.doFilter(request, response);
    } else {
      httpResponse.sendRedirect(redirectUrl);
    }
  }

  @Override
  public void destroy() {

  }
}
