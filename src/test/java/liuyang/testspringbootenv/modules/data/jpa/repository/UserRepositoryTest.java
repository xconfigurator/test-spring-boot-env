package liuyang.testspringbootenv.modules.data.jpa.repository;

import liuyang.testspringbootenv.modules.security.springsecurity.archived.dao.jpa.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author liuyang
 * @scine 2021/7/2
 */
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testInit() {
        // 用于创建表结构
    }

    @Test
    void testFindOne() {
        userRepository.findAll().stream().forEach(System.out::println);
    }

}
