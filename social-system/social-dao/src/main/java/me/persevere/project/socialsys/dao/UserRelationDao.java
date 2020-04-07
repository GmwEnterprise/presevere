package me.persevere.project.socialsys.dao;

import me.persevere.project.socialsys.domain.UserRelation;

public interface UserRelationDao {
    int deleteByPrimaryKey(Long id);

    int insert(UserRelation record);

    int insertSelective(UserRelation record);

    UserRelation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserRelation record);

    int updateByPrimaryKey(UserRelation record);
}