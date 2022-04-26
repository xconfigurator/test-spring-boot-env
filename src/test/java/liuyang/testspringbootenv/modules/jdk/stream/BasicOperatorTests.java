package liuyang.testspringbootenv.modules.jdk.stream;

import liuyang.testspringbootenv.modules.jdk.stream.domain.User;
import liuyang.testspringbootenv.modules.jdk.stream.domain.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Predicate;

import static java.util.stream.Collectors.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class BasicOperatorTests {

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

    }

    @Test
    public void givenUsers_whenForEachOrdered_thenPrintInfoUsingStream() {

    }

    @Test
    public void givenUsers_whenMatchUsername_thenFindFirst() {

    }

    @Test
    public void givenUsers_whenMatchUsername_thenFindFirstUsingStream() {

    }

    @Test
    public void givenUsers_whenMatchUsername_thenFindAnyUsingStream() {

    }

    @Test
    public void givenUsers_withAnyMatch_thenReturnTrue() {

    }

    @Test
    public void givenUsers_withAnyMatch_thenReturnTrueUsingStream() {

    }

    @Test
    public void givenUsers_withNoneMatch_thenReturnTrue() {

    }

    @Test
    public void givenUsers_withNoneMatch_thenReturnTrueUsingStream() {

    }

    @Test
    public void givenUsers_withAllMatch_thenReturnTrueUsingStream() {

    }

    @Test
    public void givenUsers_withMap_thenTransformUsingStream() {

    }

    @Test
    public void givenUsers_whenFilterUsername_thenGetCount() {

    }

    @Test
    public void givenUsers_whenFilterUsername_thenGetCountUsingStream() {

    }

    @Test
    public void givenUsers_thenTestOtherTerminalOperatorsUsingStream() {

    }
}
