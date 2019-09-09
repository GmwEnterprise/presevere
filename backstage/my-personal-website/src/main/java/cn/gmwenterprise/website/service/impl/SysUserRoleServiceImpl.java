package cn.gmwenterprise.website.service.impl;

import cn.gmwenterprise.website.dao.SysRoleDao;
import cn.gmwenterprise.website.domain.SysRole;
import cn.gmwenterprise.website.domain.SysUser;
import cn.gmwenterprise.website.vo.SysUserRoleVo;
import cn.gmwenterprise.website.dao.SysUserRoleDao;
import cn.gmwenterprise.website.domain.SysUserRole;
import cn.gmwenterprise.website.service.SysUserRoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysUserRoleServiceImpl implements SysUserRoleService {
    private final SysUserRoleDao sysUserRoleDao;

    public SysUserRoleServiceImpl(SysUserRoleDao sysUserRoleDao, SysRoleDao sysRoleDao) {
        this.sysUserRoleDao = sysUserRoleDao;
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return sysUserRoleDao.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(SysUserRoleVo vo) {
        return sysUserRoleDao.insert(domain(vo));
    }

    @Override
    public SysUserRoleVo selectByPrimaryKey(Integer id) {
        return vo(sysUserRoleDao.selectByPrimaryKey(id));
    }

    @Override
    public List<SysUserRoleVo> selectPage(SysUserRoleVo vo) {
        return sysUserRoleDao.selectPage(domain(vo))
            .stream()
            .map(this::vo)
            .collect(Collectors.toList());
    }

    @Override
    public int updateByPrimaryKey(SysUserRoleVo vo) {
        return sysUserRoleDao.updateByPrimaryKey(domain(vo));
    }

    @Override
    public List<SysRole> getRoleByUser(SysUser user) {
        return sysUserRoleDao.selectRoleByUserId(user.getId());
    }

    private SysUserRoleVo vo(SysUserRole domain) {
        if (domain == null) {
            return null;
        }
        SysUserRoleVo vo = new SysUserRoleVo();
        BeanUtils.copyProperties(domain, vo);
        return vo;
    }

    private SysUserRole domain(SysUserRoleVo vo) {
        if (vo == null) {
            return null;
        }
        SysUserRole domain = new SysUserRole();
        BeanUtils.copyProperties(vo, domain);
        return domain;
    }
}
