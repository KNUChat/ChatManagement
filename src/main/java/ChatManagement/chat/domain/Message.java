package ChatManagement.chat.domain;

import ChatManagement.chat.domain.status.ChatMessageType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatMessageId;

    private Long senderId;

    private Long receiverId;

    private String message;

    private LocalDateTime sendTime;

    @Enumerated(EnumType.STRING)
    private ChatMessageType chatMessageType;

    private Long roomId;

    private Message(Long senderId, Long receiverId, String message, Long roomId) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
        this.roomId = roomId;
        this.sendTime = LocalDateTime.now();
        this.chatMessageType = ChatMessageType.TEXT;
    }

    private Message(Long senderId, Long receiverId, String message, Long roomId, LocalDateTime sendTime,
                    ChatMessageType chatMessageType) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
        this.roomId = roomId;
        this.sendTime = sendTime;
        this.chatMessageType = chatMessageType;
    }

    public static Message of(Long senderId, Long receiverId, String message, Long roomId) {
        return new Message(senderId, receiverId, message, roomId);
    }

    public static Message of(Long roomId, Long senderId, Long receiverId, String message, LocalDateTime sendTime,
                             ChatMessageType chatMessageType) {
        return new Message(senderId, receiverId, message, roomId, sendTime, chatMessageType);
    }
}
