package liuyang.testspringbootenv.modules.utils.springtools2023;

import liuyang.testspringbootenv.modules.utils.easyexcel20220611.vo.AIOpsAlarmDictionary;
import org.junit.jupiter.api.Test;
import org.springframework.util.ReflectionUtils;

/**
 * https://www.bilibili.com/video/BV1Lv411P7Ua?p=10&spm_id_from=pageDriver&vd_source=8bd7b24b38e3e12c558d839b352b32f4
 * 05:59
 *
 * @author xconf
 * @since 2023/2/17
 */
public class ReflectionUtilsTest {
    @Test
    void test() {
        // TODO 20230217 略看了一遍 后面用到了再说。可以联合JDK反射课程一起使用。
        // 1. 获取本来以及所有父类（包含父类的父类...）的所有属性
        // 2. 获取本类以及所有父类（包含父类的父类...）的所有方法

        // demo 14:37
        AIOpsAlarmDictionary dic = new AIOpsAlarmDictionary();
        dic.setAlarmInfo("foo");
        // 通过反射来看这个对象的值
        ReflectionUtils.doWithFields(AIOpsAlarmDictionary.class, field -> {
            field.setAccessible(true);
            System.out.println(ReflectionUtils.getField(field, dic));
        });

    }
}
