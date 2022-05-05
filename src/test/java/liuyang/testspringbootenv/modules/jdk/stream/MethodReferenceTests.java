package liuyang.testspringbootenv.modules.jdk.stream;

import liuyang.testspringbootenv.modules.jdk.stream.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

// chap02
@Slf4j
public class MethodReferenceTests {

    // 方法引用
    @Test
    public void givenNewInstance_thenReferenceConstructorInFunction() {
        Supplier<User> supplier = () -> new User();
        //Supplier<User> supplier = User::new;// 推荐写法
        assertNotNull(supplier.get());
    }

    // 实例引用
    @Test
    public void givenInstance_thenReferenceInFunction() {
        User user = User.builder().username("zhangsan").build();
        Supplier<String> supplier = () -> user.getUsername();
        //Supplier<String> supplier = user::getUsername;// 推荐写法
        assertEquals("zhangsan", supplier.get());
    }

    // 参数引用
    @Test
    public void givenStringIndex_thenReferenceInFunction() {
        BiFunction<String, String, Integer> paramRef = (a, b) -> a.indexOf(b);
        //BiFunction<String, String, Integer> paramRef = String::indexOf;// 推荐写法
        assertEquals(-1, paramRef.apply("Hello", "World"));
    }

    // 静态方法
    @Test
    public void givenStaticMethod_thenReferenceInFunction() {
        Greeting greeting = (a, b) -> Player.sayHello(a, b);
        //Greeting greeting = Player::sayHello;// 推荐写法
        assertEquals("Hello: World", greeting.sayHello("Hello", "World"));
    }

    interface Greeting {
        String sayHello(String s1, String s2);
    }

    static class Player {
        static String sayHello(String s1, String s2) {
            return s1 + ": " + s2;
        }
    }
}
