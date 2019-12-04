package cn.gmwenterprise.presevere;

import cn.gmwenterprise.presevere.dao.ArticleDraftMapper;
import cn.gmwenterprise.presevere.dao.SysPermissionMapper;
import cn.gmwenterprise.presevere.domain.SysPermission;
import cn.gmwenterprise.presevere.dto.ArticleSearchDto;
import cn.gmwenterprise.presevere.vo.ArticleDraftMetaData;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
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
}
