package me.persevere.project.socialsys.service.impl;

import me.persevere.project.socialsys.dao.SysUserDao;
import me.persevere.project.socialsys.domain.SysUser;
import me.persevere.project.socialsys.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    SysUserDao sysUserDao;

    @Override
    public SysUser getUserById(Long id) {
        return sysUserDao.selectByPrimaryKey(id);
    }
}
