package liuyang.testspringbootenv.modules.utils.guava2023;

import com.google.common.collect.HashMultiset;
import org.junit.jupiter.api.Test;

/**
 * https://www.bilibili.com/video/BV1Lv411P7Ua?p=6&spm_id_from=pageDriver&vd_source=8bd7b24b38e3e12c558d839b352b32f4
 *
 * @author xconf
 * @since 2023/2/17
 */
public class Guava05MultisetTest {
    @Test
    void test() {
        final HashMultiset<String> multiset = HashMultiset.create();
        multiset.add("a");
        multiset.add("b");
        multiset.add("c");
        multiset.add("a");
        System.out.println(multiset);

        // 遍历
        multiset.stream().forEach(e -> System.out.println(e));
        System.out.println(multiset.entrySet());// 包含重复
        System.out.println(multiset.elementSet());// 不包含重复元素
    }
}
