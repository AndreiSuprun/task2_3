package com.suprun.periodicals.dao.impl;

import com.suprun.periodicals.dao.DaoException;
import com.suprun.periodicals.dao.FrequencyDao;
import com.suprun.periodicals.dao.mapper.EntityMapper;
import com.suprun.periodicals.dao.mapper.MapperFactory;
import com.suprun.periodicals.entity.Frequency;
import com.suprun.periodicals.util.Resource;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class SqlFrequencyDao implements FrequencyDao {
    private final static String SELECT_ALL =
            Resource.QUERIES.getProperty("frequency.select.all");
    private final static String INSERT =
            Resource.QUERIES.getProperty("frequency.insert");
    private final static String UPDATE =
            Resource.QUERIES.getProperty("frequency.update");
    private final static String DELETE =
            Resource.QUERIES.getProperty("frequency.delete");
    private final static String COUNT =
            Resource.QUERIES.getProperty("frequency.count");
    private final static String WHERE_ID =
            Resource.QUERIES.getProperty("frequency.where.id");

    private final SqlUtilDao<Frequency> sqlUtilDao;

    public SqlFrequencyDao() {
        this(MapperFactory.getFrequencyMapper());
    }

    public SqlFrequencyDao(EntityMapper<Frequency> mapper) {
        this(new SqlUtilDao<>(mapper));
    }

    public SqlFrequencyDao(SqlUtilDao<Frequency> sqlUtilDao) {
        this.sqlUtilDao = sqlUtilDao;
    }

    @Override
    public Optional<Frequency> findOne(Integer id) throws DaoException {
        return sqlUtilDao.findOne(SELECT_ALL + WHERE_ID, id);
    }

    @Override
    public List<Frequency> findAll() throws DaoException {
        return sqlUtilDao.findAll(SELECT_ALL);
    }

    @Override
    public List<Frequency> findAll(long skip, long limit) throws DaoException {
        if (skip < 0 || limit < 0) {
            throw new DaoException("Skip or limit params cannot be negative");
        }
        return sqlUtilDao.findAll(SELECT_ALL + SqlUtilDao.LIMIT, skip, limit);
    }

    @Override
    public Frequency insert(Frequency obj) throws DaoException {
        if (Objects.isNull(obj)) {
            throw new DaoException("Attempt to insert nullable frequency");
        }

        Integer id = sqlUtilDao.executeInsertWithGeneratedPrimaryKey(
                INSERT,
                Integer.class,
                obj.getName(),
                obj.getDescription());
        obj.setId(id);

        return obj;
    }

    @Override
    public void update(Frequency obj) throws DaoException {
        if (obj == null) {
            throw new DaoException("Attempt to update nullable frequency");
        }

        sqlUtilDao.executeUpdate(
                UPDATE + WHERE_ID,
                obj.getName(),
                obj.getDescription(),
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
