package liuyang.testspringbootenv.modules.jdk.stream;

import liuyang.testspringbootenv.modules.jdk.stream.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.util.Pair;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 用来处理流的嵌套（高阶流）
 *
 */
@Slf4j
public class _03_01_FlatMapTests {
    private static final User[] arrayOfUsers = {
            User.builder()
                    .id(1L)
                    .username("zhangsan")
                    .name("张三")
                    .age(30)
                    .enabled(true)
                    .mobile("13000000001")
                    .roles(List.of("ROLE_ADMIN", "ROLE_USER"))
                    .build(),
            User.builder()
                    .id(2L)
                    .username("lisi")
                    .name("李四")
                    .age(32)
                    .enabled(false)
                    .mobile("13000000002")
                    .roles(List.of("ROLE_ADMIN"))
                    .build(),
            User.builder()
                    .id(3L)
                    .username("wangwu")
                    .name("王五")
                    .age(41)
                    .enabled(true)
                    .mobile("13000000003")
                    .roles(List.of("ROLE_USER"))
                    .build(),
    };

    private List<User> userList;

    static class ThirdPartyApi {
        static Optional<Profile> findByUsername(String username) {
            return Arrays.stream(arrayOfUsers)
                    .filter(user -> !"zhangsan".equals(username) && user.getUsername().equals(username))
                    .findAny()
                    .map(user -> new Profile(user.getUsername(), "Hello, " + user.getName()));
        }
    }

    @AllArgsConstructor
    @Data
    static class Profile {
        private String username;
        private String greeting;
    }

    @BeforeEach
    void setup() {
        userList = Arrays.asList(arrayOfUsers);
    }

    @Test
    public void givenUsersWithRoles_whenParentChild_withoutFlatMap() {
        //userList.stream().map(user -> user.getRoles()).forEach(System.out::println);// ok
        //userList.stream().map(user -> user.getRoles()).peek(roles -> log.debug("roles = {}", roles));// 并不执行
        List<List<String>> collect = userList.stream().map(user -> user.getRoles()).peek(roles -> log.debug("roles = {}", roles)).collect(toList());
        System.out.println(collect);
    }

    @Test
    public void givenUsersWithRoles_withFlatMap() {
        List<String> collect = userList.stream()
                .flatMap(user -> user.getRoles().stream()) // flatMap是要操作流的
                .peek(roles -> log.debug("roles = {}", roles))
                .collect(toList());
        System.out.println(collect);
    }

    // 处理流当中有Optional的情况 - 引例
    @Test
    public void givenUsers_withOptional_thenWithStream() {
        // 得到了一个这种模式的集合
        // 在一般操作过程中，这显然并不是我们期待的样子。
        // 去掉Option以及Optional.empty见后面的例子givenUsers_withOptional_thenFlatMapWithStream
        List<Optional<Profile>> collect = userList.stream()
                .map(user -> ThirdPartyApi.findByUsername(user.getUsername()))
                .peek(profile -> log.debug("profile: {}", profile))
                .collect(toList());
    }

    // 处理流当中有Optional的情况 - 实现
    @Test
    public void givenUsers_withOptional_thenFlatMapWithStream() {
        List<Profile> collect = userList.stream()
                .map(user -> ThirdPartyApi.findByUsername(user.getUsername()))
                // 关键一步 begin 只要有值的就打印出来了，并且去掉了Optional
                .flatMap(Optional::stream)
                // 关键一步 end
                .peek(profile -> log.debug("profile: {}", profile))
                .collect(toList());
    }

    // 视频 2-3
    @Test
    public void givenUsers_withOptional_thenDealElseWithStream() {
        String greeting = ThirdPartyApi.findByUsername("zhangsan")
                .map(Profile::getGreeting)
                .orElse("未知用户");
        assertEquals("未知用户", greeting);
    }

    @Test
    public void givenUsersWithRoles_whenFlatMap_thenGroupByRole() {
        
    }

    @Test
    public void givenUsersWithRoles_whenFlatMap_thenGroupByRoleWithStream() {

    }
}
