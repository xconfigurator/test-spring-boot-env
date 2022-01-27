package liuyang.testspringbootenv.modules.security.springsecurity.validation;

import org.passay.*;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

/**
 * 基于Passay
 *
 * @author liuyang(wx)
 * @since 2022/1/27
 */
public class MyPasswordValidator implements ConstraintValidator<MyPassword, String> {

    private PasswordValidator passwordValidator;

    @Override
    public void initialize(MyPassword constraintAnnotation) {
        passwordValidator = new PasswordValidator(Arrays.asList(
                new LengthRule(8, 30),
                new CharacterRule(EnglishCharacterData.UpperCase, 1),// 至少一个大写字符
                new CharacterRule(EnglishCharacterData.LowerCase, 1),// 至少一个小写字符
                new CharacterRule(EnglishCharacterData.Special, 1),// 至少一个特殊字符
                new IllegalSequenceRule(EnglishSequenceData.Alphabetical, 5, false), // 不允许五个连续(字母表顺序)字符
                new IllegalSequenceRule(EnglishSequenceData.Numerical, 5, false), // 不允许五个连续数字
                new IllegalSequenceRule(EnglishSequenceData.USQwerty, 5, false), // 不允许键盘上连续五个字符
                new WhitespaceRule()
        ));
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return passwordValidator.validate(new PasswordData(s)).isValid();
    }
}
