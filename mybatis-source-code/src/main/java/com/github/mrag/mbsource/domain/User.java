package com.github.mrag.mbsource.domain;

public class User {
    private Integer userId;
    private Integer username;

    public Integer getUserId() {
        return userId;
    }

    public User setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public Integer getUsername() {
        return username;
    }

    public User setUsername(Integer username) {
        this.username = username;
        return this;
    }

    @Override
    public String toString() {
        return String.format("User{userId=%d, username=%d}", userId, username);
    }
}
