package liuyang.testspringbootenv.modules.json.vo;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 专为序列化测试使用
 * @author liuyang(wx)
 * @since 2022/4/13
 */
@Data
@ToString
public class Person {
    private Long id;
    private String name;
    private String info;
    private Date testDate;
    private LocalDateTime testJSR310Date;
    private Double d;
    private BigDecimal bd;
}
