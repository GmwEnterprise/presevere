package cn.gmwenterprise.website.service.impl;

import cn.gmwenterprise.website.vo.SysRoleVo;
import cn.gmwenterprise.website.dao.SysRoleDao;
import cn.gmwenterprise.website.domain.SysRole;
import cn.gmwenterprise.website.service.SysRoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysRoleServiceImpl implements SysRoleService {
    private final SysRoleDao sysRoleDao;

    public SysRoleServiceImpl(SysRoleDao sysRoleDao) {
        this.sysRoleDao = sysRoleDao;
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return sysRoleDao.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(SysRoleVo vo) {
        return sysRoleDao.insert(domain(vo));
    }

    @Override
    public SysRoleVo selectByPrimaryKey(Integer id) {
        return vo(sysRoleDao.selectByPrimaryKey(id));
    }

    @Override
    public List<SysRoleVo> selectPage(SysRoleVo vo) {
        return sysRoleDao.selectPage(domain(vo))
            .stream()
            .map(this::vo)
            .collect(Collectors.toList());
    }

    @Override
    public int updateByPrimaryKey(SysRoleVo vo) {
        return sysRoleDao.updateByPrimaryKey(domain(vo));
    }

    private SysRoleVo vo(SysRole domain) {
        if (domain == null) {
            return null;
        }
        SysRoleVo vo = new SysRoleVo();
        BeanUtils.copyProperties(domain, vo);
        return vo;
    }

    private SysRole domain(SysRoleVo vo) {
        if (vo == null) {
            return null;
        }
        SysRole domain = new SysRole();
        BeanUtils.copyProperties(vo, domain);
        return domain;
    }
}
