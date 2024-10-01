package ChatManagement.chat.presentation.dto;

import ChatManagement.chat.application.dto.MessageInfo;
import ChatManagement.chat.domain.status.ChatMessageType;
import java.time.LocalDateTime;

public record ChatMessageResponse(
        Long senderId,
        Long receiverId,
        String message,
        ChatMessageType chatMessageType,
        LocalDateTime sendTime
) {
    public static ChatMessageResponse from(MessageInfo message) {
        return new ChatMessageResponse(
                message.senderId(),
                message.receiverId(),
                message.message(),
                message.chatMessageType(),
                message.sendTime()
        );
    }
}
