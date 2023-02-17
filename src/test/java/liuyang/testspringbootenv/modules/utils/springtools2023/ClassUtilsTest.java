package liuyang.testspringbootenv.modules.utils.springtools2023;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.util.ClassUtils;

/**
 * 应用：Spring Boot 中的 @Conditional
 * @author xconf
 * @since 2023/2/17
 */
@Slf4j
public class ClassUtilsTest {

    @Test
    void test() {
        // 问题；是否加载了指定类
        log.info("{}", ClassUtils.isPresent("liuyang.Foo", null));
        log.info("{}", ClassUtils.isPresent("java.lang.String", null));
    }
}
