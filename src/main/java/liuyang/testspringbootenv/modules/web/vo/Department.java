package liuyang.testspringbootenv.modules.web.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    /**
     * 主键
     */
    @Null(message = "id必须为空") //必须为空
    private Long id;

    /**
     * 父ID
     */
    @NotNull(message = "parent_id不能为空")
    private Long parent_id;

    /**
     * 部门名称
     */
    @NotNull(message = "部门名称name不能为空")
    @NotBlank(message = "部门名称name不能为空字符串")
    private String name;

    /**
     * 成立时间
     */
    @PastOrPresent(message = "不能晚于当前时间") // 这个字段的判空问题湖面再谈
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")// 这个是在反序列化的时候提供给Jackson看的。
    private LocalDateTime createTime;
}
