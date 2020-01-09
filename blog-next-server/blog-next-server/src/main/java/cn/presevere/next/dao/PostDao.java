package cn.presevere.next.dao;

import cn.presevere.next.domain.Post;
import org.apache.ibatis.annotations.Insert;

public interface PostDao {

    @Insert("")
    void insertOne(Post post);
}
