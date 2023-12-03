package ChatManagement.chat.dao;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity @Getter
@Builder @ToString
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    private Long mentorId;
    private Long menteeId;

    @Enumerated(EnumType.STRING)
    private RoomStatus roomStatus;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "chatRoom")
    private List<ChatMessage> chatMessages;

    public void activateRoom(){
        if(this.roomStatus.equals(RoomStatus.CHAT_WAITING)){
            this.roomStatus = RoomStatus.CHAT_PROCEEDING;
        }
    }

    public void initChatMessage(ChatMessage chatMessage) {
        if (this.chatMessages == null) {
            this.chatMessages = new ArrayList<>();
        }
        chatMessages.add(chatMessage);
        chatMessage.setChatRoom(this);
    }
}
