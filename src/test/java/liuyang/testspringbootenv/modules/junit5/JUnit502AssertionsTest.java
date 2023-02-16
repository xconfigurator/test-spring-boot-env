package liuyang.testspringbootenv.modules.junit5;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 断言（断定某件事情一定会发生，如果它没有发生，那就证明出现了问题。）
 * import static org.junit.jupiter.api.Assertions.*;
 * <p>
 * 1. 简单断言
 * 2. 数组断言
 * 3. 组合断言
 * 4. 异常断言
 * 5. 超时断言
 * 6. 快速失败
 *
 * @author xconf
 * @since 2023/2/16
 */
public class JUnit502AssertionsTest {

    /*
    1. 简单断言
    方法	说明
    assertEquals	判断两个对象或两个原始类型是否相等
    assertNotEquals	判断两个对象或两个原始类型是否不相等
    assertSame	判断两个对象引用是否指向同一个对象
    assertNotSame	判断两个对象引用是否指向不同的对象
    assertTrue	判断给定的布尔值是否为 true
    assertFalse	判断给定的布尔值是否为 false
    assertNull	判断给定的对象引用是否为 null
    assertNotNull	判断给定的对象引用是否不为 null
     */
    @Test
    @DisplayName("simple assertion")
    public void simple() {
        assertEquals(3, 1 + 2, "simple math");
        assertNotEquals(3, 1 + 1);

        assertNotSame(new Object(), new Object());
        Object obj = new Object();
        assertSame(obj, obj);

        assertFalse(1 > 2);
        assertTrue(1 < 2);

        assertNull(null);
        assertNotNull(new Object());
    }

    /*
    2、数组断言
    通过 assertArrayEquals 方法来判断两个对象或原始类型的数组是否相等
     */
    @Test
    @DisplayName("array assertion")
    public void array() {
        assertArrayEquals(new int[]{1, 2}, new int[] {1, 2});
    }

    /*
    3、组合断言
    assertAll 方法接受多个 org.junit.jupiter.api.Executable 函数式接口的实例作为要验证的断言，可以通过 lambda 表达式很容易的提供这些断言
     */
    @Test
    @DisplayName("assert all")
    public void all() {
        assertAll("Math",
                () -> assertEquals(2, 1 + 1),
                () -> assertTrue(1 > 0)
        );
    }

    /*
    4、异常断言
    在JUnit4时期，想要测试方法的异常情况时，需要用@Rule注解的ExpectedException变量还是比较麻烦的。而JUnit5提供了一种新的断言方式Assertions.assertThrows() ,配合函数式编程就可以进行使用。
     */
    @Test
    @DisplayName("异常测试")
    public void exceptionTest() {
        ArithmeticException exception = Assertions.assertThrows(
                //扔出断言异常
                ArithmeticException.class, () -> System.out.println(1 % 0));

    }

    /*
    5、超时断言
    Junit5还提供了Assertions.assertTimeout() 为测试方法设置了超时时间
     */
    @Test
    @DisplayName("超时测试")
    public void timeoutTest() {
        //如果测试方法时间超过1s将会异常
        /*
        Assert that execution of the supplied executable completes before the given timeout is exceeded.
        Note: the executable will be executed in the same thread as that of the calling code. Consequently, execution of the executable will not be preemptively aborted if the timeout is exceeded.
         */
        Assertions.assertTimeout(Duration.ofMillis(1000), () -> Thread.sleep(500));
    }

    /*
    6、快速失败
    通过 fail 方法直接使得测试失败
     */
    @Test
    @DisplayName("fail")
    public void shouldFail() {
        fail("This should fail");
    }
}
