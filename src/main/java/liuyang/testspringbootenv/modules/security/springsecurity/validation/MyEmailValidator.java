package liuyang.testspringbootenv.modules.security.springsecurity.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * @author liuyang(wx)
 * @since 2022/1/26
 */
public class MyEmailValidator implements ConstraintValidator<MyEmail, String> {

    private final static String PATTERN_EMAIL = "/^ [a-zA-Z0-9_.]+@ [a-zA-Z0-9-]+ [.a-zA-Z]+$/.";
    private static Pattern pattern;

    @Override
    public void initialize(MyEmail constraintAnnotation) {
        pattern = Pattern.compile(PATTERN_EMAIL);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return pattern.matcher(s).matches();
    }
}
