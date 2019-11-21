package cn.gmwenterprise.presevere.config;

import cn.gmwenterprise.presevere.dao.SysUserMapper;
import cn.gmwenterprise.presevere.dao.SysUserRoleMapper;
import cn.gmwenterprise.presevere.domain.SysUser;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SystemConfig {
    @Resource
    SysUserMapper sysUserMapper;
    @Resource
    SysUserRoleMapper sysUserRoleMapper;

    @PostConstruct
    public void init() {
        // 删除多余的用户角色关系
        deleteUnlessUserRoleRelation();
    }

    private void deleteUnlessUserRoleRelation() {
        List<Integer> userIds = sysUserMapper
            .selectByCondition(null)
            .stream()
            .map(SysUser::getId)
            .collect(Collectors.toList());
        sysUserRoleMapper.deleteByUserIds(userIds);
    }
}
