package com.example.distcomp_2.controller;

import com.example.distcomp_2.dto.MessageCreateDto;
import com.example.distcomp_2.dto.MessageResponseDto;
import com.example.distcomp_2.mapper.MessageMapper;
import com.example.distcomp_2.model.Message;
import com.example.distcomp_2.repository.MessageRepository;
import com.example.distcomp_2.service.MessageService;
import com.example.distcomp_2.service.NewsService;
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

    private final MessageMapper messageMapper;
    private final MessageRepository messageRepository;
    private final MessageService messageService;
    private final NewsService newsService;

    @Autowired
    public MessageController(MessageMapper messageMapper, MessageRepository messageRepository, MessageService messageService, NewsService newsService) {
        this.messageMapper = messageMapper;
        this.messageRepository = messageRepository;
        this.messageService = messageService;
        this.newsService = newsService;
    }
    @GetMapping("/messages")
    @ResponseStatus(HttpStatus.OK)
    public List<MessageResponseDto> getMarkers() {
        return messageService.findAll().stream().map(messageMapper::toDto).collect(Collectors.toList());
    }

    @PostMapping("/messages")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MessageResponseDto> createMarker(@RequestBody @Valid MessageCreateDto inputDto) {
        Message entity = messageMapper.toEntity(inputDto, newsService.findById(inputDto.getNewsId()));
        if (newsService.findById(inputDto.getNewsId()) == null) {
            return new ResponseEntity<>(messageMapper.toDto(entity), HttpStatus.BAD_REQUEST);
        }
        Message saved = messageService.save(entity);
        return new ResponseEntity<>(messageMapper.toDto(saved), HttpStatus.CREATED);
    }

    @GetMapping("/messages/{id}")
    public MessageResponseDto getMarkerById(@PathVariable Long id) {
        return messageMapper.toDto(messageService.findById(id));
    }

    @PutMapping("/messages")
    public ResponseEntity<MessageResponseDto> updateMarker(@RequestBody @Valid MessageCreateDto in) {
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
    public ResponseEntity<Void> deleteMarker(@PathVariable Long id) {
        if (messageService.findById(id) != null) {
            messageService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
