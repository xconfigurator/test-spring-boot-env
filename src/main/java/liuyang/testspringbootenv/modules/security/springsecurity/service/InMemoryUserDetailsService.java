package liuyang.testspringbootenv.modules.security.springsecurity.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author liuyang
 * @scine 2021/10/14
 */
@Service
@Slf4j
public class InMemoryUserDetailsService extends InMemoryUserDetailsManager {

    @PostConstruct
    private void init() {// 202110140935 ok
        // 如何得到这些加密串？
        // 答：参考BCryptTest
        this.createUser(User.withUsername("liuyang")
                .password("$2a$10$kLtge.Vw3FRRNGWPYdCNse/zOiqN0sM/Z/7F8solSY0hShG5wgt46") // 123
                .authorities("r1", "r2", "r3", "r4")
                //.roles("admin", "xxx") // 20211014 貌似roles和authorities有干扰，指定了roles之后，authorities就不生效了。
                .build());
        this.createUser(User.withUsername("liuyang2")
                .password("$2a$10$xu4HmcMsZrNdp0742ql06upKdJ6cn34P924wuWtR6yx2rtv0BCov.") // 123123
                .authorities("r2")
                //.roles("admin") // 20211014 貌似roles和authorities有干扰，指定了roles之后，authorities就不生效了。
                .build());
    }
}
