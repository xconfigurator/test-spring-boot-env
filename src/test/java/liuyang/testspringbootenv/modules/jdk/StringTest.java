package liuyang.testspringbootenv.modules.jdk;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author liuyang(wx)
 * @since 2022/7/25
 */
@Slf4j
public class StringTest {

    @ParameterizedTest
    @ValueSource(strings = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "001", "10", "20", "200", "1", "12", "123" })
    void test202211171112(String provinceId) {
        final Pattern pattern = Pattern.compile("^0+");
        String s = pattern.matcher(provinceId).replaceFirst("");
        log.info("处理前 = {}， 处理后 = {}", provinceId, s);
    }

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

    @Test
    void test202207251602() {
        String[] arr = {};
        for (String str : arr) {
            log.debug("foo");
        }
        log.debug("bar");
    }
}
