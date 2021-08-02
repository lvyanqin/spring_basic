package lynn.util.validator;

import lynn.util.annotation.IdCard;
import lynn.util.ValidateUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @ClassName IdCardValidator
 * @Description TODO
 * @Date 2019-12-16
 * @Version 0.1
 */
public class IdCardValidator implements ConstraintValidator<IdCard, Object> {
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return ValidateUtil.isIDNumber(value.toString());
    }
}
