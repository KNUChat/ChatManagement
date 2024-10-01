package ChatManagement.chat.application.command;

import ChatManagement.chat.domain.Message;
import ChatManagement.chat.domain.status.ChatMessageType;
import java.time.LocalDateTime;

public record CreateMessageCommand(
        Long roomId,
        Long senderId,
        Long receiverId,
        String message,
        LocalDateTime sendTime,
        ChatMessageType chatMessageType
) {
    public Message toEntity() {
        return Message.of(roomId, senderId, receiverId, message, sendTime, chatMessageType);
    }
}
