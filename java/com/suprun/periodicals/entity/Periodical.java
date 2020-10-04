package com.suprun.periodicals.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.StringJoiner;

public class Periodical implements Serializable {
    private static final long serialVersionUID = -8852341448368620250L;
    private static final int PRICE_SCALE = 2;

    private Long id;
    private String name;
    private boolean availability;
    private BigDecimal price;
    private Publisher publisher;
    private Frequency frequency;
    private PeriodicalCategory periodicalCategory;
    private String periodicalDescription;

    public static class Builder {
        private final Periodical periodical;

        public Builder() {
            periodical = new Periodical();
        }

        public Builder setId(Long id) {
            periodical.setId(id);
            return this;
        }

        public Builder setName(String name) {
            periodical.setName(name);
            return this;
        }

        public Builder setAvailability(boolean availability) {
            periodical.setAvailability(availability);
            return this;
        }

        public Builder setPrice(BigDecimal price) {
            periodical.setPrice(price);
            return this;
        }

        public Builder setPublisher(Publisher publisher) {
            periodical.setPublisher(publisher);
            return this;
        }

        public Builder setFrequency(Frequency frequency) {
            periodical.setFrequency(frequency);
            return this;
        }

        public Builder setPeriodicalCategory(PeriodicalCategory periodicalCategory) {
            periodical.setPeriodicalCategory(periodicalCategory);
            return this;
        }

        public Builder setPeriodicalDescription(String periodicalDescription) {
            periodical.setPeriodicalDescription(periodicalDescription);
            return this;
        }

        public Periodical build() {
            return periodical;
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Periodical() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        if (price.scale() != PRICE_SCALE)
            this.price = price.setScale(PRICE_SCALE, RoundingMode.HALF_EVEN);
        else
            this.price = price;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }

    public PeriodicalCategory getPeriodicalCategory() {
        return periodicalCategory;
    }

    public void setPeriodicalCategory(PeriodicalCategory periodicalCategory) {
        this.periodicalCategory = periodicalCategory;
    }

    public String getPeriodicalDescription() {
        return periodicalDescription;
    }

    public void setPeriodicalDescription(String periodicalDescription) {
        this.periodicalDescription = periodicalDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Periodical that = (Periodical) o;

        if (availability != that.availability) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (publisher != null ? !publisher.equals(that.publisher) : that.publisher != null) return false;
        if (frequency != null ? !frequency.equals(that.frequency) : that.frequency != null) return false;
        if (periodicalCategory != null ? !periodicalCategory.equals(that.periodicalCategory) : that.periodicalCategory != null)
            return false;
        return periodicalDescription != null ? periodicalDescription.equals(that.periodicalDescription) : that.periodicalDescription == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (availability ? 1 : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (publisher != null ? publisher.hashCode() : 0);
        result = 31 * result + (frequency != null ? frequency.hashCode() : 0);
        result = 31 * result + (periodicalCategory != null ? periodicalCategory.hashCode() : 0);
        result = 31 * result + (periodicalDescription != null ? periodicalDescription.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Periodical.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("availability=" + availability)
                .add("price=" + price)
                .add("publisher=" + publisher)
                .add("frequency=" + frequency)
                .add("periodicalCategory=" + periodicalCategory)
                .add("periodicalDescription=" + periodicalDescription)
                .toString();
    }
}
