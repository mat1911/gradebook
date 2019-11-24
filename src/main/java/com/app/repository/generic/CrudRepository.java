package com.app.repository.generic;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T, ID>{

    Optional<T> add(T t);
    Optional<T> update(T t);
    Optional<T> findOne(ID id);
    List<T> findAll();
    Optional<T> delete(ID id);
    boolean deleteAll();

}
