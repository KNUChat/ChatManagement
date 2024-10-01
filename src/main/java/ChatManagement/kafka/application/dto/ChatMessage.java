package ChatManagement.kafka.application.dto;

import ChatManagement.chat.application.command.CreateMessageCommand;
import ChatManagement.chat.domain.status.ChatMessageType;
import java.time.LocalDateTime;

public record ChatMessage(
        Long roomId,
        Long senderId,
        Long receiverId,
        String message,
        LocalDateTime sendTime,
        ChatMessageType chatMessageType
) {
    public CreateMessageCommand toCommand() {
        return new CreateMessageCommand(roomId, senderId, receiverId, message, sendTime, chatMessageType);
    }
}
