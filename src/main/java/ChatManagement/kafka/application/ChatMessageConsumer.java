package ChatManagement.kafka.application;

import ChatManagement.chat.application.ChatRoomService;
import ChatManagement.kafka.application.dto.ChatMessage;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatMessageConsumer {
    private final ChatRoomService chatRoomService;

    @KafkaListener(
            topics = "chatlog",
            containerFactory = "batchContainerFactory",
            errorHandler = "kafkaListenerErrorHandler"
    )
    public void saveKafkaMessage(
            List<ChatMessage> messages,
            Acknowledgment ack
    ) {
        try {
            var commands = messages.stream()
                    .map(ChatMessage::toCommand)
                    .toList();
            chatRoomService.sendMessages(commands);
            ack.acknowledge();
        } catch (Exception e) {
            log.error("Failed to save kafka message", e);
        }
    }
}
