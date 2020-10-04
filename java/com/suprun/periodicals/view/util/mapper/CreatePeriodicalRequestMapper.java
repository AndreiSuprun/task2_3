package com.suprun.periodicals.view.util.mapper;

import com.suprun.periodicals.entity.Frequency;
import com.suprun.periodicals.entity.Periodical;
import com.suprun.periodicals.entity.PeriodicalCategory;
import com.suprun.periodicals.entity.Publisher;
import com.suprun.periodicals.view.constants.RequestParameters;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

public class CreatePeriodicalRequestMapper implements RequestEntityMapper<Periodical> {

    @Override
    public Periodical mapToObject(HttpServletRequest request) {
        Integer periodicalTypeId = Integer.valueOf(
                request.getParameter(RequestParameters.PERIODICAL_CATEGORY_ID));
        Integer periodicalFrequencyId = Integer.valueOf(
                request.getParameter(RequestParameters.PERIODICAL_FREQUENCY_ID));
        Long periodicalPublisherId = Long.valueOf(
                request.getParameter(RequestParameters.PERIODICAL_PUBLISHER_ID));

        BigDecimal price = new BigDecimal(
                request.getParameter(RequestParameters.PERIODICAL_PRICE));
        PeriodicalCategory periodicalCategory = PeriodicalCategory.newBuilder()
                .setId(periodicalTypeId)
                .build();
        Frequency frequency = Frequency.newBuilder()
                .setId(periodicalFrequencyId)
                .build();
        Publisher publisher = new Publisher(periodicalPublisherId, null);

        return Periodical.newBuilder()
                .setName(request.getParameter(RequestParameters.PERIODICAL_NAME))
                .setAvailability(true)
                .setPeriodicalDescription(request.getParameter(RequestParameters.PERIODICAL_DESCRIPTION))
                .setPrice(price)
                .setPeriodicalCategory(periodicalCategory)
                .setFrequency(frequency)
                .setPublisher(publisher)
                .build();
    }
}
