package cn.gmwenterprise.presevere;

import cn.gmwenterprise.presevere.dao.SysPermissionMapper;
import cn.gmwenterprise.presevere.domain.SysPermission;
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

}
