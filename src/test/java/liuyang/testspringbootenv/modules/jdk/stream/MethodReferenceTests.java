package liuyang.testspringbootenv.modules.jdk.stream;

import liuyang.testspringbootenv.modules.jdk.stream.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.function.BiFunction;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

// chap02
@Slf4j
public class MethodReferenceTests {

    @Test
    public void givenNewInstance_thenReferenceConstructorInFunction() {

    }

    @Test
    public void givenInstance_thenReferenceInFunction() {

    }

    @Test
    public void givenStringIndex_thenReferenceInFunction() {

    }

    @Test
    public void givenStaticMethod_thenReferenceInFunction() {

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
