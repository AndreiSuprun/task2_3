package com.suprun.periodicals.view.util.validator;

import com.suprun.periodicals.entity.Periodical;
import com.suprun.periodicals.view.constants.Attributes;
import com.suprun.periodicals.view.util.validator.impl.DescriptionValidator;
import com.suprun.periodicals.view.util.validator.impl.PeriodicalPriceValidator;
import com.suprun.periodicals.view.util.validator.impl.TitleValidator;

import java.io.Serializable;
import java.util.Map;

public class PeriodicalValidator extends EntityValidator {

    @Override
    protected void validateObject(Map<String, Boolean> errors, Serializable object) {
        if (!(object instanceof Periodical)) {
            throw new IllegalArgumentException("The object has not Periodical type");
        }
        Periodical periodical = (Periodical) object;
        validateField(new TitleValidator(),
                periodical.getName(),
                Attributes.ERROR_PERIODICAL_NAME,
                errors);
        validateField(new DescriptionValidator(),
                periodical.getPeriodicalDescription(),
                Attributes.ERROR_PERIODICAL_DESCRIPTION,
                errors);
        validateField(new PeriodicalPriceValidator(),
                periodical.getPrice(),
                Attributes.ERROR_PERIODICAL_PRICE,
                errors);
    }
}
