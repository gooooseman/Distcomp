package com.example.distcomp.repository;

import com.example.distcomp.model.Writer;
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
public class InMemoryWriterRepository implements WriterRepository {
    private final Map<Long, Writer> writers = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(0);

    @Override
    public Writer save(Writer writer) {
        if (writer.getId() == -1) {
            writer.setId(idGenerator.getAndIncrement());
        }
        writers.put(writer.getId(), writer);
        return writer;
    }

    @Override
    public Optional<Writer> findById(Long id) {
        return Optional.ofNullable(writers.get(id));
    }

    @Override
    public List<Writer> findAll() {
        return new ArrayList<>(writers.values());
    }

    @Override
    public List<Writer> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public void deleteById(Long id) {
        writers.remove(id);
    }

    @Override
    public boolean existsById(Long id) {
        return writers.containsKey(id);
    }

    @Override
    public void delete(Writer entity) {
        writers.remove(entity.getId());
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Writer> entities) {

    }

    @Override
    public void deleteAll() {
        writers.clear();
    }

    @Override
    public <S extends Writer> List<S> saveAll(Iterable<S> entities) {
        List<S> result = new ArrayList<>();
        entities.forEach(e -> result.add((S) save(e)));
        return result;
    }

    @Override
    public long count() {
        return writers.size();
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Writer> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Writer> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Writer> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Writer getOne(Long aLong) {
        return null;
    }

    @Override
    public Writer getById(Long aLong) {
        return null;
    }

    @Override
    public Writer getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Writer> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Writer> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Writer> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Writer> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Writer> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Writer> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Writer, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public List<Writer> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Writer> findAll(Pageable pageable) {
        return null;
    }
}
