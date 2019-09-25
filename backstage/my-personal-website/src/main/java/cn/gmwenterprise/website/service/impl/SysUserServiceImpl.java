package cn.gmwenterprise.website.service.impl;

import cn.gmwenterprise.website.vo.SysUserVo;
import cn.gmwenterprise.website.dao.SysUserDao;
import cn.gmwenterprise.website.domain.SysUser;
import cn.gmwenterprise.website.service.SysUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysUserServiceImpl implements SysUserService {
    private final SysUserDao sysUserDao;

    public SysUserServiceImpl(SysUserDao sysUserDao) {
        this.sysUserDao = sysUserDao;
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return sysUserDao.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(SysUserVo vo) {
        return sysUserDao.insert(domain(vo));
    }

    @Override
    public SysUserVo selectByPrimaryKey(Integer id) {
        return vo(sysUserDao.selectByPrimaryKey(id));
    }

    @Override
    public List<SysUserVo> selectPage(SysUserVo vo) {
        return sysUserDao.selectPage(domain(vo))
            .stream()
            .map(this::vo)
            .collect(Collectors.toList());
    }

    @Override
    public int updateByPrimaryKey(SysUserVo vo) {
        return sysUserDao.updateByPrimaryKey(domain(vo));
    }

    private SysUserVo vo(SysUser domain) {
        if (domain == null) {
            return null;
        }
        SysUserVo vo = new SysUserVo();
        BeanUtils.copyProperties(domain, vo);
        return vo;
    }

    private SysUser domain(SysUserVo vo) {
        if (vo == null) {
            return null;
        }
        SysUser domain = new SysUser();
        BeanUtils.copyProperties(vo, domain);
        return domain;
    }
}
