package ChatManagement.chat.dao;

import ChatManagement.global.status.ChatMessageType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity @Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatMessageId;

    private Long senderId;
    private Long receiverId;
    private String message;


    @ManyToOne
    @JoinColumn(name = "roomId")
    private ChatRoom chatRoom;

    private LocalDateTime sendTime;

    @Enumerated(EnumType.STRING)
    private ChatMessageType chatMessageType;

    public void activateMessage() {
        this.sendTime = LocalDateTime.now();
    }
    public void setChatRoom(ChatRoom chatRoom) {
        if(this.chatRoom == null){
            this.chatRoom = chatRoom;
        }
    }
    @Override
    public String toString() {
        return "ChatMessage{" +
                "chatMessageId=" + chatMessageId +
                ", senderId=" + senderId +
                ", receiverId=" + receiverId +
                ", message='" + message + '\'' +
                ", chatRoom=" + chatRoom.getRoomId() +
                ", sendTime=" + sendTime +
                '}';
    }
}
