package liuyang.testspringbootenv.modules.security.springsecurity.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import liuyang.testspringbootenv.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

/**
 * @author liuyang(wx)
 * @since 2022/1/25
 */
@Slf4j
public class JSONLoginSuccesssHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        ObjectMapper om = new ObjectMapper();
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        Map<String, Object> msg = Map.of(
                "msg", "认证成功",
                "details", authentication);
        response.getWriter().println(om.writeValueAsString(R.ok(msg)));
        //response.getWriter().println(om.writeValueAsString(authentication));
        log.debug("认证成功");
    }
}