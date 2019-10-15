package cn.gmwenterprise.website.service.impl;

import cn.gmwenterprise.website.vo.PreArticleMsgVo;
import cn.gmwenterprise.website.dao.PreArticleMsgDao;
import cn.gmwenterprise.website.domain.PreArticleMsg;
import cn.gmwenterprise.website.service.PreArticleMsgService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PreArticleMsgServiceImpl implements PreArticleMsgService {
    private final PreArticleMsgDao preArticleMsgDao;

    public PreArticleMsgServiceImpl(PreArticleMsgDao preArticleMsgDao) {
        this.preArticleMsgDao = preArticleMsgDao;
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return preArticleMsgDao.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(PreArticleMsgVo vo) {
        return preArticleMsgDao.insert(domain(vo));
    }

    @Override
    public PreArticleMsgVo selectByPrimaryKey(Integer id) {
        return vo(preArticleMsgDao.selectByPrimaryKey(id));
    }

    @Override
    public List<PreArticleMsgVo> selectPage(PreArticleMsgVo vo) {
        return preArticleMsgDao.selectPage(domain(vo))
            .stream()
            .map(this::vo)
            .collect(Collectors.toList());
    }

    @Override
    public int updateByPrimaryKey(PreArticleMsgVo vo) {
        return preArticleMsgDao.updateByPrimaryKey(domain(vo));
    }

    private PreArticleMsgVo vo(PreArticleMsg domain) {
        if (domain == null) {
            return null;
        }
        PreArticleMsgVo vo = new PreArticleMsgVo();
        BeanUtils.copyProperties(domain, vo);
        return vo;
    }

    private PreArticleMsg domain(PreArticleMsgVo vo) {
        if (vo == null) {
            return null;
        }
        PreArticleMsg domain = new PreArticleMsg();
        BeanUtils.copyProperties(vo, domain);
        return domain;
    }
}
