package liuyang.testspringbootenv.modules.cache.redis.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.LocalDateTime;

/**
 * 使用JSON格式序列化到Redis中，而不是默认的JDK序列化。
 *
 * 说明：
 *  雷丰阳老师讲的是Spring Boot 1.环境下的方法。
 *  suhj在pdt项目脚手架中给出的是Spring Boot 2.x环境下的方法。
 * 这里参考suhj在Spring Boot 2.x中给出的方案。
 *
 * @author liuyang
 * @scine 2021/4/15
 */
@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class RedisTemplateConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);

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
                        , new liuyang.testspringbootenv.modules.cache.redis.serializer.LocalDateTimeSerializer())
                .addDeserializer(LocalDateTime.class
                        , new liuyang.testspringbootenv.modules.cache.redis.serializer.LocalDateTimeDeserializer()));
        jackson2JsonRedisSerializer.setObjectMapper(om);
        // ////////////////////////////////////////////////////////////////////////////////

        // 配置模板 valueOperations
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);

        // 配置模板 hashOperations
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);

        return redisTemplate;
    }

    @Bean
    public ValueOperations<String, Object> valueOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForValue();
    }

    @Bean
    public HashOperations<String, String, Object> hashOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForHash();
    }
}
