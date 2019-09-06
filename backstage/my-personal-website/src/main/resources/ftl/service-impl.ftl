package cn.gmwenterprise.website.service.impl;

import cn.gmwenterprise.website.vo.${entityName}Vo;
import cn.gmwenterprise.website.dao.${entityName}Dao;
import cn.gmwenterprise.website.domain.${entityName};
import cn.gmwenterprise.website.service.${entityName}Service;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ${entityName}ServiceImpl implements ${entityName}Service {
    private final ${entityName}Dao ${entityAlias}Dao;

    public ${entityName}ServiceImpl(${entityName}Dao ${entityAlias}Dao) {
        this.${entityAlias}Dao = ${entityAlias}Dao;
    }

    @Override
    public int deleteByPrimaryKey(${keyPropertyType} ${keyProperty}) {
        return ${entityAlias}Dao.deleteByPrimaryKey(${keyProperty});
    }

    @Override
    public int insert(${entityName}Vo vo) {
        return ${entityAlias}Dao.insert(domain(vo));
    }
<#--
    @Override
    public int insertSelective(${entityName}Vo vo) {
        return ${entityAlias}Dao.insertSelective(domain(vo));
    }-->

    @Override
    public ${entityName}Vo selectByPrimaryKey(${keyPropertyType} ${keyProperty}) {
        return vo(${entityAlias}Dao.selectByPrimaryKey(${keyProperty}));
    }

    @Override
    public List<${entityName}Vo> selectPage(${entityName}Vo vo) {
        return ${entityAlias}Dao.selectPage(domain(vo))
            .stream()
            .map(this::vo)
            .collect(Collectors.toList());
    }
<#--
    @Override
    public int updateByPrimaryKeySelective(${entityName}Vo vo) {
        return ${entityAlias}Dao.updateByPrimaryKeySelective(domain(vo));
    }-->

    @Override
    public int updateByPrimaryKey(${entityName}Vo vo) {
        return ${entityAlias}Dao.updateByPrimaryKey(domain(vo));
    }

    private ${entityName}Vo vo(${entityName} domain) {
        if (domain == null) {
            return null;
        }
        ${entityName}Vo vo = new ${entityName}Vo();
        BeanUtils.copyProperties(domain, vo);
        return vo;
    }

    private ${entityName} domain(${entityName}Vo vo) {
        if (vo == null) {
            return null;
        }
        ${entityName} domain = new ${entityName}();
        BeanUtils.copyProperties(vo, domain);
        return domain;
    }
}
