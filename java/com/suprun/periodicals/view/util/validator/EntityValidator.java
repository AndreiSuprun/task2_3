package com.suprun.periodicals.view.util.validator;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public abstract class EntityValidator {

    public Map<String, Boolean> validate(Serializable object) {
        Map<String, Boolean> errors = new HashMap<>();
        validateObject(errors, object);
        return errors;
    }

    protected abstract void validateObject(Map<String, Boolean> errors,
                                           Serializable object);

    protected <T> void validateField(Validator<T> validator,
                                     T field,
                                     String errorAttribute,
                                     Map<String, Boolean> errors) {
        if (!validator.isValid(field)) {
            errors.put(errorAttribute, true);
        }
    }
}
