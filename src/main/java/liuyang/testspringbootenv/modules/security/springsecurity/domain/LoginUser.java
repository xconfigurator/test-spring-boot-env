package liuyang.testspringbootenv.modules.security.springsecurity.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 本对象将要被存储在MySQL中并缓存在Redis中
 *
 * @author liuyang(wx)
 * @since 2022/1/27
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser implements UserDetails {

    @Id
    private Long id;

    // 20220328 add
    @ElementCollection// JPA如果不加这个映射则 error Basic attribute type should not be a container.
    private List<String> permissions;

    @JSONField(serialize = false)// 不用向Redis中存储 （如果RedisTemplate使用fastjson序列化器）
    @JsonIgnore// 不用向Redis中存储（如果RedisTemplate使用jackson序列化器）
    @Transient//  不用向MySQL中存储（给JPA看的）
    @ElementCollection
    private List<SimpleGrantedAuthority> permissionsCollection;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 提示1： 查看一个接口的实现类 ctrl + alt + 鼠标左键
        //return permissions.stream().map(permission -> new SimpleGrantedAuthority(permission)).collect(Collectors.toList());
        // 优化点：可以考虑把这个转化的结果定义成一个成员变量，这样只在第一次调用getauthorities时进行转化，其他的时候直接返回转化后的集合即可。
        if (permissionsCollection == null) {
            permissionsCollection = permissions.stream().map(permission -> new SimpleGrantedAuthority(permission)).collect(Collectors.toList());
        }
        return permissionsCollection;
        //return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
