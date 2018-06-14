package com.mql.redhope.buisness.impl;

import com.mql.redhope.buisness.TokenIssuer;
import com.mql.redhope.web.admin.jwt.KeyGenerator;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import javax.inject.Inject;
import javax.ws.rs.core.UriInfo;
import org.slf4j.Logger;

/**
 * @author mehdithe
 */
public class DefaultTokenIssuer implements TokenIssuer {

  @Inject
  KeyGenerator keyGenerator;

  @Inject
  Logger logger;

  @Override
  public String issueToken(String email, UriInfo uriInfo) {
    Key key = keyGenerator.generateKey();
    String jwtToken = Jwts.builder()
        .setSubject(email)
        .setIssuer(uriInfo.getAbsolutePath().toString())
        .setIssuedAt(new Date())
        .setExpiration(toDate(LocalDateTime.now().plusMinutes(60L)))
        .signWith(SignatureAlgorithm.HS512, key)
        .compact();
    logger.info("#### generating token for a key : " + jwtToken + " - " + key);
    return jwtToken;
  }

  private Date toDate(LocalDateTime localDateTime) {
    return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
  }
}
