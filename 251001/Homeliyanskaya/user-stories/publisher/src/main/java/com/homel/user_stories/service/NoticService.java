package com.homel.user_stories.service;

import com.homel.user_stories.dto.NoticeMethodRequestTo;
import com.homel.user_stories.dto.NoticeRequestTo;
import com.homel.user_stories.dto.NoticeResponseTo;
import com.homel.user_stories.dto.StoryRequestTo;
import com.homel.user_stories.dto.StoryResponseTo;
import com.homel.user_stories.exception.EntityNotFoundException;
import com.homel.user_stories.kafka.NoticeConsumer;
import com.homel.user_stories.kafka.NoticeProducer;
import com.homel.user_stories.kafka.NoticeRequestProducer;
import com.homel.user_stories.mapper.NoticeMapper;
import com.homel.user_stories.model.Notice;
import com.homel.user_stories.model.Story;
import com.homel.user_stories.repository.NoticeRepository;
import com.homel.user_stories.repository.StoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class NoticService {

    private final NoticeProducer noticeProducer;
    private final NoticeRequestProducer noticeRequestProducer;
    private final NoticeConsumer noticeConsumer;

    public NoticService(NoticeProducer noticeProducer, NoticeRequestProducer noticeRequestProducer, NoticeConsumer noticeConsumer) {
        this.noticeProducer = noticeProducer;
        this.noticeRequestProducer = noticeRequestProducer;
        this.noticeConsumer = noticeConsumer;
    }

    private final String BASE_URL = "http://localhost:24130/api/v1.0/notices";

    public NoticeResponseTo createNotice(NoticeRequestTo noticeRequest) {
        // Генерируем ID и устанавливаем статус PENDING перед отправкой
        noticeRequest.setId(generateUniqueId());
        noticeRequest.setState("PENDING");
        noticeProducer.sendNotice(noticeRequest);

        // Ожидаем ответа из Kafka
        return noticeConsumer.getResponse();
    }

    public NoticeResponseTo getNotice(Long id) {
        NoticeMethodRequestTo request = new NoticeMethodRequestTo("GET", id, null, null, null);
        noticeRequestProducer.sendRequest(request);
        return noticeConsumer.getResponse();
    }

    public List<NoticeResponseTo> getAllNotices() {
        NoticeMethodRequestTo request = new NoticeMethodRequestTo("GET_ALL", null, null, null, null);
        noticeRequestProducer.sendRequest(request);
        return noticeConsumer.getResponseList();
    }

    public void deleteNotice(Long id) {
        NoticeMethodRequestTo request = new NoticeMethodRequestTo("DELETE", id, null, null, null);
        noticeRequestProducer.sendRequest(request);
        noticeConsumer.getResponse(); // Ожидаем подтверждение удаления
    }

    public NoticeResponseTo updateNotice(NoticeRequestTo noticeRequest) {
        NoticeMethodRequestTo request = new NoticeMethodRequestTo("PUT", noticeRequest.getId(), noticeRequest.getState(), noticeRequest.getStoryId(), noticeRequest.getContent());
        noticeRequestProducer.sendRequest(request);
        return noticeConsumer.getResponse();
    }

    private Long generateUniqueId() {
        return System.currentTimeMillis();
    }
}

