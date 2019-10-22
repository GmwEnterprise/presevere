package cn.gmwenterprise.website.service.impl;

import cn.gmwenterprise.website.dao.PreArticleDraftDao;
import cn.gmwenterprise.website.domain.PreArticleDraft;
import cn.gmwenterprise.website.service.PreArticleDraftService;
import cn.gmwenterprise.website.vo.PreArticleDraftVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PreArticleDraftServiceImpl implements PreArticleDraftService {
    private final PreArticleDraftDao preArticleDraftDao;

    public PreArticleDraftServiceImpl(PreArticleDraftDao preArticleDraftDao) {
        this.preArticleDraftDao = preArticleDraftDao;
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return preArticleDraftDao.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(PreArticleDraftVo vo) {
        PreArticleDraft domain = domain(vo);
        int effect = preArticleDraftDao.insert(domain);
        PreArticleDraftVo vo1 = vo(domain);
        BeanUtils.copyProperties(vo1, vo);
        return effect;
    }

    @Override
    public PreArticleDraftVo selectByPrimaryKey(Integer id) {
        return vo(preArticleDraftDao.selectByPrimaryKey(id));
    }

    @Override
    public List<PreArticleDraftVo> selectPage(PreArticleDraftVo vo) {
        return preArticleDraftDao.selectPage(domain(vo))
            .stream()
            .map(this::vo)
            .collect(Collectors.toList());
    }

    @Override
    public int updateByPrimaryKey(PreArticleDraftVo vo) {
        return preArticleDraftDao.updateByPrimaryKey(domain(vo));
    }

    private PreArticleDraftVo vo(PreArticleDraft domain) {
        if (domain == null) {
            return null;
        }
        PreArticleDraftVo vo = new PreArticleDraftVo();
        BeanUtils.copyProperties(domain, vo);
        return vo;
    }

    private PreArticleDraft domain(PreArticleDraftVo vo) {
        if (vo == null) {
            return null;
        }
        PreArticleDraft domain = new PreArticleDraft();
        BeanUtils.copyProperties(vo, domain);
        return domain;
    }
}
