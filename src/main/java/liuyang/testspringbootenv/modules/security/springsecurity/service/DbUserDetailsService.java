package liuyang.testspringbootenv.modules.security.springsecurity.service;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * @author liuyang
 * @scine 2021/4/19
 */
//@Service
@Slf4j
public class DbUserDetailsService implements UserDetailsService {

    // JPA::注入JPA服务
    // MyBatis-Plus::注入MyBatis-Plus服务
    // 类推：也可以接驳其他NoSQL数据源，如LDAP、MongoDB等...

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        // JPA
        //return jpaLoadUserByUsername(s);

        // MyBatis-Plus
        //return myBatisPlusLoadUserByUsername(s);

        // Mock
        return mockLoadUserByUsername(s);
    }

    // Spring Data JPA impl
    private UserDetails jpaLoadUserByUsername(String s) {
        UserDetails userDetails = null;
        // TODO
        return userDetails;
    }

    // MyBatis-Plus impl
    private UserDetails myBatisPlusLoadUserByUsername(String s) {
        UserDetails userDetails = null;
        // TODO
        return userDetails;
    }

    // Mock
    private UserDetails mockLoadUserByUsername(String s) {
        // UserDetails是一个接口
        UserDetails userDetails = null;
        // Mock begin
        if ("liuyang".equals(s)) {
            // userDetails = User.withUsername("liuyang").password("123").authorities("authoritiesxxx").roles("admin", "xxx").build();
            userDetails = User.withUsername("liuyang")
                    .password("$2a$10$kLtge.Vw3FRRNGWPYdCNse/zOiqN0sM/Z/7F8solSY0hShG5wgt46") // 123
                    .authorities("authoritiesxxx")
                    .roles("admin", "xxx").build();
        }
        if ("liuyang2".equals(s)) {
            userDetails = User.withUsername("liuyang2")
                    .password("$2a$10$xu4HmcMsZrNdp0742ql06upKStrdJ6cn34P924wuWtR6yx2rtv0BCov.") // 123123
                    .authorities("authoritiesxxx")
                    .roles("admin").build();
        }
        if (null == userDetails) {// 经测试，这里要是返回空，则应用直接就挂了。
            throw new UsernameNotFoundException("用户名不存在");
        }
        return userDetails;
        // Mock end
    }
}
