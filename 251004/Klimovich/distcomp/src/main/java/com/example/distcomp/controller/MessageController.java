package com.example.distcomp.controller;

import com.example.distcomp.dto.MessageRequestTo;
import com.example.distcomp.dto.MessageResponseTo;
import com.example.distcomp.mapper.MessageMapper;
import com.example.distcomp.model.Message;
import com.example.distcomp.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;
    @Autowired
    private RestTemplate restTemplate;

    @PostMapping
    public ResponseEntity<MessageResponseTo> createMessage(@RequestBody MessageRequestTo request) {
        MessageResponseTo response = messageService.createMessage(request);
        String url = "http://localhost:24130/api/v1.0/messages";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        request.setId(response.getId());
        HttpEntity<MessageRequestTo> entity = new HttpEntity<>(request, headers);

        ResponseEntity<MessageResponseTo> resp = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                MessageResponseTo.class
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<MessageResponseTo>> getAllMessages() {
        return ResponseEntity.ok(messageService.getAllMessages());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageResponseTo> getMessageById(@PathVariable Long id) {
        return ResponseEntity.ok(messageService.getMessageById(id));
    }

    @PutMapping
    public ResponseEntity<MessageResponseTo> updateMessage(@RequestBody MessageRequestTo request) {
        String url = "http://localhost:24130/api/v1.0/messages";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<MessageRequestTo> entity = new HttpEntity<>(request, headers);

        ResponseEntity<MessageResponseTo> response = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                entity,
                MessageResponseTo.class
        );
        return ResponseEntity.ok(messageService.updateMessage(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
        messageService.deleteMessage(id);
        return ResponseEntity.noContent().build();
    }
}
