package com.example.distcomp_2.service;

import com.example.distcomp_2.model.Message;
import com.example.distcomp_2.repository.MessageRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    private final MessageRepository repository;

    @Autowired
    public MessageService(MessageRepository repository) {
        this.repository = repository;
    }

    public List<Message> findAll() {
        return repository.findAll();
    }

    public Message findById(Long id) {
        return repository.getMessageById(id);
    }

    public Message save(Message message) {
        return repository.save(message);
    }

    @Transactional
    public void deleteById(Long id) {
        repository.deleteMessageById(id);
    }

    public Message update(Message message) {
        return repository.save(message);
    }

    /*public boolean existsByLogin(String login) {
        return repository.existsByLogin(login);
    }*/
}
