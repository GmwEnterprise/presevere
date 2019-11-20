package cn.gmwenterprise.presevere.service.impl;

import cn.gmwenterprise.presevere.dao.SysUserMapper;
import cn.gmwenterprise.presevere.dao.SysUserRoleMapper;
import cn.gmwenterprise.presevere.domain.SysUser;
import cn.gmwenterprise.presevere.dto.DtoSign;
import cn.gmwenterprise.presevere.service.LoginService;
import cn.gmwenterprise.presevere.vo.LoginSuccess;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class LoginServiceImpl implements LoginService {
    @Resource
    SysUserMapper sysUserMapper;
    @Resource
    SysUserRoleMapper sysUserRoleMapper;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional(rollbackFor = Exception.class)
    @Override
    public LoginSuccess register(String username, String password) {
        SysUser user = sysUserMapper.selectByUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        sysUserMapper.updateByPrimaryKeySelective(new SysUser() {{
            setId(user.getId());
            setPassword(user.getPassword());
        }});

        // TODO 未完成
        return new LoginSuccess();
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

    @Override
    public SysUser randomSalt(String username) {
        String source = username;
        if (source.length() < 16) {
            source += "0".repeat(16 - source.length());
        }
        String salt = MD5Encoder.encode(source.getBytes());
        SysUser user = new SysUser() {{
            setUsername(username);
            setSalt(salt);
        }};
        sysUserMapper.insertSelective(user);
        return user;
    }
}
