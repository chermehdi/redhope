package com.mql.redhope.dao;

import com.mql.redhope.domain.models.Region;

/**
 * @author mehdithe
 */
public interface RegionDao extends AbstractDao<Region> {

  Region findByName(String regionName);

}
