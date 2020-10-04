package com.suprun.periodicals.dao;

import com.suprun.periodicals.util.Resource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public abstract class DaoFactory {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String DB_CLASS = "factory.class";

    private static volatile DaoFactory instance;

    public static DaoFactory getInstance() throws DaoException {
        if (instance == null) {
            synchronized (DaoFactory.class) {
                if (instance == null) {
                    try {
                        String className = Resource.DATABASE.getProperty(DB_CLASS);
                        instance = (DaoFactory) Class.forName(className)
                                .getDeclaredConstructor().newInstance();
                    } catch (Exception e) {
                        LOGGER.error("Failed to initialize DaoFactory", e);
                        throw new DaoException(e);
                    }
                }
            }
        }
        return instance;
    }

    public abstract UserDao getUserDao();

    public abstract RoleDao getRoleDao();

    public abstract PeriodicalDao getPeriodicalDao();

    public abstract PeriodicalCategoryDao getPeriodicalCategoryDao();

    public abstract FrequencyDao getFrequencyDao();

    public abstract PublisherDao getPublisherDao();

    public abstract PaymentDao getPaymentDao();

    public abstract SubscriptionDao getSubscriptionDao();

    public abstract SubscriptionPeriodDao getSubscriptionPeriodDao();
}
