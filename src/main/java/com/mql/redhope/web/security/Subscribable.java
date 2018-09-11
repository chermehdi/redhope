package com.mql.redhope.web.security;

import java.time.LocalDateTime;

/**
 * @author mehdithe
 */
public interface Subscribable {

  LocalDateTime activeUntil();
}
