package com.suprun.periodicals.service;

import com.suprun.periodicals.dao.DaoException;
import com.suprun.periodicals.dao.DaoFactory;
import com.suprun.periodicals.dao.SubscriptionDao;
import com.suprun.periodicals.dao.SubscriptionPeriodDao;
import com.suprun.periodicals.entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class SubscriptionService {
    private static final Logger LOGGER =
            LogManager.getLogger(SubscriptionService.class);
    private SubscriptionDao subscriptionDao =
            DaoFactory.getInstance().getSubscriptionDao();
    private SubscriptionPeriodDao subscriptionPeriodDao =
            DaoFactory.getInstance().getSubscriptionPlanDao();
    private PaymentService paymentService =
            ServiceFactory.getPaymentService();
    private PeriodicalService periodicalService =
            ServiceFactory.getPeriodicalService();

    private SubscriptionService() {
    }

    private static class Singleton {

        private final static SubscriptionService INSTANCE = new SubscriptionService();
    }

    public static SubscriptionService getInstance() {
        return SubscriptionService.Singleton.INSTANCE;
    }

    public List<Subscription> findAllSubscriptionsByUserAndStatus(User user,
                                                                  boolean isExpired,
                                                                  long skip,
                                                                  long limit) throws ServiceException {
        LOGGER.debug("Attempt to find all subscriptions by user and status");
        try {
            return subscriptionDao.findByUserAndStatus(user, isExpired, skip, limit);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<Subscription> findAllSubscriptionsByPayment(Payment payment) throws ServiceException {
        LOGGER.debug("Attempt to find all subscriptions by payment");
        List<Subscription> subscriptions;
        try {
            subscriptions = subscriptionDao.findByPayment(payment);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        if (subscriptions.size() > 0) {
            return subscriptions;
        } else {
            LOGGER.error("Payment cannot exist without subscription: {}", payment);
            throw new ServiceException("Payment cannot exist without subscription!");
        }
    }

    public long getSubscriptionsCountByUserAndStatus(User user, boolean isExpired) throws ServiceException {
        LOGGER.debug("Attempt to get active subscriptions count by user");
        try {
            return subscriptionDao.getCountByUserAndStatus(user, isExpired);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void processSubscriptions(User user,
                                     List<Subscription> subscriptions,
                                     BigDecimal totalPrice) throws ServiceException {
        LOGGER.debug("Attempt to process subscriptions");
        if (subscriptions.size() != 0) {
            for (Subscription subscription : subscriptions) {
                Periodical periodical =
                        periodicalService.findPeriodicalById(subscription.getPeriodical().getId())
                                .orElseThrow(() -> new ServiceException(
                                        "Subscription cannot refer to a non-existent periodical"));
                if (!periodical.getAvailability()) {
                    throw new ServiceException("Can't subscribe to periodical with SUSPEND status");
                }
                LocalDate startDate = LocalDate.now().plusMonths(1).withDayOfMonth(1);
                subscription.setStartDate(startDate);
                BigDecimal price = (periodical.getPrice()).multiply(subscription.getSubscriptionPeriod().getRate());
                try {
                    paymentService.createPayment(subscription, price);
                    subscriptionDao.insert(subscription);
                } catch (DaoException e) {
                    throw new ServiceException(e);
                }
            }
        }
    }

    public boolean isAlreadySubscribed(User user, Periodical periodical) throws ServiceException {
        LOGGER.debug("Attempt to check that user is already subscribed");
        try {
            return subscriptionDao.isUserAlreadySubscribed(user, periodical);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<SubscriptionPeriod> findAllSubscriptionPeriods() throws ServiceException {
        LOGGER.debug("Attempt to find all subscription plans");
        try {
            return subscriptionPeriodDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public Optional<SubscriptionPeriod> findSubscriptionPeriodById(Integer id) throws ServiceException {
        LOGGER.debug("Attempt to find subscription plan by id");
        try {
            return subscriptionPeriodDao.findOne(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
