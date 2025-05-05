package com.example.distcomp.kafka;

import com.example.distcomp.dto.MessageResponseTo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class KafkaConsumer {

    @KafkaListener(topics = "OutTopic", groupId = "publisher-group")
    public void consumeResponse(MessageResponseTo response) {
        // здесь можно обновить статус или отправить через WebSocket
    }
}
