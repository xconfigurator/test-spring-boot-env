package liuyang.testspringbootenv.modules.security.springsecurity.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import liuyang.testspringbootenv.modules.security.springsecurity.exception.RESTAccessDeniedHandler;
import liuyang.testspringbootenv.modules.security.springsecurity.exception.RESTAuthenticationEntryPoint;
import liuyang.testspringbootenv.modules.security.springsecurity.filter.JWTAuthenticationFilter;
import liuyang.testspringbootenv.modules.security.springsecurity.filter.RESTLoginAuthenticationFilter;
import liuyang.testspringbootenv.modules.security.springsecurity.handler.RESTAuthenticationFailureHandler;
import liuyang.testspringbootenv.modules.security.springsecurity.handler.RESTAuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
 * @update 2024/2/19            完成JWT版本(部分，已经确定好方案和位置)。时间没写错，就是2024。
 *                              先后参考雷丰阳、接灰的电子产品、飞浪、三更草堂、环环、Thor以及Ruoyi的实现。
 *                              2024020190431 无JWT条件下，REST方式登录、注销流程测试成功！
 *                              JWT的就留在其他环境测试吧。
 * @update 2022/3/22            增加白名单独立方法（方便调试）。
 * @update 2024/2/20            增加授权规则独立方法（方便调试）。并修正注释。
 *
 */
//@EnableWebSecurity(debug = true)// 打开debug方便学习和调试。debug默认是false
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)// @Secure @PreAuthorize @PostAuthorize
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${liuyang.debug.security.enabled}")
    private boolean isSecurityEnabled;// 为方便调试，自定义安全规则开关。不可以写成final

    @Value("${spring.mvc.static-path-pattern}")
    private String mvcStaticPath;

    private final ObjectMapper om;// 使用构造函数进行注入

    private static final String REST_AUTH_FILTER_PROCESS_URL = "/rest/login";

    // ///////////////////////////////////////////////////////////////////////
    // 白名单 /////////////////////////////////////////////////////////////////
    // liuyang 2022/3/22
    private void whiteList(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/hello").permitAll()
                .antMatchers("/upload/excel").permitAll()
                .antMatchers("/login").permitAll()                          // 注意没有/logout，想想为什么。
                .antMatchers("/security/login/page").permitAll()
                .antMatchers("/security/login").permitAll()
                .antMatchers("/gs-guide-websocket-endpoint/**").permitAll() // 20221108 add 调试用 WebSocket的Endpoint
                .antMatchers("/ws/**").permitAll()                          // 20221108 add 调试用 WebSocket的@MessageMapping路径前缀
                .antMatchers("/actuator/**").permitAll()                    // 20221127 add 为方便查看指标。
                .antMatchers("/cn/**").permitAll()                          // 20231230 add 增加内容协商测试
                // 其他
                .antMatchers("/inma_smart/alarm").permitAll();

        // 关于permitAll()和anonymous()的区别
        // 三更草堂 22.认证配置 10:15 左右讲了。前后端不分离的项目中，对静态资源放行多用permitAll()，前后端分离项目，对API的访问多用anonymous()
        // 不过参考下面的public void configure(WebSecurity web) throws Exception ，看来三更草堂的说法也不是特备靠谱。
    }

    // ///////////////////////////////////////////////////////////////////////
    // 授权规则 ///////////////////////////////////////////////////////////////
    // liuyang 2024/2/20
    private void authorizeRequestsRoles(HttpSecurity http) throws Exception {
        // 授权规则：需要权限控制的页面
        // 1. 先配置具体的(20230128 IT老齐：推荐使用mvcMatchers而不是antMatchers)
        /*
        http.authorizeRequests()
                .antMatchers("/hello/r1").hasAuthority("r1")
                .antMatchers("/hello/r2").hasAuthority("r2");// 改用mvcMatchers试一下。 antMatchers 实际匹配的是/hello/r2/
        */
        http.authorizeRequests()
                .mvcMatchers("/hello/r1").hasAuthority("r1")
                .mvcMatchers("/hello/r2").hasAuthority("r2");// 改用mvcMatchers试一下。 antMatchers 实际匹配的是/hello/r2/
        // 2. 再配置范围的 (mvcMatchers要放在anyRequest的前面！否则启动时报错！)
        // 声明需要权限拦截的资源
        //http.authorizeRequests().anyRequest().authenticated();
        http.authorizeRequests(req -> {
            req.anyRequest().authenticated();// 另一种写法
        });
    }

    // ///////////////////////////////////////////////////////////////////////
    // 静态资源 ///////////////////////////////////////////////////////////////
    // 通过这个方法配置不需要进入过滤器链的内容
    @Override
    public void configure(WebSecurity web) throws Exception {
        //super.configure(web);
        web.ignoring().mvcMatchers(mvcStaticPath);// 静态资源
        //web.ignoring().mvcMatchers("/webjars/**");// 202201241523 ok 可以通过@EnableWebSecurity(debug = true)时的日志看出。
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
        web.ignoring().mvcMatchers("/error");
    }


    // ///////////////////////////////////////////////////////////////////////
    // 框架行为配置 ////////////////////////////////////////////////////////////
    // 授权（20240220已抽取到单独方法）
    // 定义安全拦截机制
    // 能够配置的项具体参见HttpSecurity的源码注释
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        if (!isSecurityEnabled) {
            System.out.println("***********************************************************************************************************");
            System.out.println("* liuyang Spring Secutiry permitAll(). Config using liuyang.debug.security.enabled in appliation.properties");
            System.out.println("***********************************************************************************************************");
            http.authorizeRequests().anyRequest().permitAll();
            http.csrf().disable();
            //http.cors();// liuyang 20220328 Spring Security允许跨域（Spring MVC也需要配置允许跨域，参见WebMvcConfig.java）
            return;
        }

        System.out.println("**************************************************************************************************************");
        System.out.println("* liuyang Spring Security is protecting.  Config using liuyang.debug.security.enabled in appliation.properties");
        System.out.println("**************************************************************************************************************");

        // ///////////////////////////////////////////////////////////////////////
        // 【一段相对完整的配置示例】 begin
        // 类比shiro: shiroFilterFactoryBean.setFilterChainDefinitionMap(FilterChainDefinitionMapBuilder.build())
        // 声明不需要权限拦截的资源（白名单必须在前，否则将失效）

        // 白名单：首页
        whiteList(http);// 设置白名单（方便开发）

        // 授权规则：
        authorizeRequestsRoles(http);// 2024/2/20 抽取出去

        // REST 风格改造
        // Login（前后端分离版本）
        // 【方案争议】是否采用本方案待议 20220328
        //          另一种方案是把登录和注销都放在一个Controller中，通过配置暴露出AuthenticationManager来完成相关操作。见三更草堂方案。
        // REST风格登录改造 202201261017  add
        // 指定一个REST式login的入口

        // 登录入口地址：REST_AUTH_FILTER_PROCESS_URL
        http.addFilterAt(restLoginAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);// 根据源码注释，尽量不要用at造成不确定性。
        //http.addFilterBefore(restAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        // Exception
        // REST风格认证授权异常处理 202203281513 add
        http.exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint());// e.g. 未携带token访问了受保护的资源页面
        http.exceptionHandling().accessDeniedHandler(restAccessDeniedHandler());// e.g. 访问了没有权限的页面

        // JWT 方案改造202402202158（参考Ruoyi）
        // 方案与返回JSON改造后的异常处理流程相同，但有两处区别
        // 1. 登录后要返回JWT令牌。在登录过滤器返回时返回携带JWT Token 修改点在RestLoginAut
        // 2. 增加JWT过滤器。REST && 前后端分离的JWT过滤器
        //http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        //http.addFilterBefore(jwtAuthenticationFilter(), 自定义的restLoginAuthenticationFilter.class);// liuyang 20240220 如果前面设置过滤器用了At也许应该这样写，未测试。
        // 3. 关闭session管理。
        // 4. 关闭csrf保护（CSRF禁用，因为不使用session）。
        // 5. 禁用HTTP响应头。
        //http.headers().cacheControl().disable();

        // CORS 前后端分离必须配置。
        // REST && 前后端分离 需要在Spring Security中开启跨域允许
        // liuyang 20220328 Spring Security允许跨域（Spring MVC也需要配置允许跨域，参见WebMvcConfig.java）
        http.cors();

        // Session
        // 2024/2/20 如果基于JWT Token，则不使用session管理。
        //http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // 详细策略参见SessionCreationPolicy.STATELESS注释

        // CSRF
        // 2024/2/20 如果不使用session则禁用csrf。
        //http.csrf().disable();// 禁用方法1
        http.csrf(AbstractHttpConfigurer::disable);// 禁用方法2
        //http.csrf();// 开启csrf保护方案。
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

        // Login(前后端不分离版本登录)
        // 带登录页面的
        // 登录
        // 202201241625 add
        http.formLogin(form -> {
            form.loginPage("/login");                                       // 这里提供定制登录页面入口。Spring Security默认提供的实现是/login（post），注意要在白名单中放行/login。
            form.failureUrl("/login?error");                                // 页面版本（貌似不写也可以）
            //form.successHandler(new XXLoginSuccessHandler());             // 定制
            //form.successHandler(new RESTLoginSuccesssHandler());          // 定制JSON版本 20220125 ok
            //form.failureHandler(new RESTLoginFailureHandler());           // 定制JSON版本 20220125 ok
        });

        // Logout(基于session的注销，与是否前后端分离无关)
        // 基于session的引用会涉及到的三个操作：注销，rememberme，csrf。
        // 注销
        http.logout(logout -> {
            //logout.logoutUrl("/perform_logout");                          // (前后端分离方案中基本已不再使用这种方式) Spring Security默认提供的实现是/logout（GET）,这里就给改个名。配置了之后，原来的/logout就没啦。 20220125 实测ok
            logout.logoutSuccessUrl("/login?logout");                       // (前后端分离方案中基本已不再使用这种方式) 页面版本
            //logout.logoutSuccessHandler(new RESTLogoutSuccessHandler());  // 定制JSON版本 20220125 ok
            //logout.deleteCookies("JSESSIONID")                            // 删除指定的Cookie
            //logout.invalidateHttpSession(true);                           // 另Session失效
            //logout.addLogoutHandler();                                    // Thor
        });

        // Remember-Me
        // 注：Web应用用这个选项比较多，企业内网应用应尽量避免使用该功能。
        //    解释，web应用面向个人，一般是在个人计算设备上使用，其他人使用的情况会比较少。而企业应用则不然。
        http.rememberMe(rememberMe -> {
            rememberMe.key("customizeSecretRememberMe");
            rememberMe.tokenValiditySeconds(30 * 24 * 3600);// 有效期一个月
        });
        // 原理：使用Cookie存储用户名，过期的时间，以及一个Hash。hash = md5(用户名 + 过期时间 + 密码 + key) 注意到这个里面有密码，这就意味着如果改了密码，则rememberMe失效。
        // remember-me功能需要一个UserDetailUservice

        // 【一段相对完整的配置示例】 end
        // ///////////////////////////////////////////////////////////////////////
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

        // 也可以使用配置文件 20230129 add
        // spring.security.user.name=foo
        // spring.seucrity.user.password=bar

        // auth.userDetailsService(foo).passwordEncoder(bar).userDetailsPasswordManager(xxx);

        // 配置这个方法会自动创建表结构。
        // auth.jdbcAuthentication().withDefaultSchema() // 后同inMemoryAuthentication()
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
                .password("$2a$10$kLtge.Vw3FRRNGWPYdCNse/zOiqN0sM/Z/7F8solSY0hShG5wgt46") // 123 passwordEncoder.encode("xxx");
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

    // “接灰的电子产品”的方案 20220328 是否采取这种方案有争议
    // 20240219 liuyang 参考Thor的方案。个人认为接灰和Thor的方案靠谱！决定采纳！
    @Bean
    public RESTLoginAuthenticationFilter restLoginAuthenticationFilter() throws Exception {
        RESTLoginAuthenticationFilter filter = new RESTLoginAuthenticationFilter(om);// 注意到这是一个UsernamePasswordAuthenticationFilter。
        filter.setAuthenticationSuccessHandler(new RESTAuthenticationSuccessHandler());
        filter.setAuthenticationFailureHandler(new RESTAuthenticationFailureHandler());
        filter.setAuthenticationManager(authenticationManager());// authenticationManager()是在WebSecurityConfigurerAdapter中
        filter.setPostOnly(false);// 为调试方便，也接受get请求。
        filter.setFilterProcessesUrl(REST_AUTH_FILTER_PROCESS_URL);
        return filter;
    }

    // 向容器中暴露authenticationManager 20220328 add
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();// 详细的参考这个方法源码的注释。
    }

    // JWT过滤器 20220328 add
    @Bean // 202203281708 实测 貌似注入容器就生效。（即不经过config中配置）
    public JWTAuthenticationFilter jwtAuthenticationFilter() {
        return new JWTAuthenticationFilter();
    }

    // 认证异常 20220328 add
    @Bean
    public RESTAccessDeniedHandler restAccessDeniedHandler() {
        return new RESTAccessDeniedHandler();
    }

    // 授权异常 20220328 add
    @Bean
    public RESTAuthenticationEntryPoint restAuthenticationEntryPoint() {
        return new RESTAuthenticationEntryPoint();
    }
}
