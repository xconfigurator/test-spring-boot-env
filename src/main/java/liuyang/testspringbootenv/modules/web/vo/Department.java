package liuyang.testspringbootenv.modules.web.vo;

import com.alibaba.fastjson.annotation.JSONField;
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
    @Null //必须为空
    private Long id;

    /**
     * 父ID
     */
    @NotNull
    private Long parent_id;

    /**
     * 部门名称
     */
    @NotNull
    @NotBlank
    private String name;

    /**
     * 成立时间
     */
    @PastOrPresent // 这个字段的判空问题湖面再谈
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")// 这个是在反序列化的时候提供给Jackson看的。
    private LocalDateTime createTime;
}
