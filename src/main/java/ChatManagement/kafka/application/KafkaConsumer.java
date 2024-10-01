package ChatManagement.kafka.application;

import ChatManagement.chat.application.ChatRoomService;
import ChatManagement.kafka.application.dto.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumer {
    private final ChatRoomService chatRoomService;

    @KafkaListener(topics = "chatlog", errorHandler = "kafkaListenerErrorHandler")
    public void saveKafkaMessage(ChatMessage chatMessage) {
        var command = chatMessage.toCommand();
        chatRoomService.sendMessage(command);
    }
}
