package com.mql.redhope.dao;

import com.mql.redhope.domain.models.Profile;

public interface ProfileDao extends AbstractDao<Profile> {

  Profile update(Profile profile);
}
