package com.suprun.periodicals.dao.impl;

import com.suprun.periodicals.dao.DaoException;
import com.suprun.periodicals.dao.SubscriptionPeriodDao;
import com.suprun.periodicals.dao.mapper.EntityMapper;
import com.suprun.periodicals.dao.mapper.MapperFactory;
import com.suprun.periodicals.entity.SubscriptionPeriod;
import com.suprun.periodicals.util.Resource;

import java.util.List;
import java.util.Optional;

public class SqlSubscriptionPeriodDao implements SubscriptionPeriodDao {

    private final static String SELECT_ALL =
            Resource.QUERIES.getProperty("subscription.plan.select.all");
    private final static String INSERT =
            Resource.QUERIES.getProperty("subscription.plan.insert");
    private final static String UPDATE =
            Resource.QUERIES.getProperty("subscription.plan.update");
    private final static String DELETE =
            Resource.QUERIES.getProperty("subscription.plan.delete");
    private final static String COUNT =
            Resource.QUERIES.getProperty("subscription.plan.count");
    private final static String WHERE_ID =
            Resource.QUERIES.getProperty("subscription.plan.where.id");

    private final SqlUtilDao<SubscriptionPeriod> sqlUtilDao;

    public SqlSubscriptionPeriodDao() {
        this(MapperFactory.getSubscriptionPeriodMapper());
    }

    public SqlSubscriptionPeriodDao(EntityMapper<SubscriptionPeriod> mapper) {
        this(new SqlUtilDao<>(mapper));
    }

    public SqlSubscriptionPeriodDao(SqlUtilDao<SubscriptionPeriod> sqlUtilDao) {
        this.sqlUtilDao = sqlUtilDao;
    }

    @Override
    public Optional<SubscriptionPeriod> findOne(Integer id) throws DaoException {
        return sqlUtilDao.findOne(SELECT_ALL + WHERE_ID, id);
    }

    @Override
    public List<SubscriptionPeriod> findAll() throws DaoException {
        return sqlUtilDao.findAll(SELECT_ALL);
    }

    public List<SubscriptionPeriod> findAll(long skip, long limit) throws DaoException {
        if (skip < 0 || limit < 0) {
            throw new DaoException("Skip or limit params cannot be negative");
        }
        return sqlUtilDao.findAll(SELECT_ALL + SqlUtilDao.LIMIT, skip, limit);
    }

    @Override
    public SubscriptionPeriod insert(SubscriptionPeriod obj) throws DaoException {
        if (obj == null) {
            throw new DaoException("Attempt to insert nullable subscription plan");
        }

        Integer id = sqlUtilDao.executeInsertWithGeneratedPrimaryKey(
                INSERT,
                Integer.class,
                obj.getName(),
                obj.getMonthsAmount(),
                obj.getRate());
        obj.setId(id);

        return obj;
    }

    @Override
    public void update(SubscriptionPeriod obj) throws DaoException {
        if (obj == null) {
            throw new DaoException("Attempt to update nullable subscription plan");
        }

        sqlUtilDao.executeUpdate(
                UPDATE + WHERE_ID,
                obj.getName(),
                obj.getMonthsAmount(),
                obj.getRate(),
                obj.getId());
    }

    @Override
    public void delete(Integer id) throws DaoException {
        sqlUtilDao.executeUpdate(
                DELETE + WHERE_ID,
                id);
    }

    @Override
    public long getCount() throws DaoException {
        return sqlUtilDao.getRowsCount(COUNT);
    }

}
