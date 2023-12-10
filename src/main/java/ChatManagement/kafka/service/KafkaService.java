package ChatManagement.kafka.service;

import ChatManagement.chat.service.ChatMessageService;
import ChatManagement.kafka.domain.KafkaMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service @Slf4j
@RequiredArgsConstructor
public class KafkaService {
    private final ChatMessageService chatMessageService;
    @KafkaListener(topics = "chatlog", errorHandler = "kafkaListenerErrorHandler")
    public void getKafkaMessage( KafkaMessage kafkaMessage){
        log.info("Kafka Message : " + kafkaMessage);

        chatMessageService.saveKafkaMessage(kafkaMessage);
    }
}
