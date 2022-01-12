package liuyang.testspringbootenv.modules.security.ssl.config;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 所有SSL的基础配置都可以在application.yml中完成，这里仅是另80端口重定向到8443
 * 这个是李兴华老师给出的解决方案
 *
 * @author liuyang(wx)
 * @since 2021/12/27
 */
//@Configuration
public class TomcatConnectorRedirectConfig {

    @Value("${server.port}")
    private int SERVER_PORT;

    /*@Autowired
    private ConfigurableTomcatWebServerFactory factory;*/

    public Connector getHttpConnector() {
        Connector connector = new Connector();// 默认就是HTTP/1.1
        connector.setScheme("http");
        connector.setSecure(false);
        connector.setPort(80);
        connector.setRedirectPort(SERVER_PORT);
        //System.out.println("SERVER_PORT = " + SERVER_PORT);
        return connector;
    }

    @Bean
    public TomcatServletWebServerFactory tomcatServletWebServerFactory() {
        TomcatServletWebServerFactory tomcatServletWebServerFactory = new TomcatServletWebServerFactory() {
            @Override
            protected void postProcessContext(Context context) {
                //super.postProcessContext(context);
                SecurityConstraint securityConstraint = new SecurityConstraint();
                securityConstraint.setUserConstraint("CONFIDENTIAL");// 这个值是固定的
                SecurityCollection securityCollection = new SecurityCollection();
                securityCollection.addPattern("/*");// 注意这个是/*不是/**!!!!
                securityConstraint.addCollection(securityCollection);
                context.addConstraint(securityConstraint);
            }
        };
        tomcatServletWebServerFactory.addAdditionalTomcatConnectors(getHttpConnector());
        return tomcatServletWebServerFactory;
    }
}
