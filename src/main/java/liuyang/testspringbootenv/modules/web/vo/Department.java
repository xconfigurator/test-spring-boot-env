package liuyang.testspringbootenv.modules.web.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;

/**
 * 测试Bean Validation
 * @author liuyang
 * @scine 2021/6/22
 */
@Data
@ToString
@EqualsAndHashCode
public class Department {
    @Null //必须为空
    private Integer id;
    @NotNull
    private Integer parent_id;
    @NotNull
    @NotBlank
    private String name;
    @PastOrPresent // 这个字段的判空问题湖面再谈
    private LocalDateTime createTime;
}
