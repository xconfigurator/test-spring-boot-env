package liuyang.testspringbootenv.modules.security.springsecurity.archived.dao.jpa.repository;

import liuyang.testspringbootenv.modules.security.springsecurity.archived.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author liuyang
 * @scine 2021/7/1
 */
public interface UserRepository extends JpaRepository<User, Integer> {
}