package me.persevere.project.socialsys.dao;

import me.persevere.project.socialsys.domain.SysUserChatRecord;

public interface SysUserChatRecordDao {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUserChatRecord record);

    int insertSelective(SysUserChatRecord record);

    SysUserChatRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUserChatRecord record);

    int updateByPrimaryKey(SysUserChatRecord record);
}