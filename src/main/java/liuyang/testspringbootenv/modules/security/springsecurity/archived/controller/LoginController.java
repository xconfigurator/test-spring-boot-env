package liuyang.testspringbootenv.modules.security.springsecurity.archived.controller;

import com.alibaba.fastjson.JSON;
import liuyang.testspringbootenv.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * 若是非前后端分离，则这个控制器是多余的，都在配置类里配置即可。
 * 如果是前后端分离的应用，则需要提供本控制器，提供REST式的服务。
 *
 * @author liuyang
 * @scine 2021/4/9
 */
//@RestController
//@Controller
//@RequestMapping("/security")
@Slf4j
@Deprecated
// 202110140926 自定义的登录页面跳转在这个控制器里实现
public class LoginController {

    // 登录页面
    @GetMapping("/login/page")
    //public ModelAndView loginPage(ModelAndView mav) { return mav;}
    // public R loginPage() { return R.ok("自定义登录页面"); }
    public String loginPage() {
        return "login";
    }

    // 处理登录逻辑
    // 如果非前后端分离，则这个方法意义不大。如果是前后端分离，则需要提供一个REST式的服务。
    @PostMapping("/login")
    public String login(@RequestParam String username
                            , @RequestParam String password) {
        boolean isValid = true;

        log.info("username = {}", username);
        log.info("password = {}", password);

        // TODO

        if (isValid) {
            return "index";
        } else {
            return "/security/login";
        }
    }

    @PostMapping("/login/success")
    @ResponseBody
    public R loginSuccess() {
        return R.ok("login success");
    }

    @PostMapping("/login/failure")
    @ResponseBody
    public R loginFailure() {
        return R.error("login failure");
    }

    @RequestMapping(value = "/logout", method = {RequestMethod.GET, RequestMethod.POST})// 同时支持get，post请求
    @ResponseBody
    public R logout(HttpSession session) {
        session.invalidate();
        return R.ok("已经注销成功 from logout");
    }

    @PostMapping("/logout/success")
    @ResponseBody
    public R logoutSuccess() {
        return R.ok("已经注销 from logoutSuccess");
    }


    // /////////////////////////////////////////////////////////////
    // for debug begin
    @GetMapping("/userinfo")
    @ResponseBody
    public R getUsername() {
        log.info(ReflectionToStringBuilder.toString(SecurityContextHolder.getContext().getAuthentication(), ToStringStyle.MULTI_LINE_STYLE));
        log.info(JSON.toJSONString(SecurityContextHolder.getContext().getAuthentication()));
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // 示例：测试类型
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails) {
            log.info("SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails");
            log.info("强制转换成为UserDetails类型，然后从这个对象内部获取信息。");
        }

        return R.ok(JSON.toJSONString(SecurityContextHolder.getContext().getAuthentication()));
    }

    @RequestMapping(value = "/test/foo", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public R foo() {
        return R.ok("bar");
    }
    // for debug end
}
