package ChatManagement.kafka.service;

import ChatManagement.chat.dao.ChatMessage;
import ChatManagement.chat.service.ChatMessageService;
import ChatManagement.kafka.domain.KafkaMessage;
import ChatManagement.kafka.domain.LogMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service @Slf4j
@RequiredArgsConstructor
public class KafkaService {
    private final KafkaTemplate<Object, Object> kafkaTemplate;

    public void sendMessage(LogMessage logMessage){
        log.info("Producer log message : " + logMessage);
        this.kafkaTemplate.send("log", logMessage);
    }

}
