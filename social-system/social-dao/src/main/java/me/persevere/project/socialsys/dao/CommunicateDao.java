package me.persevere.project.socialsys.dao;

import me.persevere.project.socialsys.domain.Communicate;

public interface CommunicateDao {
    int deleteByPrimaryKey(Long id);

    int insert(Communicate record);

    int insertSelective(Communicate record);

    Communicate selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Communicate record);

    int updateByPrimaryKey(Communicate record);
}