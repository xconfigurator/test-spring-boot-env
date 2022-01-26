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
