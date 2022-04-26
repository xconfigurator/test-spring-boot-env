package liuyang.testspringbootenv.modules.jdk.stream.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddUserDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String username;
    private String mobile;
    private String name;
    private String email;
    private String password;
    private Boolean enabled;
}
