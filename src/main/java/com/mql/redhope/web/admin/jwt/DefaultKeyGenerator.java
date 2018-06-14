package com.mql.redhope.web.admin.jwt;

import com.mql.redhope.buisness.qualifiers.UserProperties;
import java.security.Key;
import java.util.Properties;
import javax.crypto.spec.SecretKeySpec;
import javax.inject.Inject;

/**
 * @author mehdithe
 */
public class DefaultKeyGenerator implements KeyGenerator {

  @Inject
  @UserProperties("security-keys.properties")
  Properties secret;

  @Override
  public Key generateKey() {
    String secretKey = secret.getProperty("jwt.secret.key");
    Key key = new SecretKeySpec(secretKey.getBytes(), 0, secretKey.getBytes().length, "DES");
    return key;
  }
}
