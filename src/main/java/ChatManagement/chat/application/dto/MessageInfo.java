package ChatManagement.chat.application.dto;

import ChatManagement.chat.domain.Message;
import ChatManagement.chat.domain.status.ChatMessageType;
import java.time.LocalDateTime;

public record MessageInfo(
        Long chatMessageId,
        Long senderId,
        Long receiverId,
        String message,
        ChatMessageType chatMessageType,
        Long roomId,
        LocalDateTime sendTime
) {
    public static MessageInfo from(Message message) {
        return new MessageInfo(
                message.getChatMessageId(),
                message.getSenderId(),
                message.getReceiverId(),
                message.getMessage(),
                message.getChatMessageType(),
                message.getRoomId(),
                message.getSendTime()
        );
    }
}
