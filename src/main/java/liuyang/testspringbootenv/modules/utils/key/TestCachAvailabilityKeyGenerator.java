package liuyang.testspringbootenv.modules.utils.key;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.UUID;

/**
 * 不用用到生产环境，同类方法产生相同的缓存key，没有业务数据。
 * 仅做缓存可用性测试。
 * 期待结果：所有查询方法只发出一次SQL。
 *
 * @author liuyang
 * @scine 2021/4/14
 */
@Component("testCachAvailabilityKeyGenerator")
public class TestCachAvailabilityKeyGenerator implements KeyGenerator {

    private static final String KEY_PREFIX = "LIUYANG_KEY_GEN_AVAILABILITY";

    @Override
    public Object generate(Object target, Method method, Object... params) {
        String sp = "-";
        StringBuilder sb = new StringBuilder();
        sb.append(KEY_PREFIX);
        sb.append(sp);
        // 类名
        sb.append(target.getClass().getSimpleName());
        sb.append(sp);
        // 方法名
        sb.append(method.getName());
        // sb.append(sp);
        // UUID
        // sb.append(UUID.randomUUID());
        return sb.toString();
    }
}