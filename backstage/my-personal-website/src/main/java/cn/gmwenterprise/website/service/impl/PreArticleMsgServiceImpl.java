package cn.gmwenterprise.website.service.impl;

import cn.gmwenterprise.website.dao.PreArticleBodyDao;
import cn.gmwenterprise.website.domain.PreArticleBody;
import cn.gmwenterprise.website.vo.PreArticle;
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
    private final PreArticleBodyDao preArticleBodyDao;

    public PreArticleMsgServiceImpl(PreArticleMsgDao preArticleMsgDao, PreArticleBodyDao preArticleBodyDao) {
        this.preArticleMsgDao = preArticleMsgDao;
        this.preArticleBodyDao = preArticleBodyDao;
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

    @Override
    public PreArticle getArticleById(Integer id) {
        PreArticleMsg head = preArticleMsgDao.selectByPrimaryKey(id);
        PreArticleBody body = preArticleBodyDao.selectByMsgId(head.getId());
        return new PreArticle() {{
            setHead(head);
            setBody(body);
        }};
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
