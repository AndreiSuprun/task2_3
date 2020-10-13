package com.suprun.periodicals.service;

public class ServiceFactory {

    public static UserService getUserService() {
        return UserService.getInstance();
    }

    public static PeriodicalService getPeriodicalService() {
        return PeriodicalService.getInstance();
    }

    public static PaymentService getPaymentService() {
        return PaymentService.getInstance();
    }

    public static SubscriptionService getSubscriptionService() {
        return SubscriptionService.getInstance();
    }

    public static SubscriptionBinService getSubscriptionBinService() {
        return SubscriptionBinService.getInstance();
    }
}
