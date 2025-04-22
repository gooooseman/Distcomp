package com.example.distcomp.repository;

import com.example.distcomp.model.Message;
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
public class InMemoryMessageRepository implements MessageRepository {
    private final Map<Long, Message> messages = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(0);

    @Override
    public Message save(Message message) {
        if (message.getId() == -1) {
            message.setId(idGenerator.getAndIncrement());
        }
        messages.put(message.getId(), message);
        return message;
    }

    @Override
    public Optional<Message> findById(Long id) {
        return Optional.ofNullable(messages.get(id));
    }

    @Override
    public List<Message> findAll() {
        return new ArrayList<>(messages.values());
    }

    @Override
    public List<Message> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public void deleteById(Long id) {
        messages.remove(id);
    }

    @Override
    public boolean existsById(Long id) {
        return messages.containsKey(id);
    }

    @Override
    public void delete(Message entity) {
        messages.remove(entity.getId());
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Message> entities) {

    }

    @Override
    public void deleteAll() {
        messages.clear();
    }

    @Override
    public <S extends Message> List<S> saveAll(Iterable<S> entities) {
        List<S> result = new ArrayList<>();
        entities.forEach(e -> result.add((S) save(e)));
        return result;
    }

    @Override
    public long count() {
        return messages.size();
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Message> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Message> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Message> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Message getOne(Long aLong) {
        return null;
    }

    @Override
    public Message getById(Long aLong) {
        return null;
    }

    @Override
    public Message getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Message> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Message> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Message> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Message> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Message> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Message> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Message, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public List<Message> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Message> findAll(Pageable pageable) {
        return null;
    }
}
