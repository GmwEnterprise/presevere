package cn.gmwenterprise.website.service.sys;

import cn.gmwenterprise.website.config.security.User;
import cn.gmwenterprise.website.domain.SysUser;

public interface UserTokenService {

    /**
     * 构建spring security需要的user实体
     *
     * @param sysUser user数据库实体
     * @return user security实体
     */
    User generateUser(SysUser sysUser);

    /**
     * 构建spring security需要的user实体
     *
     * @param userId user主键
     * @return user security实体
     */
    User generateUser(Integer userId);
}
