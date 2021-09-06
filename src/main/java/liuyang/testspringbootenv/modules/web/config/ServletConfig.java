package liuyang.testspringbootenv.modules.web.config;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 注册标准的原生Servlet组件：
 *  Servlet/Filter/Listener
 *
 * @author liuyang
 * @scine 2021/9/6
 */
@Configuration
public class ServletConfig {

    // Servlet
    @Bean
    public ServletRegistrationBean servletRegistrationBeanDemo() {
        // e.g.
        /*
        StringBuilder urlMapping = new StringBuilder();
        urlMapping.append("/");
        urlMapping.append("SOAP_SERVICE_PATH");
        urlMapping.append("/*");
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new CXFServlet(), urlMapping.toString());
        return servletRegistrationBean;
         */
        return null;
    }

    // Filter
    @Bean
    public FilterRegistrationBean filterRegistrationBeanDemo() {
        // e.g.
        /*
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(wsRedirectFilter);
        filterRegistrationBean.addUrlPatterns("/*");// 实测不设置优先级也不影响Shiro权限过滤器。
        return filterRegistrationBean;
        */
        return null;
    }

    // Listener
    @Bean
    public ServletListenerRegistrationBean servletListenerRegistrationBeanDemo() {
        /*
        MySwervletContextListener mySwervletContextListener = new MySwervletContextListener();
        return new ServletListenerRegistrationBean(mySwervletContextListener);
         */
        return null;
    }

}
