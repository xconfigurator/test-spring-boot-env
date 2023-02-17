package liuyang.testspringbootenv.modules.utils.guava2023;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.SpliteratorAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 视频参考
 * https://www.bilibili.com/video/BV1Lv411P7Ua?p=5&spm_id_from=pageDriver&vd_source=8bd7b24b38e3e12c558d839b352b32f4
 * <p>
 * joiner/splitter(字符串和集合互转)
 * 下划线和驼峰互转
 * Lists
 * Ints
 * Multiset:    HashMultiset
 * Multimap:    HashMaultimap
 * BiMap:       HashBiMap
 * ImmutableList
 * Preconditions
 *
 * @author xconf
 * @since 2023/2/17
 */
@Slf4j
public class Guava01JoinerSplitterTest {

    @Test
    void testJoiner() {
        // 误会的写法：
        log.info("{}", Joiner.on(",").join(
                Arrays.asList(1, 2, 3),
                Arrays.asList(2, 3, 4),
                Arrays.asList(3, 4, 5)));

        // 一般的写法
        log.info("{}", Joiner.on(",").join(
                Arrays.asList(1, 2, 3)));

        // 注意List中不可以有null
        Assertions.assertThrows(java.lang.NullPointerException.class, () -> {
            log.info("{}", Joiner.on(",").join(
                    Arrays.asList(1, 2, 3, null)));
        });

        // 配置一下 - 忽略
        log.info("{}",
                Joiner.on(",")
                        .skipNulls()
                        .join(
                                Arrays.asList(1, 2, 3, null)));

        // 配置一下 - 替代
        log.info("{}",
                Joiner.on(",")
                        //.skipNulls()
                        .useForNull("xxx")
                        .join(
                                Arrays.asList(1, 2, 3, null)));
    }

    // Joiner在JDK8 之后可以使用JDK提供的stream来完成
    @Test
    void testJoinerStream() {
        final List<String> strings = Arrays.asList("1", "2", "3", null);
        log.info("{}", strings
                .stream()
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.joining(",")));
    }


    @Test
    void testSpliter() {
        System.out.println("#######################################");
        String str = "a, b, c";
        final Iterable<String> split = Splitter.on(",").split(str);
        split.forEach(e -> System.out.println(e));

        System.out.println("#######################################");
        String str2 = "a, b, \"\",, c";
        final Iterable<String> split2 = Splitter.on(",").omitEmptyStrings().split(str2);// 会没有空白字符串，但显然把“”当成了由两个双引号组成的字符串，而不是逻辑意义上对的空字符串。
        split2.forEach(e -> System.out.println(e));

        System.out.println("#######################################");
        String str3 = "a, b, \"\",, c";
        final Iterable<String> split3 = Splitter.on(",").omitEmptyStrings().trimResults().split(str3);// 会没有空白字符串，但显然把“”当成了由两个双引号组成的字符串，而不是逻辑意义上对的空字符串。
        split3.forEach(e -> System.out.println(e));

        System.out.println("#######################################");
        Splitter.on(",").omitEmptyStrings().trimResults().splitToList(str3).stream().forEach(e -> System.out.println(e));
    }

}
