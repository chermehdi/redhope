package com.mql.redhope.dao;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.ejb.Stateless;

@Stateless
public interface AbstractDao<T> {

  /**
   * find an object by it's given id
   */
  T findById(Long id);

  /**
   * return the list of all the objects of the given type
   */
  List<T> findAll();

  /**
   * save the current instance of this object
   */
  void save(T value);

  /**
   * delete the given object and return it
   */
  T delete(T value);

  /**
   * filter the list of objects to all those matching the criteria p and returns them as a list, as
   * the method is default, no need to implement it in your Dao implementation
   */
  default List<T> findByCriteria(Predicate<T> p) {
    return findAll().stream()
        .filter(p).collect(Collectors.toList());
  }
}
