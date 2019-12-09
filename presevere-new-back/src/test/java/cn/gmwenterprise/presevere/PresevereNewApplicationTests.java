package cn.gmwenterprise.presevere;

import cn.gmwenterprise.presevere.common.MarkdownUtils;
import cn.gmwenterprise.presevere.dao.ArticleBodyMapper;
import cn.gmwenterprise.presevere.dao.ArticleDraftMapper;
import cn.gmwenterprise.presevere.dao.ArticleMetadataMapper;
import cn.gmwenterprise.presevere.dao.SysPermissionMapper;
import cn.gmwenterprise.presevere.domain.ArticleBodyWithBLOBs;
import cn.gmwenterprise.presevere.domain.ArticleMetadata;
import cn.gmwenterprise.presevere.domain.SysPermission;
import cn.gmwenterprise.presevere.dto.ArticleSearchDto;
import cn.gmwenterprise.presevere.vo.ArticleDraftMetaData;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@SpringBootTest
class PresevereNewApplicationTests {

    @Resource
    SysPermissionMapper sysPermissionMapper;

    @Test
    void contextLoads() {
        SysPermission condition = new SysPermission();
        List<SysPermission> sysPermissionList = sysPermissionMapper.selectByCondition(condition);
        assert sysPermissionList.size() == 0;
    }

    @Resource
    ArticleDraftMapper articleDraftMapper;

    @Test
    void testSelect1() {
        ArticleSearchDto condition = new ArticleSearchDto();
        condition.setSelf(true);
        condition.setCurrentUserId(1);
        condition.setWriterName("管理");
        List<ArticleDraftMetaData> articleDraftMetaData = articleDraftMapper.selectBySearchCondition(condition);
        assert articleDraftMetaData != null && articleDraftMetaData.get(0).getWriterObject() != null;
    }

    @Resource
    ArticleMetadataMapper articleMetadataMapper;
    @Resource
    ArticleBodyMapper articleBodyMapper;
    @Resource
    DataSource dataSource;

    // 已用过
    @Test
    void updateMetadataIntro() throws SQLException {
        List<ArticleBodyWithBLOBs> list = articleBodyMapper.selective(null);
        list.forEach(body -> {
            body.setContentPlain(MarkdownUtils.plain(body.getContent()));
            articleBodyMapper.updateByPrimaryKeySelective(body);
        });

        PreparedStatement statement = dataSource.getConnection().prepareStatement("\n" +
            "select * from article_metadata where introduction is null or introduction = ''");
        ResultSet r = statement.executeQuery();
        while(r.next()) {
            long urlNumber = r.getLong("url_number");
            String contentPlain = articleBodyMapper.selectByUrlNumber(urlNumber).getContentPlain();
            ArticleMetadata metadata = new ArticleMetadata();
            metadata.setId(r.getInt("id"));
            metadata.setIntroduction(contentPlain.substring(0, Math.min(contentPlain.length(), 250)));
            articleMetadataMapper.updateByPrimaryKeySelective(metadata);
        }
    }
}
