package com.suprun.periodicals.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface GenericDao<T, ID extends Serializable> {

    Optional<T> findOne(ID id) throws DaoException;

    List<T> findAll() throws DaoException;

    List<T> findAll(long skip, long limit) throws DaoException;

    T insert(T obj) throws DaoException;

    void update(T obj) throws DaoException;

    void delete(ID id) throws DaoException;

    default boolean exist(ID id) throws DaoException {
        return findOne(id).isPresent();
    }

    long getCount() throws DaoException;
}
