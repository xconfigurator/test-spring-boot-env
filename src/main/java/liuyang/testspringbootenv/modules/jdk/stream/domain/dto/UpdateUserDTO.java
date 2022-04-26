package liuyang.testspringbootenv.modules.jdk.stream.domain.dto;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdateUserDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String mobile;
    private String name;
    private String email;
}
