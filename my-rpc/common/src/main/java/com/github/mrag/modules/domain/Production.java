package com.github.mrag.modules.domain;

import java.time.LocalDateTime;

public class Production {
    private Integer id;
    private String productionName;
    private LocalDateTime productionCreatedTime;
    private Integer ownerId;

    public Integer getId() {
        return id;
    }

    public Production setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getProductionName() {
        return productionName;
    }

    public Production setProductionName(String productionName) {
        this.productionName = productionName;
        return this;
    }

    public LocalDateTime getProductionCreatedTime() {
        return productionCreatedTime;
    }

    public Production setProductionCreatedTime(LocalDateTime productionCreatedTime) {
        this.productionCreatedTime = productionCreatedTime;
        return this;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public Production setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
        return this;
    }
}
