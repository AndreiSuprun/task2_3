package com.suprun.periodicals.service;

import com.suprun.periodicals.dao.*;
import com.suprun.periodicals.entity.Frequency;
import com.suprun.periodicals.entity.Periodical;
import com.suprun.periodicals.entity.PeriodicalCategory;
import com.suprun.periodicals.entity.Publisher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;


public class PeriodicalService {
    private static final Logger LOGGER = LogManager.getLogger(PeriodicalService.class);
    private PeriodicalDao periodicalDao =
            DaoFactory.getInstance().getPeriodicalDao();
    private PeriodicalCategoryDao periodicalTypeDao =
            DaoFactory.getInstance().getPeriodicalTypeDao();
    private FrequencyDao frequencyDao =
            DaoFactory.getInstance().getFrequencyDao();
    private PublisherDao publisherDao =
            DaoFactory.getInstance().getPublisherDao();

    private PeriodicalService() {
    }

    private static class Singleton {
        private final static PeriodicalService INSTANCE = new PeriodicalService();
    }

    public static PeriodicalService getInstance() {
        return PeriodicalService.Singleton.INSTANCE;
    }

    public Periodical createPeriodical(Periodical periodical) throws ServiceException {
        try{
            return periodicalDao.insert(periodical);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void updatePeriodical(Periodical periodical) throws ServiceException {
        LOGGER.debug("Attempt to update periodical");
        try{
            periodicalDao.update(periodical);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void changeStatus(Periodical periodical, boolean newStatus) throws ServiceException {
        LOGGER.debug("Attempt to change status of periodical");
        if (periodical.getAvailability() != newStatus) {
            periodical.setAvailability(newStatus);
            updatePeriodical(periodical);
        }
    }

    public Optional<Periodical> findPeriodicalById(Long id) throws ServiceException {
        LOGGER.debug("Attempt to find periodical by id");
        try{
            return periodicalDao.findOne(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<Periodical> findAllPeriodicals(long skip, long limit) throws ServiceException {
        LOGGER.debug("Attempt to find all periodicals");
        try{
            return periodicalDao.findAll(skip, limit);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<Periodical> findAllPeriodicalsByStatus(boolean status, long skip, long limit) throws ServiceException {
        LOGGER.debug("Attempt to find all periodicals by status");
        try{
            return periodicalDao.findByStatus(status, skip, limit);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public long getPeriodicalsCountByStatus(boolean status) throws ServiceException {
        LOGGER.debug("Attempt to get count of periodicals by status");
        try{
            return periodicalDao.getCountByStatus(status);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public long getPeriodicalsCount() throws ServiceException {
        LOGGER.debug("Attempt to get count of periodicals");
        try{
            return periodicalDao.getCount();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<PeriodicalCategory> findAllPeriodicalCategory() throws ServiceException {
        LOGGER.debug("Attempt to find all periodical types");
        try{
            return periodicalTypeDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<Frequency> findAllFrequencies() throws ServiceException {
        LOGGER.debug("Attempt to find all frequencies");
        try{
            return frequencyDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<Publisher> findAllPublishers() throws ServiceException {
        LOGGER.debug("Attempt to find all publishers");
        try{
            return publisherDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
