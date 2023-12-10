package ChatManagement.chat.response;


import ChatManagement.chat.dao.ChatMessage;
import ChatManagement.global.status.ChatMessageType;
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

    public static ChatMessageResponse from(ChatMessage chatMessage){
        return ChatMessageResponse.builder()
                .message(chatMessage.getMessage())
                .senderId(chatMessage.getSenderId())
                .receiverId(chatMessage.getReceiverId())
                .chatMessageType(chatMessage.getChatMessageType())
                .sendTime(chatMessage.getSendTime())
                .build();
    }
}
