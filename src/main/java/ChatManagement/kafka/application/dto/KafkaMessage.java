package ChatManagement.kafka.application.dto;

import ChatManagement.chat.domain.status.ChatMessageType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class KafkaMessage {
    private Long roomId;
    private Long senderId;
    private Long receiverId;
    private String message;
    private LocalDateTime sendTime;
    private ChatMessageType chatMessageType;
}
