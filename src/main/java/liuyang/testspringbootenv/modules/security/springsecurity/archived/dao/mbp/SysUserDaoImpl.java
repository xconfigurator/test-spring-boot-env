package liuyang.testspringbootenv.modules.security.springsecurity.archived.dao.mbp;

import liuyang.testspringbootenv.modules.security.springsecurity.archived.dao.SysUserDao;
import liuyang.testspringbootenv.modules.security.springsecurity.archived.vo.SysUserAuthenticationInfo;
import liuyang.testspringbootenv.modules.security.springsecurity.archived.vo.SysUserAuthorizationInfo;

import java.util.List;

/**
 * MyBatis-Plus实现
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