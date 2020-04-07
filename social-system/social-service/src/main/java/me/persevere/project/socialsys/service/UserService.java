package me.persevere.project.socialsys.service;

import me.persevere.project.socialsys.domain.UserMsg;

import java.util.List;

public interface UserService {

    UserMsg getUserById(Long id);

    UserMsg getUserByUsername(String username);

    List<UserMsg> getUserList(UserMsg user);
}
