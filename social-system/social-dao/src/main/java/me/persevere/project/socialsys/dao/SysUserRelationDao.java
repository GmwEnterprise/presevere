package me.persevere.project.socialsys.dao;

import me.persevere.project.socialsys.domain.SysUserRelation;

public interface SysUserRelationDao {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUserRelation record);

    int insertSelective(SysUserRelation record);

    SysUserRelation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUserRelation record);

    int updateByPrimaryKey(SysUserRelation record);
}