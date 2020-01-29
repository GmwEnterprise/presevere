package cn.gmwenterprise.website.service.impl;

import cn.gmwenterprise.website.vo.SysRouterVo;
import cn.gmwenterprise.website.dao.SysRouterDao;
import cn.gmwenterprise.website.domain.SysRouter;
import cn.gmwenterprise.website.service.SysRouterService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysRouterServiceImpl implements SysRouterService {
    private final SysRouterDao sysRouterDao;

    public SysRouterServiceImpl(SysRouterDao sysRouterDao) {
        this.sysRouterDao = sysRouterDao;
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return sysRouterDao.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(SysRouterVo vo) {
        return sysRouterDao.insert(domain(vo));
    }

    @Override
    public SysRouterVo selectByPrimaryKey(Integer id) {
        return vo(sysRouterDao.selectByPrimaryKey(id));
    }

    @Override
    public List<SysRouterVo> selectPage(SysRouterVo vo) {
        return sysRouterDao.selectPage(domain(vo))
            .stream()
            .map(this::vo)
            .collect(Collectors.toList());
    }

    @Override
    public int updateByPrimaryKey(SysRouterVo vo) {
        return sysRouterDao.updateByPrimaryKey(domain(vo));
    }

    private SysRouterVo vo(SysRouter domain) {
        if (domain == null) {
            return null;
        }
        SysRouterVo vo = new SysRouterVo();
        BeanUtils.copyProperties(domain, vo);
        return vo;
    }

    private SysRouter domain(SysRouterVo vo) {
        if (vo == null) {
            return null;
        }
        SysRouter domain = new SysRouter();
        BeanUtils.copyProperties(vo, domain);
        return domain;
    }
}
