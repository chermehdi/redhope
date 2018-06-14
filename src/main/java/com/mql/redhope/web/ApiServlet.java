package com.mql.redhope.web;

import com.mql.redhope.buisness.AdminService;
import java.io.IOException;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;

/**
 * @author mehdithe
 */
@WebServlet(name = "ApiServlet", urlPatterns = {"/api/test"})
public class ApiServlet extends HttpServlet {

  @Inject
  Logger log;

  @Inject
  AdminService adminService;

  protected void doPost(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {
    process(request, response);
  }

  private void process(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String regionName = request.getParameter("region");
    String json = "";
    response.setHeader("Content-Type", "application/json");
    response.getWriter().print(json);
    response.getWriter().close();
    response.setStatus(200);
  }

  protected void doGet(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {
    process(request, response);
  }
}
