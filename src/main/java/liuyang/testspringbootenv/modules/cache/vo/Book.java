package liuyang.testspringbootenv.modules.cache.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liuyang(wx)
 * @since 2022/7/8
 */
@Data
public class Book implements Serializable {
    private Integer id;
    private String name;
    private String author;
}
