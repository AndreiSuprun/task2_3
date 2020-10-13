package com.suprun.periodicals.dao.impl;

import com.suprun.periodicals.dao.DaoException;
import com.suprun.periodicals.dao.UserDao;
import com.suprun.periodicals.dao.connection.ConnectionPool;
import com.suprun.periodicals.dao.mapper.EntityMapper;
import com.suprun.periodicals.dao.mapper.MapperFactory;
import com.suprun.periodicals.entity.User;
import com.suprun.periodicals.util.Resource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class SqlUserDao implements UserDao {

    private final static String SELECT_ALL =
            Resource.QUERIES.getProperty("user.select.all");
    private final static String INSERT =
            Resource.QUERIES.getProperty("user.insert");
    private final static String UPDATE =
            Resource.QUERIES.getProperty("user.update");
    private final static String DELETE =
            Resource.QUERIES.getProperty("user.delete");
    private final static String COUNT =
            Resource.QUERIES.getProperty("user.count");
    private final static String WHERE_ID =
            Resource.QUERIES.getProperty("user.where.id");
    private final static String WHERE_EMAIL =
            Resource.QUERIES.getProperty("user.where.email");

    private final SqlUtilDao<User> userSqlUtilDao;

    public SqlUserDao() {
        this(MapperFactory.getUserMapper());
    }

    public SqlUserDao(EntityMapper<User> mapper) {
        this(new SqlUtilDao<User>(mapper));
    }

    public SqlUserDao(SqlUtilDao<User> userSqlUtilDao) {
        this.userSqlUtilDao = userSqlUtilDao;
    }

    @Override
    public Optional<User> findOne(Long id) throws DaoException {
        return userSqlUtilDao.findOne(SELECT_ALL + WHERE_ID, id);
    }

    @Override
    public Optional<User> findOneByEmail(String email) throws DaoException {
        return userSqlUtilDao.findOne(SELECT_ALL + WHERE_EMAIL, email);
    }

    @Override
    public List<User> findAll() throws DaoException {
        return userSqlUtilDao.findAll(SELECT_ALL);
    }

    public List<User> findAll(long skip, long limit) throws DaoException {
        if (skip < 0 || limit < 0) {
            throw new DaoException("Skip or limit params cannot be negative");
        }
        return userSqlUtilDao.findAll(SELECT_ALL + SqlUtilDao.LIMIT, skip, limit);
    }

    @Override
    public User insert(User obj) throws DaoException {
        if (obj == null) {
            throw new DaoException("Attempt to insert nullable user");
        }

        long id = userSqlUtilDao.executeInsertWithGeneratedPrimaryKey(
                INSERT,
                obj.getRole().getId(),
                obj.getFirstName(),
                obj.getLastName(),
                obj.getEmail(),
                obj.getPassword());

        obj.setId(id);
        return obj;
    }

    @Override
    public void update(User obj) throws DaoException {
        if (obj == null) {
            throw new DaoException("Attempt to update nullable user");
        }
        userSqlUtilDao.executeUpdate(
                UPDATE + WHERE_ID,
                obj.getRole().getId(),
                obj.getFirstName(),
                obj.getLastName(),
                obj.getEmail(),
                obj.getPassword(),
                obj.getId());
    }

    @Override
    public void delete(Long id) throws DaoException {
        userSqlUtilDao.executeUpdate(
                DELETE + WHERE_ID,
                id);
    }

    @Override
    public long getCount() throws DaoException {
        return userSqlUtilDao.getRowsCount(COUNT);
    }

    @Override
    public boolean existByEmail(String email) throws DaoException {
        return findOneByEmail(email).isPresent();
    }
}
