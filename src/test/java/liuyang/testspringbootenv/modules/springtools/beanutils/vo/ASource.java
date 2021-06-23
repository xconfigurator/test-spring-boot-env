package liuyang.testspringbootenv.modules.springtools.beanutils.vo;

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
public class ASource {
    private String a;
    private String b;
    private String ca;
    private List<String> list;
    private List<Integer> lista;
}
