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

    }

    @Test
    public void givenUsers_whenQueryEmpty_thenOr() {

    }

    @Test
    public void givenUsers_whenQuerying_thenIfPresent() {

    }

    @Test
    public void givenUsers_whenQuerying_thenIfPresentOrElse() {

    }
}
