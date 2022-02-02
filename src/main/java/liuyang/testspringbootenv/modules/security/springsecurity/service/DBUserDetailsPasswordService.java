package liuyang.testspringbootenv.modules.security.springsecurity.service;

import liuyang.testspringbootenv.modules.security.springsecurity.dao.jpa.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.stereotype.Service;

/**
 * 用来进行密码升级的
 * 登录的时候将用户密码升级（用户无感知）
 * @author liuyang(wx)
 * @since 2022/1/28
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class DBUserDetailsPasswordService implements UserDetailsPasswordService {

    private UserRepository userRepository;

    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        // TODO @param newPassword是系统已经按照新的加密方式处理好的密文。

        return null;
    }
}
