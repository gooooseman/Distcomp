package com.homel.user_stories.service;

import com.homel.user_stories.dto.NoticeRequestTo;
import com.homel.user_stories.dto.NoticeResponseTo;
import com.homel.user_stories.dto.StoryRequestTo;
import com.homel.user_stories.dto.StoryResponseTo;
import com.homel.user_stories.exception.EntityNotFoundException;
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
public class NoticeService {

    private final RestTemplate restTemplate;

    @Autowired
    public NoticeService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private final String BASE_URL = "http://localhost:24130/api/v1.0/notices";

    public NoticeResponseTo createNotice(NoticeRequestTo noticeRequest) {
        String url = BASE_URL; // URL для создания записи
        return restTemplate.postForObject(url, noticeRequest, NoticeResponseTo.class);
    }

    public NoticeResponseTo getNotice(Long id) {
        String url = BASE_URL + "/" + id; // URL для получения записи по ID
        return restTemplate.getForObject(url, NoticeResponseTo.class);
    }

    public List<NoticeResponseTo> getAllNotices() {
        String url = BASE_URL; // URL для получения всех записей
        NoticeResponseTo[] noticesArray = restTemplate.getForObject(url, NoticeResponseTo[].class);
        return noticesArray != null ? Arrays.asList(noticesArray) : Collections.emptyList();
    }

    public void deleteNotice(Long id) {
        String url = BASE_URL + "/" + id; // URL для удаления записи по ID
        restTemplate.delete(url);
    }

    public NoticeResponseTo updateNotice(NoticeRequestTo noticeRequest) {
        String url = BASE_URL;
        ResponseEntity<NoticeResponseTo> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                new HttpEntity<>(noticeRequest),
                NoticeResponseTo.class
        );

        // Получаем ответ в виде объекта NoticeResponseTo
        NoticeResponseTo noticeResponse = responseEntity.getBody();

        // Возвращаем полученный ответ
        return noticeResponse;
    }
}
