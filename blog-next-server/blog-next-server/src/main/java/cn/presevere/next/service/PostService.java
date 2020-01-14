package cn.presevere.next.service;

import cn.presevere.next.domain.Post;
import cn.presevere.next.dto.PostDTO;

import java.util.List;

public interface PostService {
    /**
     * 构建Post实例
     */
    Post createPost(PostDTO postDTO);
    /**
     * 通过主键获取单个Post实例
     */
    Post findById(Long id);
    /**
     * 获取列表
     */
    List<Post> findAll();
}
