package liuyang.testspringbootenv.modules.security.springsecurity.dao;

import liuyang.testspringbootenv.modules.security.springsecurity.entity.SysUserAuthenticationVO;
import liuyang.testspringbootenv.modules.security.springsecurity.entity.SysUserAuthorizationVO;

import java.util.List;

/**
 * @author liuyang
 * @scine 2021/4/19
 */
public interface SysUserDao {

    // 认证信息
    SysUserAuthenticationVO getUserAuthenticationInfo(String username);

    // 授权信息
    List<SysUserAuthorizationVO> getUserAuthorizationInfo(String username);

}
