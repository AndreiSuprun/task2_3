package com.suprun.periodicals.dao.impl;

import com.suprun.periodicals.dao.DaoException;
import com.suprun.periodicals.dao.PeriodicalDao;
import com.suprun.periodicals.dao.mapper.EntityMapper;
import com.suprun.periodicals.dao.mapper.MapperFactory;
import com.suprun.periodicals.entity.Periodical;
import com.suprun.periodicals.util.Resource;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class SqlPeriodicalDao implements PeriodicalDao {
    private final static String SELECT_ALL =
            Resource.QUERIES.getProperty("periodical.select.all");
    private final static String INSERT =
            Resource.QUERIES.getProperty("periodical.insert");
    private final static String UPDATE =
            Resource.QUERIES.getProperty("periodical.update");
    private final static String DELETE =
            Resource.QUERIES.getProperty("periodical.delete");
    private final static String COUNT =
            Resource.QUERIES.getProperty("periodical.count");
    private final static String WHERE_ID =
            Resource.QUERIES.getProperty("periodical.where.id");
    private final static String WHERE_STATUS =
            Resource.QUERIES.getProperty("periodical.where.status");
    private final static String ORDER_BY_STATUS_AND_ID =
            Resource.QUERIES.getProperty("periodical.select.order");

    private final SqlUtilDao<Periodical> sqlUtilDao;

    public SqlPeriodicalDao() {
        this(MapperFactory.getPeriodicalMapper());
    }

    public SqlPeriodicalDao(EntityMapper<Periodical> mapper) {
        this(new SqlUtilDao<>(mapper));
    }

    public SqlPeriodicalDao(SqlUtilDao<Periodical> utilMySqlDao) {
        this.sqlUtilDao = utilMySqlDao;
    }

    @Override
    public Optional<Periodical> findOne(Long id) throws DaoException {
        return sqlUtilDao.findOne(SELECT_ALL + WHERE_ID, id);
    }

    @Override
    public List<Periodical> findAll() throws DaoException {
        return sqlUtilDao.findAll(SELECT_ALL + ORDER_BY_STATUS_AND_ID);
    }

    @Override
    public List<Periodical> findAll(long skip, long limit) throws DaoException {
        if (skip < 0 || limit < 0) {
            throw new DaoException("Skip or limit params cannot be negative");
        }
        return sqlUtilDao.findAll(SELECT_ALL + ORDER_BY_STATUS_AND_ID + SqlUtilDao.LIMIT, skip, limit);
    }

    @Override
    public List<Periodical> findByStatus(boolean status, long skip, long limit) throws DaoException {
        if (skip < 0 || limit < 0) {
            throw new DaoException("Skip or limit params cannot be negative");
        }
        return sqlUtilDao.findAll(SELECT_ALL + WHERE_STATUS + ORDER_BY_STATUS_AND_ID + SqlUtilDao.LIMIT,
                status, skip, limit);
    }

    @Override
    public Periodical insert(Periodical obj) throws DaoException {
        if (Objects.isNull(obj)) {
            throw new DaoException("Attempt to insert nullable periodical");
        }

        Long id = sqlUtilDao.executeInsertWithGeneratedPrimaryKey(
                INSERT,
                obj.getPublisher().getId(),
                obj.getFrequency().getId(),
                obj.getPeriodicalCategory().getId(),
                obj.getName(),
                obj.getPrice(),
                obj.getPeriodicalDescription());
        obj.setId(id);

        return obj;
    }

    @Override
    public void update(Periodical obj) throws DaoException {
        if (Objects.isNull(obj)) {
            throw new DaoException("Attempt to update nullable periodical");
        }

        sqlUtilDao.executeUpdate(
                UPDATE + WHERE_ID,
                obj.getPublisher().getId(),
                obj.getFrequency().getId(),
                obj.getPeriodicalCategory().getId(),
                obj.getName(),
                obj.getAvailability(),
                obj.getPrice(),
                obj.getPeriodicalDescription(),
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
    public long getCountByStatus(boolean periodicalStatus) throws DaoException {
        return sqlUtilDao.getRowsCount(COUNT + WHERE_STATUS, periodicalStatus);
    }
}
