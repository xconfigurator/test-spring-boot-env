package liuyang.testspringbootenv.modules.utils.key;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.UUID;

/**
 * 缓存上要是用这个，就永远别指望缓存成功了！
 *
 * @author liuyang
 * @scine 2021/4/14
 */
@Component("uuidKeyGenerator")
public class UUIDKeyGenerator implements KeyGenerator {

    private static final String KEY_PREFIX = "LIUYANG_KEY_GEN_UUID";

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
        sb.append(sp);
        // UUID
        sb.append(UUID.randomUUID());
        return sb.toString();
    }
}
