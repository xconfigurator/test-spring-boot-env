package liuyang.testspringbootenv.modules.security.springsecurity.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import liuyang.testspringbootenv.modules.security.springsecurity.filter.RESTAuthenticationFilter;
import liuyang.testspringbootenv.modules.security.springsecurity.handler.JSONLoginFailureHandler;
import liuyang.testspringbootenv.modules.security.springsecurity.handler.JSONLoginSuccesssHandler;
import liuyang.testspringbootenv.modules.security.springsecurity.handler.JSONLogoutSuccessHandler;
import liuyang.testspringbootenv.modules.security.springsecurity.handler.XXLoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.security.MessageDigestSpi;
import java.util.Map;

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
 * @update 2022/1/25            整理，清理旧笔记，完成页面版本。（页面和JSON版本的都是基于session的。）
 * @update 2022/1/26            增加REST式登录入口。
 * @update TODO                 完成JWT版本。
 *
 */
@EnableWebSecurity(debug = true)// 打开debug方便学习和调试。debug默认是false
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)// @Secure @PreAuthorize @PostAuthorize
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${liuyang.debug.security.enabled}")
    private boolean isSecurityEnabled;// 为方便调试，自定义安全规则开关。不可以写成final

    @Value("${spring.mvc.static-path-pattern}")
    private String mvcStaticPath;

    private final ObjectMapper om;// 使用构造函数进行注入

    private static final String REST_AUTH_FILTER_PROCESS_URL = "/rest/login";

    // 通过这个方法配置不需要进入过滤器链的内容
    @Override
    public void configure(WebSecurity web) throws Exception {
        //super.configure(web);
        web.ignoring().mvcMatchers(mvcStaticPath);// 静态资源
        //web.ignoring().mvcMatchers("/webjars/**");// 202201241523 ok 可以通过@EnableWebSecurity(debug = true)时的日志看出。
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
        web.ignoring().mvcMatchers("/error");
    }

    // 授权
    // 定义安全拦截机制
    // 能够配置的项具体参见HttpSecurity的源码注释
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //http.authorizeRequests().anyRequest().permitAll();

        // ///////////////////////////////////////////////
        // 【一段相对完整的配置示例】 begin
        // 类比shiro: shiroFilterFactoryBean.setFilterChainDefinitionMap(FilterChainDefinitionMapBuilder.build())
        // 声明不需要权限拦截的资源（白名单必须在前，否则将失效）
        // 白名单：首页
        http.authorizeRequests().antMatchers("/").permitAll();
        // 白名单：登录页面相关
        http.authorizeRequests()
                .antMatchers("/login").permitAll()                  // 注意没有/logout，想想为什么。
                .antMatchers("/security/login/page").permitAll()
                .antMatchers("/security/login").permitAll();

        // 配置授权规则：需要权限控制的页面
        http.authorizeRequests()
                .antMatchers("/hello/r1").hasAuthority("r1")
                .antMatchers("/hello/r2").hasAuthority("r2");
        // 声明需要权限拦截的资源
        //http.authorizeRequests().anyRequest().authenticated();
        http.authorizeRequests(req -> {
            req.anyRequest().authenticated();// 另一种写法
        });

        // REST风格登录改造 202201261017  add
        // 指定一个REST式login的入口
        // 入口地址：REST_AUTH_FILTER_PROCESS_URL
        http.addFilterAt(restAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);// at是替代，尝试一下before

        // 登录
        // 202201241625 add
        http.formLogin(form -> {
            form.loginPage("/login");                                       // 这里提供定制登录页面入口。Spring Security默认提供的实现是/login（post），注意要在白名单中放行/login。
            form.failureUrl("/login?error");                                // 页面版本（貌似不写也可以）
            //form.successHandler(new XXLoginSuccessHandler());             // 定制
            //form.successHandler(new JSONLoginSuccesssHandler());          // 定制JSON版本 20220125 ok
            //form.failureHandler(new JSONLoginFailureHandler());           // 定制JSON版本 20220125 ok
        });

        // 基于session的引用会涉及到的三个操作：注销，rememberme，csrf。
        // 注销
        http.logout(logout -> {
            //logout.logoutUrl("/perform_logout");                          // Spring Security默认提供的实现是/logout（GET）,这里就给改个名。配置了之后，原来的/logout就没啦。 20220125 实测ok
            logout.logoutSuccessUrl("/login?logout");                       // 页面版本
            //logout.logoutSuccessHandler(new JSONLogoutSuccessHandler());  // 定制JSON版本 20220125 ok
            //logout.deleteCookies("JSESSIONID")                            // 删除指定的Cookie
            //logout.invalidateHttpSession(true);                           // 另Session失效
        });


        // csrf
        // https://blog.csdn.net/t894690230/article/details/52404105
        // http.csrf().disable();
        http.csrf(AbstractHttpConfigurer::disable);
        // CSRF(Cross-Site Request Forgery)方案：
        // 方案1： csrf_token: token是由服务器生成，并存储在浏览器的cookie当中。服务端每个请求都要求携带这个token。（目前默认的方案）
        // 方案2： 设置Cookie中的SameSite属性。但是存在浏览器的兼容性问题。
        // 注：使用JWT的前后端分离的应用，对CSRF是天然免疫。
        //    CSRF对无状态应用无效。通过防止钓鱼站点对JWT的伪造来实现。
        //    故简单了解一下即可。
        // csrf 放开纯rest请求的csrf_token验证方法
        /*
        http.csrf(csrf -> {
            csrf.ignoringAntMatchers("/data/*");                            // /data/*下的所有请求肯定是REST式的，如果打开了csrf功能，则csrf过滤器不会验证这些请求下的csrf_token
        });
         */

        // remember-me
        // 注：Web应用用这个选项比较多，企业内网应用应尽量避免使用该功能。
        //    解释，web应用面向个人，一般是在个人计算设备上使用，其他人使用的情况会比较少。而企业应用则不然。
        http.rememberMe(rememberMe -> {
            rememberMe.key("customizeSecretRememberMe");
            rememberMe.tokenValiditySeconds(30 * 24 * 3600);// 有效期一个月
        });
        // 原理：使用Cookie存储用户名，过期的时间，以及一个Hash。hash = md5(用户名 + 过期时间 + 密码 + key) 注意到这个里面有密码，这就意味着如果改了密码，则rememberMe失效。
        // remember-me功能需要一个UserDetailUservice

        // 【一段相对完整的配置示例】 end
        // ///////////////////////////////////////////////
    }

    // 认证 // UserDetailService
    // 配置认证规则
    // 这个配合remember-me没问题。
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //super.configure(auth);
        auth.inMemoryAuthentication()
                .withUser("liuyang")
                .password("$2a$10$K9IoTnC9nYFT.UOhBC.dJ.cqxcvxX6Znz3gxxMDNp/V5RT/U6R3s6")
                .authorities("r1", "r2", "r3", "r4")
                .and()
                .withUser("liuyang2")
                .password("$2a$10$GtPWJSyNUKZJqTKyQJHnk.h31bI0usp6KakWCadWQdg8dC0hM74r6")
                .authorities("r2")
        ;
    }

    // UserDetailService
    // 可以放到外面去实现 (20220125 实测 这个配合logout方法有问题。500)
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

        // 问题1： 更换密文编码
        // 对策：  多编码器共存
        // Spring Security 为变更编码器提出的解决方案 - DelegatingPasswordEncoder
        // 这个要求从一开始就用这个DelegatingPasswordEncoder
        // 还是用这个吧
        // 密码格式：{passwordEncoderId}encodedPassword
        // 密码匹配：encoder.matches(CharSequence, String)
        /*
        String defaultPasswordEncoder = "bcrypt";
        Map<String, PasswordEncoder> passwordEncoders = Map.of(
                defaultPasswordEncoder, new BCryptPasswordEncoder(),
                "SHA-1", new MessageDigestPasswordEncoder("SHA-1")
        );
        return new DelegatingPasswordEncoder(defaultPasswordEncoder, passwordEncoders);
        */

        // 问题2： 旧密码升级
        // 对策：  UserDetailsPasswordService中的updatePassword
    }

    private RESTAuthenticationFilter restAuthenticationFilter() throws Exception {
        RESTAuthenticationFilter filter = new RESTAuthenticationFilter(om);
        filter.setAuthenticationSuccessHandler(new JSONLoginSuccesssHandler());
        filter.setAuthenticationFailureHandler(new JSONLoginFailureHandler());
        filter.setAuthenticationManager(authenticationManager());// authenticationManager()是在WebSecurityConfigurerAdapter中
        filter.setFilterProcessesUrl(REST_AUTH_FILTER_PROCESS_URL);
        return filter;
    }
}
