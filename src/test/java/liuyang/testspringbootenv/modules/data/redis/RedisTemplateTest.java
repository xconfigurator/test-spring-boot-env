package liuyang.testspringbootenv.modules.data.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;

/**
 *
 *
 * @author liuyang(wx)
 * @since 2022/7/20
 * 
 */
@SpringBootTest
//@ActiveProfiles({"dev-aliyun"})// hbfec连不上阿里云的redis，但是mysql可以。
@ActiveProfiles({"dev-x13"})
@Slf4j
public class RedisTemplateTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void testHello() {
        redisTemplate.opsForValue().set("foo", "bar");
        log.info("{}", redisTemplate.opsForValue().get("foo"));
    }
}
