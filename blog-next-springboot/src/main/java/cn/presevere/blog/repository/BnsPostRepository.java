package cn.presevere.blog.repository;

import cn.presevere.blog.entity.BnsPost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BnsPostRepository extends JpaRepository<BnsPost, Long> {
    @Override
    List<BnsPost> findAll();

    @Override
    Page<BnsPost> findAll(Pageable pageable);

    @Override
    Optional<BnsPost> findById(Long id);

    @Override
    List<BnsPost> findAllById(Iterable<Long> longs);

    @Override
    <S extends BnsPost> S save(S entity);

    @Override
    <S extends BnsPost> List<S> saveAll(Iterable<S> entities);

    @Override
    void delete(BnsPost entity);
}
