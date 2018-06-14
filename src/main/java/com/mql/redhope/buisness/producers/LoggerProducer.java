package com.mql.redhope.buisness.producers;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mehdithe
 */
public class LoggerProducer {

  @Produces
  public Logger getLogger(InjectionPoint ip) {
    return LoggerFactory.getLogger(ip.getMember().getDeclaringClass().getName());
  }
}

