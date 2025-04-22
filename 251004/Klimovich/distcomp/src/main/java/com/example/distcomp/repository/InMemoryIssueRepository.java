package com.example.distcomp.repository;

import com.example.distcomp.model.Issue;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

@Repository
@Primary
public class InMemoryIssueRepository implements IssueRepository {
    private final Map<Long, Issue> issues = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(0);

    @Override
    public Issue save(Issue issue) {
        if (issue.getId() == -1) {
            issue.setId(idGenerator.getAndIncrement());
        }
        issues.put(issue.getId(), issue);
        return issue;
    }

    @Override
    public Optional<Issue> findById(Long id) {
        return Optional.ofNullable(issues.get(id));
    }

    @Override
    public List<Issue> findAll() {
        return new ArrayList<>(issues.values());
    }

    @Override
    public List<Issue> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public void deleteById(Long id) {
        issues.remove(id);
    }

    @Override
    public boolean existsById(Long id) {
        return issues.containsKey(id);
    }

    @Override
    public void delete(Issue entity) {
        issues.remove(entity.getId());
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Issue> entities) {

    }

    @Override
    public void deleteAll() {
        issues.clear();
    }

    @Override
    public <S extends Issue> List<S> saveAll(Iterable<S> entities) {
        List<S> result = new ArrayList<>();
        entities.forEach(e -> result.add((S) save(e)));
        return result;
    }

    @Override
    public long count() {
        return issues.size();
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Issue> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Issue> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Issue> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Issue getOne(Long aLong) {
        return null;
    }

    @Override
    public Issue getById(Long aLong) {
        return null;
    }

    @Override
    public Issue getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Issue> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Issue> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Issue> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Issue> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Issue> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Issue> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Issue, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public List<Issue> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Issue> findAll(Pageable pageable) {
        return null;
    }
}
