package cn.gmwenterprise.presevere.common;

import cn.gmwenterprise.presevere.domain.SysRole;

import java.util.Arrays;
import java.util.List;

public interface Constants {

    String UTF_8 = "UTF-8";
    String APPLICATION_JSON = "application/json";

    List<SysRole> BASIC_ROLE_LIST = Arrays.asList(
        new SysRole("ROLE_USER", "用户"),
        new SysRole("ROLE_ADMIN", "管理员"),
        new SysRole("ROLE_TOURIST", "游客")
    );
}
