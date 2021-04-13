package liuyang.testspringbootenv.security.springsecurity.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author liuyang
 * @scine 2021/4/1
 *
 */
@EnableWebSecurity
public class SecuriityConfig extends WebSecurityConfigurerAdapter {

    // 能够配置的项具体参见HttpSecurity的源码注释
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http); // 父类有一些默认的规则(包含登录和注销)

        // 授权规则, 如果这里不配置则不拦截。
        // TODO

        // 登录
        // http.formLogin();
        // 1. /login来到登录页
        // 2. /login?error表示登录失败
        // 定制登录页, 具体细节参见 formLogin方法和loginPage方法的代码注释。
        // 定制接收参数：formLogin().usernameParameter("user").passwordParameter("pwd")
        // 定制登录处理方法: .loginPage("/myLoginPage").loginProcessingUrl("");
        // e.g. http.formLogin().usernameParameter("user").passwordParameter("pwd").loginPage("/myLoginPage").loginProcessingUrl("");

        // 注销
        // http.logout();
        // 1. 访问/logout <form th:action"@{/logout}" method="post"><input type="submit" value="注销"></form>
        // 2. 注销成功会返回 /login?Logout 修改注销成功页面 http.logout().logoutSuccessUrl("/");

        // 记住我
        http.rememberMe(); // 重启服务需要重新登录
        // 在浏览器上写入一个名为remember-me的Cookie。
        // 定制记住我参数名称： http.rememberMe().rememberMeParameter("remember");

        // csrf Spring Security 4 之后默认是开启的
        // http.csrf().disable();
    }

    // 配置认证规则
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // super.configure(auth);
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .withUser("liuyang").password(new BCryptPasswordEncoder().encode("123")).roles("admin", "xxxx").and()
                .withUser("liuyang2").password(new BCryptPasswordEncoder().encode("123")).roles("xxxx");
    }
}
