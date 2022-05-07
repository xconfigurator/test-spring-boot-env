package liuyang.testspringbootenv.modules.jdk.stream;

import liuyang.testspringbootenv.modules.jdk.stream.domain.User;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 2-1 什么是流和创建流的方式
 *
 */
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
        List<User> collect = Arrays.stream(arrayOfUsers)
                .peek(user -> log.debug("user: {}", user))
                .collect(toList());
        assertEquals(arrayOfUsers.length, collect.size());
    }

    @Test
    public void givenUsers_createStreamWithList() {
        List<User> collect = userList.stream()
                .peek(user -> log.debug("user: {}", user))
                .collect(toList());
        assertEquals(userList.size(), collect.size());
    }

    @Test
    public void givenUsers_createStreamWithStreamOf() {
        List<User> collect = Stream.of(arrayOfUsers[0], arrayOfUsers[1], arrayOfUsers[2])
                .peek(user -> log.debug("user: {}", user))
                .collect(toList());
        assertEquals(arrayOfUsers.length, collect.size());
    }

    @Test
    public void givenUsers_createStreamWithStreamIterate() {
        List<Integer> collect = Stream.iterate(0, n -> n + 1)// 无尽流
                .limit(10)// 限制条数
                .peek(n -> log.debug("n = {}", n))
                .collect(toList());
    }

    @Test
    public void givenUsers_createStreamWithStreamGenerate() {
        List<Double> collect = Stream.generate(() -> Math.random())
                .limit(10)
                .peek(n -> log.debug("n = {}", n))
                .collect(toList());
    }

    @Test
    public void givenUsers_createStreamWithStreamSplitIterator() {
        Iterator<User> iterator = userList.iterator();
        Spliterator<User> userSpliterator = Spliterators.spliteratorUnknownSize(iterator, Spliterator.NONNULL);
        Stream<User> userStream = StreamSupport.stream(userSpliterator, false);
        List<User> collect = userStream.peek(user -> log.debug("user: {}", user.getUsername())).collect(toList());
        assertEquals(3, collect.size());
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
        Stream.Builder<User> builder = Stream.builder();
        builder.add(arrayOfUsers[0])
                .add(arrayOfUsers[1])
                .add(arrayOfUsers[2])
                .build()
                .skip(1)// 跳过n个元素
                .peek(user -> log.debug("user: {}", user.getUsername()))
                .collect(Collectors.toList());
    }

    @Test
    public void givenSentence_createStreamWithNewAPIs() {
        // 没有讲
    }
}
