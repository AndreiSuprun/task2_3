package com.suprun.periodicals.dao.impl;

import com.suprun.periodicals.dao.DaoException;
import com.suprun.periodicals.dao.PaymentDao;
import com.suprun.periodicals.dao.mapper.EntityMapper;
import com.suprun.periodicals.dao.mapper.MapperFactory;
import com.suprun.periodicals.entity.Payment;
import com.suprun.periodicals.util.Resource;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class SqlPaymentDao implements PaymentDao {
    private final static String SELECT_ALL =
            Resource.QUERIES.getProperty("payment.select.all");
    private final static String INSERT =
            Resource.QUERIES.getProperty("payment.insert");
    private final static String UPDATE =
            Resource.QUERIES.getProperty("payment.update");
    private final static String DELETE =
            Resource.QUERIES.getProperty("payment.delete");
    private final static String COUNT =
            Resource.QUERIES.getProperty("payment.count");
    private final static String WHERE_ID =
            Resource.QUERIES.getProperty("payment.where.id");
    private final static String ORDER_BY_DATE =
            Resource.QUERIES.getProperty("payment.select.order");

    private final SqlUtilDao<Payment> sqlUtilDao;

    public SqlPaymentDao() {
        this(MapperFactory.getPaymentMapper());
    }

    public SqlPaymentDao(EntityMapper<Payment> mapper) {
        this(new SqlUtilDao<>(mapper));
    }

    public SqlPaymentDao(SqlUtilDao<Payment> sqlUtilDao) {
        this.sqlUtilDao = sqlUtilDao;
    }

    @Override
    public Optional<Payment> findOne(Long id) throws DaoException {
        return sqlUtilDao.findOne(SELECT_ALL + WHERE_ID, id);
    }

    @Override
    public List<Payment> findAll() throws DaoException {
        return sqlUtilDao.findAll(SELECT_ALL + ORDER_BY_DATE);
    }

    @Override
    public List<Payment> findAll(long skip, long limit) throws DaoException {
        if (skip < 0 || limit < 0) {
            throw new DaoException("Skip or limit params cannot be negative");
        }
        return sqlUtilDao.findAll(SELECT_ALL + ORDER_BY_DATE + SqlUtilDao.LIMIT, skip, limit);
    }

    @Override
    public Payment insert(Payment obj) throws DaoException {
        if (Objects.isNull(obj)) {
            throw new DaoException("Attempt to insert nullable payment");
        }

        Long id = sqlUtilDao.executeInsertWithGeneratedPrimaryKey(
                INSERT,
                obj.getSubscription().getId(),
                obj.getTotalPrice(),
                obj.getCreationDate());
        obj.setId(id);

        return obj;
    }

    @Override
    public void update(Payment obj) throws DaoException {
        if (Objects.isNull(obj)) {
            throw new DaoException("Attempt to update nullable payment");
        }

        sqlUtilDao.executeUpdate(
                UPDATE + WHERE_ID,
                obj.getSubscription().getId(),
                obj.getTotalPrice(),
                obj.getCreationDate(),
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
}
