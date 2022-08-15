package liuyang.testspringbootenv.modules.jdk.stream;

import liuyang.testspringbootenv.modules.jdk.stream.domain.User;
import liuyang.testspringbootenv.modules.jdk.stream.domain.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 三类操作符
 *
 * 1. filter, map, peek, findAny, findFirst
 * 2. 终端操作符 forEach, anyMatch, noneMatch
 * 3. 终端操作符 count, min, max
 *
 * 重点：
 * map：givenUsers_withMap_thenTransformUsingStream
 *
 */
@Slf4j
public class _02_02_BasicOperatorTests {

    private static final User[] arrayOfUsers = {
        User.builder().id(1L).username("zhangsan").name("张三").enabled(true).mobile("13000000001").build(),
        User.builder().id(2L).username("lisi").name("李四").enabled(false).mobile("13000000002").build(),
        User.builder().id(3L).username("wangwu").name("王五").enabled(true).mobile("13100000000").build(),
    };

    private List<User> userList;

    @BeforeEach
    void setup() {
        userList = Arrays.asList(arrayOfUsers);
    }

    @Test
    public void givenUsers_whenForEach_thenChangeGender() {
        for (User user: arrayOfUsers) {
            user.setEnabled(true);
        }
        assertTrue(userList.get(1).isEnabled());
    }

    @Test
    public void givenUsers_whenForEach_thenChangeGenderUsingStream() {
        userList.stream().forEach(user -> user.setEnabled(true));
        assertTrue(userList.get(1).isEnabled());
    }

    @Test
    public void givenUsers_whenForEachOrdered_thenPrintInfo() {
        List<User> toSort = new ArrayList<>();
        /*
        for (User user : userList) {
            toSort.add(user);
        }*/
        toSort.addAll(userList);
        toSort.sort(Comparator.comparing(User::getUsername));
        for (User user : toSort) {
            log.info("用户信息:{}", user);
        }
    }

    @Test
    public void givenUsers_whenForEachOrdered_thenPrintInfoUsingStream() {
        userList.stream()
                .sorted(Comparator.comparing(User::getUsername))
                .forEachOrdered(user -> log.info("用户信息:{}", user));// forEach vs forEachOrdered 在非并行流时没有区别。
        // 对比一下上面的写法，就会显得写与想表达的意思高度一致且非常流畅自然。
    }

    // 在一个集合中找满足某个条件元素的第一个
    @Test
    public void givenUsers_whenMatchUsername_thenFindFirst() {
        User first = null;
        for (User user : arrayOfUsers) {
            if (user.getUsername().equals("lisi")) {
                first = user;
                break;
            }
        }

        assertNotNull(first);
        assertEquals("lisi", first.getUsername());
    }

    @Test
    public void givenUsers_whenMatchUsername_thenFindFirstUsingStream() {
        Optional<User> lisi = userList.stream().filter(user -> user.getUsername().equals("lisi")).findFirst();

        assertTrue(lisi.isPresent());
        if (lisi.isPresent()) {
            assertEquals("lisi", lisi.get().getUsername());
        }
    }

    @Test
    public void givenUsers_whenMatchUsername_thenFindFirstUsingStream_ifUserListIsEmpty() {
        userList = new ArrayList<>();
        //Optional<User> lisi = userList.stream().filter(user -> user.getUsername().equals("lisi")).findFirst();
        /*
        assertTrue(lisi.isPresent());
        if (lisi.isPresent()) {
            assertEquals("lisi", lisi.get().getUsername());
        }
         */

        // 实测到这里不会报错
        userList.stream().filter(user -> user.getUsername().equals("lisi"));

        /*
        .ifPresentOrElse(
                        username -> {
                            log.debug("username: {}", username);
                            assertEquals("zhangsan", username);
                        },
                        () -> {
                            log.debug("cannot reach else block");
                });
         */

        Optional<User> lisi = userList.stream()
                .filter(user -> user.getUsername().equals("lisi"))
                .findFirst();

        if (lisi.isPresent()){
            log.info("有lisi");
        } else {
            log.info("无lisi");
        }
    }

    @Test
    public void givenUsers_whenMatchUsername_thenFindAnyUsingStream() {
        Optional<User> lisi = userList.stream().filter(user -> user.getUsername().equals("lisi")).findAny();

        assertTrue(lisi.isPresent());
        if (lisi.isPresent()) {
            assertEquals("lisi", lisi.get().getUsername());
        }
    }

    @Test
    public void givenUsers_withAnyMatch_thenReturnTrue() {
        boolean existed = false;
        for (User user : userList) {
            if (user.getMobile().startsWith("130")) {
                existed = true;
                break;
            }
        }
        assertTrue(existed);
    }

    @Test
    public void givenUsers_withAnyMatch_thenReturnTrueUsingStream() {
        boolean b = userList.stream().anyMatch(user -> user.getMobile().startsWith("130"));
        assertTrue(b);

        // 下面的方法也能达到目的
        assertTrue(userList.stream().filter(user -> user.getMobile().startsWith("130")).count() > 0);

        // 使用Predicated
        Predicate<User> userPredicate = user -> user.getMobile().startsWith("130");
        assertTrue(userList.stream().anyMatch(userPredicate));
    }

    @Test
    public void givenUsers_withNoneMatch_thenReturnTrue() {
        boolean nonExisted = true;
        for (User user : userList) {
            if (user.getMobile().startsWith("132")) {
                nonExisted = false;
                break;
            }
        }
        assertTrue(nonExisted);
    }

    @Test
    public void givenUsers_withNoneMatch_thenReturnTrueUsingStream() {
        boolean b = userList.stream().noneMatch(user -> user.getMobile().startsWith("123"));
        assertTrue(b);
    }

    @Test
    public void givenUsers_withAllMatch_thenReturnTrueUsingStream() {
        boolean b = userList.stream().allMatch(user -> user.getMobile().startsWith("13"));
        assertTrue(b);
        boolean b1 = userList.stream().allMatch(user -> user.getMobile().startsWith("130"));
        assertFalse(b1);
    }

    // map
    @Test
    public void givenUsers_withMap_thenTransformUsingStream() {
        List<UserDTO> userDTOList = userList.stream()
                .map(user -> UserDTO.builder()
                        .username(user.getUsername())
                        .name(user.getName())
                        .enabled(user.isEnabled() ? "激活" : "禁用")
                        .mobile(user.getMobile())
                        .build())
                .collect(Collectors.toList());
        assertEquals(3, userDTOList.size());
    }

    @Test
    public void givenUsers_whenFilterUsername_thenGetCount() {
        long count = 0l;
        for (User user : userList) {
            if (user.getMobile().startsWith("130")) {
                count ++;
            }
        }
        assertEquals(2, count);
    }

    // count
    @Test
    public void givenUsers_whenFilterUsername_thenGetCountUsingStream() {
        long count = userList.stream().filter(user -> user.getMobile().startsWith("130")).count();
        assertEquals(2, count);

        // 使用Predicate
        Predicate<User> userPredicate = user -> user.getMobile().startsWith("130");
        long count1 = userList.stream().filter(userPredicate).count();
        assertEquals(2, count1);
    }

    // max
    // 需要指定比较规则：肯定是先对某一个字段进行排序，然后才能取到最大或者最小，所以要指定比较规则。
    @Test
    public void givenUsers_thenTestOtherTerminalOperatorsUsingStream() {
        // 最大的ID
        Optional<User> max = userList.stream().max(Comparator.comparing(User::getId));
        assertTrue(max.isPresent());
        assertEquals(3l, max.get().getId());
    }
}
