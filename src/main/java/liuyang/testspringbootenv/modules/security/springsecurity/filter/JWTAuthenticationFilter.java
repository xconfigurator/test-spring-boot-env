package liuyang.testspringbootenv.modules.security.springsecurity.filter;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 一句话描述：从token中解析userId，再从缓存（redis）中拿出用户信息（LoginUser extends UserDetails）放入SecurityContextHolder。
 *
 * 很重要！
 *
 * 参考：
 *  1. 三更草堂方案
 *  2. 飞浪方案
 * 注：Spring Security OAuth2中有对JWT的支持，但在一个相对简单的前后端分离应用当中，自定义实现即可。
 *
 * @author liuyang(wx)
 * @since 2022/1/28
 */
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // TODO
        filterChain.doFilter(request, response);
    }
}
