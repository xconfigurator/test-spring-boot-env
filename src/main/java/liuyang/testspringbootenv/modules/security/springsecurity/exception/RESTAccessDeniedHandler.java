package liuyang.testspringbootenv.modules.security.springsecurity.exception;

import liuyang.testspringbootenv.modules.security.springsecurity.util.RespHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证异常处理 e.g. 访问了没有权限的受保护资源
 * 会被过滤器链中的ExceptionTranslationFilter调用
 *
 * @author liuyang(wx)
 * @since 2022/3/28
 */
@Slf4j
public class RESTAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.error(accessDeniedException.getMessage(), accessDeniedException);
        RespHelper.printJSONError(response, accessDeniedException.getMessage());
    }
}
