package ChatManagement.chat.domain;

import ChatManagement.chat.domain.status.RoomStatus;
import ChatManagement.global.execption.NotAllowChangeRoomStatusException;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    private Long mentorId;
    private Long menteeId;

    @Enumerated(EnumType.STRING)
    private RoomStatus roomStatus;

    private Room(Long mentorId, Long menteeId) {
        this.mentorId = mentorId;
        this.menteeId = menteeId;
        this.roomStatus = RoomStatus.CHAT_WAITING;
    }

    public static Room of(Long mentorId, Long menteeId) {
        return new Room(mentorId, menteeId);
    }

    public void activateRoom() {
        if (this.roomStatus.equals(RoomStatus.CHAT_WAITING)) {
            this.roomStatus = RoomStatus.CHAT_PROCEEDING;
        }
    }

    public void endRoom() {
        if (this.roomStatus.equals(RoomStatus.CHAT_PROCEEDING)) {
            this.roomStatus = RoomStatus.CHAT_ENDED;
            return;
        }
        throw new NotAllowChangeRoomStatusException("[Status ERROR] 채팅방을 종료할 수 없습니다", HttpStatus.NOT_ACCEPTABLE);
    }

    public void deleteRoom() {
        if (this.roomStatus.equals(RoomStatus.CHAT_ENDED)
                || this.roomStatus.equals(RoomStatus.CHAT_WAITING)) {
            this.roomStatus = RoomStatus.CHAT_DELETED;
            return;
        }

        throw new NotAllowChangeRoomStatusException("[Status ERROR] 채팅방을 삭제할 수 없습니다", HttpStatus.NOT_ACCEPTABLE);
    }

    public void blockRoom() {
        this.roomStatus = RoomStatus.CHAT_BLOCKED;
    }
}
