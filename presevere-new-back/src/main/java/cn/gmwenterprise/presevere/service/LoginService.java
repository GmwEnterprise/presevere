package cn.gmwenterprise.presevere.service;

import cn.gmwenterprise.presevere.dto.DtoSign;

public interface LoginService {

    void register(DtoSign body);

    String login(DtoSign body);
}
