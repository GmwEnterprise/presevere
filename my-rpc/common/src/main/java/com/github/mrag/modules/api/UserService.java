package com.github.mrag.modules.api;

import com.github.mrag.modules.domain.User;

import java.util.List;

public interface UserService {

    void addUser(User user);

    User findById(Integer userId);

    List<User> findAll();
}
