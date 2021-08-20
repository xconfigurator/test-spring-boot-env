package liuyang.testspringbootenv.modules.security.springsecurity.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author liuyang
 * @scine 2021/7/1
 */
@Entity
@Data
@ToString
@EqualsAndHashCode
//@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String password;
    private String email;
    // @Column(name = "phone_number")
    private String phoneNumber;
    private int status; // 状态码
    // @Column(name = "create_time")
    private Date createTime;
    // @Column(name = "last_login_time")
    private Date lastLoginTime;
    // @Column(name = "last_update_time")
    private Date lastUpdateTime;
    private String avatar;// 头像
}
