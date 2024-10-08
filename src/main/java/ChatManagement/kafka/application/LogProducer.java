package ChatManagement.kafka.application;

import ChatManagement.kafka.application.dto.LogMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class LogProducer {
    private final KafkaTemplate<Object, Object> kafkaTemplate;

    public void sendMessage(LogMessage logMessage) {
        log.info("Producer log message : " + logMessage);
        this.kafkaTemplate.send("log", logMessage);
    }

}
