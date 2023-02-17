package liuyang.testspringbootenv.modules.utils.apachecommons2023;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * commons-lang3
 * <commons-lang3.version>3.12.0</commons-lang3.version>
 *
 * 视频：https://www.bilibili.com/video/BV1Lv411P7Ua?p=2&vd_source=8bd7b24b38e3e12c558d839b352b32f4
 *
 * 1. StringUtils
 * 2. NumberUtils
 * 3. ObjectUtils
 * 4. ArrayUtils
 *
 * @author xconf
 * @since 2023/2/16
 */
@Slf4j
public class ApacheCommonsLang3Test {

    // StringUtils
    @Test
    void testStringUtils() {
        // 判断字符串
        Assertions.assertTrue(StringUtils.isBlank(""));
        Assertions.assertTrue(StringUtils.isBlank("  "));
        Assertions.assertTrue(StringUtils.isBlank(null));
        // 字符串翻转
        log.info("hello 翻转 = {}", StringUtils.reverse("hello"));
        // 脱敏
        String phoneStr = "18931160972";
        log.info("{}", StringUtils.left(phoneStr, 3) + "****" + StringUtils.right(phoneStr, 4));
        log.info("{}{}", StringUtils.rightPad(StringUtils.left(phoneStr, 3) , 7, "*"), StringUtils.right(phoneStr, 4));
    }

    // NumberUtils
    @Test
    void testNumberUtils() {
        // 判断一个参数是否是数字（整型，浮点型）
        Assertions.assertFalse(NumberUtils.isDigits("12.3aaa"));
        // 整型
        Assertions.assertTrue(NumberUtils.isDigits("123"));
        Assertions.assertFalse(NumberUtils.isDigits("123.4"));
        // 整型 浮点型
        Assertions.assertTrue(NumberUtils.isParsable("123.4"));
        Assertions.assertTrue(NumberUtils.isParsable("123"));
        Assertions.assertTrue(NumberUtils.isParsable("-123"));
        Assertions.assertFalse(NumberUtils.isParsable("0x1f"));
        // isCreatable与进制有关
        Assertions.assertTrue(NumberUtils.isCreatable("0x1f"));
    }

    // ObjectUtils
    @Test
    void testObjectUtils() {
        // 1.
        String str1 = null;
        String str2 = null;
        String str3 = "str3";
        Assertions.assertEquals("str3", ObjectUtils.firstNonNull(str1, str2, str3));

        // 2.
        // 调用了JDK的System.identityHashCode() 该方法(System.identityHashCode)在Spring框架中被广泛使用(Bean ID)
        log.info("{}", ObjectUtils.identityToString(new Object()));
    }

    // ArrayUtils
    @Test
    void testArrayUtils() {
        // 判空
        Assertions.assertTrue(ArrayUtils.isEmpty(new Object[]{}));
        Assertions.assertFalse(ArrayUtils.isEmpty(new Integer[]{1, 2, 3}));
        // 给已有数组“添加元素”
        int[] ints = new int[1];
        ints[0] = 4;
        final int[] newInts = ArrayUtils.add(ints, 7);
        log.info("ints = {}, newInts = {}", ints, newInts);

    }
}
