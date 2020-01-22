package cn.presevere.blog.dao.impl;

import cn.presevere.blog.dao.BnsPostDao;
import cn.presevere.blog.entity.BnsPost;
import cn.presevere.blog.repository.BnsPostRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BnsPostDaoImpl implements BnsPostDao {
    private final BnsPostRepository repository;

    public BnsPostDaoImpl(BnsPostRepository repository) {this.repository = repository;}

    @Override
    public List<BnsPost> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<BnsPost> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<BnsPost> findAllById(Iterable<Long> ids) {
        return repository.findAllById(ids);
    }

    @Override
    public <S extends BnsPost> S save(S entity) {
        return repository.save(entity);
    }

    @Override
    public <S extends BnsPost> List<S> saveAll(Iterable<S> entities) {
        return repository.saveAll(entities);
    }

    @Override
    public void delete(BnsPost entity) {
        repository.delete(entity);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }
}
