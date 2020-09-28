package com.suprun.periodicals.dao.impl;

import com.suprun.periodicals.dao.DaoException;
import com.suprun.periodicals.dao.UserDao;
import com.suprun.periodicals.dao.connection.ConnectionPool;
import com.suprun.periodicals.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class SqlUserDao implements UserDao {

    private final static String SELECT_ALL = "SELECT * FROM users JOIN roles ON users.role_id = roles.role_id";
    private final static String INSERT = "INSERT INTO users (role_id, first_name, last_name, email, password) " +
            "VALUES (?, ?, ?, ?, ?)";
    private final static String UPDATE = "UPDATE users SET role_id = ?, first_name = ?, last_name = ?, email = ?, " +
            "password = ?";
    private final static String DELETE = "DELETE FROM users";
    private final static String COUNT = "SELECT COUNT(user_id) FROM users)";
    private final static String WHERE_ID = " WHERE users.user_id = ?";
    private final static String WHERE_EMAIL = " WHERE users.email = ?";
    private final static String LIMIT = " LIMIT ?,?";
    private static final String LIMIT_ONE = " LIMIT 1";

    private final SqlUtilDao sqlUtilDao;

    public SqlUserDao(SqlUtilDao sqlUtilDao) {
        this.sqlUtilDao = sqlUtilDao;
    }

    public SqlUserDao() {
    }

    @Override
    public Optional<User> findOne(Long id) {
        return sqlUtilDao.findOne(SELECT_ALL + WHERE_ID, id);
    }

    @Override
    public Optional<User> findOneByEmail(String email) {
        return sqlUtilDao.findOne(SELECT_ALL + WHERE_EMAIL, email);
    }

    @Override
    public List<User> findAll() throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL)) {
            sqlUtilDao.setParamsToStatement(statement);
            ResultSet resultSet = statement.executeQuery();
            return mapToObjectList(resultSet); // to do
        } catch (SQLException e) {
            LOGGER.error("Failed to execute query", e);
            throw new DaoException(e);
        }
    }

    public List<User> findAll(long skip, long limit) throws DaoException {
        if (skip < 0 || limit < 0) {
            throw new DaoException("Skip or limit params cannot be negative");
        }
        return sqlUtilDao.findAll(SELECT_ALL + sqlUtilDao.LIMIT, skip, limit);
    }

    @Override
    public User insert(User obj) throws DaoException {
        if (obj == null) {
            throw new DaoException("Attempt to insert nullable user");
        }
        long id = sqlUtilDao.executeInsertWithGeneratedPrimaryKey(
                INSERT,
                obj.getRole().getId(),
                obj.getFirstName(),
                obj.getLastName(),
                obj.getEmail(),
                obj.getPassword(),
        obj.setId(id);
        return obj;
    }

    @Override
    public void update(User obj) throws DaoException {
        if (obj == null) {
            throw new DaoException("Attempt to update nullable user");
        }
        sqlUtilDao.executeUpdate(
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
        sqlUtilDao.executeUpdate(
                DELETE + WHERE_ID,
                id);
    }

    @Override
    public long getCount() throws DaoException {
        return sqlUtilDao.getRowsCount(COUNT);
    }

    @Override
    public boolean existByEmail(String email) {
        return findOneByEmail(email).isPresent();
    }
}
