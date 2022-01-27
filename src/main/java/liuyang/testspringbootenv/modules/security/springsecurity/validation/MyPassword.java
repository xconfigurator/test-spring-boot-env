package liuyang.testspringbootenv.modules.security.springsecurity.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义密码验证规则
 *
 * @author liuyang(wx)
 * @since 2022/1/27
 */
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MyPasswordValidator.class)
public @interface MyPassword {
    String message() default "Invalid Password";
    Class<?>[] groups() default {};// Constraint中的功能，对本注解没有太大意义。
    Class<? extends Payload>[] payload() default {};// Constraint中的功能，对本注解没有太大意义。
}
