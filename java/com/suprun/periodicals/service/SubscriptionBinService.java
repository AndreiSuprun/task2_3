package com.suprun.periodicals.service;

import com.suprun.periodicals.entity.Periodical;
import com.suprun.periodicals.entity.Subscription;
import com.suprun.periodicals.entity.SubscriptionPeriod;
import com.suprun.periodicals.entity.User;
import com.suprun.periodicals.service.entity.SubscriptionBin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class SubscriptionBinService {

    private static final Logger LOGGER =
            LogManager.getLogger();
    private PeriodicalService periodicalService = ServiceFactory.getPeriodicalService();

    private SubscriptionBinService() {
    }

    private static class Singleton {
        private final static SubscriptionBinService INSTANCE = new SubscriptionBinService();
    }

    public static SubscriptionBinService getInstance() {
        return SubscriptionBinService.Singleton.INSTANCE;
    }

    public boolean addItemToCart(SubscriptionBin subscriptionBin,
                                 User user,
                                 Periodical periodical,
                                 SubscriptionPeriod subscriptionPeriod) {
        LOGGER.debug("Attempt to add item to cart");
        Subscription subscription = Subscription.newBuilder()
                .setUser(user)
                .setPeriodical(periodical)
                .setSubscriptionPeriod(subscriptionPeriod)
                .build();
        return subscriptionBin.addItem(subscription);
    }

    public void removeItemFromCart(SubscriptionBin subscriptionBin, Long binItemId) {
        LOGGER.debug("Attempt to remove item from cart");
        subscriptionBin.removeItem(binItemId);
    }

    public void removeAllItemFromCart(SubscriptionBin subscriptionBin) {
        LOGGER.debug("Attempt to remove all item from cart");
        subscriptionBin.removeAll();
    }

    public void updateShoppingCartItemsFromDatabase(SubscriptionBin subscriptionBin) throws ServiceException {
        for (Subscription subscription : subscriptionBin.getItems()) {
            Optional<Periodical> periodicalOpt =
                    periodicalService.findPeriodicalById(subscription.getPeriodical().getId());
            if (periodicalOpt.isPresent()) {
                subscription.setPeriodical(periodicalOpt.get());
                subscriptionBin.updateItem(subscription);
            } else {
                LOGGER.error("A subscription cannot refer to a non-existent periodical");
                throw new ServiceException("A subscription cannot refer to a non-existent periodical");
            }
        }
    }
}
