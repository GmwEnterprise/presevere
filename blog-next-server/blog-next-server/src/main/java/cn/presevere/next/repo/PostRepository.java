package cn.presevere.next.repo;

import cn.presevere.next.domain.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {
}
