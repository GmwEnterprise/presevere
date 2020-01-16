package cn.presevere.next.repo;

import cn.presevere.next.domain.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PostRepository extends CrudRepository<Post, Long> {
    @Override
    <S extends Post> S save(S s);

    @Override
    Optional<Post> findById(Long aLong);

    @Override
    Iterable<Post> findAllById(Iterable<Long> iterable);

    Optional<Post> findAllByTitle(String title);
}
