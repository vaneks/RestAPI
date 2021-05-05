package com.vaneks.restapi.dao;

import java.util.List;

public interface EntityInterface<T> {
    List<T> getAll();

    T getById(long id);

    void save(T t);

    void update(T t);

    void deleteById(long id);
}

