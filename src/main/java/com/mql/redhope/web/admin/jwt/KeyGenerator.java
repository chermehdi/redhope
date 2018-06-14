package com.mql.redhope.web.admin.jwt;

import java.security.Key;

/**
 * @author mehdithe
 */
public interface KeyGenerator {

  Key generateKey();
}
