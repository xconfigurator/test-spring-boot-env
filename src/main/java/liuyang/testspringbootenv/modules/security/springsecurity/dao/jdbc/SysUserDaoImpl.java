package liuyang.testspringbootenv.modules.security.springsecurity.dao.jdbc;

import liuyang.testspringbootenv.modules.security.springsecurity.dao.SysUserDao;
import liuyang.testspringbootenv.modules.security.springsecurity.vo.SysUserAuthenticationInfo;
import liuyang.testspringbootenv.modules.security.springsecurity.vo.SysUserAuthorizationInfo;

import java.util.List;

/**
 * JDBC实现
 * @author liuyang
 * @scine 2021/4/19
 */
public class SysUserDaoImpl implements SysUserDao {
    @Override
    public SysUserAuthenticationInfo getUserAuthenticationInfo(String username) {
        // TODO
        return null;
    }

    @Override
    public List<SysUserAuthorizationInfo> getUserAuthorizationInfo(String username) {
        // TODO
        return null;
    }
}
