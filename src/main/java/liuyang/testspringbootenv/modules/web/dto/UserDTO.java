package liuyang.testspringbootenv.modules.web.dto;

import lombok.*;
import org.springframework.stereotype.Component;

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
public class UserDTO {
    @NotNull
    private String id;
    private String username;
    private String info;
    private Double d;
    private BigDecimal bd;
}
