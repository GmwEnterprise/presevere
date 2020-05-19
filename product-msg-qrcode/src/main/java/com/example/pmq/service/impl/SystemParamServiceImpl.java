package com.example.pmq.service.impl;

import com.example.pmq.domain.PmqParam;
import com.example.pmq.mapper.PmqParamMapper;
import com.example.pmq.service.SystemParamService;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.example.pmq.mapper.PmqParamDynamicSqlSupport.paramCode;
import static com.example.pmq.mapper.PmqParamDynamicSqlSupport.pmqParam;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;
import static org.mybatis.dynamic.sql.SqlBuilder.select;

@Service
public class SystemParamServiceImpl implements SystemParamService {
    @Resource
    PmqParamMapper paramMapper;

    @Override
    public void save(PmqParam record) {
        paramMapper.selectOne(
                select(paramCode)
                        .from(pmqParam)
                        .where(paramCode, isEqualTo(record.getParamCode()))
                        .build()
                        .render(RenderingStrategies.MYBATIS3))
                .ifPresentOrElse(
                        result -> paramMapper.updateByPrimaryKeySelective(record),
                        () -> paramMapper.insert(record));
    }

    @Override
    public List<PmqParam> params() {
        return paramMapper.selectMany(
                select(PmqParamMapper.selectList)
                        .from(pmqParam)
                        .build().render(RenderingStrategies.MYBATIS3));
    }
}
