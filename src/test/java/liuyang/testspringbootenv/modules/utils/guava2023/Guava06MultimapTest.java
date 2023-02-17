package liuyang.testspringbootenv.modules.utils.guava2023;

import com.google.common.collect.HashMultimap;
import org.junit.jupiter.api.Test;

/**
 * https://www.bilibili.com/video/BV1Lv411P7Ua?p=7&spm_id_from=pageDriver&vd_source=8bd7b24b38e3e12c558d839b352b32f4
 * @author xconf
 * @since 2023/2/17
 */
public class Guava06MultimapTest {
    @Test
    void test() {
        final HashMultimap<String, String> stringStringHashMultimap = HashMultimap.<String, String>create();
        stringStringHashMultimap.put("k1", "v1");
        stringStringHashMultimap.put("k1", "v1");
        stringStringHashMultimap.put("k1", "v2");
        stringStringHashMultimap.put("k2", "v2");
        System.out.println(stringStringHashMultimap);
        System.out.println(stringStringHashMultimap.get("k1"));
        System.out.println(stringStringHashMultimap.asMap());// 转化成JDK原生API
    }
}
