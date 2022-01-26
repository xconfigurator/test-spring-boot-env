package liuyang.testspringbootenv.modules.security.springsecurity;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

/**
 * @author liuyang(wx)
 * @since 2022/1/26
 */
@Slf4j
public class MessageDigestPasswordEncoderTest {

    private static PasswordEncoder encoder = new MessageDigestPasswordEncoder("sha-1");

    @Test
    void testEncrypt() {
        // 同样的明文，两次加密是不一样的。但都可以匹配。
        log.info("passwd = {} ", encoder.encode("123"));
        log.info("passwd = {}", encoder.encode("123"));
    }

    @Test
    void testDecrypt() {
        log.info("matches = {}", encoder.matches("123", "{/BUp1tCpQfLNZfDtiClEU9mR2TwxbP5ntdElDc6hIUM=}f3596774df7e6c0988403370fbe9f55f588bb44c"));
    }
}
