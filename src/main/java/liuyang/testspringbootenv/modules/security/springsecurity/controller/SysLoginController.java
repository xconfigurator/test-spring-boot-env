package liuyang.testspringbootenv.modules.security.springsecurity.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liuyang
 * @scine 2021/4/9
 */
@RestController
@RequestMapping("/sys")
@Slf4j
public class SysLoginController {

    @GetMapping("/test/username")
    public String getUsername() {
        log.info(ReflectionToStringBuilder.toString(SecurityContextHolder.getContext().getAuthentication(), ToStringStyle.MULTI_LINE_STYLE));
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // 示例：测试类型
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails) {
            log.info("SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails");
            log.info("强制转换成为UserDetails类型，然后从这个对象内部获取信息。");
        }

        return username;
    }
}
