package liuyang.testspringbootenv.modules.utils.springtools2021.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.*;

/**
 * @author liuyang
 * @scine 2021/6/23
 */
@Data
@ToString
@EqualsAndHashCode
public class BTarget {
    private String a;
    private String b;
    private String cb;// 测试名称不同的普通属性
    private List<String> list;
    private List<Integer> listb;// 测试名称不同集合属性
    private String bOnly; // 只有B有这个字段
}
