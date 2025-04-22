package com.example.distcomp.service;

import com.example.distcomp.dto.MessageRequestTo;
import com.example.distcomp.dto.MessageResponseTo;
import com.example.distcomp.exception.ServiceException;
import com.example.distcomp.mapper.MessageMapper;
import com.example.distcomp.model.Message;
import com.example.distcomp.repository.MessageRepository;
import com.example.distcomp.utils.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;

    public MessageResponseTo createMessage(MessageRequestTo request) {
        validateMessageRequest(request);
        return messageMapper.toResponse(messageRepository.save(messageMapper.toEntity(request)));
    }

    public List<MessageResponseTo> getAllMessages() {
        return messageMapper.listToResponse(messageRepository.findAll());
    }

    public MessageResponseTo getMessageById(Long id) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new ServiceException("Message not found with id: " + id, 404));
        return messageMapper.toResponse(message);
    }

    public MessageResponseTo updateMessage(MessageRequestTo request) {
        validateMessageRequest(request);
        Message entity = messageMapper.toEntity(request);
        if (!messageRepository.existsById(entity.getId())) {
            throw new ServiceException("Message not found with id: " + entity.getId(), 404);
        }
        return messageMapper.toResponse(messageRepository.save(entity));
    }

    public void deleteMessage(Long id) {
        if (!messageRepository.existsById(id)) {
            throw new ServiceException("Message not found with id: " + id, 404);
        }
        messageRepository.deleteById(id);
    }

    private void validateMessageRequest(MessageRequestTo request) {
        ValidationUtils.validateNotNull(request, "Message");
        ValidationUtils.validateString(request.getContent(), "Text", 2, 2048);
        ValidationUtils.validateNotNegative(request.getIssueId(), "Issue ID");
    }
}
