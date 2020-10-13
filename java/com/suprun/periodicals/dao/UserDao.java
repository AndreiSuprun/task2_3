package com.suprun.periodicals.dao;

import com.suprun.periodicals.entity.User;

import java.util.Optional;

public interface UserDao extends GenericDao<User, Long> {

    Optional<User> findOneByEmail(String email) throws DaoException;

    boolean existByEmail(String email) throws DaoException;
}
