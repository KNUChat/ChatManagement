package ChatManagement.chat.dao;

import ChatManagement.global.execption.NotAllowChangeRoomStatusException;
import ChatManagement.global.status.RoomStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;

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

    public void endRoom() {
        if(this.roomStatus.equals(RoomStatus.CHAT_PROCEEDING)){
            this.roomStatus = RoomStatus.CHAT_ENDED;
        }
        throw new NotAllowChangeRoomStatusException("[Status ERROR] 채팅방을 종료할 수 없습니다", HttpStatus.NOT_ACCEPTABLE);
    }

    public void deleteRoom() {
        if(this.roomStatus.equals(RoomStatus.CHAT_ENDED)){
            this.roomStatus = RoomStatus.CHAT_DELETED;
        }

        throw new NotAllowChangeRoomStatusException("[Status ERROR] 채팅방을 삭제할 수 없습니다", HttpStatus.NOT_ACCEPTABLE);
    }

    public void blockRoom(){
        this.roomStatus = RoomStatus.CHAT_BLOCKED;
    }


    public void initChatMessage(ChatMessage chatMessage) {
        if (this.chatMessages == null) {
            this.chatMessages = new ArrayList<>();
        }
        chatMessages.add(chatMessage);
        chatMessage.setChatRoom(this);
    }

}
