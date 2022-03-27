package liuyang.testspringbootenv.modules.security.springsecurity;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

/**
 * @author liuyang(wx)
 * @since 2022/1/26
 */
@Slf4j
public class BCryptPasswordEncoderTest {
    private static PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Test
    void testEncrypt() {
        // 同样的明文，两次加密是不一样的。但都可以匹配。
        //log.info("passwd = {} ", encoder.encode("123"));
        //log.info("passwd = {}", encoder.encode("123"));
        String encode = encoder.encode("123");
        String encode2 = encoder.encode("123");
        log.info("passwd = {}", encode);
        log.info("passwd = {}", encode2);
        log.info("matches = {}", encoder.matches("123", encode));
        log.info("matches = {}", encoder.matches("123", encode2));
    }

    @Test
    void testDecrypt() {
        log.info("matches = {}", encoder.matches("123", "$2a$10$xBPbYwGR06h/k.TGAPESQuzCu6rnc8a/6ruw3Z1lRX1hhh/dlPXXO"));
    }
}
