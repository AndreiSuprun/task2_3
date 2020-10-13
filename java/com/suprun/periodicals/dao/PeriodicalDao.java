package com.suprun.periodicals.dao;

import com.suprun.periodicals.entity.Periodical;

import java.util.List;

public interface PeriodicalDao extends GenericDao<Periodical, Long> {

    List<Periodical> findByStatus(boolean isAvailable, long skip, long limit) throws DaoException;

    long getCountByStatus(boolean isAvailable) throws DaoException;
}
