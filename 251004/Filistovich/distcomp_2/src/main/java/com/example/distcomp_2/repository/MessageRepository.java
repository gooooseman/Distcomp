package com.example.distcomp_2.repository;

import com.example.distcomp_2.model.Message;
import jakarta.validation.constraints.Positive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    //List<Message> getAll();
    Message getMessageById(@Positive Long id);
    void deleteMessageById(@Positive Long id);
}
