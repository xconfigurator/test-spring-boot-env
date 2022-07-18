package liuyang.testspringbootenv.modules.jdk.stream;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

/**
 *
 *
 * @author liuyang(wx)
 * @since 2022/7/18
 * 
 */
public class HelloTest {

    @Test
    void hey() {
        List<Integer> integers = Arrays.asList(1, 2, 3, 4);
        for (Integer i : integers) {
            System.out.println(i);
        }
        integers = null;
        for (Integer i : integers) {
            System.out.println(i);
        }
    }
}
