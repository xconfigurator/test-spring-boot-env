package liuyang.testspringbootenv.modules.security.springsecurity.filter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
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
 * 2024/2/10
 * Thor的方案是把登录功能放在了这里。这样整个REST形式的登录、注销的就都不需要写Spring MVC的Controller了！业务与安全代码彻底分离！ 干脆利索
 * 编写
 * https://www.bilibili.com/video/BV1Fd4y1k7rq?p=38&vd_source=8bd7b24b38e3e12c558d839b352b32f4
 * 修正
 * https://www.bilibili.com/video/BV1Fd4y1k7rq/?p=45&spm_id_from=pageDriver&vd_source=8bd7b24b38e3e12c558d839b352b32f4
 *
 * @author liuyang(wx)
 * @since 2022/1/26
 *        2024/2/10 Thor的方案好！
 */
//@RequiredArgsConstructor
@Slf4j
public class RESTLoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper om;// final类型的初始化只能在构造函数中进行。

    public RESTLoginAuthenticationFilter(ObjectMapper om) {
        this.om = om;

        // Thor的配置方法。其实都可以从SecurityConfig中配置。
        // 为调试方便：接收POST和GET请求
        // this.setPostOnly(false);// 默认是true，只接受post请求。 Thor
        // 指定登录URL。 Thor
        //this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login"));

        // 另一种指定登录失败、成功的方式 （liuyang发现的）：
        // TODO this.setAuthenticationFailureHandler();
        // TODO this.setAuthenticationSuccessHandler();
    }

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

    // 另一种指定登录失败、成功的方式。 Thor方案：
    /*
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // TODO 认证成功
        // 1. 生成Token并返回
        // 2. 刷Redis
        super.successfulAuthentication(request, response, chain, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        // TODO 认证失败
        // 生成错误信息并返回
        super.unsuccessfulAuthentication(request, response, failed);
    }
     */
}
