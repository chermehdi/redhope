package com.mql.redhope.buisness;

import javax.ws.rs.core.UriInfo;

/**
 * @author mehdithe
 */
public interface TokenIssuer {

  String issueToken(String email, UriInfo uriInfo);
}
