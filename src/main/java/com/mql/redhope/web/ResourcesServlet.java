package com.mql.redhope.web;

import com.mql.redhope.buisness.ResourceManager;
import com.mql.redhope.buisness.qualifiers.UserProperties;
import java.io.IOException;
import java.util.Collection;
import java.util.Properties;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.slf4j.Logger;

/**
 * @author mehdithe
 */
@WebServlet(urlPatterns = {"/static/*"})
@MultipartConfig
public class ResourcesServlet extends HttpServlet {

  @Inject
  Logger logger;

  @Inject
  ResourceManager resourceManager;

  /**
   * serves the user static resources, mainly images the routes is not required, i.e images are not
   * protected routes anyone can access them
   */
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
  }

  /**
   * handles creating a saving user images
   */
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    logger.info("In the post request");
    Collection<Part> parts = req.getParts();
    for (Part part : parts) {
      logger.info(part.getSubmittedFileName());
      logger.info(String.valueOf(part.getHeaderNames()));
      logger.info(part.getContentType());
    }
  }
}
