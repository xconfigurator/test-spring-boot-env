package liuyang.testspringbootenv.modules.security.springsecurity;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author liuyang
 * @scine 2021/4/19
 */
@Slf4j
public class BCryptTest {

    // 用BCrypt神奇的地方是BCrypt.gensalt()是随机的，仍然能够被验证出来！
    @Test
    public void testEncrypt() {
       String passwd123 = BCrypt.hashpw("123", BCrypt.gensalt());
       log.info(passwd123);
       String passwd123123 = BCrypt.hashpw("123123", BCrypt.gensalt());
       log.info(passwd123123);
    }

    @Test
    public void testDecrypt() {
        // 123
        // $2a$10$kLtge.Vw3FRRNGWPYdCNse/zOiqN0sM/Z/7F8solSY0hShG5wgt46
        Assertions.assertEquals(true, BCrypt.checkpw("123", "$2a$10$kLtge.Vw3FRRNGWPYdCNse/zOiqN0sM/Z/7F8solSY0hShG5wgt46"));

        // 123123
        // $2a$10$xu4HmcMsZrNdp0742ql06upKdJ6cn34P924wuWtR6yx2rtv0BCov.
        Assertions.assertEquals(true, BCrypt.checkpw("123123", "$2a$10$xu4HmcMsZrNdp0742ql06upKdJ6cn34P924wuWtR6yx2rtv0BCov."));
    }
}