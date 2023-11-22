package ChatManagement.service;

import ChatManagement.domain.ChatMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaService {

    @KafkaListener(topics = "chatlog")
    public void test( ChatMessage chatMessage){
        log.info(String.valueOf(chatMessage));
    }
}
