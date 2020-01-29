package cn.gmwenterprise.website.service.impl;

import cn.gmwenterprise.website.vo.PreArticleBodyVo;
import cn.gmwenterprise.website.dao.PreArticleBodyDao;
import cn.gmwenterprise.website.domain.PreArticleBody;
import cn.gmwenterprise.website.service.PreArticleBodyService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PreArticleBodyServiceImpl implements PreArticleBodyService {
    private final PreArticleBodyDao preArticleBodyDao;

    public PreArticleBodyServiceImpl(PreArticleBodyDao preArticleBodyDao) {
        this.preArticleBodyDao = preArticleBodyDao;
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return preArticleBodyDao.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(PreArticleBodyVo vo) {
        return preArticleBodyDao.insert(domain(vo));
    }

    @Override
    public PreArticleBodyVo selectByPrimaryKey(Integer id) {
        return vo(preArticleBodyDao.selectByPrimaryKey(id));
    }

    @Override
    public List<PreArticleBodyVo> selectPage(PreArticleBodyVo vo) {
        return preArticleBodyDao.selectPage(domain(vo))
            .stream()
            .map(this::vo)
            .collect(Collectors.toList());
    }

    @Override
    public int updateByPrimaryKey(PreArticleBodyVo vo) {
        return preArticleBodyDao.updateByPrimaryKey(domain(vo));
    }

    private PreArticleBodyVo vo(PreArticleBody domain) {
        if (domain == null) {
            return null;
        }
        PreArticleBodyVo vo = new PreArticleBodyVo();
        BeanUtils.copyProperties(domain, vo);
        return vo;
    }

    private PreArticleBody domain(PreArticleBodyVo vo) {
        if (vo == null) {
            return null;
        }
        PreArticleBody domain = new PreArticleBody();
        BeanUtils.copyProperties(vo, domain);
        return domain;
    }
}
