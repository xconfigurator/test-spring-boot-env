package liuyang.testspringbootenv.modules.security.springsecurity.config;

import liuyang.testspringbootenv.modules.security.springsecurity.service.SysUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 *
 * @author liuyang
 * @scine 2021/4/1
 *
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)// @Secure @PreAuthorize @PostAuthorize
public class SecuriityConfig extends WebSecurityConfigurerAdapter {

    // 授权
    // 能够配置的项具体参见HttpSecurity的源码注释
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http); // 父类有一些默认的规则(包含登录和注销)

        // 自定义登录页面配置
        // http.formLogin();
        // 1. /login来到登录页
        // 2. /login?error表示登录失败
        // 定制登录页, 具体细节参见 formLogin方法和loginPage方法的代码注释。
        // 定制接收参数：formLogin().usernameParameter("user").passwordParameter("pwd")
        // 定制登录处理方法: .loginPage("/myLoginPage").loginProcessingUrl("");
        // e.g.
        /*
        http.formLogin()
            .loginPage("/myLoginPage")
            .usernameParameter("user")
            .passwordParameter("pwd")
            .loginProcessingUrl("/doLogin")
            .failureForwardUrl()
            .successForwardUrl()
            .permitAll();
         */

        // 自定义授权规则, 如果这里不配置则拦截所有。
        // e.g.1 只拦截/r/**下的请求，
        // http.authorizeRequests().antMatchers("/r/**").authenticated().anyRequest().permitAll();
        // e.g.2
        /*
        http.authorizeRequests()
                .antMatchers("/r/r1").hasAuthority("p1").hasAnyAuthority("p1", "p2")
                .antMatchers("/r/r2").hasAuthority("p2")
                .antMatchers("/r/**").authenticated()
                .anyRequest().permitAll() // 除这些之外允许直接访问 注意校验顺序 从上到下 如果这条放在第一条则所有权限规则失效。
                .and()
                .formLogin()
                .loginPage("/myLoginPage")
                .usernameParameter("user")
                .passwordParameter("pwd")
                .successForwardUrl("/myLoginSuccess")
                .failureForwardUrl("/myLoginFailure");
        */

        // 拦截顺序从上到下
        // 实践：具体的规则放前面，通配的规则放后面。
        /*
            // OK
            .antMatchers("/admin/login").permitAll()
            .antMatchers("/admin/**").hashRole("ADMIN")

            // Wrong!
            .antMatchers("/admin/**").hashRole("ADMIN")
            .antMatchers("/admin/login").permitAll()
         */
        // .access()中可以使用SpEL表达式 (不推荐这样写)
        /*
            .antMatchers(“/r/r3”).access("hasAuthority('p1') and hasAuthority('p2')")
         */


        // 配置session
        // http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED); 默认

        // 自定义注销
        // http.logout();
        // 1. 访问/logout <form th:action"@{/logout}" method="post"><input type="submit" value="注销"></form>
        // 2. 注销成功会返回 /login?Logout 修改注销成功页面 http.logout().logoutSuccessUrl("/");
        // e.g.
        /*
        http.logout()
            .logoutUrl("/logout")
            .logoutSuccessUrl("/login");
         */


        // 记住我
        http.rememberMe(); // 重启服务需要重新登录
        // 在浏览器上写入一个名为remember-me的Cookie。
        // 定制记住我参数名称： http.rememberMe().rememberMeParameter("remember");

        // csrf Spring Security 4 之后默认是开启的
        // http.csrf().disable(); // cross-site request forgery跨站伪造请求。
    }

    // 认证
    // 配置认证规则
    /*
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // super.configure(auth);
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .withUser("liuyang").password(new BCryptPasswordEncoder().encode("123")).roles("admin", "xxxx").and()
                .withUser("liuyang2").password(new BCryptPasswordEncoder().encode("123")).roles("xxxx");

        // 测试用的明文对比编码器
        // NoOpPasswordEncoder.getInstance();

    }
     */

    // UserDetailService
    /*
    @Bean
    public UserDetailsService userDetailsService() {

        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

        // type 1. NoOpPasswrodEncoder.getInstance()
        // manager.createUser(User.withUsername("liuyang").password("123").authorities("authoritiesxxx").roles("admin", "xxx").build());
        // manager.createUser(User.withUsername("liuyang2").password("123123").authorities("authoritiesxxx").roles("admin").build());

        // type 2. new BCryptPasswordEncoder()
        // 如何得到这些加密串？
        // 答：参考BCryptTest
        manager.createUser(User.withUsername("liuyang")
                .password("$2a$10$kLtge.Vw3FRRNGWPYdCNse/zOiqN0sM/Z/7F8solSY0hShG5wgt46") // 123
                .authorities("authoritiesxxx")
                .roles("admin", "xxx").build());
        manager.createUser(User.withUsername("liuyang2")
                .password("$2a$10$xu4HmcMsZrNdp0742ql06upKdJ6cn34P924wuWtR6yx2rtv0BCov.") // 123123
                .authorities("authoritiesxxx")
                .roles("admin").build());
        return manager;
    }
     */

    // PasswordEncoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        // return NoOpPasswordEncoder.getInstance();
        return new BCryptPasswordEncoder();
    }
}
