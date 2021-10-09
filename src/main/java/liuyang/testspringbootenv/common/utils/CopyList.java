package liuyang.testspringbootenv.common.utils;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 从源List<S>，复制到目标List<T>
 * 利用Spring BeanUtils工具类
 * 说明：fix 20210708 调试发现问题。JAXB对象中list引用源、目标的DTO全路径名不同导致list未复制。本工具类用于在adapter中处理JAXB中包含list对象的情况。
 *
 * @author liuyang(wx)
 * @since 2021/7/8
 */
public class CopyList {
    /**
     *
     * @param sourceList 源列表集合
     * @param <S>    源集合内元素的类型）
     * @param <T>    目标集合内元素的类型
     * @param aimClazz  目标列表中的对象类型
     * @return
     */
    public static <S, T> void copy(List<S> sourceList, List<T> targetList, Class<T> aimClazz) {
        if (targetList == null){
            targetList = new ArrayList<>();
        }
        for (S sourceElement : sourceList) {
            T targetElement = BeanUtils.instantiateClass(aimClazz);
            BeanUtils.copyProperties(sourceElement, targetElement);
            targetList.add(targetElement);
        }

    }

    // topQuery应该还需要一个订制方法

}
