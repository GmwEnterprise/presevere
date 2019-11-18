package cn.gmwenterprise.presevere.service.impl;

import cn.gmwenterprise.presevere.common.Constants;
import cn.gmwenterprise.presevere.dao.SysUserMapper;
import cn.gmwenterprise.presevere.dao.SysUserRoleMapper;
import cn.gmwenterprise.presevere.domain.SysRole;
import cn.gmwenterprise.presevere.domain.SysUser;
import cn.gmwenterprise.presevere.domain.SysUserRole;
import cn.gmwenterprise.presevere.dto.DtoSign;
import cn.gmwenterprise.presevere.service.LoginService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LoginServiceImpl implements LoginService {
    @Resource
    SysUserMapper sysUserMapper;
    @Resource
    SysUserRoleMapper sysUserRoleMapper;

    @Override
    public void register(DtoSign body) {
        SysUser newUser = new SysUser() {{
            setUsername(body.getLoginName());
            setPassword(body.getPassword());
        }};
        sysUserMapper.insertSelective(newUser);

    }

    @Override
    public String login(DtoSign body) {
        return null;
    }
}
