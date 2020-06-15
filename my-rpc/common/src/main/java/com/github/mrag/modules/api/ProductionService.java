package com.github.mrag.modules.api;

import com.github.mrag.modules.domain.Production;
import com.github.mrag.modules.domain.User;

import java.util.List;

public interface ProductionService {

    User owner(Integer userId);

    void addProduction(Production production);

    Production findById(Integer productionId);

    List<Production> findAll();
}
