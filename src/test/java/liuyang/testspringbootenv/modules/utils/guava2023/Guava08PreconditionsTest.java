package liuyang.testspringbootenv.modules.utils.guava2023;

import org.assertj.core.util.Preconditions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * https://www.bilibili.com/video/BV1Lv411P7Ua?p=9&spm_id_from=pageDriver&vd_source=8bd7b24b38e3e12c558d839b352b32f4
 *
 * @author xconf
 * @since 2023/2/17
 */
public class Guava08PreconditionsTest {
    @Test
    void test() {
        String param = null;
        Assertions.assertThrows(NullPointerException.class, () -> {
            Preconditions.checkNotNull(param, "参数不能为空");
        });

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Preconditions.checkArgument(param != null, "参数不能为空");
        });
    }
}
