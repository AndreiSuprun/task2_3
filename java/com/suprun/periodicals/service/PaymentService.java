package com.suprun.periodicals.service;

import com.suprun.periodicals.dao.DaoException;
import com.suprun.periodicals.dao.DaoFactory;
import com.suprun.periodicals.dao.PaymentDao;
import com.suprun.periodicals.entity.Payment;
import com.suprun.periodicals.entity.Subscription;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class PaymentService {

    private static final Logger LOGGER = LogManager.getLogger(PaymentService.class);
    private PaymentDao paymentDao = DaoFactory.getInstance().getPaymentDao();

    private PaymentService() {
    }

    private static class Singleton {
        private final static PaymentService INSTANCE = new PaymentService();
    }

    public static PaymentService getInstance() {
        return Singleton.INSTANCE;
    }

    public Optional<Payment> findPaymentById(Long id) throws ServiceException {
        LOGGER.debug("Attempt to find payment by id");
        try{
            return paymentDao.findOne(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<Payment> findAllPayments(long skip, long limit) throws ServiceException {
        LOGGER.debug("Attempt to find all payments");
        try{
            return paymentDao.findAll(skip, limit);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public Payment createPayment(Subscription subscription, BigDecimal totalPrice) throws ServiceException {
        LOGGER.debug("Attempt to create payment");
        Payment payment = Payment.newBuilder()
                .setSubscription(subscription)
                .setTotalPrice(totalPrice)
                .setPaymentDate(LocalDate.now())
                .build();
        try{
            return paymentDao.insert(payment);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public long getPaymentsCount() throws ServiceException {
        LOGGER.debug("Attempt to get payments count");
        try{
            return paymentDao.getCount();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
