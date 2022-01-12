package liuyang.testspringbootenv.modules.security.springsecurity.config;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author liuyang(wx)
 * @since 2022/1/4
 */
//@EnableWebSecurity
public class SecutiryConfig02 extends WebSecurityConfigurerAdapter {
    private static final String USER_NAME = "liuyang";
    private static final String PASSWORD = "123123";


}
