package liuyang.testspringbootenv.modules.security.springsecurity.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author liuyang(wx)
 * @since 2022/1/27
 */
/*
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MyPasswordValidator.class)
 */
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MyIdenticalValidator.class)
public @interface MyIdentical {
    String message() default "Password Inconformity";
    Class<?>[] groups() default {};// Constraint中的功能，对本注解没有太大意义。
    Class<? extends Payload>[] payload() default {};// Constraint中的功能，对本注解没有太大意义。
}
