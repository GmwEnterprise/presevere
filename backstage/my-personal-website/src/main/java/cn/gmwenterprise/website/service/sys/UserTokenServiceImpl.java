package cn.gmwenterprise.website.service.sys;

import cn.gmwenterprise.website.config.security.User;
import cn.gmwenterprise.website.dao.SysRoleDao;
import cn.gmwenterprise.website.dao.SysUserDao;
import cn.gmwenterprise.website.domain.SysRole;
import cn.gmwenterprise.website.domain.SysUser;
import cn.gmwenterprise.website.service.SysUserService;
import cn.gmwenterprise.website.vo.SysUserVo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserTokenServiceImpl implements UserTokenService {
    @Resource
    private SysUserDao sysUserDao;
    @Resource
    private SysRoleDao sysRoleDao;

    @Override
    public User generateUser(SysUser sysUser) {
        List<SysRole> roleList = sysRoleDao.selectRoleListByUser(sysUser.getId());
        return new User(sysUser, roleList);
    }

    @Override
    public User generateUser(Integer userId) {
        SysUser user = sysUserDao.selectByPrimaryKey(userId);
        return generateUser(user);
    }

    @Override
    public UserDetails generateUser(String username) {
        SysUser sysUser = sysUserDao.selectByUsername(username);
        return generateUser(sysUser);
    }
}
