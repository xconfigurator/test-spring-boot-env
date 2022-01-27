package liuyang.testspringbootenv.modules.security.springsecurity.domain;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author liuyang(wx)
 * @since 2022/1/27
 */
@Entity
@Data
public class Role implements GrantedAuthority {

    @Id
    private Long id;

    // TODO
    @Override
    public String getAuthority() {
        return null;
    }
}
