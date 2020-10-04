package com.suprun.periodicals.view.util.mapper;

import com.suprun.periodicals.entity.Periodical;
import com.suprun.periodicals.view.constants.RequestParameters;

import javax.servlet.http.HttpServletRequest;

public class EditPeriodicalRequestMapper implements RequestEntityMapper<Periodical> {

    @Override
    public Periodical mapToObject(HttpServletRequest request) {
        Long periodicalId = Long.valueOf(
                request.getParameter(RequestParameters.PERIODICAL_ID));
        Periodical periodicalDTO =
                RequestMapperFactory.getCreatePeriodicalMapper()
                        .mapToObject(request);
        periodicalDTO.setId(periodicalId);
        return periodicalDTO;
    }
}
