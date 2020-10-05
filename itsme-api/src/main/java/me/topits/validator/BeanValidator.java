package me.topits.validator;


import me.topits.exception.BaseException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;
import java.util.Set;

/**
 * @author QingKe
 * @date 2020-09-23 00:30
 **/
public class BeanValidator {

    private static final ValidatorFactory VALIDATOR_FACTORY = Validation.buildDefaultValidatorFactory();

    public static <T> void validate(T object) {
        StringBuilder errorMessage = null;
        Set<ConstraintViolation<T>> set = VALIDATOR_FACTORY.getValidator().validate(object, Default.class);
        if (set != null && set.size() > 0) {
            errorMessage = new StringBuilder();
            boolean isFirst = true;
            for (ConstraintViolation<T> cv : set) {
                if (isFirst) {
                    errorMessage.append(cv.getMessage());
                    isFirst = false;
                }else {
                    errorMessage.append(",").append(cv.getMessage());
                }
            }
        }
        if (errorMessage != null) {
            throw new BaseException(errorMessage.toString());
        }
    }
}
