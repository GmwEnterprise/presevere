package cn.presevere.blog.dao;

import cn.presevere.blog.entity.BnsPost;

import java.util.List;
import java.util.Optional;

public interface BnsPostDao {

    List<BnsPost> findAll();

    Optional<BnsPost> findById(Long id);

    List<BnsPost> findAllById(Iterable<Long> longs);

    <S extends BnsPost> S save(S entity);

    <S extends BnsPost> List<S> saveAll(Iterable<S> entities);

    void delete(BnsPost entity);

    void deleteAll();
}
