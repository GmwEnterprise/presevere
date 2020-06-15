package com.github.mrag.production;

import com.github.mrag.modules.api.ProductionService;
import com.github.mrag.modules.api.UserService;
import com.github.mrag.modules.domain.Production;
import com.github.mrag.modules.domain.User;
import com.github.mrag.rpc.annotation.RpcConsumer;
import com.github.mrag.rpc.annotation.RpcProvider;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@RpcProvider
public class ProductionServiceImpl implements ProductionService {
    private static final AtomicInteger idCounter = new AtomicInteger();
    private static final Map<Integer, Production> repositories = new ConcurrentHashMap<>();

    @RpcConsumer
    UserService userService;

    @Override
    public User owner(Integer productionId) {
        Production production = findById(productionId);
        return userService.findById(production.getOwnerId());
    }

    @Override
    public void addProduction(Production production) {
        int id = idCounter.incrementAndGet();
        repositories.putIfAbsent(id, production.setId(id));
    }

    @Override
    public Production findById(Integer productionId) {
        return repositories.get(productionId);
    }

    @Override
    public List<Production> findAll() {
        return Lists.newArrayList(repositories.values());
    }
}
