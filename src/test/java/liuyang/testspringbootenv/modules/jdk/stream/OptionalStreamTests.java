package liuyang.testspringbootenv.modules.jdk.stream;

import liuyang.testspringbootenv.modules.jdk.stream.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Optional - 一种防止空指针异常的措施
 * 其最主要的作用是在流操作过程中确保流不因为null而中断。
 * https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/Optional.html
 *
 * isPresent, isEmpty
 * 空的时候：    orElse（给出默认值 常量）, orElseGet（给出默认值 工厂）, orElseThrow（抛异常）, or（继续返回一个Optional）
 * 有值得时候：   ifPresent, ifPresentOrElse（有值没值都处理）
 *
 * 应用场景参考 FlatMapTests.givenUsers_withOptional_thenDealElseWithStream
 *
 */
@Slf4j
public class OptionalStreamTests {

    private static final User[] arrayOfUsers = {
            User.builder().id(1L).username("zhangsan").name("张三").enabled(true).mobile("13000000001").build(),
            User.builder().id(2L).username("lisi").name("李四").enabled(false).mobile("13000000002").build(),
            User.builder().id(3L).username("wangwu").name("王五").enabled(true).mobile("13100000000").build(),
    };

    private MockRepo repo;

    @BeforeEach
    void setup() {
        repo = new MockRepo();
    }

    static class MockRepo {
        Optional<User> findByUsername(String username) {
            return Arrays.stream(arrayOfUsers)
                    .filter(user -> user.getUsername().equals(username))
                    .findAny();
        }
    }

    @Test
    public void givenUsers_whenQueryOptional_thenCheckPresent() {

    }

    @Test
    public void givenUsers_whenQueryEmpty_thenOrElseThrow() {

    }

    @Test
    public void givenUsers_whenQueryEmpty_thenOrElse() {

    }

    @Test
    public void givenUsers_whenQueryEmpty_thenOrElseGet() {
        String username = repo.findByUsername("zhaoliu")
                .map(User::getUsername)
                .orElseGet(() -> "anonymous");// Supplier。 orElse提供一个常量（可以是一个复杂对象），orElseGet是一个工厂。
        assertNotNull(username);
        assertEquals("anonymous", username);
    }

    @Test
    public void givenUsers_whenQueryEmpty_thenOr() {
        Optional<String> or = repo.findByUsername("zhaoliu")
                .map(User::getUsername)
                .or(() -> Optional.of("notExisted"));// 场景：如果不想抛异常、也不想返回默认值，而是仍然返回衣蛾Optional对象。
        assertTrue(or.isPresent());
        assertEquals("notExisted", or.get());
    }

    @Test
    public void givenUsers_whenQuerying_thenIfPresent() {
        log.debug("1. ===================");
        repo.findByUsername("zhangsan")
                .map(User::getUsername)
                .ifPresent(username -> {
                    log.debug("username: {}", username);
                    assertEquals("zhangsan", username);
                });

        log.debug("2. ===================");
        repo.findByUsername("zhansan")
                .map(User::getUsername)
                .ifPresent(username -> {
                    log.debug("username: {}", username);// 不会输出，因为没有zhansan这个人
                    assertEquals("zhansan", username);
                });

        log.debug("end");
    }

    @Test
    public void givenUsers_whenQuerying_thenIfPresentOrElse() {
        repo.findByUsername("zhangsan")
                .map(User::getUsername)// 规定了ifPresentOrElse中Consumer入参的值
                .ifPresentOrElse(
                        username -> {
                            log.debug("username: {}", username);
                            assertEquals("zhangsan", username);
                        },
                        () -> {
                            log.debug("cannot reach else block");
                });
        repo.findByUsername("zhaoliu")
                .map(User::getUsername)// 规定了ifPresentOrElse中Consumer入参的值
                .ifPresentOrElse(
                        username -> {
                            log.debug("username: {}", username);
                            assertEquals("zhaoliu", username);
                        },
                        () -> {
                            log.debug("cannot reach else block");
                        });
    }
}
