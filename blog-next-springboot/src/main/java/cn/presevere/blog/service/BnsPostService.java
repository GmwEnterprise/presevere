package cn.presevere.blog.service;

import cn.presevere.blog.common.Page;
import cn.presevere.blog.entity.BnsPost;

/**
 * 文章服务类
 */
public interface BnsPostService {

    /**
     * 保存文章
     */
    void save();

    /**
     * 文章列表
     */
    Page<BnsPost> findAll();
}
