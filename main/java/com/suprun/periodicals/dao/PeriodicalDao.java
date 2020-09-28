package com.suprun.periodicals.dao;

import com.suprun.periodicals.entity.Periodical;

import java.util.List;

public interface PeriodicalDao extends GenericDao<Periodical, Long> {

    List<Periodical> findByStatus(boolean isAvailable, long skip, long limit);

    long getCountByStatus(boolean isAvailable);
}
