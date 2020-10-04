package com.suprun.periodicals.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.StringJoiner;

public class Payment implements Serializable {

    private static final long serialVersionUID = -22759703204341490L;

    private Long id;
    private Subscription subscription;
    private BigDecimal totalPrice;
    private boolean paid;
    private LocalDate creationDate;
    private LocalDate paymentDate;

    public static class Builder {
        private final Payment payment;

        public Builder() {
            this.payment = new Payment();
        }

        public Builder setId(Long id) {
            payment.setId(id);
            return this;
        }

        public Builder setSubscription(Subscription subscription) {
            payment.setSubscription(subscription);
            return this;
        }

        public Builder setTotalPrice(BigDecimal totalPrice) {
            payment.setTotalPrice(totalPrice);
            return this;
        }

        public Builder setPaid(boolean isPaid) {
            payment.setPaid(isPaid);
            return this;
        }

        public Builder setCreationDate(LocalDate creationDate) {
            payment.setCreationDate(creationDate);
            return this;
        }

        public Builder setPaymentDate(LocalDate paymentDate) {
            payment.setPaymentDate(paymentDate);
            return this;
        }

        public Payment build() {
            return payment;
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Payment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setPaid(boolean isPaid){
        this.paid = isPaid;
    }

    public boolean isPaid() {
        return paid;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Payment payment = (Payment) o;

        if (paid != payment.paid) return false;
        if (id != null ? !id.equals(payment.id) : payment.id != null) return false;
        if (subscription != null ? !subscription.equals(payment.subscription) : payment.subscription != null)
            return false;
        if (totalPrice != null ? !totalPrice.equals(payment.totalPrice) : payment.totalPrice != null) return false;
        if (creationDate != null ? !creationDate.equals(payment.creationDate) : payment.creationDate != null)
            return false;
        return paymentDate != null ? paymentDate.equals(payment.paymentDate) : payment.paymentDate == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (subscription != null ? subscription.hashCode() : 0);
        result = 31 * result + (totalPrice != null ? totalPrice.hashCode() : 0);
        result = 31 * result + (paid ? 1 : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (paymentDate != null ? paymentDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Payment.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("subscription=" + subscription)
                .add("totalPrice=" + totalPrice)
                .add("isPaid=" + paid)
                .add("creationDate=" + creationDate)
                .add("paymentDate=" + paymentDate)
                .toString();
    }
}
