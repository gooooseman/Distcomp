package com.example.distcomp.repository;

import com.example.distcomp.model.Label;
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
public class InMemoryLabelRepository implements LabelRepository {
    private final Map<Long, Label> labels = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(0);

    @Override
    public Label save(Label label) {
        if (label.getId() == -1) {
            label.setId(idGenerator.getAndIncrement());
        }
        labels.put(label.getId(), label);
        return label;
    }

    @Override
    public Optional<Label> findById(Long id) {
        return Optional.ofNullable(labels.get(id));
    }

    @Override
    public List<Label> findAll() {
        return new ArrayList<>(labels.values());
    }

    @Override
    public List<Label> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public void deleteById(Long id) {
        labels.remove(id);
    }

    @Override
    public boolean existsById(Long id) {
        return labels.containsKey(id);
    }

    @Override
    public void delete(Label entity) {
        labels.remove(entity.getId());
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Label> entities) {

    }

    @Override
    public void deleteAll() {
        labels.clear();
    }

    @Override
    public <S extends Label> List<S> saveAll(Iterable<S> entities) {
        List<S> result = new ArrayList<>();
        entities.forEach(e -> result.add((S) save(e)));
        return result;
    }

    @Override
    public long count() {
        return labels.size();
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Label> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Label> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Label> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Label getOne(Long aLong) {
        return null;
    }

    @Override
    public Label getById(Long aLong) {
        return null;
    }

    @Override
    public Label getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Label> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Label> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Label> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Label> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Label> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Label> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Label, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public List<Label> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Label> findAll(Pageable pageable) {
        return null;
    }
}
