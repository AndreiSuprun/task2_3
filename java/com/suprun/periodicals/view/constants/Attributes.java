package com.suprun.periodicals.view.constants;

import com.suprun.periodicals.util.Resource;

public final class Attributes {
    public static final String USER = Resource.ATTRIBUTE.getProperty("user");
    public static final String SUBSCRIPTION_BIN = Resource.ATTRIBUTE.getProperty("subscription.bin");
    public static final String CATALOG = Resource.ATTRIBUTE.getProperty("catalog");
    public static final String PERIODICAL = Resource.ATTRIBUTE.getProperty("periodical");
    public static final String SUBSCRIPTIONS = Resource.ATTRIBUTE.getProperty("subscriptions");
    public static final String ACTIVE_SUBSCRIPTIONS = Resource.ATTRIBUTE.getProperty("subscriptions.active");
    public static final String EXPIRED_SUBSCRIPTIONS = Resource.ATTRIBUTE.getProperty("subscriptions.expired");
    public static final String PAYMENTS = Resource.ATTRIBUTE.getProperty("payments");
    public static final String SUBSCRIPTION_PERIODS = Resource.ATTRIBUTE.getProperty("subscription.periods");
    public static final String PERIODICAL_CATEGORIES = Resource.ATTRIBUTE.getProperty("periodical.categories");
    public static final String FREQUENCIES = Resource.ATTRIBUTE.getProperty("frequencies");
    public static final String PUBLISHERS = Resource.ATTRIBUTE.getProperty("publishers");
    public static final String USER_DTO = Resource.ATTRIBUTE.getProperty("user.dto");
    public static final String PERIODICAL_DTO = Resource.ATTRIBUTE.getProperty("periodical.dto");
    public static final String PAYMENT_DTO = Resource.ATTRIBUTE.getProperty("payment.dto");
    public static final String ERRORS = Resource.ATTRIBUTE.getProperty("errors");
    public static final String ERROR_EMAIL = Resource.ATTRIBUTE.getProperty("error.email");
    public static final String ERROR_PASSWORD = Resource.ATTRIBUTE.getProperty("error.password");
    public static final String ERROR_FIRST_NAME = Resource.ATTRIBUTE.getProperty("error.firstname");
    public static final String ERROR_LAST_NAME = Resource.ATTRIBUTE.getProperty("error.lastname");
    public static final String ERROR_AUTHENTICATION = Resource.ATTRIBUTE.getProperty("error.authentication");
    public static final String ERROR_REGISTRATION = Resource.ATTRIBUTE.getProperty("error.registration");
    public static final String ERROR_IS_ALREADY_SUBSCRIBED = Resource.ATTRIBUTE.getProperty("error.is.already.subscribed");
    public static final String ERROR_PERIODICAL_INVALID = Resource.ATTRIBUTE.getProperty("error.periodical.invalid");
    public static final String ERROR_IS_ALREADY_IN_BIN = Resource.ATTRIBUTE.getProperty("error.is.already.in.bin");
    public static final String ERROR_PERIODICAL_NAME = Resource.ATTRIBUTE.getProperty("error.periodical.name");
    public static final String ERROR_PERIODICAL_DESCRIPTION = Resource.ATTRIBUTE.getProperty("error.periodical.description");
    public static final String ERROR_PERIODICAL_PRICE = Resource.ATTRIBUTE.getProperty("error.periodical.price");
    public static final String PAGINATION_PAGE = Resource.ATTRIBUTE.getProperty("pagination.page");
    public static final String PAGINATION_NUMBER_OF_PAGES = Resource.ATTRIBUTE.getProperty("pagination.number.pages");
    public static final String PAGINATION_ACTIVE_SUBSCRIPTIONS_PAGE = Resource.ATTRIBUTE.getProperty("pagination.active.subscriptions.page");
    public static final String PAGINATION_EXPIRED_SUBSCRIPTIONS_PAGE = Resource.ATTRIBUTE.getProperty("pagination.expired.subscriptions.page");
    public static final String PAGINATION_ACTIVE_SUBSCRIPTIONS_NUMBER_OF_PAGES = Resource.ATTRIBUTE.getProperty("pagination.number.active.subscriptions.page");
    public static final String PAGINATION_EXPIRED_SUBSCRIPTIONS_NUMBER_OF_PAGES = Resource.ATTRIBUTE.getProperty("pagination.number.expired.subscriptions.page");

    private Attributes() {
    }
}
