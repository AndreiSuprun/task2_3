package com.suprun.periodicals.dao;

import com.suprun.periodicals.entity.Payment;
import com.suprun.periodicals.entity.Periodical;
import com.suprun.periodicals.entity.Subscription;
import com.suprun.periodicals.entity.User;

import java.util.List;

public interface SubscriptionDao extends GenericDao<Subscription, Long> {

    boolean isUserAlreadySubscribed(User user, Periodical periodical) throws DaoException;

    List<Subscription> findByUserAndStatus(User user, boolean isExpired, long skip, long limit) throws DaoException;

    List<Subscription> findByPayment(Payment payment) throws DaoException;

    long getCountByUserAndStatus(User user, boolean isExpired) throws DaoException;
}
