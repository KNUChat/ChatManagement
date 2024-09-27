package ChatManagement.chat.presentation.dto;


import ChatManagement.chat.domain.Message;
import ChatManagement.chat.domain.status.ChatMessageType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder @Getter
public class ChatMessageResponse {

    private Long senderId;
    private Long receiverId;
    private String message;
    private ChatMessageType chatMessageType;
    private LocalDateTime sendTime;

    public static ChatMessageResponse from(Message message){
        return ChatMessageResponse.builder()
                .message(message.getMessage())
                .senderId(message.getSenderId())
                .receiverId(message.getReceiverId())
                .chatMessageType(message.getChatMessageType())
                .sendTime(message.getSendTime())
                .build();
    }
}
