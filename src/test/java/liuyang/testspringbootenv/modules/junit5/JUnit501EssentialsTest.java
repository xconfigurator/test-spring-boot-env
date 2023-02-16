package liuyang.testspringbootenv.modules.junit5;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

/**
 * https://www.bilibili.com/video/BV19K4y1L7MT/?p=71&vd_source=8bd7b24b38e3e12c558d839b352b32f4
 *
 * @author xconf
 * @since 2023/2/16
 * <p>
 * 文字版：
 * https://www.yuque.com/atguigu/springboot/ksndgx
 * <p>
 * 1. 简介
 * https://www.bilibili.com/video/BV19K4y1L7MT/?p=71&vd_source=8bd7b24b38e3e12c558d839b352b32f4
 * 2. 常用测试注解
 * https://www.bilibili.com/video/BV19K4y1L7MT/?p=72&spm_id_from=pageDriver&vd_source=8bd7b24b38e3e12c558d839b352b32f4
 * 3. 断言机制
 * <p>
 * 4. 前置条件
 * <p>
 * 5. 嵌套测试
 * <p>
 * 6. 参数化测试
 */
//@SpringBootTest // 只添加中一个注解就可以使用Spring容器支持
@Slf4j
@DisplayName("JUnit5 常用测试注解 ")
public class JUnit501EssentialsTest {

    // ///////////////////////////////////////////////////////////////
    @BeforeAll
    static void beforeAll() {// 必须是static
        log.info("@BeforeAll##############################");
    }

    @BeforeEach
    void beforeEache() {
        log.info("@BeforeEach");
    }

    @AfterEach
    void afterEache() {
        log.info("@AfterEach");
    }

    @AfterAll
    static void afterAll() {// 必须是static
        log.info("@AfterAll##############################");
    }
    // ///////////////////////////////////////////////////////////////

    @DisplayName("DisplayName::hello, junit5!")
    @Test
    void hello() {
        log.info("foo");
    }

    @Test
    void hello2() {
        log.info("bar");
    }

    // 不执行
    @Disabled
    @Test
    void disabled() {
        log.info("@Disabled");
    }

    // 超时
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    @Test
    void timeout() {
        try {
            TimeUnit.SECONDS.sleep(6);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 重复测试
    @RepeatedTest(5)
    @Test
    void repeatTest() {
        log.info("hey");
    }
}
