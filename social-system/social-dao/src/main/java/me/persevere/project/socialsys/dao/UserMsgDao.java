package me.persevere.project.socialsys.dao;

import me.persevere.project.socialsys.domain.UserMsg;

import java.util.List;

public interface UserMsgDao {
    int deleteByPrimaryKey(Long id);

    int insert(UserMsg record);

    int insertSelective(UserMsg record);

    UserMsg selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserMsg record);

    int updateByPrimaryKey(UserMsg record);

    List<UserMsg> getUserListByIndex(UserMsg user);

    UserMsg selectByUsername(String username);
}