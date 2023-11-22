package ChatManagement.kafka.service;

import ChatManagement.kafka.domain.KafkaMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaService {

    @KafkaListener(topics = "chatlog")
    public void test( KafkaMessage kafkaMessage){
        log.info(String.valueOf(kafkaMessage));
    }
}
