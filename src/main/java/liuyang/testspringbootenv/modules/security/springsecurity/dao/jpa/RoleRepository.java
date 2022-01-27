package liuyang.testspringbootenv.modules.security.springsecurity.dao.jpa;

import liuyang.testspringbootenv.modules.security.springsecurity.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author liuyang(wx)
 * @since 2022/1/27
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
