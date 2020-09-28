package com.suprun.periodicals.dao;

import com.suprun.periodicals.entity.Payment;
import com.suprun.periodicals.entity.Periodical;
import com.suprun.periodicals.entity.Subscription;
import com.suprun.periodicals.entity.User;

import java.util.List;

public interface SubscriptionDao extends GenericDao<Subscription, Long> {

    boolean isUserAlreadySubscribed(User user, Periodical periodical);

    List<Subscription> findByUser(User user, long skip, long limit);

    List<Subscription> findByPayment(Payment payment);

    long getCountByUser(User user);
}
