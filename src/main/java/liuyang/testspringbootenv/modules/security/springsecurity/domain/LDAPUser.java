package liuyang.testspringbootenv.modules.security.springsecurity.domain;

import org.springframework.security.core.userdetails.UserDetails;

import javax.naming.Name;

/**
 * @author liuyang(wx)
 * @since 2022/1/28
 */
public class LDAPUser {
//public class LDAPUser implements UserDetails {
    private Name id;
    private String username;
    private String password;
}
