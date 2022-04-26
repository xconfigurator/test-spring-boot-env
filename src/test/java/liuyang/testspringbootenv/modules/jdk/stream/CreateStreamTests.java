package liuyang.testspringbootenv.modules.jdk.stream;

import liuyang.testspringbootenv.modules.jdk.stream.domain.User;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class CreateStreamTests {

    private static final User[] arrayOfUsers = {
            User.builder().id(1L).username("zhangsan").name("张三").enabled(true).mobile("13000000001").build(),
            User.builder().id(2L).username("lisi").name("李四").enabled(false).mobile("13000000002").build(),
            User.builder().id(3L).username("wangwu").name("王五").enabled(true).mobile("13000000003").build(),
    };

    private List<User> userList;

    @BeforeEach
    void setup() {
        userList = Arrays.asList(arrayOfUsers);
    }

    @Test
    public void givenUsers_createStreamWithArray() {

    }

    @Test
    public void givenUsers_createStreamWithList() {

    }

    @Test
    public void givenUsers_createStreamWithStreamOf() {

    }

    @Test
    public void givenUsers_createStreamWithStreamIterate() {

    }

    @Test
    public void givenUsers_createStreamWithStreamGenerate() {

    }

    @Test
    public void givenUsers_createStreamWithStreamSplitIterator() {

    }

    @Test
    public void givenIntegerRange_createStreamWithIntStream() {
        val list = IntStream.rangeClosed(0, 5)
                .boxed()
                .peek(i -> log.debug("the number is {}", i))
                .collect(toList());
        assertEquals(6, list.size());
    }

    @Test
    public void givenUsers_createStreamWithStreamBuilder() {

    }

    @Test
    public void givenSentence_createStreamWithNewAPIs() {

    }
}
