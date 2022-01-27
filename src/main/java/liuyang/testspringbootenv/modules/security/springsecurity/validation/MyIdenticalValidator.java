package liuyang.testspringbootenv.modules.security.springsecurity.validation;

import liuyang.testspringbootenv.modules.security.springsecurity.domain.dto.UserDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author liuyang(wx)
 * @since 2022/1/27
 */
public class MyIdenticalValidator implements ConstraintValidator<MyIdentical, UserDto> {
    @Override
    public void initialize(MyIdentical constraintAnnotation) {
    }

    @Override
    public boolean isValid(UserDto u, ConstraintValidatorContext constraintValidatorContext) {
        return u.getPassword().equals(u.getPasswordMatch());
    }
}
