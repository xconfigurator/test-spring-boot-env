package liuyang.testspringbootenv.modules.security.springsecurity.archived.dao;

import liuyang.testspringbootenv.modules.security.springsecurity.archived.vo.SysUserAuthenticationInfo;
import liuyang.testspringbootenv.modules.security.springsecurity.archived.vo.SysUserAuthorizationInfo;

import java.util.List;

/**
 * @author liuyang
 * @scine 2021/4/19
 */
public interface SysUserDao {

    // 认证信息
    SysUserAuthenticationInfo getUserAuthenticationInfo(String username);

    // 授权信息
    List<SysUserAuthorizationInfo> getUserAuthorizationInfo(String username);

}
