package com.mql.redhope.web;

import com.mql.redhope.buisness.UserService;
import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "MailActivation", urlPatterns = {"/validate"})
public class MailActivation extends HttpServlet {

  @Inject
  UserService userService;

  Logger log = LoggerFactory.getLogger(getClass());

  protected void doPost(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {

  }

  protected void doGet(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {
    String token = request.getParameter("token");
    boolean success = userService.activateAccount(token);
    log.info("token is " + token);
    // TODO: add error page if not success
    response.sendRedirect("/redhope/faces/login-ind.xhtml");
  }
}
