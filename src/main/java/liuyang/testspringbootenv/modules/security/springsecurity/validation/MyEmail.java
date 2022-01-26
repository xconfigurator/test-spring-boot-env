package liuyang.testspringbootenv.modules.security.springsecurity.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author liuyang(wx)
 * @since 2022/1/26
 */
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MyEmailValidator.class)
@Documented
public @interface MyEmail {
    String message() default "Invalid Email";
    Class<?>[] groups() default {};// Constraint中的功能，对本注解没有太大意义。
    Class<? extends Payload>[] payload() default {};// Constraint中的功能，对本注解没有太大意义。
}
