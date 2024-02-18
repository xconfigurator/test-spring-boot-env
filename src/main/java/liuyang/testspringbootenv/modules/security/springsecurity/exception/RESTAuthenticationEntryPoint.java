package liuyang.testspringbootenv.modules.security.springsecurity.exception;

import com.alibaba.fastjson.JSON;
import liuyang.testspringbootenv.common.utils.R;
import liuyang.testspringbootenv.modules.security.springsecurity.util.RespHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 授权异常处理（请求未认证资源时返回） e.g. 未携带token访问了受保护的资源页面
 * 会被过滤器链中的ExceptionTranslationFilter调用
 *
 * Security filter chain: [
 * WebAsyncManagerIntegrationFilter
 * SecurityContextPersistenceFilter
 * HeaderWriterFilter
 * CorsFilter
 * LogoutFilter
 * RequestCacheAwareFilter
 * SecurityContextHolderAwareRequestFilter
 * AnonymousAuthenticationFilter
 * SessionManagementFilter
 * ExceptionTranslationFilter
 * FilterSecurityInterceptor
 * ]
 *
 * 参考“三更草堂”视频 30. 自定义失败处理
 * 注：飞浪的解决方案里也使用的是这个
 *
 * @author liuyang(wx)
 * @since 2022/3/28
 */
@Slf4j
public class RESTAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        /*
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().println(JSON.toJSONString(R.error(authException.getMessage())));
        response.getWriter().flush();
         */
        log.error(authException.getMessage(), authException);
        // 当然可以在这里把authException分类翻译一下。
        RespHelper.printJSONError(response, authException.getMessage());

        // 好用！
        /*
        2023-12-30 10:30:42.659 [http-nio-80-exec-3] ERROR l.t.m.s.s.exception.RESTAuthenticationEntryPoint - Full authentication is required to access this resource
org.springframework.security.authentication.InsufficientAuthenticationException: Full authentication is required to access this resource
         */
    }
}
