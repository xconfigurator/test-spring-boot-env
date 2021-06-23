package liuyang.testspringbootenv.modules.springtools.beanutils;

import liuyang.testspringbootenv.modules.springtools.beanutils.vo.ASource;
import liuyang.testspringbootenv.modules.springtools.beanutils.vo.BTarget;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

/**
 * @author liuyang
 * @scine 2021/6/23
 */
@Slf4j
public class BeanUtilsTest {

    private static ASource a;

    @BeforeAll
    public static void init() {
        a = new ASource();
        a.setA("from A");
        a.setB("from A");
        a.setCa("from A");
        a.setList(Arrays.asList("lfa", "lfa", "lfa"));
        a.setLista(Arrays.asList(1, 2, 3));
        log.info(a.toString());
    }

    @Test
    @DisplayName("Spring BeanUtils")
    void testSpringBeanUtils() {
        BTarget b = new BTarget();
        log.info("拷贝前：" + b.toString());
        // 集合可拷贝。对得上名称就拷贝，对不上名称就错过。不抛必须处理的异常。
        org.springframework.beans.BeanUtils.copyProperties(a, b);
        log.info("拷贝后：" + b.toString());
    }

    @Test
    @DisplayName("Apache commons-beanutils")
    void testApacheCommonsBeanUtils() {
        BTarget bb = new BTarget();
        log.info("拷贝前：" + bb.toString());
        try {
            // 这个用起来真的不如Spring的BeanUtils招人喜欢。
            org.apache.commons.beanutils.BeanUtils.copyProperties(bb, a);
            log.info("拷贝后：" + bb.toString());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
