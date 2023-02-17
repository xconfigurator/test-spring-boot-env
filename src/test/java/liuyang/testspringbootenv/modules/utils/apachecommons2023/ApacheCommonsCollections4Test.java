package liuyang.testspringbootenv.modules.utils.apachecommons2023;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * 视频参考:
 * https://www.bilibili.com/video/BV1Lv411P7Ua?p=3&spm_id_from=pageDriver&vd_source=8bd7b24b38e3e12c558d839b352b32f4
 *
 * @author xconf
 * @since 2023/2/16
 */
public class ApacheCommonsCollections4Test {

    /**
     * 集合运算
     */
    @Test
    void testCalc() {
        List<Integer> list1 = Arrays.asList(1, 2, 3);
        List<Integer> list2 = Arrays.asList(2, 3, 4);
        Assertions.assertEquals(Arrays.asList(2, 3), CollectionUtils.intersection(list1, list2));
        Assertions.assertEquals(Arrays.asList(1, 2, 3, 4), CollectionUtils.union(list1, list2));
        Assertions.assertEquals(Arrays.asList(1), CollectionUtils.subtract(list1, list2));
    }

    /**
     * 判空
     * list/set
     * map
     */
    @Test
    void testNull() {
        Assertions.assertTrue(CollectionUtils.isEmpty(new ArrayList<>()));
        Assertions.assertTrue(CollectionUtils.isEmpty(new TreeSet<>()));
        Assertions.assertTrue(CollectionUtils.isEmpty(null));
        Assertions.assertTrue(MapUtils.isEmpty(new HashMap<>()));
    }
}
