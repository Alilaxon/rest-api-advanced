package com.epam.esm.persistance.dao;

import org.hibernate.HibernateException;

import java.util.List;
import java.util.Optional;

public interface DefaultRepository<T> {

    Optional<T> get(long id);

    List<T> getAll();

    void save(T t);

    void delete(T t);
}
