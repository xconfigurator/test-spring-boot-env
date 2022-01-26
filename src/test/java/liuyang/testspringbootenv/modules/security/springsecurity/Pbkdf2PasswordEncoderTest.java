package liuyang.testspringbootenv.modules.security.springsecurity;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

/**
 * @author liuyang(wx)
 * @since 2022/1/26
 */
@Slf4j
public class Pbkdf2PasswordEncoderTest {

    private static PasswordEncoder encoder = new Pbkdf2PasswordEncoder();

    @Test
    void testEncrypt() {
        // 同样的明文，两次加密是不一样的。但都可以匹配。
        log.info("passwd = {} ", encoder.encode("123"));
        log.info("passwd = {}", encoder.encode("123"));
    }

    @Test
    void testDecrypt() {
        log.info("matches = {}", encoder.matches("123", "21b36eeb81ade26d919299b4d62132f825b07d1fcccddc8c7b48611712db849d00b99e353304c331"));
    }
}
