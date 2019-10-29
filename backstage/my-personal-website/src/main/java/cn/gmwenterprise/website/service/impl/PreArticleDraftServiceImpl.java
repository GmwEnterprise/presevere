package cn.gmwenterprise.website.service.impl;

import cn.gmwenterprise.website.config.security.User;
import cn.gmwenterprise.website.dao.PreArticleBodyDao;
import cn.gmwenterprise.website.dao.PreArticleDraftDao;
import cn.gmwenterprise.website.dao.PreArticleMsgDao;
import cn.gmwenterprise.website.domain.PreArticleBody;
import cn.gmwenterprise.website.domain.PreArticleDraft;
import cn.gmwenterprise.website.domain.PreArticleMsg;
import cn.gmwenterprise.website.service.PreArticleDraftService;
import cn.gmwenterprise.website.service.SysUserService;
import cn.gmwenterprise.website.vo.PreArticleDraftVo;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PreArticleDraftServiceImpl implements PreArticleDraftService {
    private final PreArticleDraftDao preArticleDraftDao;
    private final PreArticleMsgDao preArticleMsgDao;
    private final PreArticleBodyDao preArticleBodyDao;
    private final SysUserService sysUserService;

    public PreArticleDraftServiceImpl(
        PreArticleDraftDao preArticleDraftDao,
        PreArticleMsgDao preArticleMsgDao,
        PreArticleBodyDao preArticleBodyDao,
        SysUserService sysUserService) {
        this.preArticleDraftDao = preArticleDraftDao;
        this.preArticleMsgDao = preArticleMsgDao;
        this.preArticleBodyDao = preArticleBodyDao;
        this.sysUserService = sysUserService;
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
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        vo.setCreator(principal.getUser().getId());
        return preArticleDraftDao.selectPage(domain(vo))
            .stream()
            .map(domain -> {
                PreArticleDraftVo item = vo(domain);
                item.setCreatorName(sysUserService.selectByPrimaryKey(item.getCreator()).getNickname());
                return item;
            })
            .collect(Collectors.toList());
    }

    @Override
    public int updateByPrimaryKey(PreArticleDraftVo vo) {
        return preArticleDraftDao.updateByPrimaryKey(domain(vo));
    }

    @Override
    public void pushTag(Integer id, String tag) {
        PreArticleDraft domain = preArticleDraftDao.selectByPrimaryKey(id);
        if (!StringUtils.isEmpty(domain.getTag())) {
            String newTag = domain.getTag() + "," + tag;
            preArticleDraftDao.setTag(id, newTag);
        } else {
            preArticleDraftDao.setTag(id, tag);
        }
    }

    @Override
    public void removeTagById(Integer id, String tag) {
        PreArticleDraft target = preArticleDraftDao.selectByPrimaryKey(id);
        String origin = target.getTag();
        if (origin.contains(tag)) {
            String separator = ",";
            if (!origin.contains(separator)) {
                preArticleDraftDao.setTag(target.getId(), null);
            } else {
                String finallyTags = Arrays.stream(origin.split(separator))
                    .filter(t -> !t.equals(tag))
                    .collect(Collectors.joining(separator));
                preArticleDraftDao.setTag(id, finallyTags);
            }
        }
    }

    @Override
    public void pushContentById(Integer id, String content) {
        preArticleDraftDao.updateContent(id, content);
    }

    @Override
    public Integer pushContent(String content) {
        PreArticleDraft domain = new PreArticleDraft();
        domain.setContent(content);
        preArticleDraftDao.insert(domain);
        return domain.getId();
    }

    @Override
    public void updateTitle(Integer id, String title) {
        preArticleDraftDao.updateTitleById(id, title);
    }

    @Override
    public void updateIntroduction(Integer id, String introduction) {
        preArticleDraftDao.updateIntroductionById(id, introduction);
    }

    @Override
    public void publishArticle(Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        PreArticleDraft targetDraft = preArticleDraftDao.selectByPrimaryKey(id);
        if (targetDraft.getCreator().equals(currentUser.getUser().getId())) {
            // 验证身份成功
            PreArticleMsg msg = new PreArticleMsg() {{
                setWriter(targetDraft.getCreator());
                setTitle(targetDraft.getTitle());
                setIntroduction(targetDraft.getIntroduction());
                setTag(targetDraft.getTag());
                setStatus(1);
            }};
            preArticleMsgDao.insert(msg);
            PreArticleBody body = new PreArticleBody() {{
                setArticleMsgId(msg.getId());
                setContentType("markdown");
                setContent(targetDraft.getContent());
            }};
            preArticleBodyDao.insert(body);
            preArticleDraftDao.publishDraft(id);
        }
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
