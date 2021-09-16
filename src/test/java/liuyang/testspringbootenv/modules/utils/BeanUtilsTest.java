package liuyang.testspringbootenv.modules.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author liuyang
 * @scine 2021/8/27
 */
@Slf4j
public class BeanUtilsTest {

    @Test
    void testCopyProperties() {
        // 测试点：long -> Long， int -> Integer, double -> Double是否可以拷贝
        // 结论：是可以的！
        @AllArgsConstructor
        @NoArgsConstructor
        @Data
        @ToString
        class A {
            String str;
            Long l;
            Integer i;
            Double d;
        }

        @AllArgsConstructor
        @NoArgsConstructor
        @Data
        @ToString
        class B {
            String str;
            long l;
            int i;
            double d;
        }

        A a1 = new A("测试A复制到B", 123L, 123, 12.3);
        B b1 = new B();
        BeanUtils.copyProperties(a1, b1);
        //System.out.println(b1);
        log.info("b1 = {}", b1);

        A a2 = new A();
        B b2 = new B("测试B复制到A", 123L, 123, 12.3);
        BeanUtils.copyProperties(b2, a2);
        //System.out.println(a2);
        log.info("a2 = {}", a2);
    }

    @Test
    void test() {
        log.info("now = {}", LocalDateTime.now());
    }

    @Test
    void testDefaultValue() {
        @AllArgsConstructor
        @NoArgsConstructor
        @Data
        @ToString
        class A {
            String str;
            Long l;
            Integer i;
            Double d;
        }

        A a = new A();

        log.info("a = {}", a);
    }
}
