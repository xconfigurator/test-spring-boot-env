package liuyang.testspringbootenv.modules.security.springsecurity.filter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * 只是提供了一个过滤规则，配合SecurityConfig中的配置，完成对某一个REST式请求的抽取信息过程。
 *
 * 注： 对比三更草堂的方案，三更草堂把Login和Logout放在了一个独立的Controller中。独立到Controller中的方案扩展起来会更容易理解，比如增加图形验证码功能。
 *      他在39.其他认证方案畅享一节中提到了本类使用的方案，即集成UsernamePasswordAuthenticationFilter，可以听下思路。
 *      如果再这里添加验证码，则需要在本过滤器之前再添加一个过滤器，来校验验证码。
 *
 * @author liuyang(wx)
 * @since 2022/1/26
 */
@RequiredArgsConstructor
@Slf4j
public class RESTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper om;// final类型的初始化只能在构造函数中进行。

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authRequest;
        try (InputStream is = request.getInputStream()) {
            JsonNode jsonNode = om.readTree(is);
            String username = jsonNode.get("username").textValue();
            String password = jsonNode.get("password").textValue();
            log.debug("username, password = {}, {}", username, password);
            authRequest = new UsernamePasswordAuthenticationToken(username, password);
        } catch (IOException e) {
            throw new BadCredentialsException("没有找到用户名或密码参数。");
        }
        setDetails(request, authRequest);
        //return super.attemptAuthentication(request, response);// 错误!
        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
