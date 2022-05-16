package liuyang.testspringbootenv.modules.jdk.stream;

import liuyang.testspringbootenv.modules.jdk.stream.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.mutable.MutableInt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 执行归集操作 - 某种程度上和Collect作用类似
 *
 */
@Slf4j
public class _03_02_ReduceTests {
    private static final User[] arrayOfUsers = {
            User.builder().id(1L).username("zhangsan").name("张三").enabled(true).age(30).mobile("13000000001").build(),
            User.builder().id(2L).username("lisi").name("李四").enabled(false).age(32).mobile("13000000002").build(),
            User.builder().id(3L).username("wangwu").name("王五").enabled(true).age(41).mobile("13000000003").build(),
    };

    private List<User> userList;

    @BeforeEach
    void setup() {
        userList = Arrays.asList(arrayOfUsers);
    }

    @Test
    public void givenUsers_thenCompareReduceAndCollect() {
        // 设计上，reduce 应该和不可变对象一起工作。
        // 如果使用可变对象，也可以得到结果，但是不是线程安全的
        // 而且通常意义上来说， reduce 的性能要弱于 collect
        // 但 reduce 是一个非常灵活的选项，在各个语言和框架中有广泛应用
        Integer sumByReduce = userList
                .stream()
                .map(User::getAge)
                .reduce(0, (Integer acc, Integer curr) -> {
                    log.debug("acc {}, curr: {}", acc, curr);
                    return acc + curr;
                });
        assertEquals(103, sumByReduce);
        // collect 使用可变的容器
        MutableInt sumByCollect = userList.stream().collect(
                MutableInt::new,
                (MutableInt container, User user) -> container.add(user.getAge()),
                MutableInt::add
        );
        assertEquals(103, sumByCollect.getValue());
    }

    @Test
    public void givenTwoNumber_withoutStream_thenSumSuccess() {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
        int sum = 0;
        for (Integer number : numbers) {
            sum += number;
        }
        assertEquals(45, sum);
    }

    @Test
    public void givenTwoNumber_withReduce_thenSumSuccess() {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
        int sum = numbers.stream().reduce(0, (acc, curr) -> acc + curr);
        assertEquals(45, sum);
    }

    // max
    @Test
    public void givenUsers_thenReduceToMaxId() {
        Optional<User> userOptional = userList.stream()
                .reduce((acc, curr) -> {
                    // 注1：在没有累加器的情况下，acc的初始值就是流的第一个元素值。
                    // 注2：流中如果只有一个元素则根本不会进入这个位置。直接返回该值即结束。 见下面的测试。
                    log.debug("acc {}, curr {}", acc, curr);
                    return acc.getId() > curr.getId() ? acc : curr;
                });
        assertTrue(userOptional.isPresent());
        assertEquals(3L, userOptional.get().getId());

        // liuyang 202205161403
        System.out.println("========================================");
        Optional<User> reduce = userList.stream()
                .filter(user -> "lisi".equals(user.getUsername()))// 保证只有一条数据
                .peek(user -> log.debug("user = {}", user))
                .reduce((acc, curr) -> {
                    // 如果输入流只有一个元素，则不会进入这个代码块。
                    log.debug("acc {}, curr {}", acc, curr);
                    return acc.getId() > curr.getId() ? acc : curr;
                });
        log.debug("只有一条记录的时候的返回值 user = {}", reduce);
        //.collect(Collectors.toList());
    }

    // count
    // 注意reduce的第三个参数Integer::sum
    @Test
    public void givenUsers_thenReduceToCount() {
        Integer count = userList.stream()
                .reduce(0, (acc, curr) -> acc + 1, Integer::sum);
        assertEquals(3, count);
    }

    // 使用reduce操作实现一个toList
    // 对标：Collectors.toList();
    @Test
    public void givenUsers_thenReduceToList() {
        List<User> list = userList.parallelStream().reduce(
                Collections.emptyList(),// 初始值
                (acc, curr) -> {
                    List<User> newAcc= new ArrayList<>();
                    newAcc.addAll(acc);
                    newAcc.add(curr);
                    return newAcc;
                },
                // combiner 这个函数的作用主要是考虑并行流
                // 并行流的情况下，一个流会分成多个分片进行处理
                // 每一个分片会产生一个临时的中间结果
                // combiner 的作用是把这些中间结果再合并成一个最终结果
                (left, right) -> {
                    List<User> merged= new ArrayList<>();
                    merged.addAll(left);
                    merged.addAll(right);
                    return merged;
                }
        );
        assertEquals(3, list.size());
    }

    // liuyang 1 + 2 + 3 + ... + 100 的几种写法 - 01
    @Test
    public void testSum1to100_01() {
        int sum = 0;
        for (int i = 1; i <= 100; i++) {
            sum += i;
        }
        System.out.println(sum);
        assertEquals(5050, sum);
    }

    // liuyang 1 + 2 + 3 + ... + 100 的几种写法 - 02
    @Test
    public void testSum1to100_02() {
        int sum = IntStream.rangeClosed(1, 100).reduce(0, (a, b) -> a + b);
        System.out.println(sum);
        assertEquals(5050, sum);
    }
}
