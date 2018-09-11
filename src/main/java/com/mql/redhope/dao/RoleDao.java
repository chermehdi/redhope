package com.mql.redhope.dao;

import com.mql.redhope.domain.models.Role;

public interface RoleDao extends AbstractDao<Role> {

  Role findByName(String name);
}
