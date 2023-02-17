package liuyang.testspringbootenv.modules.utils.guava2023;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 视频参考：
 * https://www.bilibili.com/video/BV1Lv411P7Ua?p=5&spm_id_from=pageDriver&vd_source=8bd7b24b38e3e12c558d839b352b32f4
 * 16:10
 * Lists/Maps/Sets
 *
 * @author xconf
 * @since 2023/2/17
 */
public class Guava03ListsTest {
    @Test
    void test() {
        // 1. 快速创建集合
        final ArrayList<String> strings = Lists.newArrayList("a", "b", "c");

        // 2. 需求：串ids，一次最多串20个
        // list分组
        final List<List<Integer>> partition = Lists.partition(Arrays.asList(1, 2, 3, 4), 1);
        System.out.println(partition);
    }
}
