package com.example.restservice.controller;

import com.example.restservice.dto.MessageCreateDto;
import com.example.restservice.dto.MessageResponseDto;
import com.example.restservice.mapper.MessageMapper;
import com.example.restservice.model.Message;
import com.example.restservice.service.MessageService;
import com.example.restservice.service.NewsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1.0")
public class MessageController {

    private final MessageService messageService;
    private final MessageMapper messageMapper;
    private final NewsService newsService;

    @Autowired
    public MessageController(MessageService messageService, MessageMapper messageMapper, NewsService newsService) {
        this.messageService = messageService;
        this.messageMapper = messageMapper;
        this.newsService = newsService;
    }


    @GetMapping("/messages")
    @ResponseStatus(HttpStatus.OK)
    public List<MessageResponseDto> getMessages() {
        return messageService.findAll().stream()
                .map(messageMapper::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/messages")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MessageResponseDto> createMessage(@RequestBody @Valid MessageCreateDto inputDto) {
        Message entity = messageMapper.toEntity(inputDto, newsService.findById(inputDto.getNewsId()));
        /*if (authorRepository.existsByLogin(entity.getLogin())) {
            return new ResponseEntity<>(authorMapper.toDto(entity), HttpStatus.FORBIDDEN);
        }*/
        Message saved = messageService.save(entity);
        return new ResponseEntity<>(messageMapper.toDto(saved), HttpStatus.CREATED);
    }

    @GetMapping("/messages/{id}")
    public MessageResponseDto getMessageById(@PathVariable Long id) {
        return messageMapper.toDto(messageService.findById(id));
    }

    @PutMapping("/messages")
    public ResponseEntity<MessageResponseDto> updateMessage(@RequestBody @Valid MessageCreateDto in) {
        Message message = messageService.findById(in.getId());
        if (message == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            Message newMessage = messageMapper.toEntity(in, newsService.findById(in.getNewsId()));
            Message updated = messageService.update(newMessage);
            return ResponseEntity.ok(messageMapper.toDto(updated));
        }
    }

    @DeleteMapping("/messages/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
        if (messageService.findById(id) != null) {
            messageService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
