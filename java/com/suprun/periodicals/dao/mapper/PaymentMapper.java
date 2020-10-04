package com.suprun.periodicals.dao.mapper;

import com.suprun.periodicals.entity.Payment;
import com.suprun.periodicals.entity.Subscription;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class PaymentMapper implements EntityMapper<Payment> {

    private static final String ID_FIELD = "payment_id";
    private static final String TOTAL_PRICE_FIELD = "total_price";
    private static final String PAID_FIELD = "paid";
    private static final String CREATION_DATE_FIELD = "creation_date";
    private static final String PAYMENT_DATE_FIELD = "payment_date";
    private final EntityMapper<Subscription> subscriptionMapper;

    public PaymentMapper() {
        this(new SubscriptionMapper());
    }

    public PaymentMapper(EntityMapper<Subscription> subscriptionMapper) {
        this.subscriptionMapper = subscriptionMapper;
    }

    @Override
    public Payment mapToObject(ResultSet resultSet, String tablePrefix)
            throws SQLException {
        Subscription tempSubscription = subscriptionMapper.mapToObject(resultSet);

        return Payment.newBuilder()
                .setId(resultSet.getLong(
                        tablePrefix + ID_FIELD))
                .setSubscription(tempSubscription)
                .setTotalPrice(resultSet.getBigDecimal(
                        tablePrefix + TOTAL_PRICE_FIELD))
                .setCreationDate(resultSet.getObject(
                        tablePrefix + CREATION_DATE_FIELD, LocalDate.class))
                .setPaymentDate(resultSet.getObject(
                        tablePrefix + PAYMENT_DATE_FIELD, LocalDate.class))
                .setPaid(Boolean.parseBoolean(resultSet.getString(tablePrefix + PAID_FIELD)))
                .build();
    }
}
