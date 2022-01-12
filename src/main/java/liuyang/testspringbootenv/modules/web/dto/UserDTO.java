package liuyang.testspringbootenv.modules.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @author liuyang(wx)
 * @since 2021/11/26
 */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Data
//@Component
public class UserDTO {
    private String id;
    private String username;
    private String info;
    private Double d;
    private BigDecimal bd;
}
