package liuyang.testspringbootenv.modules.security.springsecurity.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 配置Spring Security
 *
 * 概览：
 * HttpSecurity                 定义安全拦截机制
 * UserDetailsService           实现用户信息获取
 * PasswordEncoder              指定密码比对规则
 *      BCryptPasswordEncoder
 *      NoOpPasswordEncoder
 *
 * @author liuyang
 * @scine 2021/4/1
 *
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)// @Secure @PreAuthorize @PostAuthorize
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${liuyang.debug.security.enabled}")
    private boolean isSecurityEnabled;// 为方便调试，自定义安全规则开关。不可以写成final

    // 授权
    // 定义安全拦截机制
    // 能够配置的项具体参见HttpSecurity的源码注释
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //http.authorizeRequests().anyRequest().permitAll();

        if (isSecurityEnabled) {
            // HttpSecurity
            // 一段相对完整的配置示例
            demoConfig(http);
        } else {
            // 放开所有资源的访问权限
            http.authorizeRequests().anyRequest().permitAll();
        }

        // 【配置项分类解释】
        // 【使用场景】：想要用一下SpringSecurity提供的登录页面做一下UserDetailsService以及PasswordEncoder测试。
        // 方法1：最简单的用法
        // 使用默认保护方案 begin
        //super.configure(http); // 父类有一些默认的规则(包含登录和注销)
        // 使用默认保护方案 end
        // 方法2：使用默认登录表单，但指定以下登录成功页面
        /*
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and().formLogin()
                .successForwardUrl("/security/test/foo");// 注意：这样发出的是post请求
        */

        // 【使用场景】：想要“关闭”SpringSecurity防护
        // 方法1：取消下面的配置，即默认开放所有的页面资源的访问。
        // 放开所有权限 begin 2021/7/3
        //http.authorizeRequests().anyRequest().permitAll();
        // 放开所有权限 end 2021/7/3
        // 方法2：彻底关闭Spring Security组件。可以取消SecurityConfig上面的注解。202110141400 实测这种方法貌似不生效！！为什么？

        // 【使用场景】：需要根据不同的角色给出不同的登录页面
        // TODO

        // 【使用场景】：自定义登录页面配置
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

        // 【使用场景】：自定义授权规则, 如果这里不配置则拦截所有。
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

        // 【注意】：拦截顺序从上到下
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

        // 【使用场景】：自定义注销
        // http.logout();
        // 1. 访问/logout <form th:action"@{/logout}" method="post"><input type="submit" value="注销"></form>
        // 2. 注销成功会返回 /login?Logout 修改注销成功页面 http.logout().logoutSuccessUrl("/");
        // e.g.
        /*
        http.logout()
            .logoutUrl("/logout")
            .logoutSuccessUrl("/login")     // 指定注销后的页面
            .deleteCookies("JSESSIONID")    // 删除指定的Cookie
            .invalidateHttpSession(true);   // 另Session失效
         */

        // 【使用场景】：记住我
        // http.rememberMe(); // 重启服务需要重新登录(配合默认方案一起使用)
        // 在浏览器上写入一个名为remember-me的Cookie。
        // 定制记住我参数名称： http.rememberMe().rememberMeParameter("remember");


        // 【其他常用配置项】：
        // 配置session
        // http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED); 默认

        // csrf Spring Security 4 之后默认是开启的
        // http.csrf().disable(); // cross-site request forgery跨站伪造请求。

        // 开启同源 (如果使用如hui等基于iframe的前端框架则需要后面做相应配合)
        // http.headers().frameOptions().sameOrigin();
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
    // 可以放到外面去实现
    // InMemoryDetailsManager:InMemoryUserDetailsService
    // JPA:DbUserDetailsService
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

    // HttpSecurity
    // 一段相对完整的配置示例
    private void demoConfig(HttpSecurity http) throws Exception {
        // ///////////////////////////////////////////////
        // 【一段相对完整的配置示例】 begin
        // 类比shiro: shiroFilterFactoryBean.setFilterChainDefinitionMap(FilterChainDefinitionMapBuilder.build())
        // 声明不需要权限拦截的资源（白名单必须在前，否则将失效）
        // 首页
        http.authorizeRequests().antMatchers("/").permitAll();
        // 登录页面
        http.authorizeRequests()
                .antMatchers("/security/login/page").permitAll()
                .antMatchers("/security/login").permitAll();
        // 配置授权规则：需要权限控制的页面
        http.authorizeRequests()
                .antMatchers("/hello/r1").hasAuthority("r1")
                .antMatchers("/hello/r2").hasAuthority("r2");
        // 声明需要权限拦截的资源
        http.authorizeRequests().anyRequest().authenticated();
        // 登录
        http.formLogin()
                // 自定义登录页面以及页面处理 若想用Spring Security默认提供页面就注释掉这两项
                // Spring Security默认提供的登录地址是/login
                //.loginPage("/security/login/page")
                //.loginProcessingUrl("/security/login")
                // 下面的配置配合Spring Security默认提供登录页面仍然有效
                .failureForwardUrl("/security/login/failure");    // 未测试 酌情配置(若自定义了登录处理方法，且为rest风格，则按照自定义的返回。)
        //.successForwardUrl("/security/login/success");  // ok 不配置的话就是访问哪个页面被阻止了就跳转到哪个页面
        //.successForwardUrl("/");                        // ok 不配置的话就是访问哪个页面被阻止了就跳转到哪个页面
        // 注销
        http.logout()
                // Spring Security默认提供的实现是/logout
                .logoutUrl("/security/logout")                      // ok
                //.logoutSuccessUrl("/security/logout/success")     // 未生效 指定注销后的页面（若/security/logout是rest风格，则这个选项失效）
                .deleteCookies("JSESSIONID")                   // 删除指定的Cookie
                .invalidateHttpSession(true);                  // 另Session失效

        // 【一段相对完整的配置示例】 end
        // ///////////////////////////////////////////////
    }
}
