package liuyang.testspringbootenv.modules.session.redis.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * https://spring.io/projects/spring-session
 *
 * 配置Spring Session Data Redis，使用JSON格式序列化到Redis中，而不是默认的JDK序列化。
 * 如果使用默认JDK序列化，则每一个被持久化的对象都必须实现Serializable接口
 * 配置为使用JSON存储之后就不需要，而且便于在Redis中进行观察。
 *
 * @author liuyang
 * @scine 2021/4/13
 *
 * https://github.com/spring-projects/spring-session/tree/2.4.2/spring-session-samples/spring-session-sample-boot-redis-json
 * @author jitendra on 3/3/16.
 *
 */
@Configuration
@EnableRedisHttpSession
public class SessionConfig implements BeanClassLoaderAware {
    private ClassLoader loader;

    @Bean
    public RedisSerializer<Object> springSessionDefaultRedisSerializer() {
        return new GenericJackson2JsonRedisSerializer(objectMapper());
    }

    /**
     * Customized {@link ObjectMapper} to add mix-in for class that doesn't have default
     * constructors
     * @return the {@link ObjectMapper} to use
     */
    private ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModules(SecurityJackson2Modules.getModules(this.loader));
        return mapper;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.springframework.beans.factory.BeanClassLoaderAware#setBeanClassLoader(java.lang
     * .ClassLoader)
     */
    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.loader = classLoader;
    }

}
