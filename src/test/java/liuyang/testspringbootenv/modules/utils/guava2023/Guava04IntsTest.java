package liuyang.testspringbootenv.modules.utils.guava2023;

import com.google.common.primitives.Doubles;
import com.google.common.primitives.Floats;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * 视频参考：
 * https://www.bilibili.com/video/BV1Lv411P7Ua?p=5&spm_id_from=pageDriver&vd_source=8bd7b24b38e3e12c558d839b352b32f4
 * 22:32
 *
 * Ints/Longs/Floats/Doubles
 * Strings, 跟上述三个略有不同
 * @author xconf
 * @since 2023/2/17
 */
@Slf4j
public class Guava04IntsTest {
    @Test
    void test() {
        log.info("{}", Ints.asList(1, 2, 3));
        log.info("{}", Longs.asList(1, 2, 3));
        log.info("{}", Floats.asList(1.1f, 1.2f, 1.3f));
        log.info("{}", Doubles.asList(1.1, 1.2, 1.3));
        // 实际上，Arrays在这个场景中要更好用
    }
}
