package cn.gmwenterprise.presevere.service.impl;

import cn.gmwenterprise.presevere.dao.SysUserMapper;
import cn.gmwenterprise.presevere.dao.SysUserRoleMapper;
import cn.gmwenterprise.presevere.domain.SysUser;
import cn.gmwenterprise.presevere.dto.DtoSign;
import cn.gmwenterprise.presevere.service.LoginService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LoginServiceImpl implements LoginService {
    @Resource
    SysUserMapper sysUserMapper;
    @Resource
    SysUserRoleMapper sysUserRoleMapper;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public void register(DtoSign body) {
        SysUser newUser = new SysUser() {{
            setUsername(body.getLoginName());
            setPassword(passwordEncoder.encode(body.getPassword()));
        }};
        sysUserMapper.insertSelective(newUser);
    }

    @Override
    public String login(DtoSign body) {
        String username = body.getLoginName();
        String password = body.getPassword();

        return null;
    }

    @Override
    public void logout() {

    }
}
