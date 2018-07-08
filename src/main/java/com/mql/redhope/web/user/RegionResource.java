package com.mql.redhope.web.user;

import com.mql.redhope.dao.RegionDao;
import com.mql.redhope.web.admin.UserSecured;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * @author mehdithe
 */
@Path("regions")
@Stateless
public class RegionResource {

  @Inject
  RegionDao regionDao;

  @UserSecured
  @GET
  public Response getAllRegions() {
    List<String> regions = regionDao.findAll().stream()
        .map(r -> r.getName())
        .sorted(String::compareTo)
        .collect(Collectors.toList());
    return Response.ok(regions).build();
  }
}
