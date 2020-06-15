package com.github.mrag.user;

import com.github.mrag.modules.api.UserService;
import com.github.mrag.modules.domain.User;
import com.github.mrag.rpc.annotation.RpcProvider;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@RpcProvider
public class UserServiceImpl implements UserService {
    private static final AtomicInteger idCounter = new AtomicInteger();
    private static final Map<Integer, User> repositories = new ConcurrentHashMap<>();

    @Override
    public void addUser(User user) {
        int id = idCounter.incrementAndGet();
        repositories.putIfAbsent(id, user.setId(id));
    }

    @Override
    public User findById(Integer userId) {
        return repositories.get(userId);
    }

    @Override
    public List<User> findAll() {
        return Lists.newArrayList(repositories.values());
    }
}
