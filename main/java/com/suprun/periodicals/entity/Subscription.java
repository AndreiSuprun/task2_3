package com.suprun.periodicals.entity;

import java.io.Serializable;
import java.time.LocalDate;

public class Subscription implements Serializable {
    private static final long serialVersionUID = 8540619478891337776L;

    private Long id;
    private User user;
    private Payment payment;
    private Periodical periodical;
    private SubscriptionPeriod subscriptionPlan;
    private LocalDate startDate;
    private LocalDate endDate;

    public static class Builder {
        private final Subscription subscription;

        public Builder() {
            subscription = new Subscription();
        }

        public Builder setId(Long id) {
            subscription.setId(id);
            return this;
        }

        public Builder setUser(User user) {
            subscription.setUser(user);
            return this;
        }

        public Builder setPayment(Payment payment) {
            subscription.payment = payment;
            return this;
        }

        public Builder setPeriodical(Periodical periodical) {
            subscription.setPeriodical(periodical);
            return this;
        }

        public Builder setSubscriptionPlan(SubscriptionPeriod subscriptionPlan) {
            subscription.setSubscriptionPlan(subscriptionPlan);
            return this;
        }

        public Builder setStartDate(LocalDate startDate) {
            subscription.setStartDate(startDate);
            return this;
        }

        public Builder setEndDate(LocalDate endDate) {
            subscription.setEndDate(endDate);
            return this;
        }

        public Subscription build() {
            return subscription;
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Subscription() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Periodical getPeriodical() {
        return periodical;
    }

    public void setPeriodical(Periodical periodical) {
        this.periodical = periodical;
    }

    public SubscriptionPeriod getSubscriptionPlan() {
        return subscriptionPlan;
    }

    public void setSubscriptionPlan(SubscriptionPeriod subscriptionPlan) {
        this.subscriptionPlan = subscriptionPlan;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subscription that = (Subscription) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Subscription.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("user=" + user)
                .add("payment=" + payment)
                .add("periodical=" + periodical)
                .add("subscriptionPlan=" + subscriptionPlan)
                .add("startDate=" + startDate)
                .add("endDate=" + endDate)
                .toString();
    }
}
