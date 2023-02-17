package liuyang.testspringbootenv.modules.utils.guava2023;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * https://www.bilibili.com/video/BV1Lv411P7Ua?p=8&spm_id_from=pageDriver&vd_source=8bd7b24b38e3e12c558d839b352b32f4
 *
 * ImmutableList/ImmutaleSet/ImmutableMap
 * 适用场景：分布式缓存
 *
 * @author xconf
 * @since 2023/2/17
 */
public class Guava07ImmutableListTest {
    @Test
    void test() {
        // 就是不想要别人改变集合
        final ImmutableList<String> immutableList = ImmutableList.<String>builder().add("foo").build();
        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            immutableList.add("cc");
        });

        // jdk也提供了不可修改list
        final List<String> unmodifiableList = Collections.unmodifiableList(Arrays.asList("bar"));
        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            unmodifiableList.add("cc");
        });
    }
}
