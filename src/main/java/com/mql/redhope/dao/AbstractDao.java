package com.mql.redhope.dao;

import java.util.List;
import java.util.function.Predicate;
import javax.ejb.Stateless;

@Stateless
public interface AbstractDao<T> {

  T findById(Long id);
  
  List<T> findAll();
  
  void save(T value);
  
  T delete(T value);
  
  List<T> findByCriteria(Predicate<T> p);
}
