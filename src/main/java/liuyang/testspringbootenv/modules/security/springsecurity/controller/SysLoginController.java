package liuyang.testspringbootenv.modules.security.springsecurity.controller;

import com.alibaba.fastjson.JSON;
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
@Deprecated // 标准使用Spring Security之后，这个控制器就没用了。应该是在Spring Security的框架下添加修改和订制。
public class SysLoginController {

    @GetMapping("/test/username")
    public String getUsername() {
        log.info(ReflectionToStringBuilder.toString(SecurityContextHolder.getContext().getAuthentication(), ToStringStyle.MULTI_LINE_STYLE));
        log.info(JSON.toJSONString(SecurityContextHolder.getContext().getAuthentication()));
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // 示例：测试类型
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails) {
            log.info("SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails");
            log.info("强制转换成为UserDetails类型，然后从这个对象内部获取信息。");
        }

        return username;
    }
}
