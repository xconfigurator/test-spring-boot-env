package liuyang.testspringbootenv.modules.jdk;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.regex.Pattern;

/**
 * @author liuyang(wx)
 * @since 2022/7/25
 */
@Slf4j
public class StringTest {
    @ParameterizedTest
    @ValueSource(strings = {
            "1_-1","2_-1", "1_1", "2_2", "1_-1", "2_0"
            ,"_", "_1", "1_", "x_x", ""})
    void test202207251101(String keyValue) {
        Pattern PATTERN_All_ENODEB = Pattern.compile("-?\\d_-?\\d");
        if (!PATTERN_All_ENODEB.matcher(keyValue).matches()) {
            log.info("dismatch");
        } else {
            log.info("match");
        }
    }
}
