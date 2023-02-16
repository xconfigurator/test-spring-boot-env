package liuyang.testspringbootenv.modules.junit5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assumptions.*;

/**
 * 前置条件
 * 断言：前面断言失败，后面的代码都不会执行。
 * 前置条件：前置条件若不满足，则不执行后面的代码。（在测试报告中，如果不满足前置条件，则汇总在“跳过”(Skipped)的部分。注：@Disable的用例也会被统计到Skipped中。）
 *
 * 4、前置条件（assumptions）
 * JUnit 5 中的前置条件（assumptions【假设】）类似于断言，不同之处在于不满足的断言会使得测试方法失败，而不满足的前置条件只会使得测试方法的执行终止。前置条件可以看成是测试方法执行的前提，当该前提不满足时，就没有继续执行的必要。
 *
 * @author xconf
 * @since 2023/2/16
 */
@DisplayName("前置条件")
public class JUnit503AssumptionTest {
    private final String environment = "DEV";

    @Test
    @DisplayName("simple")
    public void simpleAssume() {
        assumeTrue(Objects.equals(this.environment, "DEV"));
        assumeFalse(() -> Objects.equals(this.environment, "PROD"));
    }

    @Test
    @DisplayName("assume then do")
    public void assumeThenDo() {
        assumingThat(
                Objects.equals(this.environment, "DEV"),
                () -> System.out.println("In DEV")
        );
    }

}
