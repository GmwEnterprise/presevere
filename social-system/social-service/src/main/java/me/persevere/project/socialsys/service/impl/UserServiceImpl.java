package me.persevere.project.socialsys.service.impl;

import me.persevere.project.socialsys.dao.UserMsgDao;
import me.persevere.project.socialsys.domain.UserMsg;
import me.persevere.project.socialsys.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserMsgDao userMsgDao;

    @Override
    public UserMsg getUserById(Long id) {
        return userMsgDao.selectByPrimaryKey(id);
    }

    @Override
    public UserMsg getUserByUsername(String username) {
        return userMsgDao.selectByUsername(username);
    }

    @Override
    public List<UserMsg> getUserList(UserMsg user) {
        return userMsgDao.getUserListByIndex(user);
    }
}
