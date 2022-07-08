package liuyang.testspringbootenv.modules.cache.redis.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.LocalDateTime;

/**
 * https://docs.spring.io/spring-boot/docs/current/reference/html/io.html#io.caching
 *
 * spring-boot-starter-cache
 * spring-boot-starter-data-redis
 *
 * 2021/4/15日测试并没有起效
 *
 * @author liuyang
 * @scine 2021/4/15
 */
//@Configuration
public class RedisCacheManagerConfig {
    // 这个好像仅仅对Cache起效，而对直接从容器中获取的RedistTemplate无效
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        // 1. 规则
        // ////////////////////////////////////////////////////////////////////////////////
        // key序列化规则
        RedisSerializer<String> stringRedisSerializer = new StringRedisSerializer();
        // value序列化规则
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);

        // 序列化和反序列化中的细节问题
        // 解决查询缓存转换异常问题
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        // LocalDateTime
        om.registerModule(new JavaTimeModule()
                .addSerializer(LocalDateTime.class
                        , new liuyang.testspringbootenv.common.serializer.jackson.LocalDateTimeSerializer())// 好像fasterxml里有
                .addDeserializer(LocalDateTime.class
                        , new liuyang.testspringbootenv.common.serializer.jackson.LocalDateTimeDeserializer())); // 好像fasterxml里有
        jackson2JsonRedisSerializer.setObjectMapper(om);
        // ////////////////////////////////////////////////////////////////////////////////

        // 2. 注册
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                // .entryTtl(timeToLive)
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(stringRedisSerializer))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer))
                .disableCachingNullValues();

        RedisCacheManager cacheManager = RedisCacheManager.builder(factory)
                .cacheDefaults(config)
                .build();

        return cacheManager;
    }
}
