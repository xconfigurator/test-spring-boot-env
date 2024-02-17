package liuyang.testspringbootenv.modules.web.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author liuyang(wx)
 * @since 2021/11/26
 */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
//@ToString
@Data
//@Component
@JacksonXmlRootElement// 增加可导出为XML格式的能力
public class UserDTO {
    @NotNull
    private String id;
    private String username;
    private String info;
    private Double d;
    private BigDecimal bd;
}
