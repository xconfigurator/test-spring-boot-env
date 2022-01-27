package liuyang.testspringbootenv.modules.security.springsecurity.archived.ref;


import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * 瓦力的ES课程中的写法
 *
 * @author liuyang
 * @scine 2021/7/3
 */
public class AuthProvider implements AuthenticationProvider {

    // 示例 使用MD5 BUT 貌似Spring Security 5.4.5已经没有Md5PasswordEncoder了。


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 输入的值
        String username = authentication.getName();
        String inputPassword = (String) authentication.getCredentials();

        // 数据库查出来的值
        // TODO 14:15左右
        /**
         * User user = userService.findUserByName(userName);
         * if (user == null) {
         *     throw new AuthenticationCredentialsNotFoundException("");
         * }
         */

        // 比较
        // 14:35
        /**
         * if (this.passwordEncoder.isPasswordValid(encPass, rawPass, salt)) {
         *      // 返回验证通过信息
         *      return new UsernamePasswordAuthenticationToken();
         * }
         * // 不匹配就返回异常。
         * throw new BadCrednetialsException("authError");
         */

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
