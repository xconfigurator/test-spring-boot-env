package liuyang.testspringbootenv.modules.security.springsecurity.controller;

import com.alibaba.fastjson.JSON;
import liuyang.testspringbootenv.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * @author liuyang
 * @scine 2021/4/9
 */
@Controller
@RequestMapping("/security")
@Slf4j
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
    @PostMapping("/login")
    public ModelAndView login(@RequestParam String username
                            , @RequestParam String password) {
        // TODO

        return null;
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
