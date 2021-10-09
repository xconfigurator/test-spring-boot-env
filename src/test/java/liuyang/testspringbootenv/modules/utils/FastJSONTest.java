package liuyang.testspringbootenv.modules.utils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author liuyang
 * @scine 2021/9/23
 */
@Slf4j
public class FastJSONTest {

    @Test
    void test() {
        @Data
        class A {
            private String foo;
            private String bar;
        }

        A a = new A();
        a.setFoo("foo");
        a.setBar("bar");

        log.info("a = {}");
    }
}
