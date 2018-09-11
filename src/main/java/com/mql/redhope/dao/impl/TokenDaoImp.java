package com.mql.redhope.dao.impl;

import com.mql.redhope.dao.TokenDao;
import com.mql.redhope.domain.models.Token;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class TokenDaoImp implements TokenDao {

  @PersistenceContext
  private EntityManager em;

  @Override
  public Token findById(Long id) {
    try {
      return em.find(Token.class, id);
    } catch (Exception e) {
      return null;
    }
  }

  @Override
  public List<Token> findAll() {
    try {
      return em.createQuery("SELECT t FROM Token t", Token.class).getResultList();
    } catch (Exception e) {
      return Collections.emptyList();
    }
  }

  @Override
  public void save(Token value) {
    try {
      Objects.requireNonNull(value);
      em.persist(value);
    } catch (Exception e) {
      System.out.println("could not save token entity");
    }
  }

  @Override
  public Token delete(Token value) {
    try {
      em.remove(value);
      return value;
    } catch (Exception e) {
      System.out.println("could not delete token entity");
      return null;
    }
  }
}
