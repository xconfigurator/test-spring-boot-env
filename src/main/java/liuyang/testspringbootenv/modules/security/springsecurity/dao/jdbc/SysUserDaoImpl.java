package liuyang.testspringbootenv.modules.security.springsecurity.dao.jdbc;

import liuyang.testspringbootenv.modules.security.springsecurity.dao.SysUserDao;
import liuyang.testspringbootenv.modules.security.springsecurity.entity.SysUserAuthenticationVO;
import liuyang.testspringbootenv.modules.security.springsecurity.entity.SysUserAuthorizationVO;

import java.util.List;

/**
 * JDBC实现
 * @author liuyang
 * @scine 2021/4/19
 */
public class SysUserDaoImpl implements SysUserDao {
    @Override
    public SysUserAuthenticationVO getUserAuthenticationInfo(String username) {
        // TODO
        return null;
    }

    @Override
    public List<SysUserAuthorizationVO> getUserAuthorizationInfo(String username) {
        // TODO
        return null;
    }
}
