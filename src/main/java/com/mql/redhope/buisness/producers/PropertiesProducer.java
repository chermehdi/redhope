package com.mql.redhope.buisness.producers;


import com.mql.redhope.buisness.qualifiers.UserProperties;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

/**
 * @author Mehdi Maick
 */
@Dependent
public class PropertiesProducer {

  /**
   * produces the properties coming from the properties file defined in the injection point by
   * default it retrieves the mail-config.properties file see {UserProperties}
   */
  @Produces
  @UserProperties
  public Properties getMailProperties(InjectionPoint ip) {
    String fileName = ip.getAnnotated().getAnnotation(UserProperties.class).value();
    return loadProperties(fileName);
  }

  private Properties loadProperties(String fileInClasspath) {
    InputStream is = this.getClass().getClassLoader()
        .getResourceAsStream(fileInClasspath);
    try {
      Properties properties = new Properties();
      properties.load(is);
      return properties;
    } catch (IOException e) {
    }
    return null;
  }
}