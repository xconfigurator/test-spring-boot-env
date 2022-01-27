package liuyang.testspringbootenv.modules.security.springsecurity.domain.dto;

import liuyang.testspringbootenv.modules.security.springsecurity.validation.MyIdentical;
import lombok.Data;

/**
 * @author liuyang(wx)
 * @since 2022/1/27
 */
@MyIdentical
@Data
public class UserDto {
    private String password;
    private String passwordMatch;
}
